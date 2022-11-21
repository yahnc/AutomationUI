package basicSelenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;

public class YopmailSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        // implicit
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        // page load wait
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.get("https://yopmail.com/");
    }

    @AfterEach
    public void cleanup(){
        driver.quit();
    }

    @Test
    public void verifyCRUDProject() throws InterruptedException {

        // login
        driver.findElement(By.xpath("//*[@id=\"login\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys("mojix@yopmail.com");
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/main/div[3]/div/div[1]/div[2]/div/div/form/div/div[1]/div[4]/button/i")).click();
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/header/div/div[1]/a")));

        Assertions.assertTrue(driver.findElement(By.xpath("/html/body/div[1]/header/div/nav/div[2]/a[1]")).isDisplayed()
                                    ,"ERROR login was incorrect");

        // Send Email
        driver.findElement(By.xpath("/html/body/div[1]/div/main/div[2]/div[1]/div/div[1]/div[3]/div/button/i")).click();
        driver.switchTo().frame("ifmail");
        driver.findElement(By.xpath("//*[@id='msgto']")).click();
        driver.findElement(By.xpath("//*[@id='msgto']")).sendKeys("mojix@yopmail.com");
        driver.findElement(By.xpath("//*[@id='msgsubject']")).click();
        driver.findElement(By.xpath("//*[@id='msgsubject']")).sendKeys("Asunto");
        driver.findElement(By.xpath("//*[@id='msgbody']")).click();
        driver.findElement(By.xpath("//*[@id='msgbody']")).sendKeys("Hola");
        driver.findElement(By.xpath("/html/body/button/span")).click();
        Thread.sleep(1000);
        //Verificar
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//*[@id=\"refresh\"]")).click();
        Thread.sleep(1000);
        driver.switchTo().frame("ifinbox");
        Assertions.assertTrue(driver.findElement(By.xpath("/html/body/div[2]/div[2]/button/div[1]/span[2]")).isDisplayed()
                ,"ERROR login was incorrect");

        Thread.sleep(1000);
    }
}
