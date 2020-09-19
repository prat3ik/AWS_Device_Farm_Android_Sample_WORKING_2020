/*
 * (C) Copyright 2018 by Pratik Patel (https://github.com/prat3ik/)
 */
package pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import utils.AppiumUtils;

/**
 * Page Object Class: This contains all the page objects of Android messaging app
 *
 *  @author prat3ik
 */
public class AndroidMessageAppPO extends BasePO {

    /**
     * @param driver the appium driver created in the beforesuite method.
     */
    public AndroidMessageAppPO(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
    AndroidElement denyButton;

    public void clickOnDenyButton() {
        denyButton.click();
    }
}