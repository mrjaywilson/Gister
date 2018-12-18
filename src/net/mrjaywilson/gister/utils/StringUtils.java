package net.mrjaywilson.gister.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 
 * Utility class to handle string manipulation.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class StringUtils {

	/**
	 * 
	 * Clean the extra stuff from the code.
	 * 
	 * @param output
	 * @return
	 */
	public static String cleanText(String output) {
		output = output.replace("\\n", "");
		output = output.replace("  ", "");
		output = output.replace("\\", "");
		
		return output;
	}
	
	/**
	 * 
	 * Split the string between the link code and the html code.
	 * 
	 * @param outputText
	 * @return
	 */
	public static String[] getSplitCode(String outputText) {
		
		String[] output = {"", ""};

		outputText = outputText.replace("document.write(\'", "");
		outputText = outputText.replace("\')", "");

		for (int i = 0; i < outputText.length(); i++) {
			if (outputText.charAt(i) != '>') {
				output[0] = output[0] + outputText.charAt(i);
			} else {
				output[0] = output[0] + ">";
				
				output[1] = outputText.substring(i + 1);
				
				break;
			}
		}
		
		return output;
	}

	/**
	 * 
	 * Put the string content into the clipboard.
	 * 
	 * @param o
	 * @param content
	 * @return
	 */
	public static Boolean copyContent(String content) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent clipboardContent = new ClipboardContent();
		
		clipboardContent.putString(content);
		clipboard.setContent(clipboardContent);
		
		if (clipboard.getString().equals(content)) {
			return true;
		} else {
			return false;
		}
		
	}

}
