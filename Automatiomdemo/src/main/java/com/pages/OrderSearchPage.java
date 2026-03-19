package com.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import com.base.TestBase;

public class OrderSearchPage extends TestBase {

    WebDriverWait wait;

    // Constructor
    public OrderSearchPage() {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    @FindBy(id = "searchType")
    WebElement searchTypeDropdown;

    @FindBy(id = "searchValue")
    WebElement searchValue;

    @FindBy(xpath = "//button[@onclick='searchOrder()']")
    WebElement searchBtn;

    @FindBy(id = "orderDetails")
    WebElement orderDetails;

    @FindBy(id = "searchMsg")
    WebElement errorMsg;

    @FindBy(xpath = "//button[text()='Back']")
    WebElement backBtn;

    @FindBy(xpath = "//button[text()='Reset']")
    WebElement resetBtn;

    //  Actions

    public void searchOrder(String type, String value) {

        wait.until(ExpectedConditions.visibilityOf(searchValue));

        Select select = new Select(searchTypeDropdown);
        select.selectByValue(type);

        searchValue.clear();
        searchValue.sendKeys(value);

        searchBtn.click();
    }

    // Get order status (valid case)
    public String getOrderStatus() {
        wait.until(ExpectedConditions.visibilityOf(orderDetails));
        return orderDetails.getText();
    }

    //Check error message
    public boolean isErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMsg));
            return errorMsg.getText().contains("Invalid");
        } catch (Exception e) {
            return false;
        }
    }

    // Get error message text
    public String getErrorMessage() {
        return errorMsg.getText();
    }

    // Return error element (for screenshot)
    public WebElement getErrorElement() {
        return errorMsg;
    }

    // Check if details page is shown
    public boolean isDetailsPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(orderDetails));
            return orderDetails.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Click Back (only for valid flow)
    public void clickBack() {
       // wait.until(ExpectedConditions.elementToBeClickable(backBtn)).click();
    	backBtn.click();

        // wait until search page is ready instantly
        //wait.until(ExpectedConditions.visibilityOf(searchValue));
    }

    // Click Reset (invalid flow)
    public void clickReset() {
        resetBtn.click();

        //  wait until field is cleared
        wait.until(ExpectedConditions.attributeToBe(searchValue, "value", ""));
    }

    //Clear input before next iteration
    public void clearSearchField() {
        wait.until(ExpectedConditions.visibilityOf(searchValue));
        searchValue.clear();
    }
}