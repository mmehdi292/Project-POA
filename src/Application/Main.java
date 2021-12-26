package Application;

import AppCore.Container;
import AppCore.MainContainner;
import Screen.CreateAgentsScreen;
import Screen.MainScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static MainContainner mainContainner;
	public static Container buyerContainer;
	public static Container sellerContainer;
	public static Container clientContainer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.mainContainner = new MainContainner();
		this.sellerContainer = new Container();
		this.buyerContainer = new Container();
		this.clientContainer = new Container();
		new MainScreen();
		
	}

}
