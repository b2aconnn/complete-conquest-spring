package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicTest {
    @Test
    void basicConfig() {
        // ApplicationContext 가 스프링 빈 컨테이너라고 생각하면 된다.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        A beanA = applicationContext.getBean("a", A.class);
        beanA.helloA();

        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean("b", B.class));
    }

    @Configuration
    static class BasicConfig {
        @Bean
        public A a() {
            return new A();
        }
    }

    @Slf4j
    static class A {
        public void helloA() {
            log.info("helloA");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("helloB");
        }
    }
}
