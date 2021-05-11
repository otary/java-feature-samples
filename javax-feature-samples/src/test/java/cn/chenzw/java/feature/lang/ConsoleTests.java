package cn.chenzw.java.feature.lang;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Scanner;

/**
 * 控制台
 */
@RunWith(JUnit4.class)
public class ConsoleTests {


   /* public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("不能使用控制台");
        }
        System.out.println(console.readLine("请输入命令："));
        System.out.println(console.readPassword("请输入密码:"));  //不回显地输入的密码
    }*/

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = null;
        while ((line = scanner.nextLine()) != null) {
            System.out.println("读取到数据 => " + line);
        }
    }
}
