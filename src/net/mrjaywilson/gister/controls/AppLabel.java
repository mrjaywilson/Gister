package net.mrjaywilson.gister.controls;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * Customized Label for UI labels.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class AppLabel extends Label {
	public AppLabel(String text, Color color) {
		setFont(new Font("Century Gothic", 24));
		setText(text);
		setTextFill(color);
	}
}
