package com.solvd.webTests;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SecondWebTest implements IAbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void testOnlinerCompareFlow() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        homePage.acceptCookiesIfPresent();

        MobileCatalogPage catalogPage = homePage.clickMobilePhones();
        catalogPage.filterByApple();

        List<ExtendedWebElement> cards = catalogPage.getProductCards();
        String title1 = catalogPage.getCardTitle(cards.get(0));
        String title2 = catalogPage.getCardTitle(cards.get(1));
        String price1 = catalogPage.getCardPrice(cards.get(0));
        String price2 = catalogPage.getCardPrice(cards.get(1));

        LOGGER.info("Card 1 title: {}", title1);
        LOGGER.info("Card 2 title: {}", title2);
        LOGGER.info("Card 1 price: {}", price1);
        LOGGER.info("Card 2 price: {}", price2);

        ComparePage comparePage = catalogPage.selectFirstTwoAndCompare();

        List<ExtendedWebElement> compareTitles = comparePage.getPhoneTitles();
        List<ExtendedWebElement> comparePrices = comparePage.getPhonePrices();

        LOGGER.info("Compare title 1: {}", compareTitles.get(0).getText());
        LOGGER.info("Compare title 2: {}", compareTitles.get(1).getText());
        LOGGER.info("Compare price 1: {}", comparePrices.get(0).getText());
        LOGGER.info("Compare price 2: {}", comparePrices.get(1).getText());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(comparePage.isPageTitleCorrect(), "Compare page title is wrong!");
        softAssert.assertEquals(compareTitles.size(), 2, "Expected 2 phones on compare page!");

        String compareTitle1 = compareTitles.get(0).getText();
        String compareTitle2 = compareTitles.get(1).getText();
        String comparePrice1 = comparePrices.get(0).getText().replaceAll("[^0-9,]", "").trim();
        String comparePrice2 = comparePrices.get(1).getText().replaceAll("[^0-9,]", "").trim();
        String catalogPrice1 = price1.replaceAll("[^0-9,]", "").trim();
        String catalogPrice2 = price2.replaceAll("[^0-9,]", "").trim();

        softAssert.assertTrue(
                title1.contains(compareTitle1) || title2.contains(compareTitle1),
                "Compare title 1 not found in catalog titles!");
        softAssert.assertTrue(
                title1.contains(compareTitle2) || title2.contains(compareTitle2),
                "Compare title 2 not found in catalog titles!");
        softAssert.assertTrue(
                catalogPrice1.contains(comparePrice1) || catalogPrice2.contains(comparePrice1),
                "Compare price 1 not found in catalog prices!");
        softAssert.assertTrue(
                catalogPrice1.contains(comparePrice2) || catalogPrice2.contains(comparePrice2),
                "Compare price 2 not found in catalog prices!");

        softAssert.assertAll();
    }
}