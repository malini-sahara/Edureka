package com.edureka.maven;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MavenJenkinsDemo {
	
	WebDriver driver = null;
	WebDriverWait wait = null;
	public static Logger log = Logger.getLogger(MavenJenkinsDemo.class); 

	
	@Test(priority=0)
	  public void launchBrowser()
	{
			System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
			driver = new ChromeDriver();
			driver.get("https://www.edureka.co/");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,30);

	  }
	
	@Parameters({"userName","pwd"})
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
	
	@Parameters({"filePath","scriptPath"})
	@Test(priority=2)
	public void uploadPhoto(String filePath, String scriptPath) throws Exception
	{
		gotoProfile();
		Thread.sleep(1000);
		WebElement editIcon = driver.findElement(By.xpath("(//i[@class='icon-pr-edit'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(editIcon));
		editIcon.click();	
		WebElement msg = driver.findElement(By.xpath("//span[contains(text(),'receive OTP')]"));
		wait.until(ExpectedConditions.invisibilityOf(msg));
		WebElement cameraLink = driver.findElement(By.xpath("//i[@class='icon-camera']"));
		cameraLink.click();	
		WebElement chooseFileLink = driver.findElement(By.xpath("//div[@class='file-upload']"));
		wait.until(ExpectedConditions.visibilityOf(chooseFileLink));
		chooseFileLink.click();		
		runAutoItScript(filePath,scriptPath);
		Thread.sleep(3000);
		WebElement uploadBtn = driver.findElement(By.xpath("//button[@type='submit']"));
		wait.until(ExpectedConditions.elementToBeClickable(uploadBtn));
		uploadBtn.click();	
		wait.until(ExpectedConditions.invisibilityOf(uploadBtn));
		WebElement backArrow = driver.findElement(By.xpath("(//a[@class='back-arrow'])[1]"));
		wait.until(ExpectedConditions.elementToBeClickable(backArrow));
		backArrow.click();
	}
	
	public void gotoProfile() 
	{
		WebElement profileName = driver.findElement(By.xpath("//span[@class='webinar-profile-name']"));
		profileName.click();		
		WebElement profileLink = driver.findElement(By.xpath("//a[text()='My Profile']"));
		profileLink.click();	
	
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
	
	@Test(priority=3)
	public void logout()
	{
		WebElement profileName = driver.findElement(By.xpath("//span[@class='user_name']"));
		profileName.click();
		WebElement logOut = driver.findElement(By.xpath("//a[text()='Log Out']"));
		logOut.click();
	}
	

}
