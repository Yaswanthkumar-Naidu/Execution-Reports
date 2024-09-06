package api_utilities.test_settings;

import java.util.HashMap;
import java.util.Map;

import api_utilities.models.EnvModel;

public class APISessionData {

	private static Map<String,String> sessionKeys= new HashMap<>();
	private static Map<String,String> apiSessionKeys= new HashMap<>();
	public static final EnvModel envModel= new EnvModel();
	public static final String DB_LOGGING_TABLE = "";
	private APISessionData() {}
	public static void setSessionData(String testCase, String module, String browser,String iteration,String key, String value)
	{
		sessionKeys.put(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key,value);
	}
	

	public static void syncSessionData(Map<String,String> sessionData)
	{

		if(!sessionData.isEmpty())
		{
			for(Map.Entry<String,String> entry : sessionKeys.entrySet())
			{
				 String key = entry.getKey();
				 String value = entry.getValue();

				sessionKeys.put( key,value);
			}
		}
	}
	
	public static void setSessionDataCollection(String testCase, String module, String browser,String iteration,Map<String,String> sessionData)
	{
		if(!sessionData.isEmpty())
		{
			for (Map.Entry<String,String> entry : sessionData.entrySet()) {
				
			    String key = entry.getKey();
			    String value = entry.getValue();

				sessionKeys.put(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key, value);
				apiSessionKeys.put(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key, value);
			}
		}

	}
	
	
	public static String getSessionData(String testCase, String module, String browser,String iteration,String key)
	{
		return sessionKeys.get(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key);		
	}

	
	
	public static String getAPISessionData(String testCase, String module, String browser,String iteration,String key)
	{
		return apiSessionKeys.get(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key);		
	}


	public static Map<String, String> getAPISessionDataCollection()
	{
		return apiSessionKeys;		
	}

	public static String replaceSessionData(String testCase, String module, String browser,String iteration,String value)
	{
		
		for(String s : sessionKeys.keySet())
		{
			s= s.replace(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_", "");
			if(value.contains(s))
			{
				value=value.replace("##"+s + "##", sessionKeys.get(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+s));
			}
		}
		return value;		
	}
	
	public static Map<String,String> replaceSessionDataCollection(String testCase, String module, String browser,String iteration,Map<String,String> collectionData)
	{
		
	for (Map.Entry<String,String> colEntry : collectionData.entrySet()) {
			
		    String colKey = colEntry.getKey();
		    String colValue = colEntry.getValue();
			
			
			for(Map.Entry<String,String> sessionEntry : sessionKeys.entrySet())
			{
				String sessionKey = sessionEntry.getKey();
			    
				String s=sessionKey.replace(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_", "");
				s="##"+s +"##";
				
				if(colValue.contains(s))
				{
					colValue=colValue.replaceAll(s, sessionKeys.get(sessionKey));
					collectionData.put(colKey, colValue);
				}
				
			}
			
		}
		return collectionData;		
	}
	
	
	public static boolean sessionDataContainsKey(String testCase, String module, String browser,String iteration,String key)
	{
		return sessionKeys.containsKey(testCase+ "_"+ module + "_" +browser+ "_"+iteration+ "_"+ key);
	}


	public static char[] getSessionStartTime() {
	
		return new char[0];
	}
	

}
