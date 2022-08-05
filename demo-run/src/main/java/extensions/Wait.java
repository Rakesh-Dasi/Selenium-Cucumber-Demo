package extensions;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import managers.FileReaderManager;
import page.locators.BasePage;


public class Wait extends BasePage {

    public Wait(WebDriver driver) {
        super(driver);
    }

    private static final int DEFAULT_TIMEOUT = 5;

    public static void untilJqueryIsDone(WebDriver driver) {
        untilJqueryIsDone(driver, FileReaderManager.getInstance().getConfigReader().getImplicitlyWait());
    }

    public static void untilJqueryIsDone(WebDriver driver, Long timeoutInSeconds) {
        until(driver, (d) -> {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone)
                System.out.println("JQuery call is in Progress");
            return isJqueryCallDone;
        }, timeoutInSeconds);
    }

    public static void untilPageLoadComplete(Long timeoutInSeconds) {
        until(driver, (d) -> {
            Boolean isPageLoaded = ((JavascriptExecutor) driver).executeScript("return document.readyState")
                    .equals("complete");
            if (!isPageLoaded)
                System.out.println("Document is loading");
            return isPageLoaded;
        }, timeoutInSeconds);
    }

    public static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition) {
        until(driver, waitCondition, FileReaderManager.getInstance().getConfigReader().getImplicitlyWait());
    }

    public static void pageRefresh() throws InterruptedException {
        Wait.mediumSleep();
        driver.navigate().refresh();
    }

    public static void click(By element, WebDriver driver, int specifiedTimeout) {
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        ExpectedCondition<Boolean> elementIsClickable = arg0 -> {
            try {
                driver.findElement(element).click();
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        wait.until(elementIsClickable);
    }

    public static void waitForElementAndClick(WebElement element) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
            wait.until(elementIsDisplayed);
            element.click();
        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + element + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    public static void waitForElementAndDoubleClick(WebElement element) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
            wait.until(elementIsDisplayed);
            Actions action = new Actions(driver);
            action.doubleClick(element).build().perform();
        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + element + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void doubleClick(By elementLocator) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            Actions action = new Actions(driver);
            action.doubleClick(driver.findElement(elementLocator)).build().perform();
        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + elementLocator + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }
    }

    public static void doubleClick(WebElement element) throws NoSuchElementException {
        try {
            Actions action = new Actions(driver);
            action.doubleClick((element)).build().perform();

        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + element + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void dragAndDrop(By fromElementLocator, By toElementLocator) throws NoSuchElementException {
        try {
            Actions action = new Actions(driver);
            action.clickAndHold(driver.findElement(fromElementLocator))
                    .moveToElement(driver.findElement(toElementLocator)).release().build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Unable to drag and : " + fromElementLocator + " to " + toElementLocator,
                    exception);
        }
    }

    public static void waitForElementToBeDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
            wait.until(elementIsDisplayed);
        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + element + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void waitForElementToDisappear(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void waitForElementToDisappear(By elementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }
    }

    public static int getNoOfElements(By elementLocator) {
        try {
            List<WebElement> elements = driver.findElements(elementLocator);
            return elements.size();
        } catch (WebDriverException exception) {
            throw new WebDriverException(
                    "Element with locator : " + elementLocator + " was not displayed and unable to get the count",
                    exception);
        }
    }

    public static void clickAnyWhereOnWebPage() throws NoSuchElementException {
        try {
            Actions actions = new Actions(driver);
            Robot robot = new Robot();
            robot.mouseMove(50, 50);
            actions.click().build().perform();
            robot.mouseMove(200, 70);
            actions.click().build().perform();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static List<WebElement> getElements(By elementLocator) {
        try {
            List<WebElement> elements = driver.findElements(elementLocator);
            return elements;
        } catch (WebDriverException exception) {
            throw new WebDriverException(
                    "Element with locator : " + elementLocator + " was not displayed and unable to get the count",
                    exception);
        }
    }

    @SuppressWarnings("unused")
    public static void waitForElementClickableAndClick(WebElement element)
            throws Exception {
        for (int i = 0; i <= 5; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                break;
            } catch (WebDriverException exception) {
                throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void waitForElementClickableAndClick(By element) throws Exception {
        for (int i = 0; i <= 5; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element)));
                driver.findElement(element).click();
                break;
            } catch (WebDriverException exception) {
                throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
            }
        }
    }

    public static void clickWithJs(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", element);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void scrollToView(By elementLocator) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(elementLocator));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Unable to scroll : " + elementLocator + exception);
        }
    }

    public static void scrollToView(WebElement elementLocator) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", elementLocator);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Unable to scroll : " + elementLocator + exception);
        }
    }

    public static void scrollDown(int pixel) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("scroll(0, " + pixel + ");");
        } catch (WebDriverException exception) {
            throw new WebDriverException("Unable to scroll : ");
        }
    }

    public static void clickWithJs(By elementLocator) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", driver.findElement(elementLocator));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }
    }

    public static void sendKeysWithJs(WebElement element, String value) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].value = '" + value + "';", element);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void sendKeysWithJs(By element, String value) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].value = '" + value + "';", driver.findElement(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }
    }

    public static void waitForElementVisibleAndClick(By element) throws StaleElementReferenceException, NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
          //  js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
            //        driver.findElement(element));
            driver.findElement(element).click();
        } catch (StaleElementReferenceException exception) {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(element)));
            driver.findElement(element).click();
        } catch (TimeoutException exception) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static String waitForElementVisibleAndGetText(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
            return driver.findElement(element).getText();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static String waitForElementVisibleAndGetAttribute(By elementLocator, String attribute) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));
            return driver.findElement(elementLocator).getAttribute(attribute);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Unable to get attribute : " + elementLocator + " ", exception);
        }

    }

    public static void waitForElementVisible(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void mouseHoverOnElement(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(element).build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void slideToRight(By elementLocator, int start, int end) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
            Actions actions = new Actions(driver);
            actions.clickAndHold(driver.findElement(elementLocator));
            actions.moveByOffset(start, end).build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static void mouseHoverOnElement(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(element)).build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void moveToElementAndClick(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(element)).click().build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void mouseHoverOnElementByCordinates(By element, int x, int y) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(element), x, y).build().perform();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void mouseHoverOnElementByJS(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].onmouseover()", driver.findElement(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void waitForElementToBeStale(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void waitForElementToBeStale(By element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(driver.findElement(element))));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void waitForElementEnabled(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void switchToFrame(WebElement element) {
        driver.switchTo().frame(element);
    }

    public static void switchToDefaultDocument() {
        driver.switchTo().defaultContent();
    }

    public static void waitForElementPresentAndClick(By elementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
             //js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
                  //  driver.findElement(elementLocator));
            driver.findElement(elementLocator).click();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static void clearText(By elementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            //js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
   //                 driver.findElement(elementLocator));
            driver.findElement(elementLocator).clear();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static void Click(By elementLocator) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
          //  js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
            //        driver.findElement(elementLocator));
            driver.findElement(elementLocator).click();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static String GetElementText(By elementLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            return driver.findElement(elementLocator).getText();
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }
    }

    public static void implicitWait() {
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    public static void staticWait() {
        try {
            Thread.sleep(GlobalVairables.staticSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void shortSleep() {
        try {
            Thread.sleep(GlobalVairables.shortSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mediumSleep() {
        try {
            Thread.sleep(GlobalVairables.mediumSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void longSleep() {
        try {
            Thread.sleep(GlobalVairables.longSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void veryLongSleep() {
        try {
            Thread.sleep(GlobalVairables.VeryLongSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitForElementClearAndSetValue(WebElement element, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
            wait.until(elementIsDisplayed);
            element.clear();
            element.sendKeys(value);

        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + element + " was not displayed",
                    exception);
        } catch (Exception exception) {
            throw new WebDriverException("Element with locator : " + element + " was not displayed", exception);
        }

    }

    public static void waitForElementLocatorAndSetValue(By elementLocator, String value) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
          //  js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');",
          //          driver.findElement(elementLocator));
            driver.findElement(elementLocator).clear();
            driver.findElement(elementLocator).sendKeys(value);

        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + elementLocator + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static void waitForElementLocatorAndSetValue(By elementLocator, int num) throws NoSuchElementException {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementLocator));
            driver.findElement(elementLocator).clear();
            driver.findElement(elementLocator).sendKeys(String.valueOf(num));

        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + elementLocator + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static Boolean sendKeysWithDelay(By elementLocator, By elementDisplayed, String value)
            throws NoSuchElementException {
        Boolean isReslut = false;
        try {

            driver.findElement(elementLocator).clear();
            for (int i = 0; i < value.length(); i++) {
                driver.findElement(elementLocator).sendKeys(value.substring(i, i + 1));
                Wait.mediumSleep();
                isReslut = IsElementDisplayed(elementDisplayed);
            }
            return isReslut;
        } catch (StaleElementReferenceException exception) {
            throw new StaleElementReferenceException("Element with locator : " + elementLocator + " was not displayed",
                    exception);
        } catch (WebDriverException exception) {
            throw new WebDriverException("Element with locator : " + elementLocator + " was not displayed", exception);
        }

    }

    public static boolean IsElementDisplayed(WebElement element) {
        boolean result;
        try {
            result = element.isEnabled();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public static boolean IsElementDisplayed(By elementLocator) {
        boolean result;
        try {
            result = driver.findElement(elementLocator).isDisplayed();
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
        webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
        try {
            webDriverWait.until(waitCondition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
