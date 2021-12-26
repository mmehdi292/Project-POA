package Screen;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Application.Main;
import Configuration.AppTexts;

public class CreateAgentsScreen {

	public Stage stage;

	public CreateAgentsScreen(MainScreen mainScreen) {
		this.stage = new Stage();

		AnchorPane root = new AnchorPane();

		Label screenTitle = new Label();
		screenTitle.setText(AppTexts.createAgentsScreenTitle);
		screenTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		Label agentNamelabel = new Label();
		agentNamelabel.setText(AppTexts.createAgentsScreenNameText);
		agentNamelabel.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Label agentTypeLable = new Label();
		agentTypeLable.setText(AppTexts.createAgentsScreenTypeText);
		agentTypeLable.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Label agentNegocialityLable = new Label();
		agentNegocialityLable.setText(AppTexts.createAgentsScreenNegocialityText);
		agentNegocialityLable.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Label agentExtraLable = new Label();
		agentExtraLable.setText("---");
		agentExtraLable.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		TextField agentNameTextField = new TextField();
		agentNameTextField.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		agentNameTextField.setPrefWidth(240);

		ComboBox<String> agentTypeComboBox = new ComboBox<String>();
		agentTypeComboBox.setStyle("-fx-font-family:" + "'" + AppTexts.fontName + "';");
		agentTypeComboBox.setStyle("-fx-font-size:" + AppTexts.fontSizeText + ";");
		agentTypeComboBox.setPrefWidth(240);
		agentTypeComboBox.getItems().addAll(AppTexts.buyerComboText, AppTexts.sellerComboText,
				AppTexts.clientComboText);

		TextField agentNegocialityTextField = new TextField();
		agentNegocialityTextField.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		agentNegocialityTextField.setPrefWidth(240);

		TextField agentExtraTextField = new TextField();
		agentExtraTextField.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		agentExtraTextField.setPrefWidth(240);

		Button createAgentButton = new Button();
		createAgentButton.setText(AppTexts.createAgentsScreenTitle);
		createAgentButton.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		createAgentButton.setPrefWidth(240);

		

		root.getChildren().addAll(screenTitle, agentNamelabel, agentNameTextField, agentTypeLable, agentTypeComboBox,
				agentNegocialityLable, agentNegocialityTextField, agentExtraLable, agentExtraTextField,
				createAgentButton);

		AnchorPane.setTopAnchor(screenTitle, 15.0);
		AnchorPane.setLeftAnchor(screenTitle, 87.0);

		AnchorPane.setTopAnchor(agentNamelabel, 50.0);
		AnchorPane.setLeftAnchor(agentNamelabel, 30.0);

		AnchorPane.setTopAnchor(agentNameTextField, 80.0);
		AnchorPane.setLeftAnchor(agentNameTextField, 30.0);

		AnchorPane.setTopAnchor(agentTypeLable, 120.0);
		AnchorPane.setLeftAnchor(agentTypeLable, 30.0);

		AnchorPane.setTopAnchor(agentTypeComboBox, 150.0);
		AnchorPane.setLeftAnchor(agentTypeComboBox, 30.0);

		AnchorPane.setTopAnchor(agentNegocialityLable, 190.0);
		AnchorPane.setLeftAnchor(agentNegocialityLable, 30.0);

		AnchorPane.setTopAnchor(agentNegocialityTextField, 220.0);
		AnchorPane.setLeftAnchor(agentNegocialityTextField, 30.0);

		AnchorPane.setTopAnchor(agentExtraLable, 260.0);
		AnchorPane.setLeftAnchor(agentExtraLable, 30.0);

		AnchorPane.setTopAnchor(agentExtraTextField, 290.0);
		AnchorPane.setLeftAnchor(agentExtraTextField, 30.0);

		AnchorPane.setTopAnchor(createAgentButton, 330.0);
		AnchorPane.setLeftAnchor(createAgentButton, 30.0);

		agentTypeComboBox.getSelectionModel().selectedIndexProperty()
				.addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					if (new_val.equals(0)) {
						agentExtraLable.setText("Book list(BookName1,Price1,..)");
					} else if (new_val.equals(1)) {
						agentExtraLable.setText("Let it empty");
					} else {
						agentExtraLable.setText("Buy a Book (BookName,Budget)");
					}

				});

		createAgentButton.setOnAction((ActionEvent e) -> {
			if (agentTypeComboBox.getSelectionModel().isEmpty()
					|| !validate(agentNegocialityTextField.getText().toString())
					|| agentNameTextField.getText().isEmpty()) {

				Alert alert = new Alert(AlertType.ERROR, AppTexts.errorInNameOrTypeOrNegociality, ButtonType.CLOSE);
				alert.showAndWait();

			} else {
				String name = agentNameTextField.getText().toString();
				String type = agentTypeComboBox.getSelectionModel().getSelectedItem().toString();
				int negociality = Integer.parseInt(agentNegocialityTextField.getText().toString());
				String extra = agentExtraTextField.getText().toString();

				if (negociality < 0 || negociality > 100) {
					Alert alert = new Alert(AlertType.ERROR, AppTexts.errorRangeBetween0and100, ButtonType.CLOSE);
					alert.showAndWait();
				} else {

					if (type.equals(AppTexts.buyerComboText)) {
						if (!Main.buyerContainer.addAgent(name, AppTexts.buyerClassName, negociality, extra)) {

							Alert alert = new Alert(AlertType.ERROR, AppTexts.alreadyExist, ButtonType.CLOSE);
							alert.showAndWait();
						} else {
							mainScreen.addNewAgent(name, mainScreen.buyerAgentList);
							String[] extras = extra.split(",");
							String books = "";
							for (int i = 0; i < extras.length; i += 2) {
								if (i == (extras.length - 2))
									books += extras[i];
								else
									books += extras[i] + " , ";
							}
							mainScreen.addNewService(name, books);
							Alert alert = new Alert(AlertType.INFORMATION, AppTexts.hasBeenCreate, ButtonType.CLOSE);
							alert.showAndWait();
							this.stage.close();
						}

					} else if (type.equals(AppTexts.sellerComboText)) {
						if (!Main.sellerContainer.addAgent(name, AppTexts.sellerClassName, negociality, extra)) {
							Alert alert = new Alert(AlertType.ERROR, AppTexts.alreadyExist, ButtonType.CLOSE);
							alert.showAndWait();
						} else {
							mainScreen.addNewAgent(name, mainScreen.sellerAgentList);
							Alert alert = new Alert(AlertType.INFORMATION, AppTexts.hasBeenCreate, ButtonType.CLOSE);
							alert.showAndWait();
							this.stage.close();
						}
					} else {
						if (!Main.clientContainer.addAgent(name, AppTexts.clientClassName, negociality, extra)) {
							Alert alert = new Alert(AlertType.ERROR, AppTexts.alreadyExist, ButtonType.CLOSE);
							alert.showAndWait();
						} else {
							mainScreen.addNewAgent(name, mainScreen.clientAgentList);
							Alert alert = new Alert(AlertType.INFORMATION, AppTexts.hasBeenCreate, ButtonType.CLOSE);
							alert.showAndWait();
							this.stage.close();
						}
					}
				}

			}
		});

		

		Scene scene = new Scene(root, 300, 370);
		this.stage = new Stage();
		stage.setTitle(AppTexts.createAgentsScreenTitle);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.show();
	}

	public boolean validate(String text) {
		return text.matches("[0-9]*");
	}

}
