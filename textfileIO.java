package Utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class textfileIO {
	
	public static void writeTextFile(String filepath, String id) {
		try {
			FileWriter writer = new FileWriter(filepath, true);
			writer.write(id);
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String readTextfile(String filepath) throws IOException {
		  List<String> lines = new ArrayList<>();
        String Id= null;
		  try {
			  BufferedReader reader = new BufferedReader(new FileReader(filepath));
			  String line;
			  while((line = reader.readLine()) != null) {
				  lines.add(line);
			  }
		  } catch (FileNotFoundException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }

		  boolean update = false;
		  for(int i = 0; i < lines.size(); i++) {
			  if(!lines.get(i).contains("Completed")) {
				  Id = lines.get(i);
				  lines.set(i, lines.get(i) +" Completed");
				  update = true;
				  break;
			  }
		  }
		  if(update) {
			  try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
				  for(String line : lines) {
					  writer.write(line);
					  writer.newLine();
				  }
			  }
			  catch (FileNotFoundException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		return Id;	  
	  }
}
