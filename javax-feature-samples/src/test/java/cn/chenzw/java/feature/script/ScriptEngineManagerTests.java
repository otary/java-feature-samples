package cn.chenzw.java.feature.script;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Java执行Javascript代码
 */
@RunWith(JUnit4.class)
public class ScriptEngineManagerTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testEval() throws FileNotFoundException, ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");

        // 读取解析JS文件
        FileReader fileReader = new FileReader(new File(System.getProperty("user.dir") + "/001186.js"));
        engine.eval(fileReader);

        // 读取数组变量
        ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) engine.get("Data_netWorthTrend");
        for (int i = 0; i < scriptObjectMirror.size(); i++) {
            ScriptObjectMirror slot = (ScriptObjectMirror) scriptObjectMirror.getSlot(i);
            logger.info("Data_netWorthTrend x: {}, y: {}, equityReturn: {}, unitMoney: {}",
                    slot.get("x"),
                    slot.get("y"),
                    slot.get("equityReturn"),
                    slot.get("unitMoney")
            );
        }

        // 读取对象变量
        ScriptObjectMirror scriptObjectMirror2 = (ScriptObjectMirror) engine.get("Data_buySedemption");
        logger.info("Data_buySedemption categories: {}", scriptObjectMirror2.get("categories"));

        // 读取变量
        logger.info("fS_name: {}", engine.get("fS_name"));
    }

    /**
     * 混淆Js测试
     *
     * @throws ScriptException
     * @throws FileNotFoundException
     */
    @Test
    public void testConfuison() throws ScriptException, FileNotFoundException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");

        FileReader fileReader = new FileReader(Thread.currentThread().getContextClassLoader().getResource("js/confusion.txt").getFile());
        engine.eval(fileReader);

        ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) engine.get("__NUXT__");


        ScriptObjectMirror data = (ScriptObjectMirror) scriptObjectMirror.get("data");
        for (int i = 0; i < data.size(); i++) {
            ScriptObjectMirror slot = (ScriptObjectMirror) data.getSlot(i);

            ScriptObjectMirror dataList = (ScriptObjectMirror) slot.get("dataList");

            for (int j = 0; j < dataList.size(); j++) {
                ScriptObjectMirror dataItem = (ScriptObjectMirror) dataList.getSlot(j);

                String description = (String) dataItem.get("description");

                String coverImage = (String) dataItem.get("cover_image_url");
                String image = (String) dataItem.get("vertical_image_url");

                String title = (String) dataItem.get("title");

                // 分类
                ScriptObjectMirror category = (ScriptObjectMirror) dataItem.get("category");
                for (int k = 0; k < category.size(); k++) {

                }

                // 更新状态
                String updateStatus = (String) dataItem.get("update_status");




                System.out.println(description);


            }

            //  System.out.println(slot.get("count"));
        }


    }

}
