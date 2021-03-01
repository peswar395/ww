package ww_pom;

import org.openqa.selenium.By;

public class ww_workshop_schedule {
	
	private By txt_workshop_title = By.xpath("//h1");
	
	private By tbl_schedule = By.className("scheduleContainerMobile-1RfmF");
	
	private By txt_business_hrs = By.className("hoursIcon-II-H2");
	
	public By getWorkshopTitle() {
		return txt_workshop_title;
	}
	
	public By getScheduleTbl() {
		return tbl_schedule;
	}
	
	public By getBusinessHrs() {
		return txt_business_hrs;
	}
}
