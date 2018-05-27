/*
 * (C) Copyright 2018 by Pratik Patel (https://github.com/prat3ik/)
 */
package utils;

import io.appium.java_client.android.AndroidElement;

/**
 * This class contains custom appium/selenium methods for Webelement
 *
 *  @author prat3ik
 */
public class AppiumUtils {
    public static WaitUtils waitUtils = new WaitUtils();

    /**
     * This will check whether element is displayed on UI or not
     *
     * @param element
     * @return
     */
    public static boolean isElementDisplayed(AndroidElement element) {
        waitUtils.staticWait(2000);
        boolean isPresent = false;
        try {
            element.isDisplayed();
            isPresent = true;
        } catch (Exception e) {
            isPresent = false;
        }
        return isPresent && element.isDisplayed();
    }
}
