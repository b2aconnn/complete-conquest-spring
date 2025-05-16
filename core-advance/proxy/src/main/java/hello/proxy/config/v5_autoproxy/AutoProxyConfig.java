package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {
    // Advisor 만 스프링 컨테이너에 빈으로 등록하면 스프링에서 미리 만들어 둔 빈후처리기에서 프록시 만들어서 Advisor를 넣어준다.
    // Proxy 객체랑 Advisor 객체는 같이 묶어서 가져감.
    // 빈후처리기 객체에서 advisor 에 있는 pointcut 을 보고, 프록시 객체를 만들 지 말 지 판단을 함.
    // 그래서 빈후처리기 객체에서 프록시 객체부터 일반 객체까지 다 알아서 만들어서 스프링 컨테이너에 빈 등록을 다 해줌
    // 그래서 프록시 객체를 만들고 싶으면, 사용자는 Advisor 객체를 등록하면 되고, 프록시 객체르 만들고 싶으면 pointcut에 설정하면 됨.

    // 이런 방법도 있지만, 스프링에서는 어노테이션 기반으로 더 간단하게 구현하는 방법을 제공해서 굳이 안 쓸 듯?
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        // advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
