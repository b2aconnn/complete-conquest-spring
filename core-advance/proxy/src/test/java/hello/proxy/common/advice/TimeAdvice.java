package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;

import java.lang.reflect.Method;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy call");

        long startTime = System.currentTimeMillis();

        // invocation (invoke의 동사): 부르다, 호출하다, proceed : 나아가다, (이미 시작된 일을 계속) 진행하다
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy end time resultTime = {}ms", resultTime);

        return result;
    }
}
