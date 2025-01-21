package hello.springtx.apply;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {
    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        // transaction 이 설정된 클래스여서 spring 에서 proxy 객체로 등록을 했는 지 확인하기 위함
        log.info("call Service = {}", callService.getClass());
    }

    @Test
    void internalCall() {
         callService.internal();
    }

    @Test
    void externalCall() {
        callService.external();
    }

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {
        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            // transaction 이 적용이 됐는 지 확인
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive: {}", txActive);

            // transaction 이 readonly 인 지 체크
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("readOnly: {}", readOnly);
        }
    }

}
