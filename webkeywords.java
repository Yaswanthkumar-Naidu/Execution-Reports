package common_utilities.common.action_keywords;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.ExtentUtilities;
import report_utilities.Model.ExtentModel.PageDetails;
import report_utilities.common.ReportCommon;
import report_utilities.common.ScreenshotCommon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;



public class webkeywords
{
	private static final String LOG_SWITCHED_TO = "Switched to: {}";

	public static enum SelectType
	{
		SELECT_BY_INDEX,
		SELECT_BY_TEXT,
		SELECT_BY_VALUE,
	}

	private static final webkeywords _instance = new webkeywords();
	private static final Logger logger =LoggerFactory.getLogger(webkeywords.class.getName());
	private static final String STATUSPASS="PASS";
	private static final String STATUSFAIL="FAIL";
	private static final String STATUSDONE="DONE";
	ExtentUtilities extentUtilities = new ExtentUtilities();
	ScreenshotCommon scm = new ScreenshotCommon();

	ReportCommon testStepDetails = new ReportCommon();
	ReportCommon exceptionDetails = new ReportCommon();
	
	public static webkeywords instance()
	{

		return _instance;

	}
	
	public void navigate(WebDriver driver, String url, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String actionNv = "Navigate -> "+url;
		String actionDescriptionNv = "";
		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			logger.info("Url = {}",url);
			if (!(url.startsWith("http://") || url.startsWith("https://")))
			
			driver.navigate().to(url);
			LocalDateTime EndTime =  LocalDateTime.now();
			testStepDetails.logTestStepDetails(driver, testCaseParam, actionNv, actionDescriptionNv,pageDetails, startTime, STATUSDONE);
			logger.info("Successfully Navigated to {} ",url);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",actionNv,actionDescriptionNv);
			testStepDetails.logExceptionDetails(driver, testCaseParam, actionNv, actionDescriptionNv, startTime,e);
			testStepDetails.logTestStepDetails(driver, testCaseParam, actionNv, actionDescriptionNv,pageDetails, startTime, STATUSFAIL);

			throw e;
		}


	}

	public void openUrl(WebDriver drivernew, String url, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -->"+url ;
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			logger.info("Url = {}" , url);
			if (!(url.startsWith("http://") || url.startsWith("https://")))
				throw new Exception("URL is invalid format and cannot open page");
			drivernew.navigate().to(url);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
	
	public void select(WebDriver drivernew, WebElement element, SelectType type, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String action = "Select dropdown Value:"+options;
		String actionDescription = "Select dropdown Value:-"+options;
		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			logger.info("Option = {}" ,options);

			switch (type)
			{
			case SELECT_BY_INDEX:
				try
				{


					if (options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a") ) 
					{
						break;
					}					

					else
					{

						webkeywords.instance().FluentWait(drivernew,element);
						scrollIntoViewElement(drivernew, element);
						if(element.isEnabled()==true) 
						{
							action = "Selected Value:-"+options;
							actionDescription = "Selected Value:-"+options;
							Select select = new Select(element);
							select.selectByIndex(Integer.parseInt(options));
							testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
						}

					}

				}
				catch (Exception e)
				{
					logger.error("Failed ==> {} {}" , action , actionDescription);
					testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					throw e;
				}

				break;
			case SELECT_BY_TEXT:

				if (options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a") ) 
				{
					break;
				}
				else
				{
					webkeywords.instance().FluentWait(drivernew, element);
					scrollIntoViewElement(drivernew, element);
					if(element.isEnabled()==true && element.isDisplayed()==true) 
					{
						action = "Selected Value:-"+options;
						actionDescription = "Selected Value:-"+options;
						Select select1 = new Select(element);
						select1.selectByVisibleText(options);
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
						
					}


				}
				break;
			case SELECT_BY_VALUE:

				webkeywords.instance().FluentWait(drivernew, element);
				scrollIntoViewElement(drivernew, element);
				Select select2 = new Select(element);
				select2.selectByValue(options);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				verifyValueSelected(drivernew, element,options,testCaseParam,pageDetails);
				Thread.sleep(100);			
				break;
			default:
				throw new Exception("Get error in using Selected");
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} ", action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}
	}

	public void trymultipleclick(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		webkeywords.instance().FluentWait(drivernew, element);
		String action="";
		try 
		{

			if(!getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString().equals(null)) 
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			}

		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
		}

		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();


		try
		{	
			Actions actionnew = new Actions(drivernew);
			actionnew.moveToElement(element).build().perform(); 
			element.click();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			try
			{
				
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform(); 
				element.click();
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


			}

			catch(Exception ex) 
			{
				try
				{
					
					Actions actionnew = new Actions(drivernew);
					actionnew.moveToElement(element).build().perform(); 
					element.click();
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

					
				}

				catch(Exception exx) 
				{
					try
					{
						
						Actions actionnew = new Actions(drivernew);
						actionnew.moveToElement(element).build().perform(); 
						element.click();
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

						

					}

					catch(Exception exxx) 
					{
						try
						{
								Actions actionnew = new Actions(drivernew);
							actionnew.moveToElement(element).build().perform(); 
							element.click();
							testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);



						}

						catch(Exception exxxx) 
						{
							try
							{
								Actions actionnew = new Actions(drivernew);
								actionnew.moveToElement(element).build().perform(); 
								element.click();
								testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);



							}

							catch(Exception exp) 
							{
								logger.error("Failed ==> {} {}" , action , actionDescription);
								testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,exp);
								testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

								throw e;

							}

						}

					}

				}

			}
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}
	}

	public void click(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action="";

		if((testData.equalsIgnoreCase("n/a")==false)||(testData.equalsIgnoreCase("n\\a"))==false||(testData.equalsIgnoreCase("n\\\\a"))==false)
		{
		webkeywords.instance().FluentWait(drivernew, element);
		webkeywords.instance().WaitElementforelementclickable(drivernew, element, 1000);
		scrollIntoViewElement(drivernew, element);
		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();
		String xpath= getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
		
		try 
		{

			if(!getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString().equals(null)) 
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

			}
	
		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

		} 

		

			try
			{		
				
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform();

				element.click();


				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
				Thread.sleep(900);


			}
			catch (Exception e)
			{
				try 
				{
					webkeywords.instance().WaitElementforelementclickable(drivernew, element, 1000);
					JavascriptExecutor executor = (JavascriptExecutor)drivernew;
					executor.executeScript("arguments[0].scrollIntoView(true);", element);
					Thread.sleep(300);
					executor.executeScript("arguments[0].click();", element);

					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				}
				catch(Exception f) 
				{
					logger.error("Failed ==> {} {}" , action , actionDescription);
					testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					throw e;
				}

			}
			
		}
	}


	public void clickwithaction(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails,String TestData) throws Exception
	{

		String action="";
		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();
		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{
		try {

			try 
			{
				webkeywords.instance().FluentWait(drivernew, element);
				webkeywords.instance().WaitElementforelementclickable(drivernew, element, 1000);
				scrollIntoViewElement(drivernew, element);

				if(!getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString().equals(null)) 
				{ 
					action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
					actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				} 
				else

				{
					action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
					actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

				}

			}
			catch(Exception e) 
			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

			} 



			try
			{		
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform();

				element.click();


				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				Thread.sleep(900);

			}
			catch (Exception e)
			{

				logger.error("Failed ==>{} {} " ,action , actionDescription);
				testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				throw e;


			}

		}
		catch(Exception e)
		{
			throw e;
		}
		}
	}

	public void waitElementtobeVisible(WebDriver drivernew, String xpath, int timeOut) throws Exception {
		int count = 0;
		int maxTries = 5;
		while (count < maxTries) {
			try {
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				boolean elementpresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
						.isDisplayed();
				if ((elementpresent) == true) {
					break;
				}
			} catch (Exception e) {
				count++;
				if (count == maxTries) {
					break;
				}
			}
		}
	} 




	public void clickWithoutwait(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails,String TestData) throws Exception
	{
		String action="";
		Thread.sleep(100);
		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();
		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{
		try 
		{

			if(!getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString().equals(null)) 
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

			}

		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();
			actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails).toString();

		} 




		try
		{		


			JavascriptExecutor executor = (JavascriptExecutor)drivernew;
			executor.executeScript("arguments[0].click();", element);

			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			Thread.sleep(900);

		}
		catch (Exception e)
		{
			try 
			{
				JavascriptExecutor executor = (JavascriptExecutor)drivernew;
				executor.executeScript("arguments[0].click();", element);

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

			}
			catch(Exception f) 
			{
				logger.error("Failed ==> {} {}" , action , actionDescription);
				testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				throw e;
			}

		}
		}
	}
	public void waitElementToBeVisibleNew(WebDriver drivernew, WebElement element, int timeOut) throws Exception {
		int count = 0;
		int maxTries = 5;
		while (count < maxTries) {
			try {
				By locatorvalue = getLocatorvalue(element, drivernew);
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				wait.until(ExpectedConditions.presenceOfElementLocated(locatorvalue));
				break;
			} catch (Exception e) {
				Thread.sleep(10000);
				count++;
				if (++count == maxTries) {
					throw e;
				}
			}
		}
	}

	public void waitForElementToBeVisible(WebDriver drivernew, String elementId) throws Exception {
		int count = 0;
		int maxTries = 5;
		int timeOut=3000;
		while (count < maxTries) {
			try {
				WebElement element=drivernew.findElement(By.xpath(elementId));
				By locatorvalue = getLocatorvalue(element, drivernew);
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				wait.until(ExpectedConditions.presenceOfElementLocated(locatorvalue));
				break;
			} catch (Exception e) {
				Thread.sleep(10000);
				count++;
				if (++count == maxTries) {
					throw e;
				}
			}
		}
	}


	public String getLocatorFromWebElement(WebElement element,WebDriver drivernew,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String locatoroutput= element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
		String[] Splitlocator=locatoroutput.split(": ");
		Splitlocator[0].replaceAll("\\s", "");	    
		Splitlocator[1].replaceAll("\\s", "");

		String text=getElementText(drivernew, Splitlocator[0],Splitlocator[1],  testCaseParam,pageDetails);

		return text;
	}

	public void hovermousetoelement(WebDriver drivernew,WebElement element,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		try 
		{
			Thread.sleep(1000);
			Actions action = new Actions(drivernew);

			action.moveToElement(element).perform();
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
	public void SwitchtoFrame(WebDriver _driver, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			_driver.switchTo().frame(element);
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	public void switchToWindowByTitle(WebDriver _driver, String title, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{
			Thread.sleep(2000);
			Set<String> windows=_driver.getWindowHandles();


			for (String handle: windows)
			{

				String myTitle = _driver.switchTo().window(handle).getTitle();
				// now apply the condition - moving to the window with blank title
				if (myTitle.equals(title))
				{

					_driver.switchTo().window(handle);
					logger.info("switched to Window--> {}",title);
					Thread.sleep(0);
					break;
				}
				else
				{
					try
					{
						_driver.switchTo().window(handle);
						logger.info("switched to main Window");
					}
					catch (Exception e)
					{
						logger.info("Unable to switch to Main Window");
					}
				}





			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}


	public void Refresh(WebDriver _driver, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Refresh the page ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{
			_driver.navigate().refresh();
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			throw e;
		}
	}

	public boolean FindElementBool(WebDriver _driver, By by, TestCaseParam testCaseParam, int... timeOut)
	{
		if(timeOut==null)
		{
			timeOut[0]=60;
		}

		boolean found = false;
		try
		{
			WaitElementVisible(_driver, by, timeOut[0]);
			found = true;
		}
		catch (Exception e)
		{
			found = false;
		}
		logger.info(by + "found = {} ",found);
		return found;
	}

	public void ZoombackToOriginal(WebDriver _driver, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{


			JavascriptExecutor js = (JavascriptExecutor)_driver;

			js.executeScript("document.body.style.zoom='100%'");

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,Action ,ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	public void JSClick(WebDriver _driver, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails, int... timeOut) throws Exception
	{
		if(timeOut==null)
		{
			timeOut[0]=60;
		}

		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			WebDriverWait wait = new WebDriverWait(_driver, timeOut[0]);


			JavascriptExecutor js = (JavascriptExecutor)_driver;
			js.executeScript("arguments[0].click();", element);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,Action ,ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}


	public void ClickRadioButton(WebDriver _driver, TestCaseParam testCaseParam, WebElement element) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			for (int i = 0; i < 10; i++)
			{
				element.click();

				if (element.isSelected())
				{
					break;
				}


			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,Action, ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	
	public void SetText(WebDriver _driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Entered Text -> "+ text;
		String ActionDescription = "Entered Text -> "+ text;;

		LocalDateTime StartTime=  LocalDateTime.now();

		try
		{

			if(text.equalsIgnoreCase("N/A")|| text.equalsIgnoreCase("N//A"))
			{

			}

			else
			{
				webkeywords.instance().FluentWait(_driver, element);
				WebDriverWait wait = new WebDriverWait(_driver, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 if (textBoxValue.matches("^[a-zA-Z0-9]+$")||textBoxtest.matches("^[a-zA-Z0-9]+$")) 
					 {
						 isDataFilled = true;
					 }
				       
				        else if (textBoxValue.matches("^[a-zA-Z]+$")||textBoxtest.matches("^[a-zA-Z]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^[a-z A-Z]+$")||textBoxtest.matches("^[a-z A-Z]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        
				        else if (textBoxValue.matches("^[0-9]+$")||textBoxtest.matches("^[0-9]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/_-]).+$")) {
				        	isDataFilled = true;				        }
				        
				        else if (textBoxValue.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;				        }
				        
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;
				        	}
					 
				        else if (textBoxValue.matches("^(?=.*[@#$!%^&+=-]).+$")||textBoxtest.matches("^(?=.*[@#$!%^&+=-]).+$")) {
				        	isDataFilled = true;
				        	}
				       

		            else 
		            {
		            	
		               logger.info("Textbox is empty. Retrying...");
		                element.clear();

						
						JavascriptExecutor jsExecutor = (JavascriptExecutor) _driver;
			            jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		            }
		        }

				LocalDateTime endTime =  LocalDateTime.now();

				logger.info("Successfully Entered Text {} to {}" ,text , element);

				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);

				verifyValueEntered(_driver, element,text,testCaseParam,pageDetails);
				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,Action ,ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			throw e;
		}

	}
	
	public void SetText_MultipleRetry(WebDriver driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Entered Text -> "+ text;
		String ActionDescription = "Entered Text -> "+ text;;

		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			if(text.equalsIgnoreCase("N/A")|| text.equalsIgnoreCase("N//A"))
			{

			}

			
			else
			{
				webkeywords.instance().FluentWait(driver, element);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 if (textBoxValue.matches("^[a-zA-Z0-9]+$")||textBoxtest.matches("^[a-zA-Z0-9]+$")) 
					 {
						 isDataFilled = true;
					 }
				    
				        else if (textBoxValue.matches("^[a-zA-Z]+$")||textBoxtest.matches("^[a-zA-Z]+$")) 
				        {
				        	isDataFilled = true;
				        }
				     
				        else if (textBoxValue.matches("^[0-9]+$")||textBoxtest.matches("^[0-9]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/_-]).+$")) {
				        	isDataFilled = true;				        }
				      
				        else if (textBoxValue.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;				        }
				      
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;
				        	}
				       

		            else 
		            {
		            	
		               logger.info("Textbox is empty. Retrying...");
		                element.clear();

						
						JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			            jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		            }
		        }

				logger.info("Successfully Entered Text {} to {}" ,text ,element);
				testStepDetails.logTestStepDetails(driver, testCaseParam, Action, ActionDescription,pageDetails, startTime, STATUSDONE);
				verifyValueEntered(driver, element,text,testCaseParam,pageDetails);
				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,Action ,ActionDescription);
			testStepDetails.logExceptionDetails(driver, testCaseParam, Action, ActionDescription, startTime,e);
			testStepDetails.logTestStepDetails(driver, testCaseParam, Action, ActionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}

	}

	public void SetTextwithoutverification(WebDriver _driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Entered Text -> "+ text;
		String ActionDescription = "Entered Text -> "+ text;;

		LocalDateTime StartTime=  LocalDateTime.now();

		try
		{

			if(text.equalsIgnoreCase("N/A")|| text.equalsIgnoreCase("N//A"))
			{

			}

			else
			{
				webkeywords.instance().FluentWait(_driver, element);
				WebDriverWait wait = new WebDriverWait(_driver, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 if (textBoxValue.matches("^[a-zA-Z0-9]+$")||textBoxtest.matches("^[a-zA-Z0-9]+$")) 
					 {
						 isDataFilled = true;
					 }
				        
				        else if (textBoxValue.matches("^[a-zA-Z]+$")||textBoxtest.matches("^[a-zA-Z]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        
				        else if (textBoxValue.matches("^[0-9]+$")||textBoxtest.matches("^[0-9]+$")) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=/_-]).+$")) {
				        	isDataFilled = true;				        }
				       
				        else if (textBoxValue.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;				        }
				       
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;
				        	}
				        
				       

		            else 
		            {
		            	
		                logger.info("Textbox is empty. Retrying...");
		                element.clear();
						
						
						JavascriptExecutor jsExecutor = (JavascriptExecutor) _driver;
			            jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		            }
		        }



				LocalDateTime EndTime =  LocalDateTime.now();

				logger.info("Successfully Entered Text {} to {}" ,text ,element);

				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);

				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,Action ,ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			throw e;
		}

	}


	public void JSSetText(WebDriver _driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();


		try
		{
			waitweb_ElementVisible(_driver, element,200);

			 JavascriptExecutor js = (JavascriptExecutor)_driver;

			 js.executeScript("arguments[0].setAttribute('value','" + text + "');", element);
		}
		catch (WebDriverException e)
		{
			throw new Exception("Element is not enable for set text" + "\r\n" + "error: " + e.getMessage());
		}

		catch (Exception e)
		{
			throw new Exception("Element is not enable for set text" + "\r\n" + "error: " + e.getMessage() + e.getStackTrace());
		}

	}

	public String JSGetText(WebDriver _driver, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		String webText = "";

		try
		{
			webText = element.getText().trim();

		}
		catch (WebDriverException e)
		{
			throw new Exception("Element is not enable for get text" + "\r\n" + "error: " + e.getMessage());
		}

		catch (Exception e)
		{
			throw new Exception("Element is not enable for get text" + "\r\n" + "error: " + e.getMessage() +  e.getStackTrace());
		}
		return webText;

	}

	public void Submit(WebDriver _driver, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{
			waitweb_ElementVisible(_driver, element,200);
			element.submit();
			LocalDateTime EndTime =  LocalDateTime.now();
			logger.info("Successfully Clicked Button ==> {}" ,element);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}

	}

	public void SetDate(WebDriver _driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try


		{
			String ID = element.getAttribute("ID");
			((JavascriptExecutor)_driver).executeScript("document.getElementById('" + ID + "').removeAttribute('readonly',0);");


			element.click();

			((JavascriptExecutor)_driver).executeScript("document.getElementById('" + ID + "').setAttribute('value', '" + text + "')");

			_driver.findElement(By.xpath("//div[@id=\"fare_" + text + "\"]")).click();


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}

	}


	public void WaitElementforelementclickable(WebDriver _driver,WebElement element, int timeOut) throws Exception
	{

		try
		{	
			By locatorvalue=getLocatorvalue(element,_driver);

			WebDriverWait wait = new WebDriverWait(_driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorvalue));

		}
		catch (WebDriverException e)
		{
			try 
			{
				Thread.sleep(1500);
				By locatorvalue=getLocatorvalue(element,_driver);

				WebDriverWait wait = new WebDriverWait(_driver, timeOut);
				wait.until(ExpectedConditions.elementToBeClickable(locatorvalue));
			}
			catch(WebDriverException f) 
			{

			}


		}
	}



	public static void waitForPageLoad(WebDriver driver) {


	}
	


	public By getLocatorvalue(WebElement element,WebDriver _driver) throws Exception
	{

		String locatoroutput= element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
		String[] Splitlocator=locatoroutput.split(": ");
		Splitlocator[0].replaceAll("\\s", "");	    
		Splitlocator[1].replaceAll("\\s", "");


		By locatorvalue=getlocatorvalueforwait(_driver, Splitlocator[0],Splitlocator[1]);
		return locatorvalue;
	}

	public By getlocatorvalueforwait(WebDriver _driver, String LocatorType,String LocatorValue) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{
			By locatorvalue = null;
			LocatorType.strip().trim();
			LocatorValue.strip().trim();
			logger.info("Finding Element having ==> LocatorType = {} => LocatorValue =  {}" ,LocatorType , LocatorValue);
			switch (LocatorType.toLowerCase())
			{
			case " id":
				locatorvalue = By.id(LocatorValue.trim());
				break;
			case " name":
				locatorvalue = By.name(LocatorValue.trim());
				break;
			case " xpath":
				locatorvalue = By.xpath(LocatorValue.trim());
				break;
			case " tag":
				locatorvalue = By.name(LocatorValue.trim());
				break;
			case " link text":
				locatorvalue = By.linkText(LocatorValue.trim());
				break;
			case " css":
				locatorvalue = By.cssSelector(LocatorValue.trim());
				break;
			case " class":
				locatorvalue = By.className(LocatorValue.trim());
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType =  {}" , LocatorType);
				throw new Exception("Support FindElement with 'id' 'name' 'xpath' 'tag' 'link' 'css' 'class'");
			}
			return locatorvalue;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} " , Action ,ActionDescription);

			throw e;
		}

	}

	public void WaitElementToBeClickable(WebDriver _driver, By locatorValue, int timeOut)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(_driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorValue));
		}
		catch (WebDriverException e)
		{
			
		}
	}

	public void Waitforinvisibilityofelement(WebDriver _driver, By locator) throws Exception
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(_driver, 3000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
		catch(Exception e) 
		{

		}

	}

	public String WP_getPageTitle(WebDriver _driver) 
	{
		try {
			String WP_PageTitle="";
			int len=_driver.findElements(By.xpath("//*[@alt='Print' and @tabindex='0' and @class='vertical-align-middle cursor-hand height-20px printDoc']/.././../div/h2/span/label")).size();
			if(len>0) 
			{
				WP_PageTitle=_driver.findElement(By.xpath("//*[@alt='Print' and @tabindex='0' and @class='vertical-align-middle cursor-hand height-20px printDoc']/.././../div/h2/span/label")).getText();
			}
			else 
			{
				WP_PageTitle="";
			}
			return WP_PageTitle;
		}
		catch(Exception e) 
		{
			throw e;
		}
	}




	public void FluentWait(WebDriver _driver, WebElement element) throws Exception
	{

		try
		{																		

			Wait<WebDriver> wait2 = new FluentWait<WebDriver>(_driver)
					.withTimeout(1000, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(Exception.class)
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(ElementNotVisibleException.class);

			wait2.until(new Function<WebDriver, WebElement>() 
			{
				public WebElement apply(WebDriver driver) {

					if(element.isDisplayed()==true)
					{
						return element;

					}else
					{
						return null;
					}
				}
			});

		}
		catch (TimeoutException e)
		{

			e.printStackTrace();
		}
	}
	
	public void WaitElementVisible(WebDriver _driver, By locatorValue, int timeOut)
	{



		try
		{
			logger.info("Waiting for Element = {}" ,locatorValue);
			WebDriverWait wait1 = new WebDriverWait(_driver, timeOut);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(locatorValue));

		}
		catch (TimeoutException e)
		{
			logger.error("Get {} , {} is not visible" , e.toString(), locatorValue);
			
		}
	}

	public void WaitElementClickable(WebDriver _driver, WebElement element)
	{
		LocalDateTime StartTime=  LocalDateTime.now();

		try
		{
			logger.info("Waiting for Element = {}" ,element);
			WebDriverWait wait = new WebDriverWait(_driver, 5000);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch (Exception e)
		{
			throw e;
		}
	}


	public void WaitElementClickable(WebDriver _driver, WebElement locatorValue, int timeOut)
	{
		LocalDateTime StartTime=  LocalDateTime.now();

		try
		{
			logger.info("Waiting for Element = {} ", locatorValue);
			WebDriverWait wait = new WebDriverWait(_driver, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorValue));

		}
		catch (TimeoutException e)
		{
			logger.error("Get {} , {} is not visible",e.toString() ,locatorValue);
			
		}
	}
	public void WaitElementEnabled(WebDriver _driver, WebElement element, int timeOut) throws Exception
	{
		for (int i = 0; i < timeOut; i++)
		{
			try
			{
				if (element.isEnabled())
				{
					break;
				}



			}
			catch (WebDriverException e)
			{
				throw new Exception("Get " + e.getMessage() + ", " + "Element is not visible");
			}
		}
	}

	public void SwitchFocusToOtherWindow(WebDriver _driver, By pagetitlelocator)
	{
		try {
			String currentWindow = _driver.getWindowHandle();

			Set<String> allWindows =  _driver.getWindowHandles();
			for(String curWindow : allWindows){
				_driver.switchTo().window(curWindow);
				if(_driver.findElements(pagetitlelocator).size()>0) 
				{
					String PageTitle=_driver.findElement(pagetitlelocator).getText();
					logger.info(LOG_SWITCHED_TO,PageTitle);
				}
				else 
				{
					String PageTitle=_driver.getTitle();
					logger.info(LOG_SWITCHED_TO,PageTitle);

				}
			}

		}
		catch(Exception e) 
		{
			throw e;
		}
	}

	public void SwitchFocusToMainWindow(WebDriver _driver, By pagetitlelocator)
	{
		try {
			String currentWindow = _driver.getWindowHandle();
			// _driver.switchTo().window(currentWindow);
			String PageTitle=_driver.findElement(pagetitlelocator).getText();
			logger.info(LOG_SWITCHED_TO,PageTitle);
			Set<String> allWindows =  _driver.getWindowHandles();

			for(String curWindow : allWindows){
				_driver.switchTo().window(curWindow);
				if(_driver.findElements(pagetitlelocator).size()>0) 
				{
					PageTitle=_driver.findElement(pagetitlelocator).getText();
					logger.info(LOG_SWITCHED_TO,PageTitle);
					break;
				}
				else 
				{
					PageTitle=_driver.getTitle();
					logger.info(LOG_SWITCHED_TO,PageTitle);

				}
			}
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
	
	public void WaitTitleContains(WebDriver _driver, String title) throws Exception
	{
		try
		{
			int timeOut=10000;
			WebDriverWait wait = new WebDriverWait(_driver, timeOut);
			wait.until(ExpectedConditions.titleContains(title));
		}
		catch (WebDriverException e)
		{
			throw new Exception("Get " + e.getMessage() + ", [" + title + "] is not displayed in WebPage title [" + _driver.getTitle() + "]");
		}
	}
	
	public String GetAttribute(WebDriver _driver, WebElement element, String attribute)
	{
		return element.getAttribute(attribute);
	}

	public void VerifyAttributeValue(WebDriver _driver, WebElement element, String attribute,String ExpectedText,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String Action = "Verify "+element.getAttribute(attribute)+" Attribute value-->"+ExpectedText;
		String ActionDescription = "Verify "+element.getAttribute(attribute)+" Attribute value"+ExpectedText;
		LocalDateTime StartTime = LocalDateTime.now();
		if(element.getAttribute(attribute).equalsIgnoreCase(ExpectedText)) 

		{
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);
		}
		else 
		{
			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

		}
	}
	
	public boolean GetTitle(WebDriver _driver,String ExpectedTitle, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			String PageTitle = _driver.getTitle();
			boolean ValidTitle = false;
			if (ExpectedTitle.contains(PageTitle))
			{
				ValidTitle = true;
			}
			else
			{
				ValidTitle = false;
			}
			LocalDateTime EndTime =  LocalDateTime.now();


			logger.info("Successfully Navigated to " + PageTitle);
			return ValidTitle;


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}
	
	public String GetCssValue(WebDriver _driver, WebElement element, String value)
	{
		return element.getCssValue(value);
	}
	
	public String GetPageSource(WebDriver _driver)
	{
		return _driver.getPageSource();
	}
	
	public void waitForPageToLoad(WebDriver drivernew, int time)
	{
		
	}
	
	public void SetAttribute(WebDriver _driver, WebElement element, String attributeName, String value, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			WrapsDriver wrappedElement = (WrapsDriver) element;
			if (wrappedElement == null)
				throw new Exception("Element must wrap a web driver");

			_driver = wrappedElement.getWrappedDriver();
			JavascriptExecutor js = (JavascriptExecutor)_driver;
			if (js == null)
				throw new Exception("Element must wrap a web driver that supports javascript execution");
			js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, value);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}
	/// <summary>
	/// This method use for 
	/// clear any text on text field
	/// </summary>
	/// <param name="element"></param>
	public void ClearText(WebDriver _driver, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{

			element.clear();
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}
	/// <summary>
	/// This method is use for
	/// Execute javascript
	/// </summary>
	/// <param name="driver"></param>
	/// <returns></returns>
	public JavascriptExecutor JavaScript(WebDriver driver)
	{
		return (JavascriptExecutor)driver;
	}

	/// <summary>
	/// This method is use for
	/// return element
	/// </summary>
	/// <param name="value"></param>
	/// <returns></returns>
	public WebElement FindElement(WebDriver _driver, String value, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{
			WebElement element = null;
			String LocatorType = value.split(";")[0];
			String LocatorValue = value.split(";")[1];
			logger.info("Finding Element having ==> LocatorType = " + LocatorType + " => LocatorValue = " + LocatorValue);
			switch (LocatorType.toLowerCase())
			{
			case "id":
				element = _driver.findElement(By.id(LocatorValue));
				break;
			case "name":
				element = _driver.findElement(By.name(LocatorValue));
				break;
			case "xpath":
				element = _driver.findElement(By.xpath(LocatorValue));
				break;
			case "tag":
				element = _driver.findElement(By.name(LocatorValue));
				break;
			case "link":
				element = _driver.findElement(By.linkText(LocatorValue));
				break;
			case "css":
				element = _driver.findElement(By.cssSelector(LocatorValue));
				break;
			case "class":
				element = _driver.findElement(By.className(LocatorValue));
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType = " + LocatorType);
				throw new Exception("Support FindElement with 'id' 'name' 'xpath' 'tag' 'link' 'css' 'class'");
			}
			return element;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);

			throw e;
		}

	}

	public String getElementText(WebDriver _driver, String LocatorType,String LocatorValue, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Navigate -> ";
		String ActionDescription = "";
		LocalDateTime StartTime=  LocalDateTime.now();
		try
		{


			String Text=null;
			WebElement element = null;
			LocatorType.trim();
			LocatorValue.trim();
			logger.info("Finding Element having ==> LocatorType = " + LocatorType + " => LocatorValue = " + LocatorValue);
			switch (LocatorType.toLowerCase())
			{
			case " id":
				element = _driver.findElement(By.id(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " name":
				element = _driver.findElement(By.name(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " xpath":
				element = _driver.findElement(By.xpath(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " tag":
				element = _driver.findElement(By.name(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " link text":
				element = _driver.findElement(By.linkText(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " css":
				element = _driver.findElement(By.cssSelector(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			case " class":
				element = _driver.findElement(By.className(LocatorValue.trim()));
				Text=element.getAttribute("Value");
				if(Text==null) 
				{
					Text=element.getText();
				}
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType = " + LocatorType);
				throw new Exception("Support FindElement with 'id' 'name' 'xpath' 'tag' 'link' 'css' 'class'");
			}
			return Text;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);

			throw e;
		}

	}
	/// <summary>
	/// This method is use for
	/// return elements in list
	/// </summary>
	/// <param name="value"></param>
	/// <returns></returns>
	public ArrayList<WebElement> FindElements(WebDriver _driver, String value) throws Exception
	{
		List<WebElement> elements = null;
		String LocatorType = value.split(";")[0];
		String LocatorValue = value.split(";")[1];

		logger.info("Finding Element having ==> LocatorType = " + LocatorType + " => LocatorValue = " + LocatorValue);

		switch (LocatorType.toLowerCase())
		{
		case "id":
			elements = _driver.findElements(By.id(LocatorValue));
			break;
		case "name":
			elements = _driver.findElements(By.name(LocatorValue));
			break;
		case "xpath":
			elements = _driver.findElements(By.xpath(LocatorValue));
			break;
		case "tag":
			elements = _driver.findElements(By.name(LocatorValue));
			break;
		case "link":
			elements = _driver.findElements(By.linkText(LocatorValue));
			break;
		case "css":
			elements = _driver.findElements(By.cssSelector(LocatorValue));
			break;
		case "class":
			elements = _driver.findElements(By.className(LocatorValue));
			break;
		default:
			logger.info("Incorrect Locator Type ==> LocatorType = " + LocatorType);
			throw new Exception("Support FindElement with 'id' 'name' 'xpath' 'tag' 'link' 'css' 'class'");
		}
		return (ArrayList<WebElement>) elements;
	}

	public boolean IsElementVisible(WebElement element)
	{
		return element.isDisplayed();
	}

	public void waitweb_ElementVisible(WebDriver _driver, WebElement locatorValue, int timeOut) throws Exception
	{
	//	LocalDateTime StartTime = LocalDateTime.now();

		try
		{
			logger.info("Waiting for Element = " + locatorValue);
			WebDriverWait wait = new WebDriverWait(_driver, timeOut);
			boolean visible = IsElementVisible(locatorValue);
			while (true)
			{
				if(visible)
				{
					break;
				}
				else
				{

					visible = false;
				}
			}

		}
		catch (Exception e)
		{
			logger.error("Get " + e.toString() + ", " + locatorValue + " is not visible");
			throw new Exception("Get " + e.getMessage() + ", " + locatorValue + " is not visible");
		}
	}

	public void VerifyTextDisplayed(WebDriver driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails, int timeout) throws Exception
	{

		String value = "";
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		waitweb_ElementVisible(driver, element,timeout);

		value = JSGetText(driver, element, testCaseParam,pageDetails);

		if (value.equals(text.trim()))
		{
			logger.info("The value displayed in the application is as expected: ||" + value + "||");
		}
		else
		{
			logger.info("The value displayed in the application: ||" + value + "||, Expected Value: ||" + text + "||. This is not as expected");
		}
	}



	public void VerifyElementPresent(WebDriver driver, WebElement element,TestCaseParam testCaseParam, int timeout) throws Exception
	{
		String TestStepName = "VerifyElementPresent";
		String TestStepDescription = "VerifyElementPresent";
		LocalDateTime StartTime = LocalDateTime.now();
		Boolean isDisplayed = false;

		try
		{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			waitweb_ElementVisible(driver, element,timeout);
			isDisplayed = IsElementVisible(element);
			logger.info("Element present in application: " + isDisplayed);
			if (isDisplayed)
			{
				logger.info("The Element is displayed in the application");
				//testStepLog.Log("Verifying Element Present", "Element is present on the page: ||" + by + "||", Report.Status.PASS, driver, testCase, ScenarioName, module, currentIteration, browser, node);
			}
			else
			{
				logger.info("The Element is not displayed in the application");
				//testStepLog.Log("Verifying Element Present", "Element is hidden on the page: ||" + by + "||", Report.Status.FAIL, driver, testCase, ScenarioName, module, currentIteration, browser, node);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + TestStepDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, TestStepName, TestStepDescription, StartTime);
			throw e;
		}
	}


	public void DocumentUpload(WebDriver _driver, WebElement element,String Path,String DocName, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Upload Document";
		String ActionDescription = "Upload Document";
		LocalDateTime StartTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().FluentWait(_driver, element);


			element.sendKeys(Path+DocName);

			testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);


		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}


	//*********************************SCREEN VALIDATIONS**************************************************
	public void VerifyDropdownSelection(WebDriver _driver, WebElement element, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Dropdown-->Actual:"+options+":Expected:-"+element.getText();
		String ActionDescription = "Verify Dropdown-->Actual:"+options+":Expected:-"+element.getText();
		LocalDateTime StartTime = LocalDateTime.now();
		Boolean Valuefound = false;

		try
		{
			if(options.equals("N//A")||options.equals(null)) 
			{

			}
			else
			{

				webkeywords.instance().FluentWait(_driver, element);
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(int i=0; i<allOptions.size(); i++) 
				{

					if(allOptions.get(i).getText().contains(options)) 
					{
						Valuefound=true;
						Action = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						ActionDescription = "Verify Dropdown-->Actual:"+element.getText()+":Expected:-"+allOptions.get(i).getText();
						break;
					}
				}
				if(Valuefound) 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}


	public void VerifyDropdownText(WebDriver _driver, WebElement element, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Dropdown Text-->Actual:"+options+":Expected:-"+element.getText();
		String ActionDescription = "Verify Dropdown Text-->Actual:"+options+":Expected:-"+element.getText();
		LocalDateTime StartTime = LocalDateTime.now();
		Boolean Valuefound = false;

		try
		{
			if(options.equals("N//A")||options.equals(null)) 
			{

			}
			else
			{
				webkeywords.instance().FluentWait(_driver, element);
				Select select = new Select(element);
				List<WebElement> allOptions =select.getAllSelectedOptions();			
				for(int i=0; i<allOptions.size();i++) 
				{
					if(allOptions.get(i).getText().contains(options)) 
					{
						Valuefound=true;
						Action = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						ActionDescription = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						break;
					}
				}

				if(Valuefound) 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	public void verifytextentered(WebDriver _driver, WebElement element, String text, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Dropdown Selection-->"+text;
		String ActionDescription = "Verify Dropdown Selection"+text;
		LocalDateTime StartTime = LocalDateTime.now();
		Boolean Verified = false;

		try
		{
			if(text.equals("N//A")||text.equals(null)) 
			{

			}
			else
			{
				webkeywords.instance().FluentWait(_driver, element);
				String EnteredText = element.getText();

				if(EnteredText.equals(text)) 
				{
					Verified=true;
				}

				if(Verified)
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSPASS);
				}
				else 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);
				}
			}



		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	public void VerifyElementDisplayed(WebDriver _driver, WebElement element,String testdata, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action_v1 = "Verify Element Displayed--"+element.getAttribute("Value");
		String actionDescription = "Verify Element Displayed--"+element.getAttribute("Value");
		LocalDateTime startTime = LocalDateTime.now();

		if((testdata.equalsIgnoreCase("n/a")==false)||(testdata.equalsIgnoreCase("n\\a"))==false||(testdata.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{
			scrollIntoViewElement(_driver, element);
			Actions action = new Actions(_driver);
			action.moveToElement(element).perform();
			if(element.isDisplayed()==true) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, action_v1, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, action_v1, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + action_v1 + actionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, action_v1, actionDescription, startTime,e);
			throw e;
		}
		}
	}
	
	public void VerifyElementDisplayed_textattribute(WebDriver _driver, WebElement element,String TestData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Displayed--"+element.getText();
		String ActionDescription = "Verify Element Displayed--"+element.getText();
		LocalDateTime StartTime = LocalDateTime.now();

		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{
			Actions action = new Actions(_driver);
			action.moveToElement(element).perform();
			if(element.isDisplayed()==true) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}

	public void verifyelementnotdisplayed(WebDriver _driver, List<WebElement> element, String TestData, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Not Displayed--"+ TestData;
		String ActionDescription = "Verify Element Not Displayed--"+ TestData;
		LocalDateTime StartTime = LocalDateTime.now();

		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{


		try
		{

			if(element.size()>0) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSPASS);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}
	
	
	public void verifyelementnotdisplayed(WebDriver _driver, String xpath, String TestData, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Not Displayed--"+ TestData;
		String ActionDescription = "Verify Element Not Displayed--"+ TestData;
		LocalDateTime StartTime = LocalDateTime.now();

		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{

			int size=_driver.findElements(By.xpath(xpath)).size();
			if(size>0) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSPASS);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}




	public void VerifyElementEnabled(WebDriver _driver, WebElement element,String TestData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Enabled";
		String ActionDescription = "Verify Element Enabled";
		LocalDateTime StartTime = LocalDateTime.now();

				if (!TestData.equalsIgnoreCase("n/a") || !TestData.equalsIgnoreCase("n\\a") || !TestData.equalsIgnoreCase("n\\\\a"))

			{

		try
		{
			Actions action=new Actions(_driver);
			action.moveToElement(element);

			if(element.isEnabled())
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}

	public void VerifyElementSelected(WebDriver _driver, WebElement element,String TestData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Selected";
		String actionDescription = "Verify Element Selected";
		LocalDateTime startTime = LocalDateTime.now();

		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{


		try
		{

			if(element.isSelected()==true) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + action + actionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void VerifyElementDisabled(WebDriver _driver, WebElement element,String TestData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Disabled";
		String ActionDescription = "Verify Element Disabled";
		LocalDateTime StartTime = LocalDateTime.now();
		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{


		try
		{


			if(!element.getAttribute("disabled").equals("true")) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + Action + ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}

	public void VerifyCheckBoxChecked(WebDriver _driver, WebElement element,String TestData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify CheckBox Checked";
		String ActionDescription = "Verify CheckBox Checked";
		LocalDateTime StartTime = LocalDateTime.now();

		if((TestData.equalsIgnoreCase("n/a")==false)||(TestData.equalsIgnoreCase("n\\a"))==false||(TestData.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{

			if(element.getAttribute("checked").equals("true")) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}

	public void verifyElementTitle(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Title";
		String actionDescription = "Verify Element Title";
		LocalDateTime startTime = LocalDateTime.now();

		if((testData.equalsIgnoreCase("n/a")==false)||(testData.equalsIgnoreCase("n\\a"))==false||(testData.equalsIgnoreCase("n\\\\a"))==false)
		{


		try
		{

			if(element.getAttribute("title").equals(testData)) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyTextBoxValue(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify TextBox Value";
		String actionDescription = "Verify Element Title";
		LocalDateTime startTime = LocalDateTime.now();

		if((testData.equalsIgnoreCase("n/a")==false)||(testData.equalsIgnoreCase("n\\a"))==false||(testData.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{
			webkeywords.instance().FluentWait(drivernew, element);
			if(element.getAttribute("value").equals(testData)) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public boolean verifyTextDisplayed(WebDriver drivernew, WebElement element,String testdata, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "";
		String actionDescription = "";
		LocalDateTime startTime = LocalDateTime.now();

boolean isresult=false;
		
if((testdata.equalsIgnoreCase("n/a")==false)||(testdata.equalsIgnoreCase("n\\a"))==false||(testdata.equalsIgnoreCase("n\\\\a"))==false)
{


		try
		{

			if(testdata.equals("N//A")||testdata.equals(null))
			{

			}
			else
			{
				scrollIntoViewElement(drivernew, element);
				zoomWebPage(drivernew,"50%",testCaseParam,pageDetails);
				action="Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testdata;
				actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testdata;
				webkeywords.instance().FluentWait(drivernew, element);
				if(testdata.contains("\""))
				{
					testdata=testdata.replaceAll("\"", "");
				}
				if(element.getText().toLowerCase().equalsIgnoreCase(testdata.toLowerCase()))
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testdata);
					isresult=true;
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					isresult=false;
				}
			}
			zoomWebPage(drivernew,"100%",testCaseParam,pageDetails);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
		return isresult;
	}


	
	public boolean verifyText(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "";
		String actionDescription = "";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(testData.equals("N//A")||testData.equals(null)) 
			{

			}
			else
			{
				scrollIntoViewElement(drivernew, element);
				zoomWebPage(drivernew,"50%",testCaseParam,pageDetails);
				action="Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
				actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
				webkeywords.instance().FluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replaceAll("\"", "");
				}
				if(element.getText().toLowerCase().equalsIgnoreCase(testData.toLowerCase())) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
					
					return true;
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
					return false;
				}
			}
			zoomWebPage(drivernew,"100%",testCaseParam,pageDetails);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		return false;
		}

	

	public void verifyTextDisplayedValueAttribute(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		String actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		LocalDateTime startTime = LocalDateTime.now();

		if((testData.equalsIgnoreCase("n/a")==false)||(testData.equalsIgnoreCase("n\\a"))==false||(testData.equalsIgnoreCase("n\\\\a"))==false)
		{


		try
		{

			if(testData.equals("N//A")||testData.equals(null)) 
			{

			}
			else
			{
				webkeywords.instance().FluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replaceAll("\"", "");
				}
				if(element.getAttribute("value").toLowerCase().equals(testData.toLowerCase())) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyTextDisplayedTitleAttribute(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		String actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		LocalDateTime startTime = LocalDateTime.now();

		if((testData.equalsIgnoreCase("n/a")==false)||(testData.equalsIgnoreCase("n\\a"))==false||(testData.equalsIgnoreCase("n\\\\a"))==false)
		{

		try
		{
			

			if(testData.equals("N//A")||testData.equals(null)) 
			{

			}
			else
			{
				webkeywords.instance().FluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replaceAll("\"", "");
				}
				if(element.getAttribute("title").toLowerCase().equals(testData.toLowerCase())) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
					//TestStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, Status_Done);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> " + action + actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
		}



	public void VerifyTextDisplayed(WebDriver _driver, WebElement element,ArrayList<String> Text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+Text;
		String ActionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+Text;
		LocalDateTime StartTime = LocalDateTime.now();


		try
		{
			if(Text.equals("N//A")||Text.equals(null)) 
			{

			}
			else
			{
				webkeywords.instance().FluentWait(_driver, element);
				if(element.getText().contains(Text.get(0))) 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
	}

	public boolean verifyTableData(WebDriver drivernew, ArrayList<WebElement> element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Table Columns Text Displayed-->";
		String actionDescription = "Table Columns Text Displayed-->";
		LocalDateTime startTime = LocalDateTime.now();
		boolean verifydata=false;

		try
		{
			if(text.equals("N//A")||text.equals(null)) 
			{

			}
			else
			{

				ArrayList<WebElement> elementnew=new ArrayList<>();
				elementnew=element;
				ArrayList<String> dataList = new ArrayList<>();

				String[] dataCount = new String[element.size()];
				int l = 0;
				for (WebElement data : elementnew)
				{
					action = "Actual Text Displayed-->"+data.getText()+"Expected:-"+text;
					actionDescription = "Actual Text Displayed-->"+data.getText()+"Expected:-"+text;
					dataCount[l++] = data.getText();
					dataList.add(data.getText().toString());

					if(data.getText().contains(text)) 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action,actionDescription,pageDetails, startTime, STATUSPASS);
						verifydata= true;
					} 
					else 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action,actionDescription,pageDetails, startTime, STATUSFAIL);
						verifydata= false;
					}

				}
			}
		

			return 	verifydata;	


		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			return false;
		}
	}

	public void verifyTableDataifNull(WebDriver drivernew, ArrayList<WebElement> element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Table Columns Text Displayed-->";
		String actionDescription = "Table Columns Text Displayed-->";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			ArrayList<WebElement> elementnew=new ArrayList<>();
			elementnew=element;
			ArrayList<String> dataList = new ArrayList<>();

			String[] dataCount = new String[element.size()];
			int l = 0;
			for (WebElement data : elementnew)
			{
				action = "Table Columns Text Displayed-->"+data.getText();
				actionDescription = "Table Columns Text Displayed-->"+data.getText();
				dataCount[l++] = data.getText();
				dataList.add(data.getText().toString());
				if(!data.getText().equals(null)) 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}





		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifyPartialTextDisplayed(WebDriver drivernew, WebElement element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Partial Text";
		String actionDescription = "Verify Element Partial Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(element.getText().toLowerCase().contains(text.toLowerCase())) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}
	public void verifyPartialTextDisplayed(WebDriver drivernew, WebElement element,String textnew,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verified "+text+" Partial Text from "+element.getText();
		String actionDescription = "Verified "+text+" Partial Text from "+element.getText();
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(element.getText().toLowerCase().contains(text.toLowerCase())) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}



	public void verifyValueEntered(WebDriver drivernew, WebElement element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(text.equalsIgnoreCase("N//A")||text.equalsIgnoreCase("N/A")) 
			{

			}
			else
			{

				String actualText=element.getAttribute("value");
				if(text.contains("\"")) 
				{
					text=text.replaceAll("\"", "");
				}
				if(actualText.equals("___-__-____")) 
				{
					actualText=actualText.replace("___-__-____", "");
				}
				if(actualText.contains("_-")) 
				{
					actualText=actualText.replaceAll("_", "");
					actualText=actualText.replaceAll("-", "");
				}


				action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+text;
				actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+text;

				if(actualText.equalsIgnoreCase(text)) 
				{

					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
				}
			}	
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	public void verifyValueSelected(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a")||options.equals(null)) 
			{

			}
			else
			{

				{								
					Select select = new Select(element);
					element=select.getFirstSelectedOption();

					String actualText=element.getText();


					action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+options;
					actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+options;

					if(actualText.equals(options)) 
					{

						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
					}
					else 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
					}
				}
			}


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}", action, actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}



	public void verifyDisabledPropertyOfElement(WebDriver drivernew,WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Disabled Property of Element";
		String actionDescription = "Verify Disabled Property of Element";
		LocalDateTime startTime = LocalDateTime.now();
		FluentWait(drivernew,element);
		if(pageDetails.equals(null)) 
		{
			pageDetails.PageActionName="disability";
			pageDetails.PageActionDescription="";
		}
		try
		{
			Boolean displayed = true;
			displayed = element.isDisplayed();
			if (displayed)
			{
				String isDisabled = "";

				isDisabled = element.getAttribute("disabled");
				if(isDisabled==null) 
				{
					isDisabled="";
				}
				String isReadOnly = "";
				isReadOnly = element.getAttribute("readonly");
				if(isReadOnly==null) 
				{
					isReadOnly="";
				}
				String pointereventproperty="";
				pointereventproperty =element.getAttribute("style");
				boolean pointerevent=pointereventproperty.contains("pointer-events: none");
				if(pointerevent==true) 
				{
					action = "The field is Disabled and Readonly, which is as expected";
					actionDescription = "The field is Disabled and Readonly, which is as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);                    
				}

				else 
				{
					if (isDisabled.equals("true") && isReadOnly.equals("true"))
					{
						action = "The field is Disabled and Readonly, which is as expected";
						actionDescription = "The field is Disabled and Readonly, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);

					}
					else if (isReadOnly.equals("") && isDisabled.equals("true"))
					{
						action = "The field is Disabled, which is as expected";
						actionDescription = "The field is Disabled, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
					}
					else if (isReadOnly.equals("true")&&(isDisabled.equals("")||isDisabled.equals(null)))
					{
						action = "The field is Readonly, which is as expected";
						actionDescription = "The field is Readonly, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);

					}

					else if(isDisabled.equals("")||isDisabled.equals(null))
					{
						action = "The field is not Disabled and Readonly, which is not as expected";
						actionDescription = "The field is not Disabled and Readonly, which is not as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					}

					else 
					{
						action = "The field is not Disabled and Readonly, which is not as expected";
						actionDescription = "The field is not Disabled and Readonly, which is not as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					}
				}




			}
			else
			{
				action = "The Element is not found";
				actionDescription = "The field is not Disabled and Readonly, which is not as expected";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action, actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	public void verifypropertyofelement(WebDriver drivernew, WebElement element, String dataValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verify Property of Element-->"+element.getAttribute("Value");
		String actionDescription = "Verify Property of Element-->"+element.getAttribute("Value");
		LocalDateTime startTime = LocalDateTime.now();
		if(pageDetails.equals(null)) 
		{
			pageDetails.PageActionName="";
			pageDetails.PageActionDescription="";
		}
		try
		{
			Boolean displayed = true;
			displayed = element.isDisplayed();
			if (displayed)
			{
				String prop = "";
				String[] data = null;

				data = dataValue.split("&");
				prop=element.getAttribute(data[0]) ;

				if (prop.equals(data[1]))
				{
					action = "The " + data[0] + " Property of the element is as expected";
					actionDescription = "The " + data[0] + " Property of the element is as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				}
				else 
				{
					action = "The " + data[0] + " Property of the element is not as expected";
					actionDescription = "The " + data[0] + " Property of the element is not as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	public void verifyCheckBoxIsNotChecked(WebDriver drivernew,WebElement element,TestCaseParam testCaseParam, PageDetails pageDetails ) throws Exception
	{
		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();
		try
		{

			if (!element.isSelected())
			{

				action = "The element is Not checked";
				actionDescription = "The element is Not checked";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

			}
			else
			{
				action = "The element is  checked";
				actionDescription = "The element is  checked";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}
		}
		catch (Exception e)
		{

			logger.error("Failed ==>{} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}

	}

	public void dismissAlertMessage(WebDriver drivernew, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			drivernew.switchTo().alert().dismiss();
			action = "Successfully Dismissed Alert Message";
			actionDescription = "Successfully Dismissed Alert Message";

		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void enterTextByFunctionKeys(WebDriver drivernew,WebElement element,Keys key,String keyValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().FluentWait(drivernew, element);
			WebDriverWait wait = new WebDriverWait(drivernew, 1000);
			wait.until(ExpectedConditions.elementToBeClickable(element));				

			element.sendKeys(key+keyValue);

			LocalDateTime EndTime =  LocalDateTime.now();

			logger.info("Successfully Entered Text {} to {}",key,element);
		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void enterTextByFunctionKeys(WebDriver drivernew,WebElement element,Keys key, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().FluentWait(drivernew, element);
			WebDriverWait wait = new WebDriverWait(drivernew, 1000);
			wait.until(ExpectedConditions.elementToBeClickable(element));				

			element.sendKeys(key);

			LocalDateTime EndTime =  LocalDateTime.now();

			logger.info("Successfully Entered Text {} to {}",key ,element);
		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void zoomWebPage(WebDriver drivernew,String zoomValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();



		try
		{

			JavascriptExecutor executor = (JavascriptExecutor)drivernew;
			executor.executeScript("document.body.style.zoom = '"+zoomValue+"'");
			action = "Sucessfully Zoomed the page to -->"+zoomValue;
			actionDescription = "Sucessfully Zoomed the page to -->"+zoomValue;
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			action = "Failed to Zoom the page";
			actionDescription = "Failed to Zoom the page";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void mouseHover(WebDriver drivernew,WebElement element, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String actionnew="Mouse Hover";
		String actionDescription="Mouse Hover";
		LocalDateTime startTime = LocalDateTime.now();



		try
		{
			webkeywords.instance().FluentWait(drivernew, element);
			Actions action = new Actions(drivernew);

			action.moveToElement(element).build().perform();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			actionnew = "Failed do mouse hover";
			actionDescription = "Failed do mouse hover";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void verifyDropdownValues(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Dropdown Values";
		String actionDescription = "Verify Dropdown Values";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a")||options.equals(null)) 
			{

			}
			else
			{

				{		
					ArrayList<String> actualvalues=new ArrayList<>();
					ArrayList<String> expectedvalues=new ArrayList<>();
					Select select = new Select(element);
					int count=0;

					String[] exvalues=options.split(";");
					for(int i=0; i<exvalues.length;i++) 
					{
						expectedvalues.add(exvalues[i]);
					}

					int expectedcount=expectedvalues.size();
					List<WebElement> elements = select.getOptions();
					for (WebElement we : elements) {
						for (int i = 0; i < expectedvalues.size(); i++) 
						{
							if (we.getText().equals(expectedvalues.get(i))) 
							{
								actualvalues.add(we.getText());
								count++;
								break;
							}
						}

					}
					if(count==0) 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					}
					if(count==expectedcount) 
					{
						action = "Verify Dropdown values<=>Expected Values="+expectedvalues+"<=>Actual Values="+actualvalues;
						actionDescription = "Verify Dropdown values<=>Expected Values="+expectedvalues+"<=>Actual Values="+actualvalues;
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

					}

				}	
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	public void scrollUpPageToTheTop(WebDriver driver) 
	{

		String action = "Scroll Up Page To The Top";
		String actionDescription = "Scroll Up Page To The Top";



		try
		{

			((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",action,actionDescription);
		
		}



	}

	public void scrollIntoViewElement(WebDriver driver, WebElement element) throws Exception
	{

		webkeywords.instance().FluentWait(driver, element);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true);",element);

	}



	public void verifydropdownoptionnotavailable(WebDriver drivernew, WebElement element, String options, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verifying Dropdown Options -->"+element.getText()+" : does not contain :-"+options;
		String actionDescription = "Verifying Dropdown Options -->"+element.getText()+" : does not contain :-"+options;
		LocalDateTime startTime = LocalDateTime.now();
		Boolean valuefound = true;

		try
		{
			if ("N//A".equals(options) || options == null)
				{

			}
			else
			{

				webkeywords.instance().FluentWait(drivernew, element);
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(int i=0; i<allOptions.size(); i++) 
				{

					if(allOptions.get(i).getText().contains(options)) 
					{
						valuefound=false;
						action = "Verify Dropdown-->contains "+allOptions.get(i).getText()+"which is not as Expected";
						actionDescription = "Verify Dropdown-->contains "+allOptions.get(i).getText()+"which is not as Expected";

						break;
					}
				}
				if(valuefound) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), options);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}




	public void openNewTabwithURL(WebDriver drivernew, String url, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Open new tab with url";
		String actionDescription = "Open new tab with url";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			JavascriptExecutor javaScript = (JavascriptExecutor) drivernew;

			javaScript.executeScript("window.open(arguments[0])", url);
			webkeywords.instance().waitForPageToLoad(drivernew, 5000);
			Thread.sleep(5000);
			String currentWindow = drivernew.getWindowHandle();
			logger.info("Current Window: {} " , currentWindow);

			int windowsCount = drivernew.getWindowHandles().size();
			logger.info("Count of Windows: {}" , windowsCount);

			if (windowsCount > 1) 
			{
				logger.info("New Window is opened. Switching the control to the new window");
				ArrayList<String> tab = new ArrayList<>(drivernew.getWindowHandles());
				drivernew.switchTo().window(tab.get(1));
				webkeywords.instance().waitForPageToLoad(drivernew, 5000);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",action,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifyDropdownValueSelected(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			String actualText=element.getAttribute("title");


			action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+options;
			actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+options;

			if(actualText.equals(options)) 
			{

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}



		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}


public  void navigateToNextPage(WebDriver driver,WebElement element, TestCaseParam testCaseParam, PageDetails pageDetails ) throws Exception
{
	String action = "Capture Page Response Time";
	String actionDescription = "Capture Page Response Time";
	String sspHeader = "//div[@class='ssp-header']/..//h1";


	String getHeaderBeforeClick = "";
	String getHeaderAfterClick = "";
	
	try {
		WebDriverWait wait=new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
	    getHeaderBeforeClick = driver.findElement(By.xpath("//div[@class='ssp-menuItemDropDownHeader']/p")).getText();
	    if (getHeaderBeforeClick.equals("")){
	        getHeaderBeforeClick = driver.findElement(By.xpath(sspHeader)).getText();
	    }
	    
	} catch (Exception e) {
	  logger.info("Getting title of Current page");
	    getHeaderBeforeClick = driver.getTitle();
	}

	LocalDateTime startTime = LocalDateTime.now();
	if (!element.getText().equals("NULL") || !element.getText().equals("") || !element.getAttribute("title").isBlank()) {
	    try {
	        if (element.isDisplayed()) {
	            JavascriptExecutor executor = (JavascriptExecutor) driver;
	            executor.executeScript("arguments[0].click();", element);
	            WebDriverWait wait = new WebDriverWait(driver, 3);
	            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	        }
	    } catch (Exception e) {
	        logger.info("Unable to click on element");
	   }
		long startTimePerf = System.currentTimeMillis();
		
	    try {
	        WebDriverWait wait=new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
		    getHeaderAfterClick = driver.findElement(By.xpath("//div[@class='ssp-menuItemDropDownHeader']/p")).getText();
	        if (getHeaderAfterClick.equals("")) {
	            getHeaderAfterClick = driver.findElement(By.xpath(sspHeader)).getText();
	        }
	    } catch (Exception e) {
	       logger.info("Getting Next Page Title");
	        getHeaderAfterClick = driver.getTitle();
	    }
	    
	    try {
	    	WebDriverWait wait=new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
		   
			logger.info("Current Page title : {}",getHeaderBeforeClick);
			logger.info("Next Page title : {}",getHeaderAfterClick);
			

	        logger.info("Measuring performance metrics done");
	        
	} catch (NoSuchElementException e) {
	    logger.error("Failed ==> {} {}",action,actionDescription);
	    testStepDetails.logExceptionDetails(driver, testCaseParam, action, actionDescription, startTime, e);
	    throw e;
	}
	}


	

}
}