package POM.SD;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ADAUtilities.TestADAE2E;
import CommonUtilities.Common.ActionKeywords.WebKeywords;
import CommonUtilities.Utilities.Util;
import Constants.ModuleConstants_SD;
import Constants.ScreenConstants_SD;
import ReportUtilities.Common.ReportCommon;
import ReportUtilities.Model.TestCaseParam;
import ReportUtilities.Model.ExtentModel.PageDetails;
import TestSettings.TestRunSettings;

public class Login {
	
	private static final Logger logger =LoggerFactory.getLogger(SauceLogin.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();

	String ModuleName = ModuleConstants_SD.SD;
	String ScreenName = ScreenConstants_SD.Login;
	public Login(){ }
	
	public Login(WebDriver _driver,TestCaseParam testCaseParam) throws Exception { InitializePage(_driver,testCaseParam);}
	
	public void InitializePage(WebDriver _driver,TestCaseParam testCaseParam) throws Exception 
	    {
	    	 driver = _driver;
	         PageFactory.initElements(driver, this);
	         ReportCommon TestStepLogDetails = new ReportCommon(); 
	         TestStepLogDetails.logModuleAndScreenDetails(testCaseParam, ModuleName, ScreenName);
	    }
	
	
	@FindBy(how = How.XPATH, using = "//label[text()='Username ']//parent::div//parent::div//input")
	public WebElement TXT_UserName;

	@FindBy(how = How.XPATH, using = "//label[text()='Password ']//parent::div//parent::div//input")
	public WebElement TXT_Password;

	@FindBy(how = How.XPATH, using = "//input[@class='button button-primary']")
	public WebElement BTN_LoginButton;

	public void startLogin(TestCaseParam testCaseParam, String iteration) throws Exception  {

		PageDetails action = new PageDetails();
		LocalDateTime StartTime= LocalDateTime.now();
		action.PageActionName = "Process Login";
		action.PageActionDescription = "Process Login";
		try {
			HashMap<String, ArrayList<String>> TestCaseData_SD = new HashMap<String, ArrayList<String>>();
			TestCaseData_SD = util.GetScreenTCData(ScreenName, testCaseParam.TestCaseName,TestRunSettings.TestDataPath, TestRunSettings.TestDataMappingFileName ,TestRunSettings.TestDataMappingSheetName_SD,iteration);

			String URL = TestCaseData_SD.get("URL").get(0);
			//String URL = "https://login.4.cares.cwds.ca.gov";
			String UserName = TestCaseData_SD.get("UserName").get(0);

			String Password = TestCaseData_SD.get("Password").get(0);
			String SubmitButton = TestCaseData_SD.get("SubmitButton").get(0);

			WebKeywords.Instance().Navigate(driver,  URL, testCaseParam,action);
			WebKeywords.Instance().SetText(driver, TXT_UserName, UserName, testCaseParam,action);
			WebKeywords.Instance().SetText(driver, TXT_Password, Password, testCaseParam,action);
			TestADAE2E objada = new TestADAE2E();
	    	objada.ADAScan(driver, "login");
	    	objada.generateAdaResults("AdaTestResult");
			WebKeywords.Instance().Click(driver, BTN_LoginButton,SubmitButton, testCaseParam,action);
		}
		catch (Exception e)
		{
			logger.error("Failed == " + action.PageActionDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.PageActionName, action.PageActionDescription, StartTime,e);
			throw e;
		}
	}
}
