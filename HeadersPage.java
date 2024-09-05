package pom.sd;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common_utilities.common.action_keywords.webkeywords;
import common_utilities.Utilities.Util;
import constants.ModuleConstantsSD;
import constants.ScreenConstantsSD;
import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.PageDetails;
import report_utilities.common.ReportCommon;

public class HeadersPage {
	private static final Logger logger =LoggerFactory.getLogger(HeadersPage.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();

	String moduleName = ModuleConstantsSD.SD;
	String screenName = ScreenConstantsSD.HEADERSPAGE;
	public HeadersPage(){ }
	
	public HeadersPage(WebDriver wDriver,TestCaseParam testCaseParam)
	{
		initializePage(wDriver,testCaseParam);
	}

	public void initializePage(WebDriver wDriver,TestCaseParam testCaseParam) 
    {
    	 driver = wDriver;
         PageFactory.initElements(driver, this);
         ReportCommon testStepLogDetails = new ReportCommon(); 
         testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
    }
	
	@FindBy(how = How.XPATH, using = "//a[@title='Home']/span")
	public WebElement home;

	@FindBy(how = How.XPATH, using = "//a[@title='Screenings']")
	public WebElement screenings;

	@FindBy(how = How.XPATH, using = "//a[@title='Folio']")
	public WebElement folio;

	
	public void selectHeaderTab(TestCaseParam testCaseParam,String headerName) throws Exception{
		
		PageDetails action = new PageDetails();
		action.PageActionName = "Select Headers";
		action.PageActionDescription = "Select Headers";
		
		try {
			switch(headerName) {
			case "Home":

				webkeywords.Instance().waitElementToBeVisible(driver, home, 2);
				webkeywords.Instance().Clickwithaction(driver, home, testCaseParam, action,"");
				break;
			case "Screenings":
				webkeywords.Instance().waitElementToBeVisible(driver, screenings, 2);
				webkeywords.Instance().JSClick(driver, screenings, testCaseParam, action,2000);
				break;
			case "Folio":
				webkeywords.Instance().waitElementToBeVisible(driver, folio, 2);
				webkeywords.Instance().JSClick(driver, folio, testCaseParam, action,2000);
				break;
			default :
				logger.info("headerName {}", "not seleced");
				
				break;
			}
		}catch (Exception e)
		{
			logger.error("{} Failed to generate the Live Results",e.getMessage());
		}
	}

}
