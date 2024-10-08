package pdfvalidation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.PageDetails;
import report_utilities.common.ReportCommon;
import testsettings.TestRunSettings;
import uitests.testng.common.TestNGCommon;

public class PDFValidation extends TestNGCommon
{
	private static final String PDF_SOURCE_TEXT_FILE_PATH = "\\PDF\\SourceTextFile\\";
	
	private static final String PDF_SOURCE_PDF_FILE_PATH = "\\PDF\\SourcePDFfile\\";

	private WebDriver driver;
	String browser = "";

	String applicationNumber = "0";
	String caseNumber = "0";
	String testCaseStatus = "0";
	TestCaseParam testCaseParam = new TestCaseParam();
	ReportCommon testStepDetails = new ReportCommon();
	String moduleName ="PDF validation";
	String screenName ="PDF validation";
	private static final Logger logger = LoggerFactory.getLogger(PDFValidation.class.getName());
	ReportCommon exceptionDetails = new ReportCommon();

	public PDFValidation()
	{

	}

	public PDFValidation(WebDriver wDriver,TestCaseParam testCaseParam)
	{
		driver = wDriver;
		PageFactory.initElements(driver, this);
		ReportCommon testStepLogDetails = new ReportCommon();
		testStepLogDetails.logModuleAndScreenDetails(testCaseParam, moduleName, screenName);

	}

	@BeforeClass
	public void setUpTDARDC()
	{

		driver = TestRunSettings.driver;
		browser = TestRunSettings.browser;
		testCaseParam.TestCaseName = "ValidatePDF";
		testCaseParam.ModuleName = moduleName;
		testCaseParam.Browser = TestRunSettings.browser;
		testCaseParam.CaseNumber= caseNumber;
		testCaseParam.ApplicationNumber= applicationNumber;
		testCaseParam.TestCaseDescription = testCaseParam.TestCaseName;
		initializeTestCase(testCaseParam);
		driver = initializeDriver();
	}
	@Test
	public void run() throws Exception
	{

		logger.info("Execution Started: {}",testCaseParam.TestCaseName);

		PageDetails pageDetails=new PageDetails();
		pageDetails.PageActionName = "PDF Validation";
		pageDetails.PageActionDescription = "PDF Validation";

		logger.info("Started PDF Validation ");

		String folder="CA_CARES";

		String replacedTextFile = TestRunSettings.artifactsPath+"\\PDF\\ReplacedTextFile\\"+folder;


		//non interactive pdf
		String sourcePdfName="SOC 369 - Agency-Relative Guardianship Disclosure";
		String targetPdfName="SOC 369 - Agency-Relative Guardianship Disclosure_T";

		//source file
		String pdfSourceFile = TestRunSettings.artifactsPath + PDF_SOURCE_PDF_FILE_PATH + folder + "\\" + sourcePdfName;
		String sourceTextFile = TestRunSettings.artifactsPath + PDF_SOURCE_TEXT_FILE_PATH + folder + "\\SourceTextFile";
		String sourceTextFileLoc = TestRunSettings.artifactsPath + PDF_SOURCE_TEXT_FILE_PATH + folder;


		//replacement data file path
		String replacementDataFilePath = TestRunSettings.artifactsPath + "\\PDF\\Testdata\\" + sourcePdfName + ".txt";

		caseNumber ="113216371";

		WritePDFtoTxtUtility writePDFtoTxtUtility = new WritePDFtoTxtUtility();
		writePDFtoTxtUtility.writePdfToTextFile(testCaseParam,pageDetails,pdfSourceFile + ".pdf", folder,sourceTextFile,sourceTextFileLoc);

		// Paths for text files
		String sourceTextFileLocation = TestRunSettings.artifactsPath + PDF_SOURCE_TEXT_FILE_PATH + folder;

		ReplacePlaceholders rp = new ReplacePlaceholders();

		// Add placeholders to each text file in the source text file location and save them to the intermediate text file location
		// this is just a temporary method, created for demo, will be removed once we have templates in place
		rp.addPlaceholdersToTextFiles(sourceTextFileLocation, sourceTextFileLocation);

		// Replace the place holders in the text files
		// this will replace the dynamic place holder of source text file, from test case data
		rp.replaceData(testCaseParam,pageDetails,sourceTextFileLocation, replacedTextFile,replacementDataFilePath);

		String resultfile = TestRunSettings.artifactsPath+"\\PDF\\Results"+"\\"+folder;
		String pdfTargetFile = TestRunSettings.artifactsPath + "\\PDF\\TargetPDFfile\\" + folder + "\\" + targetPdfName;
		String targetTextFile = TestRunSettings.artifactsPath + "\\PDF\\TargetTextFile\\" + folder + "\\TargetTextFile";

		ReadPFDBox readPFDBox = new ReadPFDBox(driver, testCaseParam);
		readPFDBox.validatepdfvspdfCompareLine(testCaseParam,pdfSourceFile + ".pdf",pdfTargetFile+ ".pdf",resultfile,replacedTextFile,targetTextFile,0);

	}

}
