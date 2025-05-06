package hello.proxy.app.v2;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV2 {
    public void save(String itemId) {
        // 저장 로직
        if ("ex".equals(itemId)) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
