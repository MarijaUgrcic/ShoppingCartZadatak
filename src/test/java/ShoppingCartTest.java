import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartTest {

    WebDriver driver;
    WebDriverWait wait;
    By loginLink = By.cssSelector("a[class='ico-login']");
    By email = By.xpath("//input[@id='Email']");
    By password = By.cssSelector(".inputs label[for='Password'] + input");
    By loginButton = By.cssSelector("input[class='button-1 login-button']");
    By category = By.cssSelector("a[href='/books']");
    //2.opcija u padajucem meniju//ul[@class='list']/li[@class='inactive']/a[starts-with(text(),'Books')]
    By product1 = By.cssSelector("input[onclick*=\"AjaxCart.addproducttocart_catalog('/addproducttocart/catalog/13/1/1\"]");
    By product2 = By.cssSelector("input[onclick*=\"AjaxCart.addproducttocart_catalog('/addproducttocart/catalog/45/1/1\"]");
    By product3 = By.cssSelector("input[onclick*=\"AjaxCart.addproducttocart_catalog('/addproducttocart/catalog/22/1/1\"]");
    By shoppingCartLink = By.cssSelector("p[class='content'] a[href='/cart']");
    By valueOfEachElement = By.cssSelector(".product-subtotal");
    By totalValueElement = By.xpath("//span[text()='Sub-Total:']/../following-sibling::td/span/span");

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
        clickOnElement(category);
        clickOnElement(product1);
        clickOnElement(product2);
        clickOnElement(product3);
        clickOnElement(shoppingCartLink);


        List<WebElement> allElements = driver.findElements(valueOfEachElement);
        List<Double> doubleList = new ArrayList<>();
        String actualValueEachElementText;
        double actualValueEachElement;

        for (WebElement we:allElements) {
            actualValueEachElementText = we.getText();
            actualValueEachElement = Double.parseDouble(actualValueEachElementText);
            doubleList.add(actualValueEachElement);
        }

        double expectedTotalValue = 0;
        for(int i = 0; i < doubleList.size(); i++) {
            expectedTotalValue += doubleList.get(i);
        }
        System.out.println(expectedTotalValue);

        String actualTextFromTotalValueElement = getTextFromElement(totalValueElement);
        double actualTotalValue = Double.parseDouble(actualTextFromTotalValueElement);
        System.out.println(actualTotalValue);

        Assert.assertEquals(actualTotalValue,expectedTotalValue);
    }

}


//By shoppingCartButton = By.cssSelector("input[value='Go to cart']");
//.ico-cart span[class='cart-label']
////span[@class='cart-label']
////div[@id='flyout-cart']
//input[value='Go to cart']
////a[@class='ico-cart']
//li[id='topcartlink']
////label[text()='Last name:']/following-sibling::input")
////a[text()='Computing and Internet']
////ul[@class='list']/li[@class='inactive']/a[@href="/books"]