package common_utilities.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.*;

import common_utilities.common.PoiReadExcel;


import com.github.javafaker.Faker;

import common_utilities.common.RetreiveProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class Util
{
    private static final Logger logger =LoggerFactory.getLogger(Util.class.getName()); 

    public enum Mode
    {
        ALPHA, ALPHANUMERIC, NUMERIC
    }
    public static String getRootPath(){
    return Paths.get("").toAbsolutePath().toString();
    }
    public Util()
    {
    	  throw new UnsupportedOperationException();
    }

    
    
    Formatter formatter = new Formatter();
    public String getData(String colName, String scenario, String testCase, String homePath, Integer currentIteration) {
	String dataValue = "";
	

	
	String dbPath = homePath + "/test/resources/TestScripts-TestData/"+scenario+".csv";
	PoiReadExcel poiObject = new PoiReadExcel();
	try {
		ArrayList<String> whereClause = new ArrayList<>();
		whereClause.add("TestScript::"+testCase);
		whereClause.add("Iteration::"+Integer.parseInt(currentIteration.toString()));
		Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(dbPath, "TestData", whereClause);

		for(int i=0; i<result.get("TestScript").size(); i++){
            if (testCase.equals(result.get("TestScript").get(i)) && Integer.parseInt(currentIteration.toString()) == Integer.parseInt(result.get("Iteration").get(i)))
            {
                     dataValue = result.get(colName).get(i);
				break;
			}
		}

		return dataValue;
	} catch (Exception e) {
            logger.info(e.getMessage());  
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
     throw e;
	}

}



    public Map<String, ArrayList<String>> getDictionaryFromPOI(String path,String SheetName,String Clause)
    {

        try
        { 
        	PoiReadExcel poiObject = new PoiReadExcel();
        ArrayList<String> whereClauseWebTotalCount = new ArrayList<>();
        whereClauseWebTotalCount.add(Clause);
        return poiObject.fetchWithCondition(path, SheetName, whereClauseWebTotalCount);
    } catch (Exception e) {
            logger.info(e.getMessage());
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
  			throw e;
	}
}

    public Map<String,String> getTestData(String tdPath, String testDataSheet, String testCase, Integer currentIteration)
    {
        try
        {
        	


            ArrayList<String> testDataSheets = (ArrayList<String>) List.of(testDataSheet.split(";"));
        HashMap<String, String> testData = new HashMap<>();

        for (String TD : testDataSheets)
        { 
        String testDataPath = tdPath + TD + ".csv";

        PoiReadExcel poiObject = new PoiReadExcel();



                ArrayList<String> whereClauseTestData = new ArrayList<>();
                whereClauseTestData.add("TestScript::" + testCase);
                whereClauseTestData.add("Iteration::" + currentIteration.toString());
                Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(testDataPath, "TestData", whereClauseTestData);
         


                for (String key : result.keySet())
                {

                    if (!testData.containsKey(key))
                    {
                        testData.put(key,result.get(key).get(0));
                    }
                    else
                    {
                        testData.replace(key,result.get(key).get(0));
                    }
                }

            }
            return testData;
        }
        catch (Exception e)
        {


            logger.info(e.getMessage()); 
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }

    }

    public static String getCurrentDate(){

        try { 
    LocalDateTime today = LocalDateTime.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    String date = today.format(format);
    date = date.replace(":", "_");
    date = date.replace(" ", "_");
    date = date.replace(".", "_");
    date = date.replace("-", "_");
    return date;
    }
        catch(Exception e)
        {
            logger.info(e.getMessage());   
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }
}

   
    public static String getCurrentTime(){
        try { 
        	 Date date = Calendar.getInstance().getTime();
			   DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.sss");
			   String result = dateFormat.format(date);
			   result = result.replace(":", "_");
            result = result.replace(" ", "_");
            result = result.replace(".", "_");
    return result;

    }
        catch(Exception e)
        {
            logger.info(e.getMessage());  
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }

}

public String getReusableData(String colName, String scenario, String testCase, String homePath, Integer currentIteration) {
	String dataValue = "";
	

	
	String dbPath = homePath + "/test/resources/TestScripts-TestData/"+scenario+".csv";
	PoiReadExcel poiObject = new PoiReadExcel();
	try {
		ArrayList<String> whereClause = new ArrayList<>();
		whereClause.add("TestScript::"+testCase);
		whereClause.add("Iteration::"+ Integer.parseInt(currentIteration.toString()));
		Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(dbPath, "TestData", whereClause);
		

		
		for(int i=0; i<result.get("TestScript").size(); i++){
            if (testCase.equalsIgnoreCase(result.get("TestScript").get(i)) && Integer.parseInt(currentIteration.toString()) == Integer.parseInt(result.get("Iteration").get(i)))
            {
				dataValue = result.get(colName).get(i);
				break;
			}
		}
		
		return dataValue;
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());  
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }

    }

    public String CreateModuleFolder(String resultsPath, String module)
    {

        try
        {

        	File dir = new File(resultsPath + File.separator + module);
            if (!dir.exists()) dir.mkdirs();
           

            return (resultsPath + File.separator + module);


        }
        catch (Exception e)
        {
            logger.info(e.getMessage()); 
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }
    }


    public String CreateScenarioFolder(String resultsPath, String scenario)
    {

        try
        {

        	File dir = new File(resultsPath + File.separator + scenario);
            if (!dir.exists()) dir.mkdirs();
            return (resultsPath + File.separator + scenario);


        }
        catch (Exception e)
        {
            logger.info(e.getMessage()); 
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }
    }

  
    public String CreateBrowserFolder(String scenarioFolderPath, String browser)
    {

        try
        {
        	File dir = new File(scenarioFolderPath + File.separator + browser);
            if (!dir.exists()) dir.mkdirs();
            return (scenarioFolderPath + File.separator+ browser);

        }
        catch (Exception e)
        {
            logger.info(e.getMessage()); 
            logger.error(e.getMessage());            
            throw e;
        }
    }



    public Map<String,String> getORElements(String orPath, String orFileNames)
    {
        HashMap<String, String> objRep = new HashMap<>();
        ArrayList<String> orFiles = (ArrayList<String>) List.of(orFileNames.split(";"));

        for (String ORFile : orFiles)
        {
               try
            {
   
                String propFile = orPath + ORFile + ".txt";
                RetreiveProperties rp = new RetreiveProperties();
                Map<String, String> tempObjRep = rp.getProperties(propFile);


                for (String key : tempObjRep.keySet())

                {

                    if (!objRep.containsKey(key))
                    {
                        objRep.put(key, tempObjRep.get(key));
                    }
                    else
                    {
                        objRep.replace(key, tempObjRep .get(key)) ;
                    }
                }


            }
            catch (Exception e1)
            {
                logger.info(e1.getMessage());
            }
           
        }
        return objRep;
    }


 
    public String getObjectFromObjectMap(String key, String scenario,String HomePath){
	String value = "";

	try {
        
         

            String propFile = HomePath + "/test/resources/ObjectRepository/" + scenario + ".txt";
        RetreiveProperties rp = new RetreiveProperties();
        Map<String, String> objRep = rp.getProperties(propFile);


		if(!key.equals(""))
            value = objRep.get(key);
		else
			value = null;
	} 
	catch (Exception e1) 
	{
        logger.info(e1.getMessage());
	} 
        return value;
}

    public Map<String, String> getObjectFromCommonRep(String homePath)
    {
        String value = "";
        Map<String, String> objRep = new HashMap<>();
        try
        {
           

            String propFile = homePath + "/test/resources/ObjectRepository/Common.txt";
            RetreiveProperties rp = new RetreiveProperties();
            objRep = rp.getProperties(propFile);


       
        }
        catch (FileNotFoundException e1)
        {
            logger.info(e1.getMessage());
        }
        catch (IOException e1)
        {
            logger.info(e1.getMessage());
        }
        catch (Exception e)
        {
            logger.info(e.getMessage());  
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
        }
        return objRep;
    }

    public String getDataValueForAppiumTC(String colName, String testCase, String dataPath, Integer currentIteration) {
	String dataValue = "";
	String dbPath = dataPath;
	PoiReadExcel poiObject = new PoiReadExcel();

	try {

		ArrayList<String> whereClause = new ArrayList<>();
		whereClause.add("TestScript::"+testCase);
		whereClause.add("Iteration::"+currentIteration.toString());
		Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(dbPath, "TestData", whereClause);


		for(int i=0; i<result.get("TestScript").size(); i++){
			if(testCase.equals(result.get("TestScript").get(i)) && Integer.parseInt(currentIteration.toString())==Integer.parseInt(result.get("Iteration").get(i))){
				dataValue = result.get(colName).get(i);
				break;
			}
		}

	} catch (Exception e) {
            logger.info(e.getMessage());
    throw e;
	}
	return dataValue;


}






    public  String generateRandomString(int length, Mode mode)
    {

        StringBuilder buffer = new StringBuilder();
        String characters = "";

        switch (mode)
        {

            case ALPHA:
                characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;

            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;

            case NUMERIC:
                characters = "1234567890";
                break;
        }
        int charactersLength = characters.length();

        for (int i = 0; i < length; i++)
        {

            int index = r.nextInt(charactersLength);

            buffer.append(characters.charAt(index));
        }
        return buffer.toString();
    }



    public String CreateDateFolder(String homePath)
    {
        try
        {
        	File dir = new File(homePath + "/Results");
            if (!dir.exists()) dir.mkdirs();
           


            String date = "Run_" + Util.getCurrentDate();

         String   resultsFolder = homePath + "/Results/" + date;

         
     	 dir = new File(resultsFolder);
        if (!dir.exists())
            dir.mkdirs();
        
        return resultsFolder;
        }
       

        catch (Exception e)
        {
            logger.info(e.getMessage());
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }
    }


    public String createDirectory(String directoryPath, String directoryName)
    {
        try
        {
            String directoryFulPath = directoryPath + File.separator + directoryName;
        	File dir = new File(directoryFulPath);
            if (!dir.exists()) dir.mkdirs();

            return directoryFulPath;

        }
        catch (Exception e)
        {
            logger.info(e.getMessage());
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }
    }

    public String generateFirstName() 
    {
    	Faker faker = new Faker();
    	return faker.name().firstName();
    }
    public String generateLastName() 
    {
    	Faker faker = new Faker();
    	return faker.name().lastName();
    }
    
  

    Random r = new Random();
    public String getRandom(String value)
    {
        if (value.toLowerCase().contains("string"))
        {
        	
            value =generateRandomString(r.nextInt(20-5)+5, Mode.ALPHA);
        }
        if (value.toLowerCase().contains("name"))
        {
        	
            value =generateFirstName();
        }
        else if (value.toLowerCase().contains("dob"))
        {
            value = getDateOfBirth(value);
        }

        else if (value.toLowerCase().contains("ssn"))
        {
            value = getRandomSSN();
        }
        else if (value.toLowerCase().contains("ssn_dash"))
        {
            value = getRandomSSN("-");
        }
        else if (value.toLowerCase().contains("psuedossn"))
        {
            value = getRandomSSN();
        }
        else if (value.toLowerCase().contains("psuedossn_dash"))
        {
            value = getRandomSSN("-");
        }
        else if (value.toLowerCase().contains("individualid"))
        {
            value = generateRandomString(9, Mode.NUMERIC);
        }
        else if(value.toLowerCase().contains("number"))
        {
            ArrayList<String> values = (ArrayList<String>) List.of(value.split(";")); 
            int length = values.size() > 1 ? Integer.parseInt(values.get(1)) : 10;
            value = generateRandomString(length, Mode.NUMERIC);
        }
        else if (value.toLowerCase().contains("alphanum"))
        {
            value = generateRandomString(30, Mode.ALPHANUMERIC);
        }
        else if(value.toLowerCase().contains("uuid") || value.toLowerCase().contains("guid"))
        {
            UUID guid = UUID.randomUUID();
            value = guid.toString();
        }

        return value;
    }



    public String getDateOfBirth(String dataValue, String... datTimeFormat)
    {
       String dfformat="MM/dd/yyyy";
       if(datTimeFormat.length==0)
       {
             
             dfformat = "MM/dd/yyyy";
       }
        String date = "";
        int daterange;
        try
        {
        	String[] rondomdob=dataValue.split(";");
            int count=rondomdob.length;
            
            	int NoOfdays = (Integer.parseInt(dataValue.split(";")[1])) * 365 + ((Integer.parseInt(dataValue.split(";")[1])) / 4);
                int min =NoOfdays + 1;
                int max =NoOfdays + 364;
                daterange = r.nextInt(max - min) + min;
                
                LocalDate ldate = LocalDate.now();
                if(count==2) 
                {
                ldate=ldate.minusDays(daterange);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(dfformat);
                 date = ldate.format(format);
                }
            
            else if(count==3) 
            {
            	NoOfdays = ((Integer.parseInt(dataValue.split(";")[1])) * 365) + (((Integer.parseInt(dataValue.split(";")[1])/4 )+ ((Integer.parseInt(dataValue.split(";")[2])*30))));
            	  min =NoOfdays + 1;
                  max =NoOfdays + 29;
                 daterange = r.nextInt(max - min) + min;
            	 ldate=ldate.minusDays(daterange);
                 DateTimeFormatter format = DateTimeFormatter.ofPattern(dfformat);
                  date = ldate.format(format);
            }
            else if(count==4) 
            {
            NoOfdays = ((Integer.parseInt(dataValue.split(";")[1])) * 365) + Integer.parseInt(dataValue.split(";")[1])/4 + Integer.parseInt(dataValue.split(";")[2])*30+Integer.parseInt(dataValue.split(";")[3])*1;
               ldate=ldate.minusDays(NoOfdays);
                DateTimeFormatter format = DateTimeFormatter.ofPattern(dfformat);
                date = ldate.format(format);
            }
            
        }
        catch (Exception e)
        {
            logger.info(e.toString());
            logger.info(e.getMessage());
            logger.error("Unable to Add Date Of Birth due to error:{} ", e.toString());
            throw e;
        }
        return date;
    }


    public String getRandomSSN(String... delimiter)
    {
            if (delimiter == null) {
                delimiter[0] = "";
            }

            Integer iThree = getRandomNumber(132, 921);
            Integer iTwo = getRandomNumber(12, 83);
            Integer iFour = getRandomNumber(1423, 9211);
            return iThree.toString() + delimiter[0] + iTwo.toString() + delimiter[0] + iFour.toString();
        }
       


    public String getRandomPsuedoSSN(String... delimiter) throws NullPointerException
    {
    	if(delimiter==null)
    	{
    		delimiter[0]="";
    	}
    	
    	Integer iThree = getRandomNumber(911, 988);
    	Integer iTwo = getRandomNumber(12, 83);
    	Integer iFour = getRandomNumber(1423, 9211);
        return iThree.toString() + delimiter[0] + iTwo.toString() + delimiter[0] + iFour.toString();
    }

    Random getrandom = new Random();
    public int getRandomNumber(int min, int max)
    {

        return getrandom.nextInt(max - min) + min;
        
    }

    public Map<String, String> getTestData(String tdPath, String testDataSheet, String testCase, Integer currentIteration, String... defaultTestDataFormat) throws Exception
    {

    	if(defaultTestDataFormat==null)
    	{
    		defaultTestDataFormat[0] = ".csv";
        
    	}
    	try
        {

    		

            ArrayList<String> testDataSheets = (ArrayList<String>) List.of(testDataSheet.split(";"));
            HashMap<String, String> testData = new HashMap<>();

            for (String TD : testDataSheets)
            {
                String testDataPath = tdPath + TD + defaultTestDataFormat[0];

                PoiReadExcel poiObject = new PoiReadExcel();



                ArrayList<String> whereClauseTestData = new ArrayList<>();
                whereClauseTestData.add("TestScript::" + testCase);
                whereClauseTestData.add("Iteration::" + currentIteration.toString());
                Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(testDataPath, "TestData", whereClauseTestData);



                if (result.isEmpty())
                {

                	logger.error("Blank column in Test Data - There is no data in the column for the Iteration {} of test case {}", currentIteration, testCase);
                }

                for(String key : result.keySet())              {

					if (result.get(key).get(0).toLowerCase().trim().startsWith("random_"))
                    {
                       result.get(key).set(0,getRandom(result.get(key).get(0)));
                        Thread.sleep(500);
                        logger.info("Random Value Added in {} = {}", key, result.get(key).get(0));
                    }
                    
                    if (!testData.containsKey(key))
                    {
                        testData.put(key,result.get(key).get(0));
                    }
                    else
                    {
                        testData.replace(key ,result.get(key).get(0));
                    }
                }

            }
            return testData;
        }
        catch (Exception e)
        {


            logger.info(e.getMessage()); 
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            
           
            throw e;
        }

    }
    
    
   
    
    public static String convertFromSQLDateToJAVADate(String sqlDate) 
    {
        String customDate = null;
        if (sqlDate != null) 
        {
        	LocalDate tradeDate= LocalDate.parse(sqlDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        	customDate=tradeDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }
        return customDate;
    }
    
    public String modifyDateFormat(String datenew)
    {

    		datenew=datenew.replace("-", "/");
        	LocalDate tradeDate= LocalDate.parse(datenew, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        	return tradeDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    	
    }
    
    public String modifyDateTimeFormattodate(String datenew)
    {
    		String[] date=datenew.split(" ");
    		datenew=date[0].replace("-", "/");
        	LocalDate tradeDate= LocalDate.parse(datenew, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        	return tradeDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    	
    }

    public Map<String, String> getTestData(String tdPath, String testDataSheet, String environment, String currentIteration, String... defaultTestDataFormat) throws Exception
    {

    	
    	if(defaultTestDataFormat==null)
    	{
    	defaultTestDataFormat[0] = ".csv";
        
    	}
    
        try
        {

            ArrayList<String> testDataSheets = (ArrayList<String>) List.of(testDataSheet.split(";"));
            HashMap<String, String> testData = new HashMap<>();
            for (String TD : testDataSheets)
            {
                String testDataPath = tdPath + TD + defaultTestDataFormat;

                PoiReadExcel poiObject = new PoiReadExcel();



                ArrayList<String> whereClauseTestData = new ArrayList<>();
                whereClauseTestData.add("Iteration::" + currentIteration);
                Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(testDataPath, environment, whereClauseTestData);


                if (result.isEmpty())
                {

                	logger.error("Blank column in Test Data - There is no data in the column for the Iteration {} of Environment {}", currentIteration, environment);
                }


                for (String key : result.keySet())
                {
                    if (result.get(key).get(0).toLowerCase().trim().startsWith("random_"))
                    {
                       result.get(key).set(0,getRandom(result.get(key).get(0)));
                        Thread.sleep(500);
                        logger.info("Random Value Added in {} = {}", key, result.get(key).get(0));
                    }
                    
                    if (!testData.containsKey(key))
                    {
                        testData.put(key,result.get(key).get(0));
                    }
                    else
                    {
                        testData.replace(key,result.get(key).get(0));
                    }
                }


            }
            return testData;
        }
        catch (Exception e)
        {


            logger.info(e.getMessage());
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);
            
            throw e;
        }

    }
    public String createResultsFolder(String homePath) throws Exception
    {
        try
        {

        	File dir = new File(homePath + "/Results");
            if (!dir.exists()) dir.mkdirs();

            String date = getCurrentDate();

            String resultsFolderDate = homePath + "/Results/" + date;

        	dir = new File(resultsFolderDate);
            if (!dir.exists()) dir.mkdirs();
            
            String time = "Run_" + getCurrentTime();

            String resultsFolderTime = resultsFolderDate + File.separator + time;


            dir = new File(resultsFolderTime);
            if (!dir.exists()) dir.mkdirs();
            


            return resultsFolderTime;

        }
        catch (Exception e)
        {
            logger.info(e.getMessage()); 
            logger.error(e.getMessage());
            logger.error("StackTrace:", e);

            throw e;
        }
    }


    public Map<String, ArrayList<String>> getScreenTCData(String ScreenName, String TestCaseName, String TestDataLocation, String TestDataMappingFileName , String TestDataMappingSheetName, String iteration)
    {

    	PoiReadExcel poiObject= new PoiReadExcel();
        ArrayList<String> whereClauseTestData = new ArrayList<>();
        whereClauseTestData.add("ScreenName::" + ScreenName);
        Map<String, ArrayList<String>> result = poiObject.fetchWithCondition(TestDataMappingFileName, TestDataMappingSheetName, whereClauseTestData);


        PoiReadExcel poiObject2 = new PoiReadExcel();
        ArrayList<String> whereClauseTestData2 = new ArrayList<>();
        whereClauseTestData2.add("TestCase::" + TestCaseName);
        whereClauseTestData2.add("Iteration::" + iteration);
        return poiObject2.fetchWithCondition(TestDataLocation + "\\" +result.get("TestDataFileName").get(0), result.get("TestDataSheetName").get(0), whereClauseTestData2);


    }
    
    
    
    public Properties loadProperties(String filepath)
    {
    	
        Properties prop = new Properties();

       try(FileInputStream propsInput = new FileInputStream(filepath))
       {
    	   prop.load(propsInput);
       }
       catch(Exception e) 
       {
    	  e.printStackTrace(); 
       }
       
	   return prop;

    }
    
    

}