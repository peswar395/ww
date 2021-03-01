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

import ww_pom.ww_main;
import ww_pom.ww_find_workshop;
import ww_pom.ww_workshop_schedule;

public class ww {
	
	public static WebDriver driver = new ChromeDriver();
	
	static ww_main ww = new ww_main();
	static ww_find_workshop fw = new ww_find_workshop();
	static ww_workshop_schedule ws = new ww_workshop_schedule();

    public static void main(String[] args) throws InterruptedException {
    	
    	String localDir = System.getProperty("user.dir");

        System.setProperty("webdriver.chrome.driver", localDir + "/chromedriver");
        String URL = "https://www.weightwatchers.com/us/";
        
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
        driver.get(URL);
        //maximize current window
        driver.manage().window().maximize();
        

        Assert.assertEquals(driver.getTitle(),"WW (Weight Watchers): Weight Loss Program & Wellness Help | WW USA");
        
        driver.findElement(ww.getAttendBtn()).click();

        driver.findElement(ww.getWorkshopMenu()).click();

        Assert.assertEquals(driver.getTitle(),"Find WW Studios & Meetings Near You | WW USA");
        
       
        driver.findElement(fw.getStudioBtn()).click();
        driver.findElement(fw.getLocationTxt()).sendKeys("10011");
        driver.findElement(fw.getSubmitBtn()).click();
        String title = driver.findElement(fw.getSearchResult()).getText();
        System.out.println(title);
        String distance = driver.findElement(fw.getSearchResultDistance()).getText();
        System.out.println(distance);
        driver.findElement(fw.getSearchResult()).click();
        
        
        String restitle = driver.findElement(ws.getWorkshopTitle()).getText();
        System.out.println(restitle);
        Assert.assertEquals(restitle,title);
        
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date date = calendar.getTime();
        String today = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        
        WebElement inPersonSchedule = driver.findElement(ws.getScheduleTbl());
        Actions actions = new Actions(driver);
        actions.moveToElement(inPersonSchedule);
        actions.perform();
        
        driver.findElement(ws.getBusinessHrs()).click();
        String bh = driver.findElement(By.xpath("//div[text()='"+today+"']/following-sibling::div[@class='times-fms3v']")).getText();
        System.out.println(bh);
        
        List<WebElement> virtualSchedule = driver.findElements(ws.getScheduleTbl());
        actions.moveToElement(virtualSchedule.get(1));
        actions.perform();
        
        printMeetings("Sun");
        
        
        
    }
    
    static void printMeetings(String sDayName) {
    	
    	String day = sDayName.toUpperCase();
    	
    	HashMap<String, Integer> coachSchedule = new HashMap<String, Integer>();
        int counter = 1;
        
        List<WebElement> schedule = driver.findElements(By.xpath("//div[@class='scheduleContainerMobile-1RfmF']//span[text()='"+day+"']/parent::div[@class='day-NhBOb']"));
        for(WebElement ele : schedule) {
        	String[] name = ele.getText().split("\\n");
        	for(int i=0;i<name.length;i++) {
        		if (!((name[i].contains(":")) || (name[i].equals(day)))) {
        			if (coachSchedule.containsKey(name[i])) {
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



