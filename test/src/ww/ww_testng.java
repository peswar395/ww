package ww;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ww_pom.ww_main;
import ww_pom.ww_find_workshop;
import ww_pom.ww_workshop_schedule;

public class ww_testng {
	
	public ww_main ww = new ww_main();
	public ww_find_workshop fw = new ww_find_workshop();
	public ww_workshop_schedule ws = new ww_workshop_schedule();
	
	
	public String URL = "https://www.weightwatchers.com/us/";
	public String localDir = System.getProperty("user.dir");
	public WebDriver driver ; 
	
	@BeforeTest
	public void setURL() {
		System.setProperty("webdriver.chrome.driver", localDir + "/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        driver.get(URL);
        //maximize current window
        driver.manage().window().maximize();
	}
	
	@Test
	public void verifyHomePageTitle() {
		String expectedTitle = "WW (Weight Watchers): Weight Loss Program & Wellness Help | WW USA";
		Assert.assertEquals(driver.getTitle(),expectedTitle);
	}
	
	@Test (dependsOnMethods={"verifyHomePageTitle"})
	public void clickOnFindWorkshop() {
		driver.findElement(ww.getAttendBtn()).click();
        driver.findElement(ww.getWorkshopMenu()).click();
	}
	
	@Test (dependsOnMethods={"clickOnFindWorkshop"})
	public void verifyFindWorkshopPageTitle() {
		String expectedTitle = "Find WW Studios & Meetings Near You | WW USA";
		Assert.assertEquals(driver.getTitle(),expectedTitle);
	}
	
	@Test (dependsOnMethods={"verifyFindWorkshopPageTitle"})
	public void searchMeetings() {
		driver.findElement(fw.getStudioBtn()).click();
        driver.findElement(fw.getLocationTxt()).sendKeys("10011");
        driver.findElement(fw.getSubmitBtn()).click();
	}
	
	@Test (dependsOnMethods={"searchMeetings"})
	public void printTitleDistance() {
		String title = driver.findElement(fw.getSearchResult()).getText();
        System.out.println(title);
        String distance = driver.findElement(fw.getSearchResultDistance()).getText();
        System.out.println(distance);
	}
	
	@Test (dependsOnMethods={"printTitleDistance"})
	public void clickOnFirstResult() {
		String title = driver.findElement(fw.getSearchResult()).getText();
		driver.findElement(fw.getSearchResult()).click();
        String restitle = driver.findElement(ws.getWorkshopTitle()).getText();
        System.out.println(restitle);
        Assert.assertEquals(restitle,title);
	}
	
	@Test (dependsOnMethods={"clickOnFirstResult"})
	public void printTodayBusinessHrs() {
		//get today DAY from calendar
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        //scroll to schedule table to see business hours icon
        WebElement inPersonSchedule = driver.findElement(ws.getScheduleTbl());
        Actions actions = new Actions(driver);
        actions.moveToElement(inPersonSchedule);
        actions.perform();
        //expand business hrs
        driver.findElement(ws.getBusinessHrs()).click();
        String businesshours = driver.findElement(By.xpath("//div[text()='"+today+"']/following-sibling::div[@class='times-fms3v']")).getText();
        System.out.println(businesshours);
	}
	
	@Test (dependsOnMethods={"printTodayBusinessHrs"})
	public void printMeetingsforCoach() {
		//first scroll to schedule table
		List<WebElement> virtualSchedule = driver.findElements(ws.getScheduleTbl());
		Actions actions = new Actions(driver);
        actions.moveToElement(virtualSchedule.get(1));
        actions.perform();
        
        printMeetings("Sun");
	}
	
	@AfterTest
	public void endSession() {
		driver.quit();
	}
	
	 void printMeetings(String sDayName) {
		//convert the string to uppercase as this value in page are uppercase 
    	String day = sDayName.toUpperCase();
    	HashMap<String, Integer> coachSchedule = new HashMap<String, Integer>();
        int counter = 1;
        //get all text elements for a given day of the week
        List<WebElement> schedule = driver.findElements(By.xpath("//div[@class='scheduleContainerMobile-1RfmF']//span[text()='"+day+"']/parent::div[@class='day-NhBOb']"));
        for(WebElement ele : schedule) {
        	String[] name = ele.getText().split("\\n");
        	for(int i=0;i<name.length;i++) {
        		//if the text doesn't contains ":" (time), then it is a person name other than day of the week
        		if (!((name[i].contains(":")) || (name[i].equals(day)))) {
        			if (coachSchedule.containsKey(name[i])) {
        				//if name exists, increment the counter
        				counter = coachSchedule.get(name[i]) + 1;
        			}	
        			coachSchedule.put(name[i], counter);
        		}
        	}
        }
        
        for (String i : coachSchedule.keySet()) {
            System.out.println("Name: " + i + " Meetings: " + coachSchedule.get(i));
          }
    }


}
