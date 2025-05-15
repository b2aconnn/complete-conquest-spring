package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicPostProcessorTest {
    @Test
    void basicConfig() {
        // ApplicationContext 가 스프링 빈 컨테이너라고 생각하면 된다.
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        B beanB = applicationContext.getBean("a", B.class);
        beanB.helloB();

        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
    }

    @Configuration
    static class BeanPostProcessorConfig {
        @Bean
        public A a() {
            return new A();
        }

        @Bean
        public AtoBPostProcessor postProcessor() {
            return new AtoBPostProcessor();
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

    @Slf4j
    static class AtoBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
             log.info("beanName={}, bean={}", beanName, bean);
             if (bean instanceof A) {
                 return new B();
             }
             return bean;
        }
    }
}
