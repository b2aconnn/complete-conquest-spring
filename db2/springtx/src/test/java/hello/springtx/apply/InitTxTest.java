package hello.springtx.apply;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InitTxTest {
    @Autowired
    Hello hello;

    @Test
    void test() {
        // @PostConstruct 가 선언된 메소드를 실행시키는 목적

        // 직접 호출하면 트랜잭션이 적용이 된다.
        // 초기화가 다 완료된 다음에 spring bean 으로 다 등록이 된 다음 호출을 하는 거기 때문에
//        hello.initV1();
    }

    @TestConfiguration
    static class InitTxTestConfiguration {
        @Bean
        public Hello hello() {
            return new Hello();
        }
    }

    @Slf4j
    static class Hello {

        @PostConstruct
        @Transactional
        public void initV1() {
            // Spring container 를 초기화할 때는 트랜잭션 적용이 되지 않는다.
            // 초기화 코드가 먼저 호출이 되고, 그 다음 트랜잭션 AOP 가 적용이 된다.
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("initV1 txActive: {}", txActive);
        }

        // ApplicationReadyEvent 로 지정하게 되면 spring container 가 다 실행된 다음에 동작을 할 수 있게 이벤트를 걸어줌.
        @EventListener(ApplicationReadyEvent.class)
        @Transactional
        public void initV2() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("initV2 txActive: {}", txActive);
        }
    }
}
 