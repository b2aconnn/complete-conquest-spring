package hello.proxy.pureproxy.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CacheProxy implements Subject {
    private Subject subject;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.subject = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출");
        if (cacheValue == null) {
            cacheValue = subject.operation();
        }
        return cacheValue;
    }
}
