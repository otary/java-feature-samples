package cn.chenzw.java8.feature.util.function;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Function<T, T> 的子接口 - 接受一个输入参数和返回值都是相同类型 T 的操作
 * Unary Operator：一元操作符
 *
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class UnaryOperatorTests {

    @Test
    public void test() {
        UnaryOperator<Integer> square = num -> num * num;
        log.info("square => {}", square.apply(5));

        // 拼接
        UnaryOperator<String> addExclamation = str -> str + "!";
        log.info("addExclamation => {}", addExclamation.apply("Hello"));

        // 组合
        UnaryOperator<Integer> incrementAndSquare = num -> num + 1;
        Function<Integer, Integer> fn = incrementAndSquare.andThen(square);
        log.info(" => {}", fn.apply(5));
    }
}
