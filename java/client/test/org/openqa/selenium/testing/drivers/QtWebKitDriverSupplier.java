/**
 * Created with IntelliJ IDEA.
 * User: liberus
 * Date: 3/25/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
package org.openqa.selenium.testing.drivers;

import com.google.common.base.Supplier;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;


public class QtWebKitDriverSupplier implements Supplier<WebDriver>{

    private Capabilities desiredCapabilities;
    private Capabilities requiredCapabilities;

    public QtWebKitDriverSupplier(Capabilities desiredCapabilities,
                          Capabilities requiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
        this.requiredCapabilities = requiredCapabilities;
    }

    public WebDriver get() {
        if (desiredCapabilities == null || !Boolean.getBoolean("selenium.browser.qtwebkit")) {
            System.out.println("Wrong capability was set!");
            Thread.dumpStack();
            return null;
        }

        java.net.URL hostURL;

        try {
            hostURL = new java.net.URL("http://localhost:9517");
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }

        RemoteWebDriver driver = new RemoteWebDriver(
                hostURL, desiredCapabilities, requiredCapabilities);
        driver.setFileDetector(new LocalFileDetector());
        return driver;
    }
}