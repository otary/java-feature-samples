package cn.chenzw.java.feature;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型
 */
@Slf4j
@RunWith(JUnit4.class)
public class GenericTests {

    @Test
    public void test() {
        List<Human> humans = new ArrayList<>();
        humans.add(new Woman());
        humans.add(new Man());
        testSuper(humans);

        testExtends(humans);
    }

    /**
     * 只读
     * @param humans
     */
    public void testSuper(List<? extends Human> humans) {
        log.info("humans => {}", humans);

        // humans.add(new Woman()); // 报错

        // 读
        for (Human human : humans) {
            log.info("human => {}", human);
        }
    }

    /**
     * 可写，可读（读取的是Object对象）
     * @param humans
     */
    public void testExtends(List<? super Human> humans) {
        log.info("humans => {}", humans);

        // 写
        humans.add(new Woman());

        // 读
        for (Object human : humans) {
            if (human instanceof Human) {
                log.info("human => {}", (Human)human);
            }
        }
    }


    /**
     * 父类
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

    public class Woman extends Human {

        private String name = "woman";

        @Override
        protected void sayHello(){
            System.out.println("woman say hello!");
        }
    }

}
