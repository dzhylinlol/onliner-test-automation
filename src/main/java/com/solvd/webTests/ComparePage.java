package com.solvd.webTests;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.List;

public class ComparePage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//h1[contains(text(),'Сравнение')]")
    private ExtendedWebElement pageTitle;

    @FindBy(xpath = "(//span[@class='product-summary__caption'])[position()<=2]")
    private List<ExtendedWebElement> phoneTitles;

    @FindBy(xpath = "(//a[contains(@class,'product-summary__price')])[position()<=2]")
    private List<ExtendedWebElement> phonePrices;

    public ComparePage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(pageTitle);
    }

    public boolean isPageTitleCorrect() {
        return pageTitle.isElementPresent(5);
    }

    public List<ExtendedWebElement> getPhoneTitles() {
        return phoneTitles;
    }

    public List<ExtendedWebElement> getPhonePrices() {
        return phonePrices;
    }
}