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

public class TodoistSeleniumTest {

    WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/driver/chromedriver.exe");
        driver = new ChromeDriver();
        // implicit
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        // page load wait
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().window().fullscreen();
        driver.get("https://todoist.com/");
    }

    @AfterEach
    public void cleanup(){
        driver.quit();
    }

    @Test
    public void verifyCRUDProject() throws InterruptedException {
        driver.manage().window().fullscreen();

        // login
        driver.findElement(By.xpath("/html/body/div[1]/div/div/header/nav[2]/div/ul[2]/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"element-0\"]")).sendKeys("sebastianhpancho@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"element-3\"]")).sendKeys("Bootcamp1*");
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[1]/div/div/form/button")).click();

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/button[5]/div/img")));

        Assertions.assertTrue(driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/button[5]/div/img")).isDisplayed()
                                    ,"ERROR login was incorrect");

        // create
        String nameProject="Mojix"+new Date().getTime();
        driver.findElement(By.xpath("//*[@id=\"left_menu_inner\"]/div/div[1]/div/div[1]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"edit_project_modal_field_name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"edit_project_modal_field_name\"]")).sendKeys(nameProject);
        driver.findElement(By.xpath("/html/body/reach-portal/div[2]/div/div/div[2]/div/form/footer/button[2]")).click();
        Thread.sleep(1000);
        int actualResult=driver.findElements(By.xpath("//a[contains(@aria-label, "+nameProject+")]")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The project was not created");

        String namePro="Update"+new Date().getTime();

        // Update
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/header/div/h1/span")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/header/div/form/input")).clear();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/header/div/form/input")).sendKeys(namePro);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/header/div/form/div/button[1]/span")).click();
        Thread.sleep(1000);
        actualResult=driver.findElements(By.xpath("//a[contains(@aria-label, "+namePro+")]")).size();
        Assertions.assertTrue(actualResult >= 1
                ,"ERROR The task was not updated");

        //Create task
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li/button")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li/form/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div/div")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li/form/div[1]/div[1]/div[1]/div/div/div[2]/div/div/div/div")).sendKeys("Task");
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li/form/div[2]/div/div/button[2]/span")).click();

        //Update task
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li[1]/div/div[2]/div[1]/div/div")).click();
        driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div/div[1]/div/div/div/div/div[1]")).click();
        driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div/div[1]/div/div/div/form/div[1]/div/div[1]/div/div/div/div/div/div/div")).sendKeys("Update");
        driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div/div[1]/div/div/div/form/div[2]/div/div/button[2]")).click();
        driver.findElement(By.xpath("//button[contains(@aria-label,'Cerrar ventana')]")).click();

        //Delete task
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/main/div[1]/div[2]/div/div/ul/li/section/div/ul/li[1]/div/div[2]/div[1]/div/div")).click();
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div/div[2]/div/header/div/div[2]/div/div[1]/button")).click();
        driver.findElement(By.xpath("//button[contains(@aria-label,'Eliminar tarea')]")).click();
        driver.findElement(By.xpath("/html/body/div[6]/div/div/div[2]/div/form/footer/div/button[2]/span")).click();

        // Delete Project
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[2]/div/div/div/div/ul/li[3]/div/div/div/button/span")).click();
        driver.findElement(By.xpath("/html/body/div[5]/div[2]/div/ul/li[13]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='a8af2163 _3d1243b2 _352995bd f9408a0e _56a651f6']")).click();
        Thread.sleep(10000);
        actualResult=driver.findElements(By.xpath("//a[contains(@aria-label,"+namePro+")]")).size();
        Assertions.assertTrue(actualResult == 0
                ,"ERROR The project was not removed");
    }
}
