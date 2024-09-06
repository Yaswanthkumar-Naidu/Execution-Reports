package api_utilities.test_settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api_utilities.models.APIConfig;
import api_utilities.models.APIReportModel;
import api_utilities.models.APITestCaseModel;
import api_utilities.models.APITestStepModel;

public class APITestSettings {


	public static final String DBVALIDATION = "No";
	private static String artifactsPath = null;
	private static String environment="";
	private static APIConfig apiTestSetting= null;
	private static String homePath;
	private static String trustStoreLocation;
	private static String trustStorePassword;
	
	private static Map<String,ArrayList<APIReportModel>> reportModel= new HashMap<>();
	private static  Map<String, ArrayList<APITestStepModel>> apiTcExecData= new HashMap<>();
	private static  Map<String, APITestCaseModel> apiTcInfo= new HashMap<>();

	private static Map<String,HashMap<String, String>> dictTestData= new HashMap<>();
	private static String resultsPath;
	private static String resultsRequestLocation;
	private static String resultsResponseLocation;
	private static String appUrl;
	private static String userName;
	private static String password;
	private static String browser;
	private static String excelFilepath;
	private static boolean isKeyStoreConfigurationRequired=false;
	
	private  APITestSettings() {}
	
	
	public static Map<String, ArrayList<APITestStepModel>> getApiTcExecData() {
		return apiTcExecData;
	}
	public static void setApiTcExecData(Map<String, ArrayList<APITestStepModel>> apiTcExecData) {
		APITestSettings.apiTcExecData = apiTcExecData;
	}
	public static Map<String,ArrayList<APIReportModel>> getReportModel() {
		return reportModel;
	}
	public static void setReportModel(Map<String,ArrayList<APIReportModel>> reportModel) {
		APITestSettings.reportModel = reportModel;
	}
	public static Map<String, APITestCaseModel> getApiTcInfo() {
		return apiTcInfo;
	}
	public static void setApiTcInfo(Map<String, APITestCaseModel> apiTcInfo) {
		APITestSettings.apiTcInfo = apiTcInfo;
	}
	public static APIConfig getApiTestSettings() {
		return apiTestSetting;
	}
	public static void setApiTestSettings(APIConfig apiTestSettings) {
		APITestSettings.apiTestSetting = apiTestSettings;
	}
	public static String getAppUrl() {
		return appUrl;
	}
	public static void setAppUrl(String appUrl) {
		APITestSettings.appUrl = appUrl;
	}
	public static String getArtifactsPath() {
		return artifactsPath;
	}
	public static void setArtifactsPath(String artifactsPath) {
		APITestSettings.artifactsPath = artifactsPath;
	}
	public static String getBrowser() {
		return browser;
	}
	public static void setBrowser(String browser) {
		APITestSettings.browser = browser;
	}
	public static Map<String,HashMap<String, String>> getDictTestData() {
		return dictTestData;
	}
	public static void setDictTestData(Map<String,HashMap<String, String>> dictTestData) {
		APITestSettings.dictTestData = dictTestData;
	}
	public static String getEnvironment() {
		return environment;
	}
	public static void setEnvironment(String environment) {
		APITestSettings.environment = environment;
	}
	public static String getExcelFilepath() {
		return excelFilepath;
	}
	public static void setExcelFilepath(String excelFilepath) {
		APITestSettings.excelFilepath = excelFilepath;
	}
	public static String getHomePath() {
		return homePath;
	}
	public static void setHomePath(String homePath) {
		APITestSettings.homePath = homePath;
	}
	public static boolean isKeyStoreConfigurationRequired() {
		return isKeyStoreConfigurationRequired;
	}
	public static void setKeyStoreConfigurationRequired(boolean isKeyStoreConfigurationRequired) {
		APITestSettings.isKeyStoreConfigurationRequired = isKeyStoreConfigurationRequired;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		APITestSettings.password = password;
	}
	public static String getResultsPath() {
		return resultsPath;
	}
	public static void setResultsPath(String resultsPath) {
		APITestSettings.resultsPath = resultsPath;
	}
	public static String getResultsRequestLocation() {
		return resultsRequestLocation;
	}
	public static void setResultsRequestLocation(String resultsRequestLocation) {
		APITestSettings.resultsRequestLocation = resultsRequestLocation;
	}
	public static String getResultsResponseLocation() {
		return resultsResponseLocation;
	}
	public static void setResultsResponseLocation(String resultsResponseLocation) {
		APITestSettings.resultsResponseLocation = resultsResponseLocation;
	}
	public static String getTrustStoreLocation() {
		return trustStoreLocation;
	}
	public static void setTrustStoreLocation(String trustStoreLocation) {
		APITestSettings.trustStoreLocation = trustStoreLocation;
	}
	public static String getTrustStorePassword() {
		return trustStorePassword;
	}
	public static void setTrustStorePassword(String trustStorePassword) {
		APITestSettings.trustStorePassword = trustStorePassword;
	}
	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		APITestSettings.userName = userName;
	}


	
}
