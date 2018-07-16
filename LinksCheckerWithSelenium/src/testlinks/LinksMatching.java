package testlinks;

import java.io.IOException;
import java.io.InputStream;
import java.lang.AssertionError;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LinksMatching {
    
    private static WebDriver driver = null;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        String homePage = "http://localhost/index.html";
        String url = "";
        HttpURLConnection huc = null;
        String respUrl = "";
        
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		
        driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
        driver.get(homePage);
        
        List<WebElement> links = driver.findElements(By.tagName("a"));
        
        Iterator<WebElement> it = links.iterator();
        
		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			try {

				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("GET");

				huc.connect();
				InputStream is = huc.getInputStream();

				respUrl = huc.getURL().toString(); // get the url of opened web-page
				is.close();

				Assert.assertEquals(url, respUrl);
			} catch (AssertionError e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println(url + " is not the same as " + respUrl);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println(url + " is malformed");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println(url + " is unknown");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        driver.close();
        driver.quit();

    }
}