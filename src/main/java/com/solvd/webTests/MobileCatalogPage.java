package com.solvd.webTests;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class MobileCatalogPage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(xpath = "//div[@class='catalog-form__offers-flex']")
    private ExtendedWebElement firstProductCard;

    @FindBy(xpath = "//div[@class='catalog-form__offers-flex']")
    private List<ExtendedWebElement> productCards;

    @FindBy(xpath = "//li[contains(@class,'catalog-form__checkbox-item')]//span[text()='Apple']")
    private ExtendedWebElement appleFilter;

    @FindBy(xpath = "(//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'iphone 17')])[1]")
    private ExtendedWebElement iPhone17Link;

    @FindBy(xpath = "//a[contains(@class,'catalog-interaction__sub_main')]")
    private ExtendedWebElement compareButton;

    public MobileCatalogPage(WebDriver driver) {
        super(driver);
        setUiLoadedMarker(firstProductCard);
    }

    public List<ExtendedWebElement> getProductCards() {
        return productCards;
    }

    public boolean isCardTitlePresent(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//a[contains(@class,'catalog-form__link_primary-additional') and contains(@class,'catalog-form__link_base-additional')]"))
                   .isElementPresent(3);
    }

    public boolean isCardPhotoPresent(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//img"))
                   .isElementPresent(3);
    }

    public boolean isCardPricePresent(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//div[contains(@class,'catalog-form__description_huge')]"))
                   .isElementPresent(3);
    }

    public boolean isCardRatingPresent(ExtendedWebElement card) {
        try {
            ExtendedWebElement rating = card.findExtendedWebElement(
                    By.xpath(".//a[contains(@class,'catalog-form__rating')]"));
            return rating != null && rating.isElementPresent(3);
        } catch (Exception e) {
            LOGGER.warn("Could not check rating: {}", e.getMessage());
            return false;
        }
    }

    public String getCardTitle(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//a[contains(@class,'catalog-form__link_primary-additional') and contains(@class,'catalog-form__link_base-additional')]"))
                   .getText();
    }

    public String getCardPrice(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//div[contains(@class,'catalog-form__description_huge')]"))
                   .getText();
    }

    public String getCardRating(ExtendedWebElement card) {
        return card.findExtendedWebElement(
                           By.xpath(".//a[contains(@class,'catalog-form__rating')]"))
                   .getText();
    }

    public void filterByApple() {
        appleFilter.scrollTo();
        pause(1);
        appleFilter.click();
        pause(2);
    }

    public boolean isIPhone17Present() {
        return iPhone17Link.isElementPresent(3);
    }

    public ProductDetailPage openIPhone17() {
        if (!isIPhone17Present()) {
            throw new RuntimeException("iPhone 17 not found on the page");
        }
        ExtendedWebElement card = productCards.stream()
                                              .filter(c -> isCardTitlePresent(c) &&
                                                      getCardTitle(c).toLowerCase().contains("iphone 17"))
                                              .findFirst()
                                              .orElseThrow(() -> new RuntimeException("iPhone 17 card not found"));

        String title = getCardTitle(card);
        String price = getCardPrice(card);
        String rating = isCardRatingPresent(card) ? getCardRating(card) : null;

        iPhone17Link.click();
        return new ProductDetailPage(getDriver(), title, price, rating);
    }

    public ComparePage selectFirstTwoAndCompare() {
        List<ExtendedWebElement> cards = getProductCards();
        cards.get(0).findExtendedWebElement(
                By.xpath(".//label[contains(@title,'равнению')]")).click();
        cards.get(1).findExtendedWebElement(
                By.xpath(".//label[contains(@title,'равнению')]")).click();
        compareButton.click();
        return new ComparePage(getDriver());
    }
}