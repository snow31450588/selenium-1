package org.openqa.selenium.qtwebkit.nativetests.interactions.touch;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.touch.FlickAction;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.qtwebkit.nativetests.interactions.touch.TouchTestBase;

import static org.openqa.selenium.TestWaiter.waitFor;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.openqa.selenium.TestWaiter.waitFor;

import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WaitingConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.testing.JUnit4TestBase;

/**
 * Tests single tap actions on touch enabled devices.
 */
public class TouchInteractionTests extends TouchTestBase {

    private TouchActions getBuilder(WebDriver driver) {
        return new TouchActions(driver);
    }

    private void singleTapOnElement(String elementId) {
        WebElement toSingleTap = driver.findElement(By.id(elementId));
        Action singleTap = getBuilder(driver).singleTap(toSingleTap).build();
        singleTap.perform();
    }

    private void doubleTapOnElement(String elementId) {
        WebElement toDoubleTap = driver.findElement(By.id(elementId));
        Action doubleTap = getBuilder(driver).doubleTap(toDoubleTap).build();
        doubleTap.perform();
    }

    private Point getLocationOnScreen(By locator) {
        WebElement element = driver.findElement(locator);
        return ((Locatable) element).getCoordinates().onScreen();
    }


    @Test
    public void testCanTap() {
        driver.get("qtwidget://TouchTestWidget");
        singleTapOnElement("touchArea");
        waitFor(WaitingConditions.elementTextToEqual(driver, By.id("resultLabel"), "PressedReleased"));
    }

    @Test
    public void testCanDoubleTap() {
        driver.get("qtwidget://TouchTestWidget");
        doubleTapOnElement("touchArea");
        waitFor(WaitingConditions.elementTextToEqual(driver, By.id("resultLabel"), "PressedReleasedPressedReleased"));
    }


    @Test
    public void testTouchCanDownMoveUp() {
        driver.get("qtwidget://TouchTestWidget");
        Action down = getBuilder(driver).down(200, 400).build();
        down.perform();
        Action move = getBuilder(driver).move(250, 450).build();
        move.perform();
        Action up = getBuilder(driver).up(350, 550).build();
        up.perform();

        assertThat(getLocationOnScreen(By.id("pressLabel")), is(new Point(200, 400)));
        assertThat(getLocationOnScreen(By.id("moveLabel")), is(new Point(250, 450)));
        assertThat(getLocationOnScreen(By.id("releaseLabel")), is(new Point(350, 550)));

    }
}