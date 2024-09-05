package initialize_scripts;

import common_utilities.Utilities.Util;
import testsettings.TestRunSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api_utilities.models.APIConfig;
import api_utilities.test_settings.APITestSettings;
import constants.PrjConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class InitializeTestSettings
{
	
    private static final Logger logger =LoggerFactory.getLogger(InitializeTestSettings.class.getName());


    Properties prop=new Properties();

	Util util = new Util();

	// Get the current working directory
	static String projectRoot = System.getProperty("user.dir");

	// Function to get the absolute path based on the project root and the relative path
	public static String getAbsolutePath(String relativePath) {
		return Paths.get(projectRoot, relativePath).toAbsolutePath().toString();
	}

	public void loadConfigData(String prjPath) {
		try {
			// Construct the file path in a cross-platform manner
			String configFilePath = Paths.get(prjPath, "config.properties").toString();
			File configFile = new File(configFilePath);

			if (!configFile.exists()) {
				throw new IOException("Config file not found: " + configFile.getAbsolutePath());
			}

			prop = new Properties();
			try (FileInputStream fis = new FileInputStream(configFile)) {
				prop.load(fis);
			}

			TestRunSettings.setHomePath(prjPath);
			TestRunSettings.setUrl(prop.getProperty("URL"));
			TestRunSettings.setProjectName(prop.getProperty("ProjectName"));
			TestRunSettings.setRelease(prop.getProperty("Release"));

			TestRunSettings.setEnvironment(prop.getProperty("Environment").toUpperCase().trim());
			TestRunSettings.setTestRunName(prop.getProperty("TestRunName"));
			TestRunSettings.setExecutedBy(prop.getProperty("ExecutedBy"));
			TestRunSettings.setBrowser(prop.getProperty("Browser"));

			if (prop.getProperty("CustomBrowserLocation").equalsIgnoreCase("Yes")) {
				TestRunSettings.setBrowserLocation(prop.getProperty("BrowserLocation"));
			} else {
				TestRunSettings.setBrowserLocation(TestRunSettings.getHomePath() + "/Drivers");
			}

			TestRunSettings.setMaximizedMode(prop.getProperty("MaximizedMode").equalsIgnoreCase("Yes"));
			TestRunSettings.setSnapshotForAllPass(prop.getProperty("SnapshotForAllPass").equalsIgnoreCase("Yes"));
			TestRunSettings.setFullPageScreenshot(prop.getProperty("FullPageScreenshot").equalsIgnoreCase("Yes"));

			TestRunSettings.setPageLoadTimeout(Integer.parseInt(prop.getProperty("PageLoadTimeout")));
			TestRunSettings.setElementTimeout(Integer.parseInt(prop.getProperty("ElementTimeout")));
			TestRunSettings.setElementTimeoutLongWait(Integer.parseInt(prop.getProperty("ElementTimeoutLongWait")));

			//run in headless mode
			TestRunSettings.setRunInHeadlessMode(prop.getProperty("runInHeadlessMode"));
			TestRunSettings.setCaptureUIPerformance(prop.getProperty("CaptureUIPerformance"));
			TestRunSettings.setUiPerfFileName(prop.getProperty("UIPerfFileName"));
			// Set the Artifacts Path and other paths dynamically
			TestRunSettings.setArtifactsPath(getAbsolutePath(prop.getProperty("ArtifactsPath")));
			TestRunSettings.setTestDataPath(getAbsolutePath(prop.getProperty("TestDataPath")));
			TestRunSettings.setTestDataMappingFileName(getAbsolutePath(prop.getProperty("TestDataMappingFileName")));
			TestRunSettings.setTestDataMappingSheetNameSd(prop.getProperty("TestDataMappingSheetName_SD"));
			TestRunSettings.setTestDataLocationCs(getAbsolutePath(prop.getProperty("TestDataLocation_CS")));
			TestRunSettings.setUploadDocumentPath(getAbsolutePath(prop.getProperty("UploadDocumentPath")));


			TestRunSettings.setConfigLocation(getAbsolutePath(prop.getProperty("ConfigLocation")));
			TestRunSettings.setApplicationCredentialsFileName(getAbsolutePath(prop.getProperty("ApplicationCredentialsFileName")));
			TestRunSettings.setApplicationCredentialsSheetName(prop.getProperty("ApplicationCredentialsSheetName"));

			TestRunSettings.setResultsPath(getResultsPath());

			TestRunSettings.setParallelExecution(prop.getProperty("ParallelExecution").equalsIgnoreCase("Yes"));
			if (TestRunSettings.isParallelExecution()) {
				TestRunSettings.setParallelNodesCount(Integer.parseInt(prop.getProperty("ParallelNodes")));
				TestRunSettings.setParallelExecutionConfig(prop.getProperty("ParallelExecutionConfig"));
				TestRunSettings.setParallelNodeSheetAssociation(prop.getProperty("ParallelNodeSheetAssociation"));
			}

			TestRunSettings.setLoadDriverOptions(loadDriverDetails());
			TestRunSettings.setChromeConfig(prop.getProperty("ChromeConfig"));
			TestRunSettings.setFireFoxConfig(prop.getProperty("FireFoxConfig"));
			TestRunSettings.setIeConfig(prop.getProperty("IEConfig"));
			TestRunSettings.setEdgeConfig(prop.getProperty("EdgeConfig"));
			TestRunSettings.setOperaConfig(prop.getProperty("OperaConfig"));
			TestRunSettings.setCloudConfig(prop.getProperty("CloudConfig"));
			TestRunSettings.setAndroidConfig(prop.getProperty("AndroidConfig"));
			TestRunSettings.setIOSConfig(prop.getProperty("IOSConfig"));

			TestRunSettings.setFireFoxLocation(prop.getProperty("FireFoxLocation"));
			TestRunSettings.setIeDriverLocation(TestRunSettings.getHomePath() + prop.getProperty("IEDriverLocation"));
			TestRunSettings.setOperaLocation(TestRunSettings.getHomePath() + prop.getProperty("IEDriverLocation"));

			TestRunSettings.setUploadDocumentPath(TestRunSettings.getArtifactsPath() + prop.getProperty("UploadDocumentLocation"));

			TestRunSettings.setInterfaceSheetDetails(prop.getProperty("InterfaceSheetDetails"));
			TestRunSettings.setExcelSheetExtension(prop.getProperty("ExcelSheetExtension"));
			TestRunSettings.setXmlExtension(prop.getProperty("XMLExtension"));
			TestRunSettings.setJsonExtension(prop.getProperty("JSONExtension"));
			TestRunSettings.setCommonMockSheetName(prop.getProperty("CommonMockSheetName"));
			TestRunSettings.setUseCommonMockSheet(prop.getProperty("UseCommonMockSheet"));
			TestRunSettings.setMockTemplateLocation(TestRunSettings.getArtifactsPath() + prop.getProperty("MockTemplateLocation"));

			TestRunSettings.setHeaderRepositorySheetName(prop.getProperty("HeaderRepositorySheetName"));
			TestRunSettings.setDefaultServiceTimeout(Integer.parseInt(prop.getProperty("DefaultServiceTimeout")));

			TestRunSettings.setCaptureTimeTravelDate(prop.getProperty("CaptureTimeTravelDate"));
			TestRunSettings.setUbtt(prop.getProperty("UBTT"));
			TestRunSettings.setAccountNumber(prop.getProperty("AccountNumber"));
			TestRunSettings.setAdaToolPath(TestRunSettings.getArtifactsPath() + prop.getProperty("AdatoolPath"));

		} catch (Exception e) {
			logger.info("Failed to Initialize the Test Run Settings");
		}
	}

		public void loadAPIConfig(String prjPath)
	{
		Util newutil = new Util();
		// Initialize API Settings
		Properties newProp = newutil.loadProperties(prjPath + PrjConstants.APICONFIGFILE);
		 
		 APITestSettings.setApiTestSettings(new APIConfig());
		
		APITestSettings.getApiTestSettings().setApiTestSuiteDirectory(TestRunSettings.getArtifactsPath() +newProp.getProperty("APITestSuiteDirectory"));
		APITestSettings.getApiTestSettings().setApiTestSuiteSheetName(newProp.getProperty("APITestSuiteSheetName"));
		APITestSettings.getApiTestSettings().setApiTestCaseDirectory(TestRunSettings.getArtifactsPath() +newProp.getProperty("APITestCaseDirectory"));
		APITestSettings.getApiTestSettings().setApiDirectory(TestRunSettings.getArtifactsPath() +newProp.getProperty("APIDirectory"));
		APITestSettings.getApiTestSettings().setApiTestSuiteFileName(APITestSettings.getApiTestSettings().getApiTestSuiteDirectory()+ newProp.getProperty("APITestSuiteFileName"));
		APITestSettings.getApiTestSettings().setApiTestCaseSheetName(newProp.getProperty("APITestCaseSheetName"));
		
		APITestSettings.getApiTestSettings().setEnvironment(TestRunSettings.getEnvironment());
		APITestSettings.getApiTestSettings().setTestDataPath(TestRunSettings.getTestDataPath());
		APITestSettings.getApiTestSettings().setExcelFileName(TestRunSettings.getArtifactsPath() + newProp.getProperty("APIFileName"));
		APITestSettings.getApiTestSettings().setInterfaceTestCaseSheet(newProp.getProperty("InterfaceTestCaseSheet"));
		APITestSettings.getApiTestSettings().setUrlRepositorySheet(TestRunSettings.getArtifactsPath() + newProp.getProperty("URLRepositorySheet"));
		APITestSettings.getApiTestSettings().setMockRepositorySheet(TestRunSettings.getArtifactsPath() + newProp.getProperty("MockRepositorySheet"));
		APITestSettings.getApiTestSettings().setHeaderRepository(TestRunSettings.getArtifactsPath() + newProp.getProperty("HeaderRepository"));
		APITestSettings.getApiTestSettings().setResponseSheetPath(TestRunSettings.getArtifactsPath() + newProp.getProperty("ResponseSheetPath"));

		APITestSettings.getApiTestSettings().setCertificateLocation(TestRunSettings.getArtifactsPath() + newProp.getProperty("CertificateLocation"));
		APITestSettings.getApiTestSettings().setRequestLocation(TestRunSettings.getArtifactsPath() + newProp.getProperty("RequestLocation"));
		APITestSettings.getApiTestSettings().setResponseLocation(TestRunSettings.getArtifactsPath() + newProp.getProperty("ResponseLocation"));
		APITestSettings.getApiTestSettings().setTestDataLocationSD(TestRunSettings.getArtifactsPath() + newProp.getProperty("TestDataLocation_SD"));
		APITestSettings.getApiTestSettings().setTestDataLocationXMLValidation(TestRunSettings.getArtifactsPath() + newProp.getProperty("TestDataLocation_XMLValidation"));
		APITestSettings.getApiTestSettings().setConfigLocationXMLValidation(TestRunSettings.getArtifactsPath() + newProp.getProperty("ConfigLocation_XMLValidation"));
		
		APITestSettings.getApiTestSettings().setInterfaceSheetDetails(newProp.getProperty("InterfaceSheetDetails"));

		APITestSettings.getApiTestSettings().setExcelSheetExtension(newProp.getProperty("ExcelSheetExtension"));
		APITestSettings.getApiTestSettings().setXmlExtension(newProp.getProperty("XMLExtension"));
		APITestSettings.getApiTestSettings().setJsonExtension(newProp.getProperty("JSONExtension"));
		APITestSettings.getApiTestSettings().setCommonMockSheetName(newProp.getProperty("CommonMockSheetName"));
		APITestSettings.getApiTestSettings().setUseCommonMockSheet(newProp.getProperty("UseCommonMockSheet"));
		APITestSettings.getApiTestSettings().setMockTemplateLocation(TestRunSettings.getArtifactsPath() + newProp.getProperty("MockTemplateLocation"));

		APITestSettings.getApiTestSettings().setHeaderRepositorySheetName(newProp.getProperty("HeaderRepositorySheetName"));
		APITestSettings.getApiTestSettings().setMockSheetName(getMockSheetName(APITestSettings.getApiTestSettings().getUseCommonMockSheet(), TestRunSettings.getCommonMockSheetName(), TestRunSettings.getEnvironment()));

		APITestSettings.getApiTestSettings().setDefaultServiceTimeout(Integer.parseInt(newProp.getProperty("DefaultServiceTimeout")));

		APITestSettings.getApiTestSettings().setResponseValidationFilePath(TestRunSettings.getArtifactsPath() + newProp.getProperty("ResponseValidationFileName"));
		APITestSettings.getApiTestSettings().setResponseValidationSheetName(newProp.getProperty("ResponseSheetName"));
		
		TestRunSettings.setApiConfigFileName(newProp.getProperty("APIConfig"));


		APITestSettings.getApiTestSettings().setStoreResponseDataFilePath(TestRunSettings.getArtifactsPath() + newProp.getProperty("StoreResponseDataFilePath"));
		APITestSettings.getApiTestSettings().setStoreResponseDataSheetName(newProp.getProperty("StoreResponseDataSheetName"));

		
		if(newProp.getProperty("UseCommonTestDataSheetName").equalsIgnoreCase("Yes"))
		{
		APITestSettings.getApiTestSettings().setUseCommonTestDataSheet(true);
		}

		APITestSettings.getApiTestSettings().setCommonTestDataSheetName(newProp.getProperty("CommonTestDataSheetName"));
		TestRunSettings.setRequestFolderPath(newProp.getProperty("RequestFolderName"));
		TestRunSettings.setResponseFolderPath(newProp.getProperty("ResponseFolderName"));

	}
	

	String getMockSheetName(String useCommonMockSheet, String commonMockSheetName, String environment)
	{

		if ("YES".equals(useCommonMockSheet.toUpperCase().trim()))
		{
			return commonMockSheetName;
		}
		else
		{
			return environment;
		}
	}

	
	public boolean loadDriverDetails()
	{
		try
		{
			return "YES".equalsIgnoreCase(prop.getProperty("LoadDriverOptions").trim());

		}
		catch (Exception e)
		{
			logger.info("Failed to Initialize the Driver Load Details");
		}
		return false;
	}

	public String getResultsPath(){
	  
	        if (prop.getProperty("SetCustomResultLocation").toUpperCase().trim().equals("YES")) {
	            File dir = new File(prop.getProperty("CustomResultLocation") + File.separator + Util.getCurrentDate() + File.separator + "Run_" + Util.getCurrentTime());

	            if (!dir.exists()) dir.mkdirs();
	            return prop.getProperty("CustomResultLocation") + "/" + Util.getCurrentDate() + "/Run_" + Util.getCurrentTime();
	        } else {
	            String resultFolder = TestRunSettings.getHomePath() + "/" + prop.getProperty("DefaultResultLocation") + "/" + Util.getCurrentDate() + "/Run_" + Util.getCurrentTime();
	            File dir = new File(resultFolder);
	            if (!dir.exists()) dir.mkdirs();

	            return resultFolder;
	        }
	    }
	}