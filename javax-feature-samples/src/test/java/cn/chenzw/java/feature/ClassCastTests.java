package cn.chenzw.java.feature;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 子可转父，父转子需要使用dozer
 *
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class ClassCastTests {

    @Test
    public void testFather2Son() {
        // 将父类转成子类
        Father father = new Father();

        DozerBeanMapper mapper = new DozerBeanMapper();
        Son son = mapper.map(father, Son.class);

        log.info("son => {}", son);
    }


    /**
     * 泛型（子转父）
     */
    @Test
    public void testGeneric() {
        List<Son> sons = new ArrayList<>();
        sons.add(new Son());
        sons.add(new Son());
        List<Father> fathers = (List) sons;

        log.info("fathers => {}", fathers);
    }

    /**
     * 泛型（父转子 - 报错）
     */
    @Test
    public void testGeneric2() {
        List<Father> fathers = new ArrayList<>();
        fathers.add(new Father());
        fathers.add(new Father());
        List<Son> sons = (List) fathers;

        log.info("sons => {}", sons);

        // 遍历报ClassCastException
        for (Son son : sons) {
            log.info("son => {}", son);
        }
    }

    /**
     * 泛型（父转子）
     */
    @Test
    public void testGeneric3() {
        List<Father> fathers = new ArrayList<>();
        fathers.add(new Father());
        fathers.add(new Father());

        DozerBeanMapper mapper = new DozerBeanMapper();
        List<Son> sons = fathers.stream().map(father -> mapper.map(father, Son.class)).collect(Collectors.toList());

        log.info("sons => {}", sons);
    }



    @Data
    public static class Father {

        private String name = "父亲";

        private Integer age = 50;

        private String posts;
    }

    @Data
    public static class Son extends Father {

        private String name = "儿子";

        private Integer age = 10;
    }

}
