package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wrappers {

    WebDriver driver;
    WebDriverWait wait;

    public Wrappers(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        //.driver = driver;
    }

    public void openYoutube() {
        driver.get("https://www.youtube.com/");
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }

    }

    public void clickOnSideBar(String title) {

        WebElement selectingMovies = wait.until(
                ExpectedConditions.
                        presenceOfElementLocated(
                                By.xpath("//yt-formatted-string[text()='"+title+"']")));
        selectingMovies.click();



    }

    public int getNumberFromText(String imp){
        StringBuilder rev = new StringBuilder();
        for (int i = 0; i < imp.length(); i++){

            if (Character.isDigit(imp.charAt(i)))
                rev.append(imp.charAt(i));
        }

        if (rev.length() > 0){
            return Integer.parseInt(rev.toString());
        }else
            return 0    ;
    }

}
