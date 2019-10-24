package at.technikum.rh.Interfaces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class urlImplementaionTest {
//Test for rawUrl()
    @Test
    public void rawUelTest01() {
        urlImplementaion object = new urlImplementaion("https://www.facebook.com/profile.php.php?a=1&b=2&c=3");
        String output = object.getRawUrl();
        //Assertions.assertNotNull("Wrong result", object);
        Assertions.assertEquals("https://www.facebook.com/profile.php.php?a=1&b=2&c=3", output);
    }

    @Test
    public void rawUelTest02() {
        urlImplementaion object = new urlImplementaion("www.google.com/root/Documents/test.php");
        String output = object.getRawUrl();
        //Assertions.assertNotNull("Wrong result", object);
        Assertions.assertEquals("www.google.com/root/Documents/test.php", output);
    }


    //Test for  getPath()
    @Test
    public void pathTest01() {
        urlImplementaion object = new urlImplementaion("https://example.com/root/phpproj/test.php?a=1&b=2");
        String output = object.getPath();
        Assertions.assertEquals("/root/phpproj/test.php", output);
    }
    @Test
    public void pathTest02() {
        urlImplementaion object = new urlImplementaion("root/phpproj/test.php?a=1&b=2&c=3");
        String output = object.getPath();
        Assertions.assertEquals("/phpproj/test.php", output);
    }


    @Test
    public void pathTest03() {
        urlImplementaion object = new urlImplementaion("/root/desktop/test/");
        String output = object.getPath();
        Assertions.assertEquals("/root/desktop/test/", output);
    }

   //Test for getParameter
    @Test
    void getParameterTest01() {
        urlImplementaion url = new urlImplementaion("https://example.com/root/desktop/test?a=1&b=2&c=3");
        String k = url.getParameter().get("a");
        Assertions.assertEquals("1", k);

        String k1 = url.getParameter().get("b");
        Assertions.assertEquals("2",k1);

        String k2 = url.getParameter().get("c");
        Assertions.assertEquals("3", k2);
    }

    //Test for getParameterCount
    @Test
    public void paramCountTest01() {
        urlImplementaion object = new urlImplementaion("/test.php?a=1");
        int output = object.getParameterCount();
        Assertions.assertEquals(1, output);
    }

    @Test
    public void paramCountTest02() {
        urlImplementaion object = new urlImplementaion("www.example.com/phpproj/test.php?a=1&b=2&c=3#ss");
        int output = object.getParameterCount();
        Assertions.assertEquals(3, output);
    }

    @Test
    public void paramCountTest03() {
        urlImplementaion object = new urlImplementaion("www.example.com/phpproj/test.php");
        int output = object.getParameterCount();
        Assertions.assertEquals(0, output);
    }

    //Test for getfilename
    @Test
    public void fileNameTest01() {
        urlImplementaion object = new urlImplementaion("www.example.com/phpproj/first.php?");
        String output = object.getFileName();
        Assertions.assertEquals("first.php", output);
    }

    @Test
    public void fileNameTest02() {
        urlImplementaion object = new urlImplementaion("https://www.example.com/phpproj/second.php");
        String output = object.getFileName();
        Assertions.assertEquals("second.php", output);
    }


    @Test
    public void fileNameTest03() {
        urlImplementaion object = new urlImplementaion("www.example.com/phpproj/index.php?a=1&b=2&c=3#nn");
        String output = object.getFileName();
        Assertions.assertEquals("index.php", output);
    }

    //Test for getExtension
    @Test
    void extTest01() {
        urlImplementaion url = new urlImplementaion("https://example.com/root/phpproj/test?a=1&b=2&c=3#aa");
        String ext = url.getExtension();
        Assertions.assertEquals("", ext);
    }


    //Test for getFragment
    @Test
    void fragmentTest01() {
        urlImplementaion url = new urlImplementaion("https://example.com");
        String ext = url.getFragment();
        Assertions.assertEquals("", ext);
    }
    @Test
    void fragmentTest02() {
        urlImplementaion url = new urlImplementaion("https://example.com/root/desktop/test?a=1&b=2&c=3#bb");
        String ext = url.getFragment();
        Assertions.assertEquals("bb", ext);
    }

    //Test for get Segment
    @Test
    void segmentTest01() {
        urlImplementaion url = new urlImplementaion("https://example.com/root/desktop/Ordner/test.php?a=1&b=2&c=3");
        String[] ext = url.getSegments();
        String[] answer = {"root", "desktop", "test.php"};

    }
    
}
