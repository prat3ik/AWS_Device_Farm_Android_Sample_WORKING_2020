package pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import utils.PropertyUtils;
import utils.WaitUtils;

import java.util.concurrent.TimeUnit;

public abstract class BasePO {
    public final static int IMPLICIT_WAIT = PropertyUtils.getIntegerProperty("implicitWait", 30);
    private static final int KEYBOARD_ANIMATION_DELAY = 1000;
    private static final int XML_REFRESH_DELAY = 1000;
    WaitUtils waitUtils;

    protected final AppiumDriver driver;

    /**
     * A base constructor that sets the page's driver
     *
     * The page structure is being used within this test in order to separate the
     * page actions from the tests.
     *
     * Please use the AppiumFieldDecorator class within the page factory. This way annotations
     * like @AndroidFindBy within the page objects.
     *
     * @param driver the appium driver created in the beforesuite method.
     */
    protected BasePO(AppiumDriver driver){
        this.driver = driver;
        initElements();
        loadProperties();
        waitUtils = new WaitUtils();
    }


    private void initElements() {
        PageFactory.initElements(new AppiumFieldDecorator(driver, IMPLICIT_WAIT, TimeUnit.SECONDS), this);
    }

    private void loadProperties() {

    }

}