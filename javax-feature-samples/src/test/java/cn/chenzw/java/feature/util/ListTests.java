package cn.chenzw.java.feature.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RunWith(JUnit4.class)
public class ListTests {

    @Test
    public void testListIterator() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        // 向后遍历
        ListIterator<String> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            log.info(" => {}", listIterator.next());
        }

        // 向前遍历
        while (listIterator.hasPrevious()) {
            log.info(" => {}", listIterator.previous());
        }


        // 遍历中删除元素
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            if ("bbb".endsWith(next)) {
                listIterator.remove();
            }
        }
        log.info("result => {}", list);
    }


    @Test
    public void test() {
        Map<Object, Object> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put("aaa" + i, "aaa");
            map.put("bbb" + i, "bbb");
        }

        map.get("aaa100");



    }
}
