package com.edureka.maven;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MavenJenkinsDemo2 {
	
	static WebDriver driver = null;
	WebDriverWait wait = null;
	public static Logger log = Logger.getLogger(MavenJenkinsDemo2.class); 
	  static String pageLoadStatus = null;

	
	@BeforeTest
	  public void launchBrowser()
	{
/*			System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.edureka.co/");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			UnitTest.getSessionIdURL(driver);*/
			driver= UnitTest.setDriver();	
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,10);

	  }
	
/*	@Parameters({"userName","pwd"})
	@Test(priority=1)
	public void login(String userName, String pwd)
	{
		WebElement loginLink = driver.findElement(By.xpath("//a[text()='Log In']"));
		loginLink.click();
		WebElement emailId = driver.findElement(By.xpath("//input[@id='inputName']"));
		wait.until(ExpectedConditions.visibilityOf(emailId));
		emailId.sendKeys(userName);
		WebElement pwdTxt = driver.findElement(By.xpath("//input[@id='pwd1']"));
		pwdTxt.sendKeys(pwd);
		WebElement loginBtn = driver.findElement(By.xpath("//button[text()='LOGIN']"));
		loginBtn.click();			
	}
	
	@Test(priority=2)
	public void gotoProfile() 
	{
		WebElement profileName = driver.findElement(By.xpath("//span[@class='webinar-profile-name']"));
		profileName.click();		
		WebElement profileLink = driver.findElement(By.xpath("//a[text()='My Profile']"));
		profileLink.click();	
	}*/
	
	@Parameters({"filePath","scriptPath"})
	@Test(priority=3)
	public void uploadPhoto(String filePath, String scriptPath) throws Exception
	{
		WebElement editIcon = driver.findElement(By.xpath("(//i[@class='icon-pr-edit'])[2]"));
		editIcon.click();				
		WebElement pdLink = driver.findElement(By.xpath("//a[contains(text(),'Personal Details')]"));
		pdLink.click();
		WebElement msg = driver.findElement(By.xpath("//span[contains(text(),'receive OTP')]"));
		wait.until(ExpectedConditions.invisibilityOf(msg));
		WebElement cameraLink = driver.findElement(By.xpath("//i[@class='icon-camera']"));
		cameraLink.click();				
		WebElement chooseFileLink = driver.findElement(By.xpath("//div[@class='file-upload']"));
		wait.until(ExpectedConditions.visibilityOf(chooseFileLink));
		chooseFileLink.click();		
		runAutoItScript(filePath,scriptPath);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement uploadBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		wait.until(ExpectedConditions.visibilityOf(uploadBtn));
		uploadBtn.click();	
		wait.until(ExpectedConditions.invisibilityOf(uploadBtn));
		WebElement continueLink = driver.findElement(By.xpath("//button[text()='Continue']"));
		continueLink.click();
		log.info("clicked continue");
		waitForPageToLoad();
		WebElement nextBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		nextBtn.click();
		log.info("clicked next");
		waitForPageToLoad();
		WebElement nextBtn1 = driver.findElement(By.xpath("//button[contains(@class,'pull-right')]"));
		nextBtn1.click();
		log.info("clicked next1");
		waitForPageToLoad();
		WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		submitBtn.click();	
		log.info("clicked submit");

	}
	

	  public static void waitForPageToLoad() {
		  int maxWaitMillis = 10000;
		  int pollDelimiter = 500;
		  double startTime = System.currentTimeMillis();
		  log.info("startTime::" + startTime);
		    while (System.currentTimeMillis() < startTime + maxWaitMillis) {
		        String prevState = driver.getPageSource();
	        	log.info(System.currentTimeMillis() + " into loop");
		        try {
					Thread.sleep(pollDelimiter);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		        if (prevState.equals(driver.getPageSource())) {
		        	log.info("returning");
		            return;
		        }

		    }

		  }
	
	public void runAutoItScript(String filePath, String scriptPath)
	{
		String strParam = scriptPath  + " " + filePath;
		try {
			Runtime.getRuntime().exec(strParam);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//@Test(priority=4)
	public void logout() {
		gotoHome();
		WebElement profileName = driver.findElement(By.xpath("//span[@class='webinar-profile-name']"));
		profileName.click();
		WebElement logOut = driver.findElement(By.xpath("//a[text()='Log Out']"));
		logOut.click();
	}
	
	public void gotoHome() {
		
        JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement homeLink = driver.findElement(By.xpath("//a[text()='Home']"));
		js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
		homeLink.click();
		
	}
}
