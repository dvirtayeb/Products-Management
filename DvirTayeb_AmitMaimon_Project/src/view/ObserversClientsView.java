package view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ObserversClientsView {

	private VBox vbTitle;
	private VBox vbList;
	private BorderPane bp;
	ArrayList<Text> clients;

	public ObserversClientsView(Stage ShowStage) {
		String background = "-fx-background-color: #FFFFFF";
		Text headLine = new Text("Clients Who are Intrested In Our Discounts:");
		headLine.setFont(new Font(20));
		headLine.setFill(Color.DARKBLUE);
		clients=new ArrayList<Text>();
		

		vbTitle = new VBox();
		vbTitle.getChildren().add(headLine);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		
		vbList=new VBox();
		vbList.setAlignment(Pos.CENTER);
		
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbList);
		
		Scene scene = new Scene(bp, 700, 700);
		ShowStage.setTitle("Show Profit");
		ShowStage.setScene(scene);
		ShowStage.show();
		}
	
	public void addNameToText(String name) {
		Text text=new Text(name);
		text.setFont(new Font(20));
		text.setFill(Color.DARKGREEN);
		clients.add(text);
	}
	public void addTextToVbox(int index) {
		vbList.getChildren().add(clients.get(index));
	}
}
