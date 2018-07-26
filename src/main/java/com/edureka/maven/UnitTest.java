package com.edureka.maven;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.JsonHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class UnitTest {
	
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Logger log = Logger.getLogger(UnitTest.class); 
	
	
	//@Test(priority=1)
	public static WebDriver setDriver() {

		String session_id = "1ce851bf0b75d703668da8e223543662";
		URL url;
		try {
			url = new URL("http://localhost:25666");
			driver = ((WebDriver)createDriverFromSession(session_id, url));
			//wait = new WebDriverWait(driver, 10);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public static void getSessionIdURL(WebDriver driver) {
		HttpCommandExecutor executor = (HttpCommandExecutor) ((RemoteWebDriver) driver).getCommandExecutor();
	    URL url = executor.getAddressOfRemoteServer();
	    SessionId session_id = ((RemoteWebDriver) driver).getSessionId();
	    log.info("session_id:: " + session_id);
	    log.info("url:: " + url);

	    
	}
	
	public static RemoteWebDriver createDriverFromSession(final String sessionId, URL command_executor){
	    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

	    @Override
	    public Response execute(Command command) throws IOException {
	        Response response = null;
	        if (command.getName() == "newSession") {
	            response = new Response();
	            response.setSessionId(sessionId);
	            response.setStatus(0);
	            response.setValue(Collections.<String, String>emptyMap());

	            try {
	                Field commandCodec = null;
	                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
	                commandCodec.setAccessible(true);
	                commandCodec.set(this, new JsonHttpCommandCodec());

	                Field responseCodec = null;
	                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
	                responseCodec.setAccessible(true);
	                responseCodec.set(this, new W3CHttpResponseCodec());
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }

	        } else {
	            response = super.execute(command);
	        }
	        return response;
	    }
	    };

	    return new RemoteWebDriver(executor, new DesiredCapabilities());
	}
	


	

	
	

	
	 // @Test(priority=1)
	  public void launchFlipKart() {
		  
		  System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
		  driver = new ChromeDriver();
		  driver.get("https://www.flipkart.com/");
		  
		  driver.manage().window().maximize();
		  driver.manage().deleteAllCookies();
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  wait = new WebDriverWait(driver,10);
		  
		  WebElement closeIcon = driver.findElement(By.xpath("//button[text()='✕']"));
		  closeIcon.click();
		  
		  getSessionIdURL(driver);
		  
	  }

	  
	}


