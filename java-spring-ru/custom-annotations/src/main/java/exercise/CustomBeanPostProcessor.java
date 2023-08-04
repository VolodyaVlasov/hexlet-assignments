package exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

// BEGIN
@Service
public class CustomBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, String> map = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Inspect inspect = bean.getClass().getAnnotation(Inspect.class);

        if (nonNull(inspect)) {
            map.put(beanName, inspect.level());
        }

        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (map.containsKey(beanName)) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), (proxy, method, args) -> {
                String inspect = map.get(beanName);
                String message = "Was called method: "+ method.getName() +"() with arguments: "+ Arrays.toString(args);
                if (inspect.equals("debug")) {
                    LOGGER.debug(message);
                } else {
                    LOGGER.info(message);
                }
                return method.invoke(bean, args);
            });
        }


        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private Map<String, String> getMap() {
        return map;
    }
}
// END
