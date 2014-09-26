package com.peter.webdriver;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestLostFilmGeneral {
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
    public void test1 () throws InterruptedException{
        driver.get("http://www.lostfilm.tv/");
        //agent.login("Simple", "peti2005@gmail.com", "e5bCAY1fga");
        agent.verifyUserLoggedIn("User was not logged in successfully");
        agent.selectSerial("Декстер");
        agent.verifyNumberOfEpisodes("The number is incorrect", 8, 12);
        agent.openDownloadPage("user", 1);
        agent.downloadStart();
        agent.openDownloadPage("user", 5,"02");
        agent.downloadStart();
        agent.logout();
        agent.verifyUserLoggedOut();
    }

    @AfterClass
    public static void cleanUp(){
        driver.quit();
    }
}
