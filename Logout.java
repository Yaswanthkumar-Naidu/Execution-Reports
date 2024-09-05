package pom.sd;

import java.time.LocalDateTime;

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
import uitests.testng.common.CommonOperations;


public class Logout {
	
	
	private static final Logger logger =LoggerFactory.getLogger(Logout.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	CommonOperations objcommonOperations = null;

	String moduleName = ModuleConstantsSD.SD;
	String screenName = ScreenConstantsSD.LOGOUT;
	public Logout(){ }
	
	public Logout(WebDriver wDriver,TestCaseParam testCaseParam)
	{
		initializePage(wDriver,testCaseParam);
	}
	
	public void initializePage(WebDriver wDriver,TestCaseParam testCaseParam) 
	    {
	    	 driver = wDriver;
	         PageFactory.initElements(driver, this);
	         ReportCommon testStepLogDetails = new ReportCommon(); 
	         testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);
	         objcommonOperations = new CommonOperations(wDriver);
	    }
	
	@FindBy(how = How.XPATH, using = "//span[@class='uiImage']/img[@title='User']")
	public WebElement profileIcon;

	@FindBy(how = How.XPATH, using = "//a[text()='Log Out']")
	public WebElement logoutLink;
	

	public void processLogout(TestCaseParam testCaseParam) throws Exception {
		PageDetails action = new PageDetails();
		LocalDateTime stTime= LocalDateTime.now();
		action.PageActionName = "Logout";
		action.PageActionDescription = "Logout";
		
		try {
			webkeywords.Instance().waitElementToBeVisible(driver, profileIcon, 3);
			webkeywords.Instance().JSClick(driver, profileIcon, testCaseParam, action,2000);
			webkeywords.Instance().Click(driver, logoutLink,"",testCaseParam,action);
		}catch (Exception e)
		{
			logger.error("Failed == {}",action.PageActionDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.PageActionName, action.PageActionDescription, stTime,e);
			throw e;
		}
		
	}

}
