package org.spyne.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.spyne.baseclass.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;

public class UploadFileTest extends BaseClass {
    @Test
    public void test1() throws InterruptedException, AWTException {
        WebDriver driver = driverList.get();
        driver.get(url);
        validateURL(driver,url);
        validateScreenElements(driver);
        uploadImage(driver);
    }

    public void validateURL(WebDriver driver, String input){
        Assert.assertEquals(driver.getCurrentUrl(), input,"Matching url");

    }

    public void validateScreenElements(WebDriver driver){
        String xpath_logo = "//img[@alt='spyne logo']";
        String xpath_uploadBox = "//div[contains(@class,'uploadBox')]";
        String xpath_uploadButton = "//img[@alt='Upload Icon']/..";
        String xpath_testImages = "//img[@alt='Upload Icon']/..//following-sibling::div/div/img";
        String xpath_headerOptions = "//ul[@class='flex gap-3 items-center justify-center h-full']";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath_logo)));
        Assert.assertTrue(driver.findElement(By.xpath(xpath_logo)).isDisplayed(), "Checking if logo is displayed or not");
        Assert.assertTrue(driver.findElement(By.xpath(xpath_uploadBox)).isDisplayed(), "Checking if upload box is displayed or not");
        Assert.assertTrue(driver.findElement(By.xpath(xpath_uploadButton)).isDisplayed(), "Checking if upload button is displayed or not");
        Assert.assertTrue(driver.findElement(By.xpath(xpath_testImages)).isDisplayed(), "Checking if demo images is displayed or not");
        Assert.assertTrue(driver.findElement(By.xpath(xpath_headerOptions)).isDisplayed(), "Checking if headers is displayed or not");

    }


    public void uploadImage(WebDriver driver) throws InterruptedException, AWTException {
        String xpath_uploadButton = "//img[@alt='Upload Icon']/..";
        String xpathinput = "//input[@type='file']";
        driver.findElement(By.xpath(xpathinput)).sendKeys(resourcePath + "testValidImage.jpg");

    }


}