package demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
    ChromeDriver driver;
    Wrappers wrappers;
    WebDriverWait wait;
    SoftAssert softAssert;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wrappers = new Wrappers(driver, wait);
        softAssert = new SoftAssert();

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() {


        wrappers.openYoutube();

        String actualUrl = driver.getCurrentUrl();
        String expectedUrl = "https://www.youtube.com/";
        softAssert.assertEquals(expectedUrl, actualUrl);
        System.out.println("Step 1 : Verify correct URL" + (actualUrl.contains(expectedUrl) ? ":-PASS" : "FAIL"));

        WebElement about = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='About']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", about);
        about.click();
        try {
            WebElement descriptionMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[@class='ytabout__content']/h1")));
            WebElement descriptionMessage2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//p[@class='lb-font-display-3 lb-font-color-text-primary'])[1]")));
            WebElement descriptionMessage3 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//p[@class='lb-font-display-3 lb-font-color-text-primary'])[2]")));

            System.out.println(descriptionMessage1.getText());
            System.out.println(descriptionMessage2.getText());
            System.out.println(descriptionMessage3.getText());
            assert true;
        } catch (Exception e) {
            assert false;
        }

        wrappers.sleep(20000);
        System.out.println("test case 1: - PASS");

    }

    @Test
    public void testCase02() {

        wrappers.openYoutube();
        wrappers.clickOnSideBar("Films");
        wrappers.sleep(20000);

        WebElement scroll = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[7]")));
        scroll.click();
        wrappers.sleep(1000);
        scroll.click();
        wrappers.sleep(1000);
        scroll.click();

        WebElement rating = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//p[@class='style-scope ytd-badge-supported-renderer'])[32]")));
        String ratingText = rating.getText();

        softAssert.assertTrue(ratingText.contains("A"), "The movie is not marked as “A” ");
        System.out.println("Step 2 : Verify the movie rating is A" + (ratingText.contains("A") ? ":- PASS" : " FAIL"));
        System.out.println(ratingText);
        wrappers.sleep(10000);


        WebElement movieCategory = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[contains(text(),'Drama')])[3]")));
        String movieCategoryValue = movieCategory.getText();

        softAssert.assertTrue(
                movieCategoryValue.contains("Comedy") ||
                        movieCategoryValue.contains("Animation"),
                "The movie neither “Comedy” or “Animation”");
        System.out.println("Step 3 : The movie neither “Comedy” or “Animation”" + (movieCategoryValue.contains("comedy") ||
                movieCategoryValue.contains("Animation") ? ":- PASS" : ":- FAIL"));

        wrappers.sleep(20000);

    }

    @Test
    public void testCase03() {

        wrappers.openYoutube();
        wrappers.clickOnSideBar("Music");

//        WebElement about = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[@id='title'])[2]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
        wrappers.sleep(1000);
//        js.executeScript("arguments[0].scrollIntoView();", about);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,250)", "");

//        WebElement scroll = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[8]")));
        WebElement scroll = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[8]"));
        scroll.click();
        wrappers.sleep(1000);
        scroll.click();
        wrappers.sleep(1000);
        scroll.click();
        wrappers.sleep(2000);

        WebElement nameofThePlayList = driver.findElement(By.xpath("//*[@id=\"items\"]/ytd-compact-station-renderer[11]/div/a/h3"));
        wrappers.sleep(2000);
        //*[@id="items"]/ytd-compact-station-renderer[11]/div/a/h3
        String playList = nameofThePlayList.getText();
        wrappers.sleep(2000);

//        wrappers.sleep(1000);
        System.out.println("Name of the playlist is :- " + playList);
        wrappers.sleep(2000);

        WebElement tracks = driver.findElement(By.xpath("(//p[@id='video-count-text'])[11]"));
        wrappers.sleep(2000);

        int tracksCount = wrappers.getNumberFromText(tracks.getText());
        softAssert.assertTrue(tracksCount<=50,"Tracks count is grater then 50");
        System.out.println(tracksCount + " Is less then equal 50" + ((tracksCount<=50) ? ": YES": ": No"));

        wrappers.sleep(200000);



//    }


//    @Test
//    public void testCase03() {
//        wrappers.openYoutube();
//        wrappers.clickOnSideBar("Music");
//        wrappers.sleep(20000);
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0,250)", "");
//
//        WebElement scroll = driver.findElement(By.xpath("(//div[@class='yt-spec-touch-feedback-shape__fill'])[8]"));
////
////        while (scroll.isDisplayed()) {
////            scroll.click();
////            wrappers.sleep(1000);
////        }


    }


}