package pom.sd;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import common_utilities.common.action_keywords.webkeywords;
import common_utilities.Utilities.Util;
import testsettings.TestRunSettings;
import uitests.testng.common.CommonOperations;
import constants.ModuleConstantsSD;
import constants.ScreenConstantsSD;
import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.PageDetails;
import report_utilities.common.ReportCommon;

public class InitialScreening {
	private static final Logger logger =LoggerFactory.getLogger(InitialScreening.class.getName());
	private WebDriver driver;
	ReportCommon exceptionDetails = new ReportCommon();
	Util util = new Util();
	CommonOperations objcommonOperations = null;

	String moduleName = ModuleConstantsSD.SD;
	String screenName = ScreenConstantsSD.INITIALSCREENINGPAGE;
	public InitialScreening(){ }
	
	public InitialScreening(WebDriver wDriver,TestCaseParam testCaseParam)
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
	
	@FindBy(how = How.XPATH, using = "//label[text()='Date']//parent::div//input")
	public WebElement dateTb;
	
	@FindBy(how = How.XPATH, using = "//button[contains(@name,'ReasonForCall')]")
	public WebElement reasonForCallDd;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Screening Name']//following-sibling::div/input")
	public WebElement screeningNameTb;
	
	@FindBy(how = How.XPATH, using = "//div[@aria-label='Screening Narrative']//div[contains(@class,'rich-text-area')]")
	public WebElement screeningNarrativeTb;
	
	@FindBy(how = How.XPATH, using = "//h1[text()='Caller Type']")
	public WebElement callerTypeRb;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Caller First Name']//following-sibling::div/input")
	public WebElement callerFnTb;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Caller Last Name']//following-sibling::div/input")
	public WebElement callerLnTb;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Preferred Method to Receive ERNRD']//parent::div/div//div//button")
	public WebElement preferredMethodDd;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Mandated Reporter Type']//parent::div/div//div//button")
	public WebElement mandatedReporterTypeDd;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Phone Type']//parent::div/div//div//button")
	public WebElement phoneTypeDd;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Phone']//following-sibling::div/input")
	public WebElement phoneTb;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Call Back Required']")
	public WebElement callBackRequired;
	
	
	@FindBy(how = How.XPATH, using = "//label[text()='Call Back Required']//parent::div/div//div//button")
	public WebElement callBackRequiredDD;
	
	@FindBy(how = How.XPATH, using = "//button[text()='Save and Proceed']")
	public WebElement saveAndproceedBtn;
	
	@FindBy(how = How.XPATH, using = "//label[text()='Email']//following-sibling::div/input")
	public WebElement emailTb;
	
	
	
	public void addInitialScreeningInfo(TestCaseParam testCaseParam, String iteration) throws Exception  {

		PageDetails action = new PageDetails();
		LocalDateTime startTime= LocalDateTime.now();
		action.PageActionName = "Process Initial Screening";
		action.PageActionDescription = "Process Initial Screening";
		try {
			
			Map<String, ArrayList<String>>	testCaseDataSd = util.getScreenTCData(screenName, testCaseParam.TestCaseName,TestRunSettings.testDataPath, TestRunSettings.testDataMappingFileName ,TestRunSettings.testDataMappingSheetNameSd,iteration);

			String date = testCaseDataSd.get("TodaysDate").get(0);
			String reasonForTheCallValue = testCaseDataSd.get("ReasonForTheCall").get(0);
			String screeningName = testCaseDataSd.get("ScreeningName").get(0);
			String screeningNarrrative = testCaseDataSd.get("ScreeningNarrative").get(0);
			String callerType = testCaseDataSd.get("CallerType").get(0);
			String callerFn = testCaseDataSd.get("FirstName").get(0);
			String callerLn = testCaseDataSd.get("LastName").get(0);
			String preferredMethod = testCaseDataSd.get("PreferredMethodToReceiveERNRD").get(0);
			String mandatedReporterType = testCaseDataSd.get("MandatedReporterType").get(0);
			String phoneType = testCaseDataSd.get("PhoneType").get(0);
			String phoneTextBox = testCaseDataSd.get("PhoneTextbox").get(0);
			String callBackRequiredValue = testCaseDataSd.get("CallBackRequired").get(0);
						
			webkeywords.Instance().SetText(driver, dateTb, CommonOperations.getDate("MM/dd/YYYY",date), testCaseParam,action);
			
			webkeywords.Instance().Click(driver, reasonForCallDd,"", testCaseParam,action);
			CommonOperations.selectDropdownvalue(driver,"Reason for the Call", reasonForTheCallValue);
			webkeywords.Instance().SetText(driver, screeningNameTb, screeningName, testCaseParam,action);
			
			CommonOperations.scrollTillElement(driver,dateTb);
			webkeywords.Instance().Click(driver, screeningNarrativeTb,"", testCaseParam,action);
			driver.findElement(By.xpath("//div[@aria-label='Screening Narrative']//div[contains(@class,'rich-text-area')]")).sendKeys(screeningNarrrative);
			webkeywords.Instance().scrollIntoViewElement(driver, callerTypeRb);
			
			WebElement mandateReporter = CommonOperations.radiobutton(driver,callerType);
			webkeywords.Instance().JSClick(driver, mandateReporter, testCaseParam, action,2000);
	    	
			webkeywords.Instance().SetText(driver, callerFnTb, callerFn, testCaseParam,action);
			webkeywords.Instance().SetText(driver, callerLnTb, callerLn, testCaseParam,action);
			
			webkeywords.Instance().Click(driver, preferredMethodDd,"", testCaseParam,action);
			CommonOperations.selectDropdownvalue(driver,"Preferred Method to Receive ERNRD", preferredMethod);
			
			webkeywords.Instance().SetText(driver, emailTb,"aaaaa@gmail.com", testCaseParam,action);
			
			webkeywords.Instance().Click(driver, mandatedReporterTypeDd,"", testCaseParam,action);
			CommonOperations.selectDropdownvalue(driver,"Mandated Reporter Type",mandatedReporterType);
	    	
			webkeywords.Instance().Click(driver, phoneTypeDd,"", testCaseParam,action);
			CommonOperations.selectDropdownvalue(driver,"Phone Type",phoneType);
			
			webkeywords.Instance().SetText(driver, phoneTb, phoneTextBox, testCaseParam,action);
			webkeywords.Instance().scrollIntoViewElement(driver, callBackRequired);
			
			webkeywords.Instance().Click(driver, callBackRequiredDD,"", testCaseParam,action);
			CommonOperations.selectDropdownvalue(driver,"Call Back Required",callBackRequiredValue);
			
			webkeywords.Instance().Click(driver, saveAndproceedBtn,"", testCaseParam,action);
			objcommonOperations.pageRefresh(driver);
		}
		catch (Exception e)
		{
			logger.error("Failed== {}",action.PageActionDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, action.PageActionName, action.PageActionDescription, startTime,e);
			throw e;
		}
	}
}
