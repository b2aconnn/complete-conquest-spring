package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    // Field 를 통한 Trace 상태 관리
//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    // ThreadLocal을 통한 Trace 상태 관리
    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
