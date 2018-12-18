package net.mrjaywilson.gister.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * Utility class to handle files.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class FileUtils {
	public static String convertLocalFile(File file) {
		
		String output = "";
		
		try {
			output = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			
			return StringUtils.cleanText(output);
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
		return null;
	}
	
	public static Boolean createFile(String string, File file) {
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(string);
			
			writer.close();
			
			return true;
		} catch (IOException ex) {
			System.err.println(ex);
			return false;
		}
	}
	
	public static String ConvertHttpFile(URL url) {
		
		String output = "";
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
			StringBuilder contents = new StringBuilder();
			
			while (true) {
				final String input = reader.readLine();
				
				if (input == null) {
					break;
				}
				
				contents.append(input);
			}
			
			output = contents.toString();
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
		if (output != null) {
			return StringUtils.cleanText(output);
		} else {
			return null;
		}
	}
}