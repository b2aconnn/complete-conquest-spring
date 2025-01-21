package hello.springtx.apply;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {
    @Autowired
    CallService callService;

    @Test
    void printProxy() {
        // transaction 이 설정된 클래스여서 spring 에서 proxy 객체로 등록을 했는 지 확인하기 위함
        log.info("call Service = {}", callService.getClass());
    }

    @Test
    void externalCallV2() {
        callService.external();
    }

    @TestConfiguration
    static class TestContextConfiguration {
        @Bean
        CallService callService() {
            return new CallService(internalCall());
        }

        @Bean
        InternalService internalCall() {
            return new InternalService();
        }
    }

    @RequiredArgsConstructor
    @Slf4j
    static class CallService {
        private final InternalService internalService;

        public void external() {
            log.info("call external");
            printTxInfo();
            internalService.internal();
        }

//        @Transactional
//        public void internal() {
//            log.info("call internal");
//            printTxInfo();
//        }

        private void printTxInfo() {
            // transaction 이 적용이 됐는 지 확인
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("txActive: {}", txActive);

            // transaction 이 readonly 인 지 체크
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("readOnly: {}", readOnly);
        }
    }

    @Slf4j
    static class InternalService {
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
