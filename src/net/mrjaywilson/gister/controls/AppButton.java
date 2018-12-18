package net.mrjaywilson.gister.controls;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
 * 
 * Customized Button control for UI buttons.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class AppButton extends Button {
	public AppButton(String text) {
		
		setText(text);
		setFont(new Font("Century Gothic", 24));
		
		minHeight(50);
		minWidth(125);
		setPrefHeight(50);
		setPrefWidth(this.getMinWidth());
	}
}
