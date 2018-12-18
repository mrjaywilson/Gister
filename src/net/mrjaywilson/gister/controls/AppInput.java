package net.mrjaywilson.gister.controls;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * 
 * Customized Text input for UI input.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class AppInput extends TextField {
	public AppInput(double width, double height) {
		maxWidth(width);
		maxHeight(height);
		
		setFont(new Font("Century Gothic", 24));
	}
}
