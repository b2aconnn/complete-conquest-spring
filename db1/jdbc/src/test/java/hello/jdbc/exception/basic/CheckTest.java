package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class CheckTest {
    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        assertThatThrownBy(service::callThrow)
                        .isInstanceOf(MyCheckException.class);
    }

    /**
     * Exception 을 상속 받은 예오니느 체크 예외가 된다.
     */
    static class MyCheckException extends Exception {
        public MyCheckException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckException e) {
                // 예외 처리
                log.info("예외 처리, message={}", e.getMessage());
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 케으 예외를 예외를 잡지 않고 밖으로 던지려면 throws 예오리르 메서드에 필수로 선언해야한다.
         * @throws MyCheckException
         */
        public void callThrow() throws MyCheckException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckException {
            throw new MyCheckException("ex");
        }
    }
}
