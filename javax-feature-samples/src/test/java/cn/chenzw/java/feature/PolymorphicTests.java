package cn.chenzw.java.feature;

import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.UnsupportedEncodingException;

/**
 * 多态
 */
@RunWith(JUnit4.class)
public class PolymorphicTests {


    @Data
    public class Father {

        public String getName() {
            return "父亲";
        }

        private Number getAge() throws Exception {
            return 50;
        }
    }

    @Data
    public class Son extends Father {

        // 访问权限变小（报错）
        // private String getName() {
        //      return "";
        // }

        // 访问权限变大，抛出异常变小, 返回类型变小（两大一小）
        public Integer getAge() throws NullPointerException {
            return 10;
        }
    }





    /**
     *  分派示例
     */
    public class Human {

        private String name = "human";

        protected void sayHello(){
            System.out.println("human say hello!");
        }
    }

    public class Man extends Human {

        private String name = "man";

        @Override
        protected void sayHello(){
            System.out.println("man say hello!");
        }
    }

    public class Woman extends Human{

        private String name = "woman";

        @Override
        protected void sayHello(){
            System.out.println("woman say hello!");
        }
    }


    public class Dispatcher {
        public void sayHello(Human guy) {
            System.out.println("hello, guy!");
        }
        public void sayHello(Man guy) {
            System.out.println("hello, gentleman!");
        }
        public void sayHello(Woman guy) {
            System.out.println("hello, lady!");
        }
    }

    /**
     * 重载使用静态分派（编译时确定调用的方法）
     */
    @Test
    public void testStaticDispatch() {
        Human man = new Man();  // Human为静态类型，Man为实际类型
        Human woman = new Woman();
        Man man2 = new Man();

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.sayHello(man);  // => hello, guy!
        dispatcher.sayHello(woman);  // => hello, guy!
        dispatcher.sayHello(man2);  // => hello, gentleman!
    }

    /**
     * 重写使用动态分派（只对成员方法有效）
     */
    @Test
    public void testDynamicDispatch() {
        Human man = new Man();  // Human为静态类型，Man为实际类型
        Human woman = new Woman();
        man.sayHello();    // => man say hello
        woman.sayHello();   // => woman say hello

        man = new Woman();
        man.sayHello();   // => woman say hello

        Assert.assertEquals("human", man.name);  // 变量使用的静态分派

        Man man2 = new Man();
        Assert.assertEquals("man", man2.name);  // 根据静态类型分派

    }

    @Test
    public void test() throws UnsupportedEncodingException {
        byte[] bytes = "一".getBytes("UTF-16");
        System.out.println(bytes.length);
    }



}
