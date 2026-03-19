package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class LoginPage extends TestBase {
	
	//Constructor
	public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    
	//locators
	
	@FindBy(id= "username")
    WebElement username;

    @FindBy(id= "password")
    WebElement password;

    @FindBy(xpath = "//button[@onclick='login()']")
    WebElement loginBtn;

    // Actions
        
    public String validatingLoginPageTitle() {
        String title= driver.getTitle();
        System.out.println(title);
        return title;
    }
    
    // landing page is OrderSearchPage
    public OrderSearchPage LoginToOrderSearch(String un, String pwd) {
        username.sendKeys(un);
        password.sendKeys(pwd);
        loginBtn.click();
        return new OrderSearchPage();
    }
    
}

