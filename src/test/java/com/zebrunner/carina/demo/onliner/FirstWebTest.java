package com.zebrunner.carina.demo.onliner;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.desktop.onliner.HomePage;
import com.zebrunner.carina.demo.gui.pages.desktop.onliner.MobileCatalogPage;
import com.zebrunner.carina.demo.gui.pages.desktop.onliner.ProductDetailPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class FirstWebTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testOnlinerMobileFlow() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.acceptCookiesIfPresent();

        MobileCatalogPage catalogPage = homePage.clickMobilePhones();

        List<ExtendedWebElement> cards = catalogPage.getProductCards();
        Assert.assertFalse(cards.isEmpty(), "Product list is empty!");

        SoftAssert softAssert = new SoftAssert();
        for (ExtendedWebElement card : cards) {
            try {
                String title = catalogPage.getCardTitle(card);
                softAssert.assertTrue(catalogPage.isCardTitlePresent(card),
                        "Title missing for: " + title);
                softAssert.assertTrue(catalogPage.isCardPhotoPresent(card),
                        "Photo missing for: " + title);
                softAssert.assertTrue(catalogPage.isCardPricePresent(card),
                        "Price missing for: " + title);
                if (catalogPage.isCardRatingPresent(card)) {
                    softAssert.assertFalse(catalogPage.getCardRating(card).isEmpty(),
                            "Rating empty for: " + title);
                }
            } catch (Exception e) {
                LOGGER.warn("Skipping stale card: {}", e.getMessage());
            }
        }
        softAssert.assertAll();

        catalogPage.filterByApple();

        Assert.assertTrue(catalogPage.isIPhone17Present(), "iPhone 17 not found!");
        ProductDetailPage detailPage = catalogPage.openIPhone17();

        SoftAssert detailAssert = new SoftAssert();
        detailAssert.assertTrue(detailPage.isTitleMatchesListing(), "Title mismatch!");
        detailAssert.assertTrue(detailPage.isImagePresent(), "Image missing!");
        detailAssert.assertTrue(detailPage.isPricePresent(), "Price missing!");
        detailAssert.assertTrue(detailPage.isPriceMatchesListing(), "Price mismatch!");
        detailAssert.assertAll();
    }
}