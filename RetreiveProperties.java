package common_utilities.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class RetreiveProperties
{
    public Map<String, String> getProperties(String path) throws IOException{
        try {
            String fileData = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            fileData = fileData.replace("\r", "");

            Map<String, String> properties = new HashMap<>(); 
            String[] kvp;
            String[] records = fileData.split("\n");

            for (String rec : records) {
                if (!rec.trim().startsWith("//") && rec.contains("=")) {
                    kvp = rec.split("=");
                    if (properties.containsKey(kvp[0].trim())) {
                        properties.put(kvp[0].trim(), kvp[1].trim());
                    }
                }
            }
            return properties;

        } catch (FileNotFoundException e) {
            e.getMessage();
            e.printStackTrace();
           throw e;
        }
    }

}


