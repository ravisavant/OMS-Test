package com.test;

import java.util.List;

import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.base.TestBase;
import com.pages.LoginPage;
import com.pages.OrderSearchPage;
import com.util.ExcelUtil;
import com.util.ExtentManager;
import com.util.ScreenshotUtil;

public class OrderSearchPageTest extends TestBase {

    LoginPage loginPage;
    OrderSearchPage orderPage;
    ExtentReports extent;
    ExtentTest test;

    public OrderSearchPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {

        initialization();

        extent = ExtentManager.getInstance();

        loginPage = new LoginPage();

        orderPage = loginPage.LoginToOrderSearch(
                prop.getProperty("username"),
                prop.getProperty("password"));
    }

    //DataProvider
    @DataProvider(name = "orderData")
    public Object[][] getData() {

        List<Object[]> data = ExcelUtil.getTestData("Orders");

        Object[][] array = new Object[data.size()][3];

        for (int i = 0; i < data.size(); i++) {
            array[i] = data.get(i);
        }

        return array;
    }

    //Test method (NO LOOP)
    @Test(dataProvider = "orderData")
    public void orderSearchTest(String type, String id, String expected) {

        // 🔥 create test per detest
        test = extent.createTest("Order Test - ID: " + id);

        orderPage.clearSearchField();
        orderPage.searchOrder(type, id);

        // ✅ INVALID ORDER FLOW
        if (orderPage.isErrorDisplayed()) {

            String errorText = orderPage.getErrorMessage();

            String path = ScreenshotUtil.captureScreenshot(id + "_FAIL");

            test.fail(
                    "INVALID ORDER for ID: " + id + "<br>" +
                    "Error: " + errorText
            ).addScreenCaptureFromPath(path);

            orderPage.clickReset();
        }

        // ✅ VALID ORDER FLOW
        else if (orderPage.isDetailsPageDisplayed()) {

            String actual = orderPage.getOrderStatus();

            String actualStatus = actual.contains("Status: Created")
                    ? "Created"
                    : "Not Created";

            if (actualStatus.equalsIgnoreCase(expected)) {

                test.pass(
                        "PASS for ID: " + id + "<br>" +
                        "Expected: " + expected + "<br>" +
                        "Actual Status: " + actualStatus + "<br>" +
                        "Order Details:<br>" + actual
                );

            } else {

                String path = ScreenshotUtil.captureScreenshot(id + "_FAIL");

                test.fail(
                        "FAIL for ID: " + id + "<br>" +
                        "Expected: " + expected + "<br>" +
                        "Actual Status: " + actualStatus + "<br>" +
                        "Order Details:<br>" + actual
                ).addScreenCaptureFromPath(path);
            }

            orderPage.clickBack();
        }

        else {
        	String path = ScreenshotUtil.captureScreenshot(id + "_FAIL");
            test.fail("Unknown issue for ID: " + id).addScreenCaptureFromPath(path);
        }
    }

    @AfterMethod
    public void tearDown() {
        extent.flush();
        driver.quit();
    }
}