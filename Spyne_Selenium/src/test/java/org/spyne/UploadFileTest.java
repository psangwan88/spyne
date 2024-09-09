package org.spyne;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;
import java.util.HashMap;

public class UploadFileTest {
    public static WebDriver driver;
    public static String url = "https://www.spyne.ai/image-upscaler";
    @Test
    public void test1() throws InterruptedException, AWTException {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("directory_upgrade", true);
        options.setExperimentalOption("prefs", chromePrefs);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(url);
        validateURL(url);
        validateScreenElements();
        uploadImage();
    }

    public void validateURL(String input){
        Assert.assertEquals(driver.getCurrentUrl(), input,"Matching url");

    }

    public void validateScreenElements(){
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


    public void uploadImage() throws InterruptedException, AWTException {
        String xpath_uploadButton = "//img[@alt='Upload Icon']/..";
        String xpathinput = "//input[@type='file']";
        driver.findElement(By.xpath(xpathinput)).sendKeys("/Users/kumar.praveen/Documents/personal/Persistent/praveenk.jpg");




    }


}