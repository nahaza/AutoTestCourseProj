package loginTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    WebDriver webDriver;

    @Test
    public void validLogin(){
        File fileFF = new File("./src/drivers/91/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", fileFF.getAbsolutePath());
        webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        webDriver.get("https://qa-complex-app-for-testing.herokuapp.com/");
        System.out.println("Site was open");

        webDriver.findElement(By.xpath(".//input[@placeholder='Username']")).clear();
        webDriver.findElement(By.xpath(".//input[@placeholder='Username']")).sendKeys("auto");
        System.out.println("'auto' was entered");
        webDriver.findElement(By.xpath(".//input[@placeholder='Password']")).clear();
        webDriver.findElement(By.xpath(".//input[@placeholder='Password']")).sendKeys("123456qwerty");
        System.out.println("'pass' was entered");

        webDriver.findElement(By.xpath(".//button[text()='Sign In']")).click();
        System.out.println("Button Sign-in was clicked");

        Assert.assertTrue("Button Sign-out is not displayed", isButtonSignoutVisible());
        System.out.println("The end of valid login test case");


        webDriver.quit();

    }

    @Test
    public void inValidLogin(){
        File fileFF = new File("./src/drivers/91/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", fileFF.getAbsolutePath());
        webDriver = new ChromeDriver();

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        webDriver.get("https://qa-complex-app-for-testing.herokuapp.com/");
        System.out.println("Site was open");

        webDriver.findElement(By.xpath(".//input[@placeholder='Username']")).clear();
        webDriver.findElement(By.xpath(".//input[@placeholder='Username']")).sendKeys("1auto1");
        System.out.println("'1auto1' was entered");
        webDriver.findElement(By.xpath(".//input[@placeholder='Password']")).clear();
        webDriver.findElement(By.xpath(".//input[@placeholder='Password']")).sendKeys("123456qwerty");
        System.out.println("'pass' was entered");

        webDriver.findElement(By.xpath(".//button[text()='Sign In']")).click();
        System.out.println("Button Sign-in was clicked");

        Assert.assertFalse("Button Sign-out is displayed", isButtonSignoutVisible());
        System.out.println("Button Sign-out is not displayed");
        Assert.assertTrue("Button Sign-in is not displayed", isButtonSigninVisible());
        System.out.println("Button Sign-in is displayed");
        Assert.assertTrue("Invalid credentials message is not displayed", isMessageInvalidUsernamePasswordVisible());
        System.out.println("Invalid credentials message is displayed");
        System.out.println("The end of invalid login test case");

        webDriver.quit();

    }


    private boolean isButtonSignoutVisible() {
        try{
        return webDriver.findElement(By.xpath(".//button[text()='Sign Out']")).isDisplayed();
    }catch (Exception e){
            return false;
        }
    }

    private boolean isButtonSigninVisible() {
        try{
            return webDriver.findElement(By.xpath(".//button[text()='Sign In']")).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    private boolean isMessageInvalidUsernamePasswordVisible() {
        try{
            return webDriver.findElement(By.xpath(".//div[text()='Invalid username / password']")).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }
}
