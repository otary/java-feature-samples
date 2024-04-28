package cn.chenzw.java7.feature.nio.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * @author chenzw
 */
@RunWith(JUnit4.class)
public class PathMatcherTests {

    @Test
    public void testMatches() {
        // 通配符路径匹配
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{java,txt}");

        Assert.assertEquals(true, pathMatcher.matches(Paths.get("aaa/bbb/ccc/test.java")));
        Assert.assertEquals(false, pathMatcher.matches(Paths.get("aaa/bbb/ccc/test.class")));
    }
}
