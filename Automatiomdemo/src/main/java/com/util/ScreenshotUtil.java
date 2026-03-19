package com.util;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;

import com.base.TestBase;

public class ScreenshotUtil {

    // 📸 Full page screenshot
    public static String captureScreenshot(String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) TestBase.driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            String dirPath = System.getProperty("user.dir") + "/screenshots/";
            new File(dirPath).mkdirs(); // 🔥 create folder if not exists

            String path = dirPath + testName + ".png";

            FileHandler.copy(src, new File(path));

            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 📸 Element screenshot (for error message)
    public static String captureElementScreenshot(WebElement element, String testName) {
        try {
            File src = element.getScreenshotAs(OutputType.FILE);

            String dirPath = System.getProperty("user.dir") + "/screenshots/";
            new File(dirPath).mkdirs(); // 🔥 ensure folder exists

            String path = dirPath + testName + ".png";

            FileHandler.copy(src, new File(path));

            return path;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}