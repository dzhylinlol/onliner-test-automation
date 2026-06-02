package com.zebrunner.carina.demo.gui.pages.desktop.onliner;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class ProductDetailPage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String listingTitle;
    private final String listingPrice;
    private final String listingRating;

    @FindBy(xpath = "//h1[contains(@class,'catalog-masthead__title')]")
    private ExtendedWebElement detailTitle;

    @FindBy(xpath = "//div[contains(@class,'offers-description__preview')]//img")
    private ExtendedWebElement detailImage;

    @FindBy(xpath = "//div[@data-gtm-selector='fi_location__buybox']//div[contains(@class,'h_txt_tl0')]")
    private ExtendedWebElement detailPrice;

    public ProductDetailPage(WebDriver driver, String listingTitle, String listingPrice, String listingRating) {
        super(driver);
        this.listingTitle = listingTitle;
        this.listingPrice = listingPrice;
        this.listingRating = listingRating;
        setUiLoadedMarker(detailTitle);
    }

    public String getDetailTitle() {
        return detailTitle.getText().trim();
    }

    public String getDetailPrice() {
        return detailPrice.getText().trim();
    }

    public boolean isTitleMatchesListing() {
        return listingTitle.toLowerCase()
                           .contains(getDetailTitle().toLowerCase());
    }

    public boolean isPriceMatchesListing() {
        String detailPrice = getDetailPrice();
        LOGGER.info("Listing price: {}", listingPrice);
        LOGGER.info("Detail price: {}", detailPrice);
        return listingPrice.replaceAll("[^0-9]", "")
                           .contains(detailPrice.replaceAll("[^0-9]", ""));
    }

    public boolean isImagePresent() {
        return detailImage.isElementPresent(5);
    }

    public boolean isPricePresent() {
        return detailPrice.isElementPresent(5);
    }
}