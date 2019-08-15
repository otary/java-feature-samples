package cn.chenzw.java8.feature;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DefaultInterfaceTest {

    @Test
    public void testBasic() {

        // 默认方法
        Human frank = new Frank();
        Assert.assertEquals("Boy", frank.getName());

        // 重写getName
        Human tom = new Tom();
        Assert.assertEquals("Tom", tom.getName());

        Assert.assertEquals("Hello!", Human.say());
        Assert.assertEquals("Hello, Woman!", Woman.say());

    }

}


interface Human {

    // 默认方法
    default String getName() {
        return "Human";
    }

    static String say() {
        return "Hello!";
    }
}

interface Boy extends Human {

    @Override
    default String getName() {
        return "Boy";
    }
}

interface Woman extends Human {

    @Override
    default String getName() {
        return "Woman";
    }

    static String say() {
        return "Hello, Woman!";
    }
}


class Frank implements Boy {

    // 使用默认方法
}

class Tom implements Boy {

    public String getName() {
        return "Tom";
    }
}


