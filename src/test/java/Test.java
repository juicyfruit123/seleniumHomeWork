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
    static WebDriver webDriver = null;

    @BeforeClass
    public static void testUP() {

        System.setProperty("webdriver.chrome.driver", "/home/anton/Загрузки/seleniumHomeWork/drivers/chromedriver");

        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void cleanUp() {
        webDriver.close();
        webDriver.quit();
    }

    @org.junit.Test
    public void rGS() {
        webDriver.get("http://sberbank.ru/ru/person");
        WebElement element = webDriver.findElement(By.xpath("//a[@class='hd-ft-region']"));
        element.click();
        webDriver.findElement(By.xpath("//input[@placeholder='Москва']")).sendKeys("Нижегородская область");
        webDriver.findElement(By.xpath("//a[contains(text(),'Нижегородская')]")).click();
        WebElement element1 = webDriver.findElement(By.xpath("//a[@class='hd-ft-region']"));
        Assert.assertTrue(element1.getAttribute("aria-label").contains("Нижегородская область"));
        List<WebElement> list = webDriver.findElements(By.xpath("//li[@class='footer__social_item']/a"));
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript("return arguments[0].scrollIntoView(true);", list.get(0));
        List<String> ar = Arrays.asList("http://twitter.com/sberbank", "https://www.facebook.com/bankdruzey", "http://www.youtube.com/sberbank", "http://instagram.com/sberbank"
                , "http://vk.com/sberbank", "https://ok.ru/sberbank");
        for (WebElement webElement : list) {
            String href = webElement.getAttribute("href");
            Assert.assertTrue(ar.contains(href));
        }
    }

}
