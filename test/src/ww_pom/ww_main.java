package ww_pom;

import org.openqa.selenium.By;

public class ww_main {
	private By btn_attend = By.xpath("//span[text()='Attend']");
	
	private By btn_workshop_menu = By.xpath("//*[@id=\"popup-2\"]//a[1]/span");
	
	public By getAttendBtn() {
		return btn_attend;
	}
	
	public By getWorkshopMenu() {
		return btn_workshop_menu;
	}


}
