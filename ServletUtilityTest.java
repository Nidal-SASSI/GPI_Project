package in.co.test;


import in.co.fennel.project.ctl.BaseCtl;
import in.co.fennel.project.util.ServletUtility;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class ServletUtilityTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private ServletUtility servletUtility;

    @Test
    public void getErrorMessageTest(){
        // HAVING
        String string = "test";
        when(httpServletRequest.getAttribute("test")).thenReturn("test");

        // WHEN
        String actual = servletUtility.getErrorMessage(string, httpServletRequest);

        // THEN
        Assert.assertEquals(actual, "");
    }

    @Test
    public void getSizeTest(){
        // HAVING
        String string = "testSize";
        when(httpServletRequest.getAttribute(string)).thenReturn("testSize");

        // WHEN
        int actual = servletUtility.getSize(httpServletRequest);

        // THEN
        Assert.assertEquals(actual, 8);
    }

    @Test
    public void getOprationTest(){
        // HAVING
        String string = "Opration";
        when(httpServletRequest.getAttribute(string)).thenReturn(string);

        // WHEN
        String actual = servletUtility.getOpration(httpServletRequest);

        // THEN
        Assert.assertEquals(actual, string);
    }

    @Test
    public void getPageNoTest(){
        // HAVING
        String string = "pageNo";
        when(httpServletRequest.getAttribute(string)).thenReturn(5);

        // WHEN
        int actual = servletUtility.getPageNo(httpServletRequest);

        // THEN
        Assert.assertEquals(actual, 5);
    }

    @Test
    public void getSuccessMessageTest(){
        // HAVING
        String string = BaseCtl.MSG_SUCCESS;

        // WHEN
        String actual = servletUtility.getSuccessMessage(httpServletRequest);

        // THEN
        Assert.assertEquals(actual, "");
    }



}
