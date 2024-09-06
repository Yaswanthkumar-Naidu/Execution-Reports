package api_utilities.api_common;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api_utilities.api_helpers.MSSQLUtilities;
import api_utilities.models.APIConfig;
import api_utilities.models.APIModel;
import api_utilities.models.MockData;
import api_utilities.models.ResponseValidationModel;
import api_utilities.models.StoreResponseModel;
import api_utilities.test_settings.APISessionData;
import api_utilities.test_settings.APITestSettings;

public class APIMaster {

	 static final String DATA_MODULE="Module::";
	 static final String DATA_INTERFACENAME="InterfaceName::";
	 static final String TESTDATAFILENAME="TestDataFileName";
	static final String RESPONSETYPE="ResponseType";
	static final String XPATH_JSONPATH="xpath_jsonpath";
	
	static final String INTERFACENAME="InterfaceName";
	

	static final String SLASH_SYMBOL= "////////////////////////////////////////////////////////////////////////////";
	public APIModel fetchInterfaceRepository(APIConfig interfaceTestSettings,String testCase,String moduleName, String interfaceName,String iteration,String apifilepath) throws Exception
	{

		APIUtil utilObj = new APIUtil();


		final Logger logger =LoggerFactory.getLogger(APIMaster.class.getName()); 

		try
		{

			ArrayList<String> whereClause = new ArrayList<>();

			whereClause.add(DATA_MODULE+moduleName);
			whereClause.add(DATA_INTERFACENAME+interfaceName);

			Map<String, List<String>> interfacesArrayList = ExcelUtil.fetchWithCondition(apifilepath,interfaceTestSettings.getInterfaceTestCaseSheet(), whereClause);

			try
			{

				String requestPath = "";
				String responsePath = "";

				String requestData = "";
				String responseData = "";

				String module = "";

				String fileFormat = "";
				String interfaceType = "";
				String serviceURL = "";
				String soapaction = "";
				String methodType = "";
				String certificateRequired;
				String certificateFileName = "";
				String certificatePassword = "";
				String dbquery="";
				String dbexpectedvalue="";
				String needMock;
				String mockedInterfaceName;
				int timeout;
				String templateData = "";

				ResponseValidationModel responsevalidationModel= new ResponseValidationModel();
				requestPath = interfaceTestSettings.getRequestLocation()  + interfacesArrayList.get("RequestFileName").get(0);
				responsePath = interfacesArrayList.get("ResponseFileName").get(0).trim().equals("") ? "" : interfaceTestSettings.getResponseLocation()  + interfacesArrayList.get("ResponseFileName").get(0);
				String testDataFileName = interfacesArrayList.get(TESTDATAFILENAME).get(0).trim().equals("") ? "" : interfacesArrayList.get(TESTDATAFILENAME).get(0);

				module = interfacesArrayList.get("Module").get(0);
				interfaceName = interfacesArrayList.get(INTERFACENAME).get(0);
				fileFormat = interfacesArrayList.get("FileFormat").get(0);
				interfaceType = interfacesArrayList.get("InterfaceType").get(0);
				
				needMock = interfacesArrayList.get("NeedMock").get(0);
				mockedInterfaceName = interfacesArrayList.get("MockedInterfaceName").get(0);
				if (interfacesArrayList.get("Timeout").get(0).equals(""))
				{
					timeout = interfaceTestSettings.getDefaultServiceTimeout();
				}
				else

				{
					try
					{
						timeout = Integer.parseInt(interfacesArrayList.get("Timeout").get(0));
					}

					catch (Exception e)
					{
						logger.error("Error while retrieving the timeout  value from the TestCasesheet. Hence setting the default timeout");
						timeout = interfaceTestSettings.getDefaultServiceTimeout();
					}
				}


				//Initializing InterFace_Object
				APIModel interFaceObj = new APIModel();


				//Retrieving Url from URL_Repository
				ArrayList<String> whereClauseUrl = new ArrayList<>();
				whereClauseUrl.add(DATA_MODULE+ module);
				whereClauseUrl.add(DATA_INTERFACENAME + interfaceName);

				Map<String, List<String>> serviceArrayList = ExcelUtil.fetchWithCondition(interfaceTestSettings.getUrlRepositorySheet(), interfaceTestSettings.getEnvironment(), whereClauseUrl);

				serviceURL = serviceArrayList.get("URL").get(0);
				soapaction = serviceArrayList.get("SOAPAction").get(0);
				methodType = serviceArrayList.get("MethodType").get(0);
				certificateRequired = serviceArrayList.get("CertificateRequired").get(0);
				certificateFileName = interfaceTestSettings.getCertificateLocation() + serviceArrayList.get("CertificateFileName").get(0);
				certificatePassword = serviceArrayList.get("CertificatePassword").get(0);
				dbquery = serviceArrayList.get("DBQuery").get(0);
				dbexpectedvalue = serviceArrayList.get("DBExpectedValue").get(0);
				if(dbquery.contains("#StartDate#"))
				{
					dbquery = dbquery.replace("#StartDate#", String.valueOf(APISessionData.getSessionStartTime()));
				
				}

				//Adding Mock Data
				MockData mockData = new MockData();
				interFaceObj.setMockDetails(needMock, mockedInterfaceName);

				if (interFaceObj.isMockRequired())
				{
					ArrayList<String> whereClauseMock = new ArrayList<>();
					whereClauseMock.add("TemplateName::" + mockedInterfaceName);
					Map<String, List<String>> dictMockData = ExcelUtil.fetchWithCondition(interfaceTestSettings.getMockRepositorySheet(), interfaceTestSettings.getMockSheetName(), whereClauseMock);

					for (String MockKey : dictMockData.keySet())
					{


						if (MockKey.equalsIgnoreCase("ID"))
						{
							mockData.setId((dictMockData.get(MockKey).get(0)));
						}
						if (MockKey.equalsIgnoreCase("MOCKLOCATION"))
						{
							mockData.setMockLocation(dictMockData.get(MockKey).get(0));

						}
						if (MockKey.equalsIgnoreCase("TEMPLATENAME"))
						{
							mockData.setTemplateName(dictMockData.get(MockKey).get(0));

						}
						if (MockKey.equalsIgnoreCase("INTERFACETYPE"))
						{
							mockData.setInterfaceType(dictMockData.get(MockKey).get(0));

						}
						if (MockKey.equalsIgnoreCase("RECORDCOUNT"))
						{
							mockData.setRecordCount(dictMockData.get(MockKey).get(0));

						}
						if (MockKey.equalsIgnoreCase("MOCKFILENAME"))
						{
							mockData.setMockFileName(dictMockData.get(MockKey).get(0));

						}
						if (MockKey.equalsIgnoreCase("MOCKFILEFORMAT"))
						{
							mockData.setMockFileFormat(dictMockData.get(MockKey).get(0));

						}

					}

				}


				//Retrieving Request and Response Template
				try
				{

					requestData = FileUtils.readFileToString(new File(requestPath),StandardCharsets.UTF_8);
					logger.info("Read the Request File : {} ",requestPath);
					logger.info("RequestData Template for {} : {}",interfaceName,requestData);

				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the Request File"+ requestPath);
				}

				try
				{



					responseData = responsePath.trim().equals("") ? "" : FileUtils.readFileToString(new File(responsePath),StandardCharsets.UTF_8).trim();
					logger.info("Read the Respomse File : {} ",responsePath);
					responsevalidationModel.setExpectedResponseFilePath(responsePath);
					responsevalidationModel.setExpectedResponse(responseData);

				}
				catch (Exception e)
				{
					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the Response File :  " + responsePath);
				}




				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
				/////////////////////////////////////////// Start of Test Data/////////////////////////////////////////////////////////////////////////                                             
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



				String testDataSheetName=interfaceTestSettings.getEnvironment();

				if(interfaceTestSettings.isUseCommonTestDataSheet())
				{
					testDataSheetName=interfaceTestSettings.getCommonTestDataSheetName();
				}






				Map<String, String> tempDictData = new HashMap<>();
				try
				{
					MSSQLUtilities sql = new MSSQLUtilities();
					tempDictData = utilObj.getTestData(interfaceTestSettings.getTestDataPath(), testDataFileName,testCase,  module,APITestSettings.getBrowser(), testDataSheetName,String.valueOf(iteration));
					for (Map.Entry<String, String> entry : tempDictData.entrySet()) {
			            String key = entry.getKey();
			            String value = entry.getValue();
			            value = value.toUpperCase();

			            if (value.contains("QUERY:")) {
			                value = value.replace("QUERY:", "");
			                value = sql.readDataDBValidation(APISessionData.envModel.getConnectionString(), value);
			                if(value==null) {
			                	value="";
			                }
			                tempDictData.put(key, value);
			                logger.info("Query has been fetched from TestData: {}",value);
			            }
			        }
					requestData = tempDictData.entrySet().stream().reduce(requestData,(s, e) -> s.replace("#*" + e.getKey()+ "*#", e.getValue()),(s1, s2) -> null);
					responseData = tempDictData.entrySet().stream().reduce(responseData,(s, e) -> s.replace("#*" + e.getKey()+ "*#", e.getValue()),(s1, s2) -> null);
					serviceURL=tempDictData.entrySet().stream().reduce(serviceURL,(s, e) -> s.replace("#*" + e.getKey()+ "*#", e.getValue()),(s1, s2) -> null);
					serviceURL=APISessionData.replaceSessionData(testCase, module, "", iteration, serviceURL);

					logger.info(SLASH_SYMBOL);
					logger.info("Request Data");
					logger.info(SLASH_SYMBOL);
					logger.info(requestData);
					logger.info(SLASH_SYMBOL);
					logger.info(SLASH_SYMBOL);




					logger.info("Test Data read for the sheet {}",testDataFileName);
					logger.info("Request Data for the Interface {}  {}",interfaceName,requestData);
					logger.info("Response Data for the Interface {}  {}",interfaceName,responseData);


					if (interFaceObj.isMockRequired())
					{
						try
						{

							templateData =FileUtils.readFileToString(new File(interfaceTestSettings.getMockTemplateLocation() + File.separator + mockData.getTemplateName() + mockData.getMockFileFormat()),StandardCharsets.UTF_8).trim();
							mockData.setMockFileContent(tempDictData.entrySet().stream().reduce(templateData,(s, e) -> s.replace(e.getKey(), e.getValue()),(s1, s2) -> null));


							logger.info("Mock Data for the Interface {} : {}",interfaceName,mockData);
						}
						catch (Exception e)
						{

							logger.info(e.getMessage());
							e.getStackTrace();

							logger.info(e.getMessage());
							logger.info(Arrays.toString(e.getStackTrace()));

							throw new Exception("Error while reading the Mock File . Please check if the Test Data Sheet/Mock File is correctly configured for : " + mockData.getMockFileName());
						}

					}



				}
				catch (Exception e)
				{


					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the TestData. Please check if the Test Data Sheet is correctly configured for : " + testDataFileName);
				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
				/////////////////////////////////////////// End of Test Data/////////////////////////////////////////////////////////////////////////                                             
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////








				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
				/////////////////////////////////////////// Response Validation////////////////////////////////////////////////////////////////////////                                             
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				try
				{
					ArrayList<String> whereClauseValidate = new ArrayList<>();
					whereClauseValidate.add("Validate::Yes");
					whereClauseValidate.add(DATA_MODULE + module);
					whereClauseValidate.add(DATA_INTERFACENAME + interfaceName);

					Map<String, List<String>> dictResponseValidationData = ExcelUtil.fetchWithCondition(interfaceTestSettings.getResponseValidationFilePath(), interfaceTestSettings.getResponseValidationSheetName(), whereClauseValidate);




					if (!dictResponseValidationData.isEmpty() && !dictResponseValidationData.get(RESPONSETYPE).isEmpty())

					{


						responsevalidationModel.setResponseType(dictResponseValidationData.get(RESPONSETYPE).get(0));
						for(int i=0;i<dictResponseValidationData.get(INTERFACENAME).size();i++ )
						{

							String xpathJsonpath="";	
							String expectedValue="";
							String dataElements="";


							if(!dictResponseValidationData.get(XPATH_JSONPATH).get(i).equals(""))
							{
								xpathJsonpath=dictResponseValidationData.get(XPATH_JSONPATH).get(i);
							}

							if(!dictResponseValidationData.get("ExpectedValue").get(i).equals(""))
							{
								expectedValue=dictResponseValidationData.get("ExpectedValue").get(i);
								expectedValue=replaceTestData(tempDictData, expectedValue);
								expectedValue=APISessionData.replaceSessionData(testCase, module, "", iteration, expectedValue);

							}

							if(!dictResponseValidationData.get("ExpectedDataValue").get(i).equals(""))
							{
								dataElements=dictResponseValidationData.get("ExpectedDataValue").get(i);
								dataElements=replaceTestData(tempDictData, dataElements);
								dataElements=APISessionData.replaceSessionData(testCase, module, "", iteration, dataElements);

							}



							if(!xpathJsonpath.equals(""))
							{
								responsevalidationModel.getxPathJsonPath().put(xpathJsonpath, expectedValue);
							}

							if(!dataElements.equals(""))
							{
								responsevalidationModel.getDataElements().add(dataElements);
							}

						}
					}

				}
				catch (Exception e)
				{

					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the Response Validation Sheet");
				}

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////End of Response Validation////////////////////////////////////////////////////////////////////////                      
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
				/////////////////////////////////////////// Store Response Data////////////////////////////////////////////////////////////////////////                                             
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				StoreResponseModel storeResponseModel=  new StoreResponseModel();                        
				try
				{
					ArrayList<String> whereClauseStore = new ArrayList<>();
					whereClauseStore.add("Capture::Yes");
					whereClauseStore.add(DATA_MODULE + module);
					whereClauseStore.add(DATA_INTERFACENAME + interfaceName);

					Map<String, List<String>> dictStoreResponseData = ExcelUtil.fetchWithCondition(interfaceTestSettings.getStoreResponseDataFilePath(), interfaceTestSettings.getStoreResponseDataSheetName(), whereClauseStore);




							if (!dictStoreResponseData.isEmpty() && (!dictStoreResponseData.get(RESPONSETYPE).isEmpty())) {
						storeResponseModel.setResponseType(dictStoreResponseData.get(RESPONSETYPE).get(0));
						for(int i=0;i<dictStoreResponseData.get(INTERFACENAME).size();i++ )
						{

							String xpathJsonpath="";
							String variableName="";


							if(!dictStoreResponseData.get(XPATH_JSONPATH).get(i).equals(""))
							{
								xpathJsonpath=dictStoreResponseData.get(XPATH_JSONPATH).get(i);
							}

							if(!dictStoreResponseData.get("VariableName").get(i).equals(""))
							{
								variableName=dictStoreResponseData.get("VariableName").get(i);
							}


							storeResponseModel.getXpathJsonPath().put(xpathJsonpath, variableName);

						}
					}

				}
				catch (Exception e)
				{

					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the Response Validation Sheet");
				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////End of Store Response Data////////////////////////////////////////////////////////////////////////                      
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                      



				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////Start of Header Data/////////////////////////////////////////////////////////////////////////////                      
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                      



				//Adding Header Data
				Map<String, String> headerData = new HashMap<>();
				try
				{
					ArrayList<String> whereClauseHeader = new ArrayList<>();
					whereClauseHeader.add(DATA_MODULE + module);
					whereClauseHeader.add(DATA_INTERFACENAME+ interfaceName);
					Map<String, List<String>> dictHeaderData = ExcelUtil.fetchWithCondition(interfaceTestSettings.getHeaderRepository(), interfaceTestSettings.getHeaderRepositorySheetName(), whereClauseHeader);


					if (!dictHeaderData.get(INTERFACENAME).isEmpty())
					{
						for(String HeaderKey : dictHeaderData.keySet())
						{
							if (!dictHeaderData.get(HeaderKey).get(0).equals(""))
							{

								String headerValue=dictHeaderData.get(HeaderKey).get(0);
								headerValue=replaceTestData(tempDictData, headerValue);
								headerData.put(HeaderKey,headerValue);
							}
						}
					}



					headerData=APISessionData.replaceSessionDataCollection(testCase, module, "", iteration, headerData);
				}
				catch (Exception e)
				{

					logger.info(e.getMessage());
					logger.info(Arrays.toString(e.getStackTrace()));

					throw new Exception("Error while reading the data from the Header Repository");
				}

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				///////////////////////////////////////End of Header Data/////////////////////////////////////////////////////////////////////////////                      
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                      




				interFaceObj.setInterfaceDetails( module, interfaceName, fileFormat, interfaceType,
						serviceURL, soapaction, methodType, certificateRequired, certificateFileName, certificatePassword,
						requestData, responseData,  needMock, mockedInterfaceName, mockData,
						headerData,  timeout,responsevalidationModel,storeResponseModel,dbquery,dbexpectedvalue);



				//Adding the interfaceObj in Global InterFace Repository
				return interFaceObj;
			}
			catch (Exception e)
			{
				logger.info(e.getMessage());
				logger.info(Arrays.toString(e.getStackTrace()));

				throw new Exception("Error while reading the data for the Interfaces");

			}

		}
		catch (Exception e)
		{

			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));

			throw new Exception("Error while reading the Excel Data. Please check the excel master sheet");
		}

	}


	public String replaceTestData(Map<String, String> testDictData,String value)
	{
		return testDictData.entrySet().stream().reduce(value,(s, e) -> s.replace(e.getKey(), e.getValue()),(s1, s2) -> null);

	}


}
