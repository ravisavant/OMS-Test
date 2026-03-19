package com.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

    public static WebDriver driver;
    public static Properties prop;

    public TestBase() {

        try {

            prop = new Properties();   // important

            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") +
                    "/src/test/resources/config.properties");

            prop.load(ip);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialization() {

        String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.implicit_wait));

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.page_load_timeout));

        driver.get(prop.getProperty("url"));
    }
}