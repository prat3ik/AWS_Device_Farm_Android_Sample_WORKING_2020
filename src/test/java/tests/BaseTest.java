/*
 * (C) Copyright 2018 by Pratik Patel (https://github.com/prat3ik/)
 */
package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.PropertyUtils;
import utils.WaitUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * An abstract base for all of the Android tests within this package
 * <p>
 * Responsible for setting up the Appium test Driver
 *  @author prat3ik
 */
public abstract class BaseTest {
    /**
     * As driver static it will be created only once and used across all of the test classes.
     */
    public static AndroidDriver<AndroidElement> driver;
    public final static String APPIUM_SERVER_URL = PropertyUtils.getProperty("appium.server.url", "http://127.0.0.1:4723/wd/hub");
    public final static String EXECUTION_TYPE = PropertyUtils.getProperty("execution.type", "aws");
    public final static int IMPLICIT_WAIT = PropertyUtils.getIntegerProperty("implicitWait", 30);
    public static WaitUtils waitUtils = new WaitUtils();

    /**
     * This method will be called everytime before your test runs
     */
    @BeforeTest
    public abstract void setUpPage();

    /**
     * This method will run before any other method and it will run at once
     * <p>
     * Appium is a client - server model:
     * So we need to set up appium client in order to connect to Device Farm's appium server.
     */
    @BeforeSuite
    public void setUpAppium() throws MalformedURLException {
        //Use a empty DesiredCapabilities object for aws and for local execution set the desired capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (EXECUTION_TYPE.equals("local"))
            setDesiredCapabilitiesForLocalExecution(capabilities);
        driver = new AndroidDriver<AndroidElement>(new URL(APPIUM_SERVER_URL), capabilities);
        setTimeOuts(driver);
    }


    /**
     * This method will always execute after each test case, This will quit the WebDriver instance called at the last
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(final ITestResult result) throws IOException {
        String fileName = result.getTestClass().getName() + "_" + result.getName();
        System.out.println("Test Case: [" + fileName + "] executed..!");
        if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
            takeScreenshot(result.getMethod().getMethodName().toLowerCase() + "_" + System.currentTimeMillis());
        }
        resetApp();
    }

    /**
     * This method will be called after class finishes the execution of all tests
     */
    @AfterClass
    public void afterClass() {
    }

    /**
     * At the end of the Test Suite(At last) this method would be called
     */
    @AfterSuite
    public void tearDownAppium() {
        quitDriver();
    }

    /**
     * It will set the DesiredCapabilities for the local execution
     * @param desiredCapabilities
     */
    private void setDesiredCapabilitiesForLocalExecution(DesiredCapabilities desiredCapabilities) {
        String PLATFORM_NAME = PropertyUtils.getProperty("android.platform");
        String PLATFORM_VERSION = PropertyUtils.getProperty("android.platform.version");
        String APP_NAME = PropertyUtils.getProperty("android.app.name");
        String APP_RELATIVE_PATH = PropertyUtils.getProperty("android.app.location") + APP_NAME;
        String APP_PATH = this.getAbsolutePath(APP_RELATIVE_PATH);
        String DEVICE_NAME = PropertyUtils.getProperty("android.device.name");
        String APP_PACKAGE_NAME = PropertyUtils.getProperty("android.app.packageName");
        String APP_ACTIVITY_NAME = PropertyUtils.getProperty("android.app.activityName");
        String APP_FULL_RESET = PropertyUtils.getProperty("android.app.full.reset");
        String APP_NO_RESET = PropertyUtils.getProperty("android.app.no.reset");

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE_NAME);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, APP_FULL_RESET);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, APP_NO_RESET);
    }

    /**
     * This will set implicit wait
     *
     * @param driver
     */
    private static void setTimeOuts(AndroidDriver<AndroidElement> driver) {
        //Use a higher value if your mobile elements take time to show up
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    }

    /**
     * This will quite the android driver instance
     */
    private void quitDriver() {
        try {
            this.driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * It will  Restart the app after every test class to go back to the main screen and to reset the behavior
     */
    protected void resetApp() {
        driver.resetApp();
    }

    /**
     * This method used to take the screenshots of failures screen(Only visible at AWS Device Farm Screenshot tab)
     * @param name
     * @return
     */
    public boolean takeScreenshot(final String name) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

    /***
     * This will get the Absolute path
     * @param appRelativePath
     * @return
     */
    private static String getAbsolutePath(String appRelativePath) {
        File file = new File(appRelativePath);
        return file.getAbsolutePath();
    }
}
