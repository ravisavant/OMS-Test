package com.test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import com.base.TestBase;
import com.pages.LoginPage;
import com.pages.OrderSearchPage;
import com.util.ExtentManager;

public class LoginPageTest extends TestBase {

    LoginPage loginPage;
    OrderSearchPage orderSearchPage;
    
    ExtentReports extent;
    ExtentTest test;   // 👉 ADDED (to represent each test in report)

    public LoginPageTest() {
        super();
    }

    @BeforeMethod
    public void setUp() {
        initialization();
        extent = ExtentManager.getInstance(); // already correct 👍
        loginPage = new LoginPage();
    }
    
    @Test
    public void loginPageTitleTest() {

        test = extent.createTest("Login Page Title Test");  
        // 👉 ADDED: creates test entry in report

        String title = loginPage.validatingLoginPageTitle();
        System.out.println(title);

        if(title.equals("Order Management System")) {
            test.pass("Title matched");  
            // 👉 ADDED: mark as pass in report
        } else {
            test.fail("Title not matched");  
            // 👉 ADDED: mark as fail in report
        }

        Assert.assertEquals(title,"Order Management System","LoginPage is not correct");
    }

    @Test
    public void loginTest() {

        test = extent.createTest("Login Test");  
        // 👉 ADDED

        orderSearchPage = loginPage.LoginToOrderSearch(
            prop.getProperty("username"),
            prop.getProperty("password")
        );

        test.pass("Login successful");  
        // 👉 ADDED
    }

    @AfterMethod
    public void tearDown() {
        extent.flush();  
        // 👉 IMPORTANT: writes report to file
        driver.quit();
    }
}