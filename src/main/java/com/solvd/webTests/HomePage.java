package com.solvd.webTests;

import com.zebrunner.carina.utils.config.Configuration;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class HomePage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//a[@href='https://catalog.onliner.by/mobile']")
    private ExtendedWebElement mobilePhonesLink;

    @FindBy(xpath = "//a[@id='submit-button']")
    private ExtendedWebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(mobilePhonesLink);
    }

    public void open() {
        openURL(Configuration.getRequired("url"));
    }

    public MobileCatalogPage clickMobilePhones() {
        mobilePhonesLink.click();
        return new MobileCatalogPage(getDriver());
    }

    public void acceptCookiesIfPresent() {
        if (acceptCookiesButton.isElementPresent(3)) {
            acceptCookiesButton.click();
            LOGGER.info("Cookie popup accepted");
        }
    }

}
