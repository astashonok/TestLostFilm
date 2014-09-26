package com.peter.webdriver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class TestLostFilmUnauthorizedUser {
    static WebDriver driver;
    static BaseOperations agent;

    @BeforeClass
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Petro\\IdeaProjects\\seleniumWebDriverIdea\\src\\main\\resources\\chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary",
                "C:\\Users\\Petro\\IdeaProjects\\seleniumWebDriverIdea\\src\\main\\resources\\chromedriver.exe");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        agent = new BaseOperations(driver);
    }
    @Test
    public void testUnauthorizedUserLoad () throws InterruptedException{
        driver.get("http://www.lostfilm.tv/");
        agent.verifyUserLoggedOut();
        agent.selectSerial("Декстер");
        agent.openDownloadPage("guest", 6);
        agent.verifyLoginPopup();
    }

    @AfterClass
    public static void cleanUp(){
        driver.quit();
    }
}
