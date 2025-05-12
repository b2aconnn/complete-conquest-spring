package hello.proxy.pureproxy.code;

import hello.proxy.pureproxy.decorator.code.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {
    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator call");

        String result = component.operation();
         String decoResult = "*****" + result + "******";
        log.info("MessageDecorator 꾸미기 전 ={}, 꾸미기 후={}", result, decoResult);
        return decoResult;
    }
}
