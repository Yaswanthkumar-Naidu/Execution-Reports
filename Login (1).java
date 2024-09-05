package pom.sd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

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
import report_utilities.common.ReportCommon;
import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.PageDetails;
import testsettings.TestRunSettings;
import uitests.testng.common.CommonOperations;

public class Login {
	
	private static final Logger logger =LoggerFactory.getLogger(Login.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	CommonOperations objcommonOperations = null;

	String moduleName = ModuleConstantsSD.SD;
	String screenName = ScreenConstantsSD.LOGIN;
	public Login(){ }
	
	public Login(WebDriver wDriver,TestCaseParam testCaseParam) 
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
	
	
	@FindBy(how = How.XPATH, using = "//label[text()='Username ']//parent::div//parent::div//input")
	public WebElement txtUserName;

	@FindBy(how = How.XPATH, using = "//label[text()='Password ']//parent::div//parent::div//input")
	public WebElement txtPassword;

	@FindBy(how = How.XPATH, using = "//input[@class='button button-primary']")
	public WebElement btnLoginButton;

	@FindBy(how = How.XPATH, using = "//div[@data-se='app-card-container']/a")
	public WebElement selectApp;
	
	public void processLogin(TestCaseParam testCaseParam, String iteration) throws Exception  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.PageActionName = "Process Login";
		action.PageActionDescription = "Process Login";
		try {
			Map<String, ArrayList<String>> testcaseDataSD;
			testcaseDataSD = util.getScreenTCData(screenName, testCaseParam.TestCaseName,TestRunSettings.testDataPath, TestRunSettings.testDataMappingFileName ,TestRunSettings.testDataMappingSheetNameSd,iteration);

			String url = testcaseDataSD.get("URL").get(0);
			String userName = testcaseDataSD.get("UserName").get(0);

			String password = testcaseDataSD.get("Password").get(0);
			String submitButton = testcaseDataSD.get("SubmitButton").get(0);

			webkeywords.Instance().Navigate(driver,  url, testCaseParam,action);
			webkeywords.Instance().SetText(driver, txtUserName, userName, testCaseParam,action);
			webkeywords.Instance().SetText(driver, txtPassword, password, testCaseParam,action);
			webkeywords.Instance().Click(driver, btnLoginButton,submitButton, testCaseParam,action);
		
			webkeywords.Instance().waitElementToBeVisible(driver, selectApp, 2);
			objcommonOperations.pageRefresh(driver);
			webkeywords.Instance().Click(driver, selectApp,"", testCaseParam,action);
			
			objcommonOperations.switchToLastTab(driver);
			driver.switchTo().defaultContent();
		}
		catch (Exception e)
		{
			logger.error("Failed == {} ", action.PageActionDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.PageActionName, action.PageActionDescription, startTime,e);
			throw e;
		}
	}
}