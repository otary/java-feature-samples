package cn.chenzw.java7.feature.lang.reflect;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理示例
 */
@RunWith(JUnit4.class)
public class ProxyTests {

    static Logger logger = LoggerFactory.getLogger(ProxyTests.class);


    /**
     * 测试
     */
    @Test
    public void testBasic() {

        MyFactory factory = new WuhahaFactory();

        // 动态创建代理
        MyFactory factoryProxy = (MyFactory) Proxy.newProxyInstance(factory.getClass().getClassLoader(),
                factory.getClass().getInterfaces(), new MyFactoryProxy(factory));

        factoryProxy.createProduct();

        // 判断是否代理类
        Assert.assertTrue(Proxy.isProxyClass(factoryProxy.getClass()));

        // 获取代理类
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(factoryProxy);
        Assert.assertTrue(invocationHandler instanceof MyFactoryProxy);
    }

    /**
     * 工厂代理 - 经销商
     */
    public static class MyFactoryProxy implements InvocationHandler {

        private Object target;

        public MyFactoryProxy(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            logger.info("--------开始代理-----------");
            Object result = method.invoke(target, args);
            logger.info("--------结束代理-----------");
            return result;
        }
    }

    /**
     * 工厂
     */
    public interface MyFactory {

        /**
         * 生产产品
         *
         * @return
         */
        String createProduct();
    }

    /**
     * 娃哈哈工厂
     */
    public class WuhahaFactory implements MyFactory {

        @Override
        public String createProduct() {
            logger.info("生产娃哈哈");
            return "娃哈哈";
        }
    }


}
