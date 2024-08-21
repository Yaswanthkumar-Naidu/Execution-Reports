package UITests.TestNG.SD;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ADAUtilities.TestADAE2E;
import CommonUtilities.Utilities.Util;
import Constants.PrjConstants;
import POM.SD.Login;
import ReportUtilities.Common.ReportCommon;
import ReportUtilities.Common.ScreenshotCommon;
import ReportUtilities.Model.TestCaseParam;
import TestSettings.TestRunSettings;
import UITests.TestNG.Common.TestNGCommon;

public class loginTest  extends TestNGCommon{
	
	private WebDriver driver;
    String Browser = "";
    ScreenshotCommon SCM = new ScreenshotCommon();
    Util util = new Util();
    HashMap<String, ArrayList<String>> TestCaseData_Execution = new HashMap<String, ArrayList<String>>();
	ReportCommon TestStepDetails = new ReportCommon();
    TestCaseParam testCaseParam = new TestCaseParam();
    
    public loginTest(){
    	
    }
    
    @BeforeMethod
    public void SetUp_Report()
    {
        Browser = TestRunSettings.Browser;
        testCaseParam.TestCaseName = "ScreeningHappyPath_Staff";
        testCaseParam.ModuleName = "SD";
        testCaseParam.Browser = Browser;
        testCaseParam.TestCaseDescription = testCaseParam.TestCaseName;
        InitializeTestCase(testCaseParam);
        driver = InitializeDriver();
        
    }
    
    @Test
    public void TestScreeningHappyPath() throws Exception
    {
    	Login objlogin = new Login(driver, testCaseParam);
    	objlogin.startLogin(testCaseParam,"1");
    	
    	Thread.sleep(PrjConstants.Delay);
    }

    @AfterMethod
    public void TearDownMethod()
    {
    	EndTestCase(testCaseParam);
    	driver.quit();
    }
}
