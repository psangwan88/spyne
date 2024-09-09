package org.spyne.constants;

import org.openqa.selenium.WebDriver;

public interface Constants {

    public static ThreadLocal<WebDriver> driverList = new ThreadLocal<>();
    public static String url = "https://www.spyne.ai/image-upscaler";
    public static String resourcePath = System.getProperty("user.dir") + "/src/test/resources/";
}
