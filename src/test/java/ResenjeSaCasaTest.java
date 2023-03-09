import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ResenjeSaCasaTest {
    WebDriver driver;
    WebDriverWait wait;
    By loginLink = By.cssSelector("a[class='ico-login']");
    By email = By.xpath("//input[@id='Email']");
    By password = By.cssSelector(".inputs label[for='Password'] + input");
    By loginButton = By.cssSelector("input[class='button-1 login-button']");

    //By headerMenuComputerslink = By.partialLinkText("Computers");
    By headerMenuComputers = By.xpath("//ul[@class='top-menu']//a[contains(text(),'Computers')]");
    By headerMenuNotebooks = By.xpath("//ul[@class='top-menu']//a[contains(text(),'Notebooks')]");
    By addProductToBasket = By.cssSelector("input[value^=Add]");
    By headerMenuBooks = By.xpath("//ul[@class='top-menu']//a[contains(text(),'Books')]");
    By goToBasket = By.cssSelector(".ico-cart .cart-label");
    By valueOfEachElement = By.cssSelector(".product-subtotal");
    By totalValueElement = By.xpath("//span[text()='Sub-Total:']/../following-sibling::td/span/span");
    // Shipping //span[contains(text(),'Shipping:')]/../following-sibling::td/span/span
    // Tax //span[contains(text(),'Tax:')]/../following-sibling::td/span/span


    public WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        //return driver.findElement(locator);
    }

    public void clickOnElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        //getElement(locator).click();
    }

    public void typeIn(By locator, String text) {
        getElement(locator).sendKeys(text);
    }

    public String getTextFromElement(By locator) {
        return getElement(locator).getText();
    }

    public void clickOnRandomElement(By locator) {
        Random random = new Random();
        List<WebElement> list = driver.findElements(locator);
        int randomElement = random.nextInt(list.size());
        list.get(randomElement).click();

    }

    public void hover (By locator) {
        Actions actions = new Actions(driver);
        WebElement element = getElement(locator);
        actions.moveToElement(element).build().perform();
    }
    public void hoverAndClick (By locator) {
        Actions actions = new Actions(driver);
        WebElement element = getElement(locator);
        actions.moveToElement(element).click().build().perform();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() throws InterruptedException {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Thread.sleep(3000);
        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shoppingCart() {
        clickOnElement(loginLink);
        typeIn(email, "kupac@gmail.com");
        typeIn(password, "password");
        clickOnElement(loginButton);
        hover(headerMenuComputers);
        hoverAndClick(headerMenuNotebooks);
        clickOnElement(addProductToBasket);
        clickOnElement(headerMenuBooks);
        clickOnRandomElement(addProductToBasket);
        clickOnElement(goToBasket);

        List<WebElement> productPricesInBasket = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(valueOfEachElement));
        double sum = 0;
        for (int i = 0; i <productPricesInBasket.size(); i++) {
            sum += Double.parseDouble(productPricesInBasket.get(i).getText());
        }

            System.out.println("Zbir cena je " + sum);

            double total = Double.parseDouble(getTextFromElement(totalValueElement));
            System.out.println("Total je " + total);

            Assert.assertTrue(sum == total, "Prices are not equal to total amount!");
            
        }

        




    }
