package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

public class ConcreteTest {
    @Test
    void noProxy() {
         ConcreteLogic log ic = new ConcreteLogic();
         ConcreteClient client = new ConcreteClient(logic);
         client.execute();
    }
}
