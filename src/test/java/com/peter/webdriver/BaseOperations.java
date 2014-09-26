package com.peter.webdriver;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class BaseOperations {
    WebDriver driver;

    BaseOperations(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String type, String user, String password) {
        switch (type) {
            case "Simple":
                driver.findElement(By.name("login")).sendKeys(user);
                driver.findElement(By.name("password")).sendKeys(password);
                driver.findElement(By.className("form_b")).click();
                break;
            case "Popup":
                driver.findElement(By.className("login-btn")).click();
                driver.findElement(By.id("l_login")).sendKeys(user);
                driver.findElement(By.id("l_password")).sendKeys(password);
                driver.findElement(By.id("login-button")).click();
                break;
        }

    }

    public void logout() throws InterruptedException {
        driver.findElement(By.linkText("Выйти")).click();
        Thread.sleep(2000);
    }

    public void selectSerial(String serial) throws InterruptedException {
        driver.findElement(By.partialLinkText(serial)).click();
        Thread.sleep(2000);
    }

    public void openDownloadPage(String userType, int season) throws InterruptedException {
        openDownloadPage(userType, season, "all");
    }


    public void openDownloadPage(String userType, int season, String episode) throws InterruptedException {
        String serialId = driver.getCurrentUrl().split("cat=")[1];
        int serialIdInt = Integer.parseInt(serialId);
        switch (userType) {
            case "user":
                if (episode == "all")
                    driver.findElement(By.xpath("//td[@onclick=\"ShowAllReleases('" + serialIdInt + "','" + season + "','99')\"]")).click();
                 else
                driver.findElement(By.xpath("//td[@onclick=\"ShowAllReleases('" + serialIdInt + "','" + season + "','" + episode + "')\"]")).click();
                break;
            case "guest":
                if (episode == "all")
                    driver.findElement(By.xpath("//td[@onclick[contains(.,\"ShowAllReleases('" + serialIdInt + "','" + season + "','99')\")]]")).click();
                 else driver.findElement(By.xpath("//td[@onclick=\"ShowAllReleases('" + serialIdInt + "','" + season + "','" + episode + "')\"]")).click();
                break;
        }


    }

    public void downloadStart(){
        Set<String> windowId = null;
        windowId = driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();
        String mainWinID = itererator.next();
        String  newAdwinID = itererator.next();
        driver.switchTo().window(newAdwinID);
        driver.findElement(By.partialLinkText("http://tracktor.in")).click();
        driver.close();
        driver.switchTo().window(mainWinID);
    }

    public void verifyNumberOfEpisodes(String message, int season, int expectedEpisodesNumber) {

        Assert.assertEquals(message, expectedEpisodesNumber, getNumberOfEpisodes(season));
    }

    private int getNumberOfEpisodes(int season) {
        List<WebElement> allElements = driver.findElements(By.xpath("//span[contains(text(),\""+season+" сезон \")]"));
      return allElements.size();
    }

    public void verifyUserLoggedIn(String message) {
        boolean present = false;
        try {driver.findElement(By.xpath("//a[@class=\"user_menu_link\"][contains(.,\"Выйти\")]"));
            present = true;
        } catch (org.openqa.selenium.NoSuchElementException e){
        }
        Assert.assertTrue(message, present);
    }

    public void verifyUserLoggedOut() {
        driver.findElement(By.xpath("//td/input[@value=' Войти ']"));
    }

    public void verifyLoginPopup() {
        driver.findElement(By.id("login-button"));
    }
}
