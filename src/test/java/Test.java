import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test {
    private static WebDriver webDriver = null;
    private static final List<String> ALL_SITES = Arrays.asList("https://www.facebook.com/bankdruzey", "http://twitter.com/sberbank", "http://www.youtube.com/sberbank", "http://instagram.com/sberbank"
            , "http://vk.com/sberbank", "https://ok.ru/sberbank");
    private static JavascriptExecutor JAVASCRIPT_EXECUTOR = null;

    @BeforeClass
    public static void testUP() {

        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");

        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
        JAVASCRIPT_EXECUTOR = (JavascriptExecutor) webDriver;
    }

    @AfterClass
    public static void cleanUp() {
        webDriver.close();
        webDriver.quit();
    }

    @org.junit.Test
    public void rGS() {
        webDriver.get("http://sberbank.ru/ru/person");
        webDriver.findElement(By.xpath("//a[@class='hd-ft-region']")).click();
        webDriver.findElement(By.xpath("//input[@placeholder='Москва']")).sendKeys("Нижегородская область");
        webDriver.findElement(By.xpath("//a[contains(text(),'Нижегородская')]")).click();
        WebElement currentRegion = webDriver.findElement(By.xpath("//div[@class='hd-ft-region__title']/span"));
        Assert.assertEquals("Неверно отобразилось местоположение на странице", currentRegion.getText(), "Нижегородская область");
        List<WebElement> iconFooterSocial = webDriver.findElements(By.xpath("//li[@class='footer__social_item']/a"));
        JAVASCRIPT_EXECUTOR.executeScript("return arguments[0].scrollIntoView(true);", iconFooterSocial.get(1));
        for (int i = 0; i < iconFooterSocial.size(); i++) {
            Assert.assertEquals("Неверно отобразилось местоположение на странице", ALL_SITES.get(i), iconFooterSocial.get(i).getAttribute("href"));
        }
    }

}
