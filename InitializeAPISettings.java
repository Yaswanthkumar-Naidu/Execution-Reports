package api_utilities.test_settings;

import java.io.IOException;
import java.util.Properties;

import api_utilities.api_common.APIUtil;
import api_utilities.models.APIConfig;


public class InitializeAPISettings {

	public APIConfig initializeInterfaceSettings(String prjPath,String artifactsPath,String configLocation,String env)
	{
	    Properties prop=new Properties();
	    APIUtil util = new APIUtil();

	    APIConfig apiConfigSettings = new APIConfig();
		
		 try {
			prop=util.loadProperties(configLocation);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
			APITestSettings.setHomePath(prjPath);
		

			
	    apiConfigSettings.setEnvironment(env);

	    APITestSettings.setArtifactsPath(artifactsPath);
		apiConfigSettings.setTestDataPath(APITestSettings.getArtifactsPath() + prop.getProperty("TestDataMappingLocation"));
		apiConfigSettings.setExcelFileName(APITestSettings.getArtifactsPath() + prop.getProperty("ExcelFileName"));
		apiConfigSettings.setInterfaceTestCaseSheet(prop.getProperty("InterfaceTestCaseSheet"));
		apiConfigSettings.setUrlRepositorySheet(APITestSettings.getArtifactsPath() + prop.getProperty("URLRepositorySheet"));
		apiConfigSettings.setMockRepositorySheet(APITestSettings.getArtifactsPath() + prop.getProperty("MockRepositorySheet"));
		apiConfigSettings.setHeaderRepository(APITestSettings.getArtifactsPath() + prop.getProperty("HeaderRepository"));
		apiConfigSettings.setResponseSheetPath(APITestSettings.getArtifactsPath() + prop.getProperty("ResponseSheetPath"));

		apiConfigSettings.setCertificateLocation(APITestSettings.getArtifactsPath() + prop.getProperty("CertificateLocation"));
		apiConfigSettings.setRequestLocation(APITestSettings.getArtifactsPath() + prop.getProperty("RequestLocation"));
		apiConfigSettings.setResponseLocation(APITestSettings.getArtifactsPath() + prop.getProperty("ResponseLocation"));
		apiConfigSettings.setInterfaceSheetDetails(prop.getProperty("InterfaceSheetDetails"));

		apiConfigSettings.setExcelSheetExtension(prop.getProperty("ExcelSheetExtension"));
		apiConfigSettings.setXmlExtension(prop.getProperty("XMLExtension"));
		apiConfigSettings.setJsonExtension(prop.getProperty("JSONExtension"));
		apiConfigSettings.setCommonMockSheetName(prop.getProperty("CommonMockSheetName"));
		apiConfigSettings.setUseCommonMockSheet(prop.getProperty("UseCommonMockSheet"));
		apiConfigSettings.setDomainName(prop.getProperty("DomainName"));
		apiConfigSettings.setMockTemplateLocation(APITestSettings.getArtifactsPath() + prop.getProperty("MockTemplateLocation"));

		
		apiConfigSettings.setHeaderRepositorySheetName(prop.getProperty("HeaderRepositorySheetName"));
		apiConfigSettings.setMockSheetName(getMockSheetName(apiConfigSettings.getUseCommonMockSheet(), apiConfigSettings.getCommonMockSheetName(), apiConfigSettings.getEnvironment()));
		apiConfigSettings.setAddReportToDB(addReportToDB(prop.getProperty("AddReportToDataBase")));
		apiConfigSettings.setDefaultServiceTimeout(Integer.parseInt(prop.getProperty("DefaultServiceTimeout")));

		//Response Validation
		apiConfigSettings.setResponseValidationFilePath(APITestSettings.getArtifactsPath() + prop.getProperty("ResponseValidationFileName"));
		apiConfigSettings.setResponseValidationSheetName(prop.getProperty("ResponseValidationSheetName"));
		
		//TrustStore Location and Password
		APITestSettings.setTrustStoreLocation(prop.getProperty("TrustStoreLocation"));
		APITestSettings.setTrustStorePassword(prop.getProperty("TrustStorePassword"));

		
		APITestSettings.setApiTestSettings(apiConfigSettings);
		return apiConfigSettings;


	}

	boolean addReportToDB(String addReportToDB)
	{

		return (addReportToDB.toUpperCase().trim().equals("YES"));
	}

	String getMockSheetName(String useCommonMockSheet, String commonMockSheetName, String environment)
	{

		if (useCommonMockSheet.toUpperCase().trim().equals("YES"))
		{
			return commonMockSheetName;
		}
		else
		{
			return environment;
		}
	}

}
