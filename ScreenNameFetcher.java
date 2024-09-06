package reusable;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenNameFetcher {
	private static final Logger logger =LoggerFactory.getLogger(ScreenNameFetcher.class.getName());
	   

    public static String getScreenName(String urlString) {
        try {
            URL url = new URL(urlString);

            // Get the path from the URL
            String path = url.getPath();
            
            
            String[] pathSegments = path.split("/");
            
        

            // Find the last non-empty segment as the screen name
            String screenName = null;
            for (int i = pathSegments.length - 1; i >= 0; i--) {
                if (!pathSegments[i].isEmpty()) {
                    screenName = pathSegments[i];
                    break;
                }
            }

            return screenName;
        } catch (Exception e) {
        	
            return null;
        }
    }


    public static void main(String[] args) {
        String url = "https://uat3.kyserve.chfsinet.ky.gov/EligibilityDetermination/EligibilityDetermination/RFISummary?tabId=Ll9ujzbzBO";
        String screenName = getScreenName(url);

        if (screenName != null) {
            logger.info("Screen Name:{} ",screenName);
        } else {
            logger.info("Failed to retrieve screen name.");
        }
    }
}
