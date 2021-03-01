package ww_pom;

import org.openqa.selenium.By;

public class ww_find_workshop {
	
	private By btn_studio = By.xpath("//span[text()='Studio']");
	
	private By txt_location = By.id("location-search");
	
	private By btn_submit = By.id("location-search-cta");
	
	private By txt_search_result = By.xpath("//*[@id=\"search-results\"]//a//a");
	
	private By txt_search_result_dist = By.xpath("//*[@id=\"search-results\"]//a//span");
	
	public By getStudioBtn() {
		return btn_studio;
	}
	
	public By getLocationTxt() {
		return txt_location;
	}
	
	public By getSubmitBtn() {
		return btn_submit;
	}
	
	public By getSearchResult() {
		return txt_search_result;
	}
	
	public By getSearchResultDistance() {
		return txt_search_result_dist;
	}
	
	
}
