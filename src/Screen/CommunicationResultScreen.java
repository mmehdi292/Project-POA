package Screen;

import Configuration.AppTexts;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CommunicationResultScreen {
	public static ListView<String> communicationList = new ListView<String>();

	public CommunicationResultScreen() {
		
		AnchorPane root = new AnchorPane();
		
		communicationList.setMaxSize(400.0, 100.0);
		
		AnchorPane.setTopAnchor(communicationList, 30.0);
		AnchorPane.setBottomAnchor(communicationList, 30.0);
		AnchorPane.setLeftAnchor(communicationList, 30.0);
		AnchorPane.setRightAnchor(communicationList, 30.0);
		
		root.getChildren().addAll(communicationList);
		
		Scene scene = new Scene(root, 400, 400);
		Stage stage = new Stage();
		stage.setTitle(AppTexts.communicationResult);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.show();
		
		
	}
	public static void addNewResulat(String name) {
		communicationList.getItems().add(name);
		communicationList.scrollTo(communicationList.getItems().size() - 1);
		communicationList.layout();
		communicationList.edit(communicationList.getItems().size() - 1);

	}

}
