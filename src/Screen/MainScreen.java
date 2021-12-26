package Screen;

import AppCore.BookBuyerAgent;
import AppCore.Container;
import AppCore.DataSync;
import Application.Main;
import Configuration.AppTexts;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Application.Main;

public class MainScreen {

	public Stage stage;
	public ListView<String> buyerAgentList;
	public ListView<String> sellerAgentList;
	public ListView<String> clientAgentList;
	public ListView<String> yallowPageList;

	public MainScreen() {

		this.stage = new Stage();
		AnchorPane root = new AnchorPane();

		Label screenTitle = new Label();
		screenTitle.setText(AppTexts.mainScreenTitle);
		screenTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		Label buyerAgentListTitle = new Label();
		buyerAgentListTitle.setText(AppTexts.buyerAgentListTitle);
		buyerAgentListTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		this.buyerAgentList = new ListView<String>();
		buyerAgentList.setMaxSize(400.0, 100.0);

		ObservableList<String> buyerItems = FXCollections.observableArrayList(Main.buyerContainer.getAgentNames());

		buyerAgentList.setItems(buyerItems);

		Label sellerAgentListTitle = new Label();
		sellerAgentListTitle.setText(AppTexts.sellerAgentListTitle);
		sellerAgentListTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		this.sellerAgentList = new ListView<String>();
		sellerAgentList.setMaxSize(400.0, 100.0);

		ObservableList<String> sellterItems = FXCollections.observableArrayList(Main.sellerContainer.getAgentNames());

		sellerAgentList.setItems(sellterItems);

		Label clientAgentListTitle = new Label();
		clientAgentListTitle.setText(AppTexts.clientAgentListTitle);
		clientAgentListTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		this.clientAgentList = new ListView<String>();
		clientAgentList.setMaxSize(400.0, 100.0);

		ObservableList<String> clientItems = FXCollections.observableArrayList(Main.clientContainer.getAgentNames());

		clientAgentList.setItems(clientItems);

		Label yallowPageListTitle = new Label();
		yallowPageListTitle.setText(AppTexts.yallowPageListTitle);
		yallowPageListTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		this.yallowPageList = new ListView<String>();
		yallowPageList.setMaxSize(400.0, 100.0);

		ObservableList<String> yallowPageItems = FXCollections
				.observableArrayList(Main.clientContainer.getAgentNames());

		yallowPageList.setItems(yallowPageItems);

		Label agentInformationTitle = new Label();
		agentInformationTitle.setText(AppTexts.agentInformationTitle);
		agentInformationTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeTitle));

		// name
		Label agentNameTitle = new Label();
		agentNameTitle.setText(AppTexts.agentNameTitle);
		agentNameTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// name contant
		Label agentName = new Label();
		agentName.setText("--");
		agentName.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// adress
		Label agentAdressTitle = new Label();
		agentAdressTitle.setText(AppTexts.agentAdressTitle);
		agentAdressTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// adress conatant
		Label agentAdress = new Label();
		agentAdress.setText("--");
		agentAdress.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// port
		Label agentPortTitle = new Label();
		agentPortTitle.setText(AppTexts.agentPortTitle);
		agentPortTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// prot contant
		Label agentPort = new Label();
		agentPort.setText("--");
		agentPort.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// negociabliy
		Label agentNegotiableTitle = new Label();
		agentNegotiableTitle.setText(AppTexts.agentNegotiableTitle);
		agentNegotiableTitle.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		// negociabliy conatnt
		Label agentNegotiable = new Label();
		agentNegotiable.setText("--");
		agentNegotiable.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Label informationLabel = new Label();
		informationLabel.setText("");
		informationLabel.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Label informationLabel2 = new Label();
		informationLabel2.setText("");
		informationLabel2.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeSousTitle));

		Button createNewAgentButton = new Button();
		createNewAgentButton.setText(AppTexts.createAgentsScreenTitle);
		createNewAgentButton.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		createNewAgentButton.setPrefWidth(240);
		
		Button communicationResult = new Button();
		communicationResult.setText(AppTexts.communicationResult);
		communicationResult.setFont(new Font(AppTexts.fontName, AppTexts.fontSizeText));
		communicationResult.setPrefWidth(240);

		root.getChildren().addAll(screenTitle, buyerAgentListTitle, buyerAgentList, sellerAgentListTitle,
				sellerAgentList, clientAgentListTitle, clientAgentList, yallowPageListTitle, yallowPageList,
				agentInformationTitle, agentNameTitle, agentName, agentAdressTitle, agentAdress, agentPortTitle,
				agentPort, agentNegotiableTitle, agentNegotiable, informationLabel, informationLabel2,communicationResult,
				createNewAgentButton);

		AnchorPane.setTopAnchor(screenTitle, 15.0);
		AnchorPane.setLeftAnchor(screenTitle, 337.0);

		AnchorPane.setTopAnchor(buyerAgentListTitle, 50.0);
		AnchorPane.setLeftAnchor(buyerAgentListTitle, 30.0);
		AnchorPane.setRightAnchor(buyerAgentListTitle, 430.0);

		AnchorPane.setTopAnchor(buyerAgentList, 80.0);
		AnchorPane.setLeftAnchor(buyerAgentList, 30.0);
		AnchorPane.setRightAnchor(buyerAgentList, 430.0);

		AnchorPane.setTopAnchor(sellerAgentListTitle, 200.0);
		AnchorPane.setLeftAnchor(sellerAgentListTitle, 30.0);
		AnchorPane.setRightAnchor(sellerAgentListTitle, 430.0);

		AnchorPane.setTopAnchor(sellerAgentList, 230.0);
		AnchorPane.setLeftAnchor(sellerAgentList, 30.0);
		AnchorPane.setRightAnchor(sellerAgentList, 430.0);

		AnchorPane.setTopAnchor(clientAgentListTitle, 350.0);
		AnchorPane.setLeftAnchor(clientAgentListTitle, 30.0);
		AnchorPane.setRightAnchor(clientAgentListTitle, 430.0);

		AnchorPane.setTopAnchor(clientAgentList, 380.0);
		AnchorPane.setLeftAnchor(clientAgentList, 30.0);
		AnchorPane.setRightAnchor(clientAgentList, 430.0);

		AnchorPane.setTopAnchor(yallowPageListTitle, 50.0);
		AnchorPane.setLeftAnchor(yallowPageListTitle, 430.0);
		AnchorPane.setRightAnchor(yallowPageListTitle, 30.0);

		AnchorPane.setTopAnchor(yallowPageList, 80.0);
		AnchorPane.setLeftAnchor(yallowPageList, 430.0);
		AnchorPane.setRightAnchor(yallowPageList, 30.0);

		AnchorPane.setTopAnchor(agentInformationTitle, 200.0);
		AnchorPane.setLeftAnchor(agentInformationTitle, 430.0);

		AnchorPane.setTopAnchor(agentNameTitle, 230.0);
		AnchorPane.setLeftAnchor(agentNameTitle, 430.0);

		AnchorPane.setTopAnchor(agentName, 230.0);
		AnchorPane.setLeftAnchor(agentName, 490.0);

		AnchorPane.setTopAnchor(agentAdressTitle, 260.0);
		AnchorPane.setLeftAnchor(agentAdressTitle, 430.0);

		AnchorPane.setTopAnchor(agentAdress, 260.0);
		AnchorPane.setLeftAnchor(agentAdress, 495.0);

		AnchorPane.setTopAnchor(agentPortTitle, 290.0);
		AnchorPane.setLeftAnchor(agentPortTitle, 430.0);

		AnchorPane.setTopAnchor(agentPort, 290.0);
		AnchorPane.setLeftAnchor(agentPort, 475.0);

		AnchorPane.setTopAnchor(agentNegotiableTitle, 320.0);
		AnchorPane.setLeftAnchor(agentNegotiableTitle, 430.0);

		AnchorPane.setTopAnchor(agentNegotiable, 320.0);
		AnchorPane.setLeftAnchor(agentNegotiable, 535.0);

		AnchorPane.setTopAnchor(informationLabel, 350.0);
		AnchorPane.setLeftAnchor(informationLabel, 430.0);

		AnchorPane.setTopAnchor(informationLabel2, 380.0);
		AnchorPane.setLeftAnchor(informationLabel2, 430.0);

		

		AnchorPane.setRightAnchor(createNewAgentButton, 30.0);
		AnchorPane.setBottomAnchor(createNewAgentButton, 30.0);
		
		AnchorPane.setBottomAnchor(communicationResult, 30.0);
		AnchorPane.setLeftAnchor(communicationResult, 30.0);

		createNewAgentButton.setOnAction((ActionEvent e) -> {

			new CreateAgentsScreen(this);
		});
		
		communicationResult.setOnAction((ActionEvent e) -> {
			new CommunicationResultScreen();
		});

		

		buyerAgentList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
					AgentController agent = Main.buyerContainer.getAgentByName(new_val);
					DataSync data = Main.buyerContainer.getAgentInfo(new_val);

					try {
						String[] values = agent.getName().split("@");
						String[] values2 = values[1].split(":");
						String[] values3 = values2[1].split("/");
						agentName.setText(values[0]);
						agentAdress.setText(values2[0]);
						agentPort.setText(values3[0]);
						agentNegotiable.setText(data.agentNego+"%");
						String[] books = data.extra.split(",");
						informationLabel.setText("Book disponible:");
						String booksForLabel = "";
						for(int i=0;i<books.length;i+=2) {
							booksForLabel+=books[i]+" | ";
						}
						informationLabel2.setText(booksForLabel);
					} catch (StaleProxyException | SecurityException e1) {
						e1.printStackTrace();
					}
					clientAgentList.getSelectionModel().clearSelection();
					sellerAgentList.getSelectionModel().clearSelection();
				});
		
		sellerAgentList.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
			AgentController agent = Main.sellerContainer.getAgentByName(new_val);
			DataSync data = Main.sellerContainer.getAgentInfo(new_val);
			try {
				String[] values = agent.getName().split("@");
				String[] values2 = values[1].split(":");
				String[] values3 = values2[1].split("/");
				agentName.setText(values[0]);
				agentAdress.setText(values2[0]);
				agentPort.setText(values3[0]);
				agentNegotiable.setText(data.agentNego+"%");
				informationLabel.setText("");
				informationLabel2.setText("");
			} catch (StaleProxyException | SecurityException e1) {
				e1.printStackTrace();
			}
			buyerAgentList.getSelectionModel().clearSelection();
			clientAgentList.getSelectionModel().clearSelection();
		});
		
		clientAgentList.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
			AgentController agent = Main.clientContainer.getAgentByName(new_val);
			DataSync data = Main.clientContainer.getAgentInfo(new_val);

			try {
				String[] values = agent.getName().split("@");
				String[] values2 = values[1].split(":");
				String[] values3 = values2[1].split("/");
				agentName.setText(values[0]);
				agentAdress.setText(values2[0]);
				agentPort.setText(values3[0]);
				agentNegotiable.setText(data.agentNego + "%");
				String[] args = data.extra.split(","); 
				informationLabel.setText("Budget: "+args[1]);
				informationLabel2.setText("Book want to buy: "+args[0]);
			} catch (StaleProxyException | SecurityException e1) {
				e1.printStackTrace();
			}
			buyerAgentList.getSelectionModel().clearSelection();
			sellerAgentList.getSelectionModel().clearSelection();

		});

		Scene scene = new Scene(root, 800, 600);

		stage.setTitle(AppTexts.mainScreenTitle);
		stage.resizableProperty().setValue(Boolean.FALSE);
		stage.setScene(scene);
		stage.show();
	}

	public void addNewAgent(String name, ListView<String> agentList) {
		agentList.getItems().add(name);
		agentList.scrollTo(agentList.getItems().size() - 1);
		agentList.layout();
		agentList.edit(agentList.getItems().size() - 1);

	}
	
	public void addNewService(String name,String extra) {
		this.yallowPageList.getItems().add(name+"("+extra+")");
		this.yallowPageList.scrollTo(this.yallowPageList.getItems().size() - 1);
		this.yallowPageList.layout();
		this.yallowPageList.edit(this.yallowPageList.getItems().size() - 1);
		
	}
	

}
