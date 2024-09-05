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

public class ScreeningsPage {
	private static final Logger logger =LoggerFactory.getLogger(ScreeningsPage.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();

	String moduleName = ModuleConstantsSD.SD;
	String screenName = ScreenConstantsSD.SCREENINGSPAGE;
	
	public ScreeningsPage(){ }
	
	public ScreeningsPage(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	@FindBy(how = How.XPATH, using = "(//a[@title='New'])[1]")
	public WebElement newButton;

	public void clickNewBtn(TestCaseParam testCaseParam) throws Exception{

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.PageActionName = "Select New Button";
		action.PageActionDescription = "Select New Button";
		try {
			webkeywords.Instance().Click(driver, newButton,"",testCaseParam,action);
		}catch (Exception e)
		{
			logger.error("Failed == {}",action.PageActionDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.PageActionName, action.PageActionDescription, startTime,e);
			throw e;
		}
    }

}
