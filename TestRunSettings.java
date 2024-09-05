package testsettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class TestRunSettings
{
	private TestRunSettings() {
		
	}
	 
    private static String runInHeadlessMode = "";
    
    public static String getRunInHeadlessMode() {
		return runInHeadlessMode;
	}
	public static void setRunInHeadlessMode(String runInHeadlessMode) {
		TestRunSettings.runInHeadlessMode = runInHeadlessMode;
	}

	private static String artifactsPath = "";
    public static String getArtifactsPath() {
		return artifactsPath;
	}
	public static void setArtifactsPath(String artifactsPath) {
		TestRunSettings.artifactsPath = artifactsPath;
	}

	private static String testDataPath = "";
    
    public static String getTestDataPath() {
		return testDataPath;
	}
	public static void setTestDataPath(String testDataPath) {
		TestRunSettings.testDataPath = testDataPath;
	}

	private static String testDataMappingFileName = "";
    
    public static String getTestDataMappingFileName() {
		return testDataMappingFileName;
	}
	public static void setTestDataMappingFileName(String testDataMappingFileName) {
		TestRunSettings.testDataMappingFileName = testDataMappingFileName;
	}

	private static String applicationCredentialsFileName = "";
    
    
    public static String getApplicationCredentialsFileName() {
		return applicationCredentialsFileName;
	}
	public static void setApplicationCredentialsFileName(String applicationCredentialsFileName) {
		TestRunSettings.applicationCredentialsFileName = applicationCredentialsFileName;
	}

	private static String applicationCredentialsSheetName = "";
    public static String getApplicationCredentialsSheetName() {
		return applicationCredentialsSheetName;
	}
	public static void setApplicationCredentialsSheetName(String applicationCredentialsSheetName) {
		TestRunSettings.applicationCredentialsSheetName = applicationCredentialsSheetName;
	}

	private static String homePath = "";
    public static String getHomePath() {
		return homePath;
	}
	public static void setHomePath(String homePath) {
		TestRunSettings.homePath = homePath;
	}

	private static Map<HashMap<String,String>,ArrayList<String>> uiperfdata=new LinkedHashMap<>();
    static final List<Integer> uiPerfScreenCounter=new LinkedList<>();


    private static List<String> pageLoadtime=new LinkedList<>();
    private static List<String> domLoadtime=new LinkedList<>();
    //TestInitialization
    static final boolean ISTESTINITIALIZED = false;
    
    private static boolean isParallelExecution = false;
    public static boolean isParallelExecution() {
		return isParallelExecution;
	}
	public static void setParallelExecution(boolean isParallelExecution) {
		TestRunSettings.isParallelExecution = isParallelExecution;
	}

	private static Integer parallelNodesCount = 1;
    public static Integer getParallelNodesCount() {
		return parallelNodesCount;
	}
	public static void setParallelNodesCount(Integer parallelNodesCount) {
		TestRunSettings.parallelNodesCount = parallelNodesCount;
	}

	//Environment Settings
    private static String url = "";
    public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		TestRunSettings.url = url;
	}

	private static String projectName = "";
    public static String getProjectName() {
		return projectName;
	}
	public static void setProjectName(String projectName) {
		TestRunSettings.projectName = projectName;
	}

	private static String release = "";
    public static String getRelease() {
		return release;
	}
	public static void setRelease(String release) {
		TestRunSettings.release = release;
	}

	private static String environment = "";
    public static String getEnvironment() {
		return environment;
	}
	public static void setEnvironment(String environment) {
		TestRunSettings.environment = environment;
	}

	private static String testRunName = "";
    public static String getTestRunName() {
		return testRunName;
	}
	public static void setTestRunName(String testRunName) {
		TestRunSettings.testRunName = testRunName;
	}

	private static String executedBy = "";
    public static String getExecutedBy() {
		return executedBy;
	}
	public static void setExecutedBy(String executedBy) {
		TestRunSettings.executedBy = executedBy;
	}

	private static String browser = "";
    public static String getBrowser() {
		return browser;
	}
	public static void setBrowser(String browser) {
		TestRunSettings.browser = browser;
	}

	private static String browserLocation = "";
    public static String getBrowserLocation() {
		return browserLocation;
	}
	public static void setBrowserLocation(String browserLocation) {
		TestRunSettings.browserLocation = browserLocation;
	}

	private static boolean maximizedMode = false;
    public static boolean isMaximizedMode() {
		return maximizedMode;
	}
	public static void setMaximizedMode(boolean maximizedMode) {
		TestRunSettings.maximizedMode = maximizedMode;
	}

	private static boolean isSnapshotForAllPass = false;
    public static boolean isSnapshotForAllPass() {
		return isSnapshotForAllPass;
	}
	public static void setSnapshotForAllPass(boolean isSnapshotForAllPass) {
		TestRunSettings.isSnapshotForAllPass = isSnapshotForAllPass;
	}

	private static boolean isFullPageScreenshot = false;
    public static boolean isFullPageScreenshot() {
		return isFullPageScreenshot;
	}
	public static void setFullPageScreenshot(boolean isFullPageScreenshot) {
		TestRunSettings.isFullPageScreenshot = isFullPageScreenshot;
	}

	private static Integer pageLoadTimeout = 60;
    public static Integer getPageLoadTimeout() {
		return pageLoadTimeout;
	}
	public static void setPageLoadTimeout(Integer pageLoadTimeout) {
		TestRunSettings.pageLoadTimeout = pageLoadTimeout;
	}

	private static Integer elementTimeout = 60;
    public static Integer getElementTimeout() {
		return elementTimeout;
	}
	public static void setElementTimeout(Integer elementTimeout) {
		TestRunSettings.elementTimeout = elementTimeout;
	}

	private static Integer elementTimeoutLongWait = 120;
    public static Integer getElementTimeoutLongWait() {
		return elementTimeoutLongWait;
	}
	public static void setElementTimeoutLongWait(Integer elementTimeoutLongWait) {
		TestRunSettings.elementTimeoutLongWait = elementTimeoutLongWait;
	}

	static final String APPDATA = "";
	static final boolean CLEARCATCH = false;
    private static String captureTimeTravelDate="";
   
    public static String getCaptureTimeTravelDate() {
		return captureTimeTravelDate;
	}
	public static void setCaptureTimeTravelDate(String captureTimeTravelDate) {
		TestRunSettings.captureTimeTravelDate = captureTimeTravelDate;
	}

	private static String ubtt = "";
    public static String getUbtt() {
		return ubtt;
	}
	public static void setUbtt(String ubtt) {
		TestRunSettings.ubtt = ubtt;
	}

	private static String accountNumber="";
    public static String getAccountNumber() {
		return accountNumber;
	}
	public static void setAccountNumber(String accountNumber) {
		TestRunSettings.accountNumber = accountNumber;
	}

	private static String captureUIPerformance="";
    
    public static String getCaptureUIPerformance() {
		return captureUIPerformance;
	}
	public static void setCaptureUIPerformance(String captureUIPerformance) {
		TestRunSettings.captureUIPerformance = captureUIPerformance;
	}

	//Artifacts Location Setup
	static final String TESTDATAMAPPINGSHEETNAMECS = "";
    private static String testDataLocationCs = "/CS";

    //pdf
	
	public static String getTestDataLocationCs() {
		return testDataLocationCs;
	}
	public static void setTestDataLocationCs(String testDataLocationCs) {
		TestRunSettings.testDataLocationCs = testDataLocationCs;
	}

	static final String TARGETPDFFILE = "TargetPDFfile";
	static final String PDFEXTENSION = "PdfExtension";
	static final String EDITEDPDFFILE = "EditedPDFFile";
	static final String SOURCEPDFFILE = "SourcePDFfile";
	static final String RESULTFILE = "Resultfile";
	static final String TEXTFILE = "TextFile";
	static final String PSFILEOUTPATH = "PSfileoutpath";
	static final String SOURCETEXTFILE = "SourceTextFile";
	static final String TARGETTEXTFILE = "TargetTextFile";
	static final String REPLACEDTEXTFILE = "ReplacedTextFile";
	static final String SOURCETEXTFILEMUSCLE = "SourceTextFile_Muscle";
	static final String SOURCETEXTFILEHNF = "SourceTextFile_HnF";
	static final String SOURCETEXTFILENEURO = "SourceTextFile_Neuro";
	static final String SOURCEPDFFILEMUSCLE = "SourcePDFfile_Muscle";
	static final String SOURCEPDFFILEHNF = "SourcePDFfile_HnF";
	static final String SOURCEPDFFILENEURO = "SourcePDFfile_Neuro";
	static final boolean REPLACEEXISTINGSHANSHOTFORALLPASS=false;
	
	//
   private static String uploadDocumentPath = "DocUpload";
   public static String getUploadDocumentPath() {
		return uploadDocumentPath;
	}
	public static void setUploadDocumentPath(String uploadDocumentPath) {
		TestRunSettings.uploadDocumentPath = uploadDocumentPath;
	}

	//DB Validation
  	static final String DBDETAILEDVALIDATIONFILENAME="";
  	static final String DNDETAILEDVALIDATIONLOCATION="";
  	static final String CRDBDETAILEDVALIDATIONFILENAME="";

    //Result  Location Setup
    static final String RESULTSFOLDERPATH = "";
    static final String SETCUSTOMRESULTLOCATION="";
    static final String CUSTOMRESULTLOCATION="";

    //ParallelConfig
    static final String PARALLELCONFIGPATH = "";

	private static String parallelExecutionConfig = "";
    public static String getParallelExecutionConfig() {
		return parallelExecutionConfig;
	}
	public static void setParallelExecutionConfig(String parallelExecutionConfig) {
		TestRunSettings.parallelExecutionConfig = parallelExecutionConfig;
	}

	private static String parallelNodeSheetAssociation = "";
    public static String getParallelNodeSheetAssociation() {
		return parallelNodeSheetAssociation;
	}
	public static void setParallelNodeSheetAssociation(String parallelNodeSheetAssociation) {
		TestRunSettings.parallelNodeSheetAssociation = parallelNodeSheetAssociation;
	}

	//Driver Config
    private static boolean loadDriverOptions = false;
    public static boolean isLoadDriverOptions() {
		return loadDriverOptions;
	}
	public static void setLoadDriverOptions(boolean loadDriverOptions) {
		TestRunSettings.loadDriverOptions = loadDriverOptions;
	}

	private static String chromeConfig = "";
    public static String getChromeConfig() {
		return chromeConfig;
	}
	public static void setChromeConfig(String chromeConfig) {
		TestRunSettings.chromeConfig = chromeConfig;
	}

	private static String fireFoxConfig = "";
    public static String getFireFoxConfig() {
		return fireFoxConfig;
	}
	public static void setFireFoxConfig(String fireFoxConfig) {
		TestRunSettings.fireFoxConfig = fireFoxConfig;
	}

	private static String ieConfig = "";
    public static String getIeConfig() {
		return ieConfig;
	}
	public static void setIeConfig(String ieConfig) {
		TestRunSettings.ieConfig = ieConfig;
	}

	private static String edgeConfig = "";
    public static String getEdgeConfig() {
		return edgeConfig;
	}
	public static void setEdgeConfig(String edgeConfig) {
		TestRunSettings.edgeConfig = edgeConfig;
	}

	private static String operaConfig = "";
    public static String getOperaConfig() {
		return operaConfig;
	}
	public static void setOperaConfig(String operaConfig) {
		TestRunSettings.operaConfig = operaConfig;
	}

	private static String cloudConfig = "";
    public static String getCloudConfig() {
		return cloudConfig;
	}
	public static void setCloudConfig(String cloudConfig) {
		TestRunSettings.cloudConfig = cloudConfig;
	}

	private static String androidConfig = "";
    public static String getAndroidConfig() {
		return androidConfig;
	}
	public static void setAndroidConfig(String androidConfig) {
		TestRunSettings.androidConfig = androidConfig;
	}

	private static String iosConfig = "";
    public static String getIOSConfig() {
		return iosConfig;
	}
	public static void setIOSConfig(String iOSConfig) {
		iosConfig = iOSConfig;
	}

	//Location for Browser in OS
    private static String fireFoxLocation = "";
    
    public static String getFireFoxLocation() {
		return fireFoxLocation;
	}
	public static void setFireFoxLocation(String fireFoxLocation) {
		TestRunSettings.fireFoxLocation = fireFoxLocation;
	}

	private static String ieDriverLocation = "";
    public static String getIeDriverLocation() {
		return ieDriverLocation;
	}
	public static void setIeDriverLocation(String ieDriverLocation) {
		TestRunSettings.ieDriverLocation = ieDriverLocation;
	}

	private static String operaLocation = "";
    public static String getOperaLocation() {
		return operaLocation;
	}
	public static void setOperaLocation(String operaLocation) {
		TestRunSettings.operaLocation = operaLocation;
	}

	private static String interfaceSheetDetails = "";
    public static String getInterfaceSheetDetails() {
		return interfaceSheetDetails;
	}
	public static void setInterfaceSheetDetails(String interfaceSheetDetails) {
		TestRunSettings.interfaceSheetDetails = interfaceSheetDetails;
	}

	private static String excelSheetExtension = "";
    public static String getExcelSheetExtension() {
		return excelSheetExtension;
	}
	public static void setExcelSheetExtension(String excelSheetExtension) {
		TestRunSettings.excelSheetExtension = excelSheetExtension;
	}

	private static String xmlExtension = "";
    public static String getXmlExtension() {
		return xmlExtension;
	}
	public static void setXmlExtension(String xmlExtension) {
		TestRunSettings.xmlExtension = xmlExtension;
	}

	private static String jsonExtension = "";
    public static String getJsonExtension() {
		return jsonExtension;
	}
	public static void setJsonExtension(String jsonExtension) {
		TestRunSettings.jsonExtension = jsonExtension;
	}

	private static String commonMockSheetName = "";
    
    public static String getCommonMockSheetName() {
		return commonMockSheetName;
	}
	public static void setCommonMockSheetName(String commonMockSheetName) {
		TestRunSettings.commonMockSheetName = commonMockSheetName;
	}

	private static String useCommonMockSheet = "";
    public static String getUseCommonMockSheet() {
		return useCommonMockSheet;
	}
	public static void setUseCommonMockSheet(String useCommonMockSheet) {
		TestRunSettings.useCommonMockSheet = useCommonMockSheet;
	}

	static final String MOCKSHEETNAME = "";
    
    private static String headerRepositorySheetName = "";
    public static String getHeaderRepositorySheetName() {
		return headerRepositorySheetName;
	}
	public static void setHeaderRepositorySheetName(String headerRepositorySheetName) {
		TestRunSettings.headerRepositorySheetName = headerRepositorySheetName;
	}

	static final String HEADERREPOSITORY = "";
    static final String RESPONSESHEETPATH = "";
    
    private static Integer defaultServiceTimeout;
    public static Integer getDefaultServiceTimeout() {
		return defaultServiceTimeout;
	}
	public static void setDefaultServiceTimeout(Integer defaultServiceTimeout) {
		TestRunSettings.defaultServiceTimeout = defaultServiceTimeout;
	}

	private static String resultsPath = "";
	
    public static String getResultsPath() {
		return resultsPath;
	}
	public static void setResultsPath(String resultsPath) {
		TestRunSettings.resultsPath = resultsPath;
	}

	private static String requestFolderPath = "";
	private static String responseFolderPath = "";
	static final String EXCELFILENAME = "";
    static final boolean GENERATEEXCELREPORT = false;
    
    private static String mockTemplateLocation = "";
    public static String getMockTemplateLocation() {
		return mockTemplateLocation;
	}
	public static void setMockTemplateLocation(String mockTemplateLocation) {
		TestRunSettings.mockTemplateLocation = mockTemplateLocation;
	}

	static final String DOMAINNAME = "";

    public static String getResponseFolderPath() {
		return responseFolderPath;
	}
	public static void setResponseFolderPath(String responseFolderPath) {
		TestRunSettings.responseFolderPath = responseFolderPath;
	}
	
	 public static String getRequestFolderPath() {
			return requestFolderPath;
		}
		public static void setRequestFolderPath(String requestFolderPath) {
			TestRunSettings.requestFolderPath = requestFolderPath;
		}
	
    //Capabilities Config
    protected static HashMap<String, HashMap<String, String>> dictCapabilities = new HashMap<>();
    static final boolean ISDRIVEROPTIONENABLED = false;


    private static final WebDriver driver = null;
    protected static HashMap<Integer, WebDriver> dictWebDriver = new HashMap<>();

    static final AndroidDriver<AndroidElement> aDriver = null;
    protected static HashMap<Integer, AndroidDriver<AndroidElement>> dictAdriver = new HashMap<>();


    static final IOSDriver<IOSElement> iDriver = null;
    protected static HashMap<Integer, IOSDriver<IOSElement>> dictIDriver = new HashMap<>();


    protected static HashMap<String, HashMap<String,String>> masterInterfaceSheet = new HashMap<>();


	static final String EXCELFILEPATH = "";
	private static String uiPerfFileName = "";

	public static String getUiPerfFileName() {
		return uiPerfFileName;
	}
	public static void setUiPerfFileName(String uiPerfFileName) {
		TestRunSettings.uiPerfFileName = uiPerfFileName;
	}

	//DB Validations
	static final String DBVALIDATIONFILENAME = "";
	static final String DBVALIDATIONSHEETNAME = "";
	static final String DBCONNECTIONSTRING = "";
	static final String STATICDATAFILENAME = "";

	static final String STATICDATASHEETNAME="";

	//ConfigLocation
	private static String configLocation = "";


	public static String getConfigLocation() {
		return configLocation;
	}
	public static void setConfigLocation(String configLocation) {
		TestRunSettings.configLocation = configLocation;
	}

	//API Config
	private static String apiConfigFileName = "";
	
    public static String getApiConfigFileName() {
		return apiConfigFileName;
	}
	public static void setApiConfigFileName(String apiConfigFileName) {
		TestRunSettings.apiConfigFileName = apiConfigFileName;
	}

	//	ExcelDriven TestCases
	static final String TESTSCENARIOFILEPATH = "";
	static final String TESTSCENARIOFILENAME = "";
	static final String TESTSCENARIOSHEETNAME = "";
	static final String TESTCASEFILEPATH = "";
	static final int PARALLETHREADCOUNT = 0;

	//Temp Results
	static final String TEMPEXCELTESTCASERESULTS = "";
	static final String TEMPXMLTESTCASERESULTS = "";
	static final String TEMPJSONTESTCASERESULTS = "";


    static final String TESTDATAMAPPINGSHEETNAMEEN = "";
	private static String testDataMappingSheetNameSd = "";
	
	public static String getTestDataMappingSheetNameSd() {
		return testDataMappingSheetNameSd;
	}
	public static void setTestDataMappingSheetNameSd(String testDataMappingSheetNameSd) {
		TestRunSettings.testDataMappingSheetNameSd = testDataMappingSheetNameSd;
	}

	private static Map<String, String[]> adaResultsMap = new HashMap<>();
	
	
	
	private static String adaToolPath="";

	public static String getAdaToolPath() {
		return adaToolPath;
	}
	public static void setAdaToolPath(String adaToolPath) {
		TestRunSettings.adaToolPath = adaToolPath;
	}
	private static final HashMap<String, String> UIPerformanceResultsMap= new HashMap<>();
	public static Map<String, String> getUiperformanceresultsmap() {
		return UIPerformanceResultsMap;
	}
	public static Map<String, String[]> getAdaResultsMap() {
		return adaResultsMap;
	}
	public static void setAdaResultsMap(Map<String, String[]> adaResultsMap) {
		TestRunSettings.adaResultsMap = adaResultsMap;
	}
	public static WebDriver getDriver() {
		return driver;
	}
	public static Map<HashMap<String,String>,ArrayList<String>> getUiperfdata() {
		return uiperfdata;
	}
	public static void setUiperfdata(Map<HashMap<String,String>,ArrayList<String>> uiperfdata) {
		TestRunSettings.uiperfdata = uiperfdata;
	}
	public static List<String> getPageLoadtime() {
		return pageLoadtime;
	}
	public static void setPageLoadtime(List<String> pageLoadtime) {
		TestRunSettings.pageLoadtime = pageLoadtime;
	}
	public static List<String> getDomLoadtime() {
		return domLoadtime;
	}
	public static void setDomLoadtime(List<String> domLoadtime) {
		TestRunSettings.domLoadtime = domLoadtime;
	}
	
}