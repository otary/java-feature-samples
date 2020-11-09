package cn.chenzw.java7.feature.nio.file;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class PathTests {

    @Test
    public void testGet() {
        Path path = Paths.get("/home", "chenzw", "cat");

        Assert.assertEquals("\\home\\chenzw\\cat", path.toString());
    }

    @Test
    public void testRelativize() {
        Path pathA = Paths.get("/home", "chenzw", "cat");
        Path pathB = Paths.get("/home", "chenzw", "ppp");

        Path relativize = pathA.relativize(pathB);
        Assert.assertEquals("..\\ppp", relativize.toString());
    }


    @Test
    public void testResolve() {
        Path pathA = Paths.get("/foo/bar");
        Path pathB = pathA.resolve("./baz");
        Assert.assertEquals("\\foo\\bar\\baz", pathB.normalize().toString());


       /*
        path.resolve("/foo/bar", "./baz");   // => '/foo/bar/baz'
        path.resolve('/foo/bar', 'baz')   // => '/foo/bar/baz'
        path.resolve('/foo/bar', '/baz')   // => '/baz'
        path.resolve('/foo/bar', '../baz')   // => '/foo/baz'
        path.resolve('home','/foo/bar', '../baz')   // => '/foo/baz'
        path.resolve('home','./foo/bar', '../baz')   // => '/home/foo/baz'
        path.resolve('home','foo/bar', '../baz')   // => '/home/foo/baz'
        path.resolve('home', 'foo', 'build','aaaa','aadada','../../..', 'asset') // => '/home/foo/asset'*/
    }

}
