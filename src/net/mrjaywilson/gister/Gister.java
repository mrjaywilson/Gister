package net.mrjaywilson.gister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.mrjaywilson.gister.controls.AppButton;
import net.mrjaywilson.gister.controls.AppInput;
import net.mrjaywilson.gister.controls.AppLabel;
import net.mrjaywilson.gister.utils.FileUtils;
import net.mrjaywilson.gister.utils.StringUtils;
import sun.awt.FwDispatcher;

/**
 * 
 * Application to convert GitHub Gists to usable html code.
 * 
 * @author 	Jay Wilson
 * 			mrjaywilson.net
 *
 */
public class Gister extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	Stage mainStage;

	@Override
	public void start(Stage stage) throws Exception {

		mainStage = stage;
		Pane pane = new Pane();
		GridPane grid = new GridPane();
		GridPane outputGrid = new GridPane();

		/*
		 * Basic Settings for the main view
		 */
		double vWidth = 700;
		double vHeight = 700;
		double gridSize = 25;
		
		for (int i = 0; i < (vWidth / gridSize); i++) {
			grid.getColumnConstraints().add(new ColumnConstraints(25));
		}

		for (int i = 0; i < (vHeight / gridSize); i++) {
			grid.getRowConstraints().add(new RowConstraints(25));
		}
		
		for (int i = 0; i < (650 / gridSize); i++) {
			outputGrid.getColumnConstraints().add(new ColumnConstraints(25));
		}

		for (int i = 0; i < (275 / gridSize); i++) {
			outputGrid.getRowConstraints().add(new RowConstraints(25));
		}

		/*
		 * UI Implementation
		 */
		
		// Label for file
		Label browseLabel = new AppLabel("Browse or paste link to file (.js)", Color.BLACK);
		grid.add(browseLabel, 1, 1, 18, 2);
		GridPane.setValignment(browseLabel, VPos.CENTER);
		GridPane.setHalignment(browseLabel, HPos.LEFT);
		
		// Input Text box to receive file
		TextField browseLink = new AppInput(375, 45);
		grid.add(browseLink, 1, 3, 19, 2);
		GridPane.setValignment(browseLink, VPos.CENTER);
		GridPane.setHalignment(browseLink, HPos.CENTER);
		
		// Browse Button to find file
		Button browseForFile = new AppButton("Browse");
		grid.add(browseForFile, 21, 3, 6, 2);
		GridPane.setValignment(browseForFile, VPos.CENTER);
		GridPane.setHalignment(browseForFile, HPos.RIGHT);
		
		// Save Label
		Label saveLabel = new AppLabel("Browse for save location", Color.BLACK);
		grid.add(saveLabel, 1, 6, 18, 2);
		GridPane.setValignment(saveLabel, VPos.CENTER);
		GridPane.setHalignment(saveLabel, HPos.LEFT);
		
		// Save Input Text box (file location)
		TextField saveLink = new AppInput(375, 45);
		grid.add(saveLink, 1, 8, 19, 2);
		GridPane.setValignment(saveLink, VPos.CENTER);
		GridPane.setHalignment(saveLink, HPos.CENTER);
		
		// Browse Button (where to save file)
		Button browseForSave = new AppButton("Browse");
		grid.add(browseForSave, 21, 8, 6, 2);
		GridPane.setValignment(browseForSave, VPos.CENTER);
		GridPane.setHalignment(browseForSave, HPos.RIGHT);
		
		// Convert Button (Converts the file)
		Button convert = new AppButton("Convert!");
		grid.add(convert, 1, 12, 26, 2);
		convert.setPrefWidth(650);
		GridPane.setValignment(convert, VPos.CENTER);
		GridPane.setHalignment(convert, HPos.RIGHT);

		// Output Link label
		Label linkLabel = new AppLabel("CSS Link", Color.BLACK);
		GridPane.setValignment(linkLabel, VPos.CENTER);
		GridPane.setHalignment(linkLabel, HPos.LEFT);
		
		// Output of Link css code
		TextField link = new AppInput(375, 45);
		GridPane.setValignment(link, VPos.CENTER);
		GridPane.setHalignment(link, HPos.CENTER);

		// Output html code label
		Label htmlLabel = new AppLabel("HTML Code", Color.BLACK);
		GridPane.setValignment(htmlLabel, VPos.CENTER);
		GridPane.setHalignment(htmlLabel, HPos.LEFT);
		
		// HTML code for gist
		TextField html = new AppInput(375, 45);
		GridPane.setValignment(html, VPos.CENTER);
		GridPane.setHalignment(html, HPos.CENTER);
		
		// Visual Designs for UI
		pane.getChildren().add(outputGrid);
		
		grid.add(pane, 1, 15, 26, 12);
		outputGrid.add(linkLabel, 1, 0, 23, 2);
		outputGrid.add(link, 1, 3, 24, 2);
		outputGrid.add(htmlLabel, 1, 6, 23, 2);
		outputGrid.add(html, 1, 9, 24, 2);
		
		link.setDisable(true);
		html.setDisable(true);
		
		FileChooser fileChooser = new FileChooser();

		/*
		 * Button Events
		 */
		
		// Browsing for file to convert (js)
		browseForFile.setOnAction(e-> {
			fileChooser.getExtensionFilters().clear();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("JavaScript Files", "*.js"));

			if (fileChooser != null) {
				try {
					File file = fileChooser.showOpenDialog(mainStage);
					browseLink.setText(file.getAbsolutePath());
				} catch (Exception ex) {
					System.err.println(e);
				}
			}
		});
		
		// Browse for save location of converted gist
		browseForSave.setOnAction(e-> {
			fileChooser.getExtensionFilters().clear();
			fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));

			if (fileChooser != null) {
				try {
					File file = fileChooser.showSaveDialog(mainStage);
					saveLink.setText(file.getAbsolutePath());
				} catch (Exception ex) {
					System.err.println(e);
				}
			}
		});
		
		// Convert the selected gist (js) to code for web insertion
		convert.setOnAction(e-> {
			
			String filePath = browseLink.getText();
			String outputText = null;
			
			try {
				
				// Check if the place to save the new code exists, if not, let user know.
				if (saveLink.getText() == "") {
					Alert alert = new Alert(AlertType.ERROR, "Must choose a valid save file location.", ButtonType.OK);
					alert.showAndWait();
				} else {
					
					// Check if the file location is remote.
					if (filePath.substring(0, 4).equals("http")) {
						HttpURLConnection.setFollowRedirects(false);
						
						HttpURLConnection connection = (HttpURLConnection) new URL(filePath).openConnection();
						
						connection.setRequestMethod("HEAD");
						
						// Check if the file exists.  If it does not, warn the user. If it does, move on with conversion.
						if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
							
							// Get the text string from the file.
							outputText = FileUtils.ConvertHttpFile(new URL(filePath));

							// Get the output as an array.
							String[] outputCode = StringUtils.getSplitCode(outputText); 
							
							// Write the conversion to a new file.
							FileUtils.createFile(outputCode[0] + "\n\n" + outputCode[1], new File(saveLink.getText()));
							
							// Output code to UI.
							link.setText(outputCode[0]);
							html.setText(outputCode[1]);
						} else {
							
							// Let user know that the file doesn't exist.
							Alert alert = new Alert(AlertType.ERROR, "Remote file does not exist. Check URL and try again.", ButtonType.OK);
							alert.showAndWait();
						}
					} else {
						File file = new File(browseLink.getText());

						// Check if the file exists, and if it does not, warn the user. Otherwise, perform the conversion.
						if (!file.exists()) {
							Alert alert = new Alert(AlertType.ERROR, "Must choose a valid file to convert.", ButtonType.OK);
							alert.showAndWait();
						} else {
							
							/********************************************************
							 * Testing theory
							 */
							
							File temp = File.createTempFile("buffer", ".tmp");
							
							System.out.println(temp.getAbsolutePath());
							
							FileWriter writer = new FileWriter(temp);
							
							Reader reader = new FileReader(file);
							BufferedReader br = new BufferedReader(reader);
							
							while (br.ready()) {
								System.out.println(br.readLine());
								// writer.write(br.readLine().replaceAll("\\n", ""));
							}
							
							temp.renameTo(new File("test.html"));
							
							/********************************************************/
							
							// Get the text string from the file.
							outputText = FileUtils.convertLocalFile(file);

							// Get the output as an array.
							String[] outputCode = StringUtils.getSplitCode(outputText); 
							
							// Write the conversion to a new file.
							FileUtils.createFile(outputCode[0] + "\n\n" + outputCode[1], new File(saveLink.getText()));

							// Output code to UI
							link.setText(outputCode[0]);
							html.setText(outputCode[1]);
						}
					}
				}
				
				link.setDisable(false);
				html.setDisable(false);
			} catch (Exception ex) {
				System.err.println(ex);
			}
		});
		
		/*
		 *
		 * Input Events
		 * 
		 */
		
		// Click on link textfield and copy text to clipboard.
		link.setOnMouseClicked(e -> {
			link.selectAll();
			
			StringUtils.copyContent(link.getText());
			
			link.setTooltip(new Tooltip("Copied to clipboard!"));
		});
		
		// Click on html textfield and copy text to clipboard.
		html.setOnMouseClicked(e -> {
			html.selectAll();
			
			StringUtils.copyContent(html.getText());
			
			html.setTooltip(new Tooltip("Copied to clipboard!"));
		});
		
		Scene scene = new Scene(grid, vWidth, vHeight);

		// Get the stylesheet for the UI design
		File css = new File(System.getProperty("user.dir") + "\\resources\\css\\style.css");
		String stylesheet = css.toURI().toURL().toString();

		// Style Sheet
		scene.getStylesheets().add(stylesheet);
		grid.getStyleClass().add("pane");
		pane.getStyleClass().add("box");

		mainStage = new Stage();
		mainStage.setTitle("Gister - Jay Wilson");
		mainStage.setScene(scene);
		mainStage.show();
	}
}
