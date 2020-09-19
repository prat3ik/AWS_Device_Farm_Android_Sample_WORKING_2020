/*
 * (C) Copyright 2018 by Pratik Patel (https://github.com/prat3ik/)
 */
package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.AndroidMessageAppPO;

/**
 * This is the Main Test Cases Class, All the test cases are defined in this.
 *
 *  @author prat3ik
 */
public class TestCases extends BaseTest {

    @BeforeTest
    @Override
    public void setUpPage() {

    }

    @Test
    public void verifyUserCanSendMessage() {
        final String phoneNo = "00011122233";

        AndroidMessageAppPO po = new AndroidMessageAppPO(driver);
        po.clickOnDenyButton();
        waitUtils.staticWait(5000);
    }

}
