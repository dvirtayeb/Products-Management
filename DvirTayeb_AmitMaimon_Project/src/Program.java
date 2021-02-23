import controller.ControllerProcess;
import javafx.application.Application;
import javafx.stage.Stage;
import model.StoreManagement;
import view.StoreView;

public class Program extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Store");
		StoreManagement theStore = new StoreManagement();
		StoreView storeView = new StoreView(primaryStage);
		ControllerProcess theController = new ControllerProcess(theStore, storeView, primaryStage);

	}

}
