package cn.chenzw.java.feature.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 小数精度操作示例
 */
@Slf4j
@RunWith(JUnit4.class)
public class DecimalTest {


    @Test
    public void testDoubleFormat() {
        Double dd = 3.33333333d;

        log.info("初始值 => {}", dd);

        // 返回的是String格式
        log.info("String.format => {}", String.format("%.2f", dd));

        // 返回的是String格式
        DecimalFormat df = new DecimalFormat("#.00");
        log.info("DecimalFormat format => {}", df.format(dd));

        // 返回的是String格式
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);  // 保留2位小数
        nf.setRoundingMode(RoundingMode.UP);  // UP: 进1（舍入位非0）；DOWN：舍入位直接去除； HALF_DOWN: 舍入位非5的，四舍五入，舍入位为5的，向最接近数舍入；HALF_UP: 四舍五入
        log.info("NumberFormat format => {}", nf.format(dd));

        // 返回long整数型（四舍五入）
        log.info("Math.round => {}", Math.round(dd));


        // 返回double类型
        BigDecimal bd = new BigDecimal(dd);
        log.info("BigDecimal => {}", bd.setScale(2, RoundingMode.HALF_UP).doubleValue());

        // long相除
        Long a = 1920394L;
        Long b = 1000L;
        double result = a / b;
        log.info("long相除结果 => {}", result);

        // 先转double再相除
        double result2 = a.doubleValue() / b.doubleValue();
        log.info("long相除结果2 => {}", result2);
    }


}
