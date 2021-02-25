package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SenderView {

	private VBox vbTitle;
	private VBox vbSend;
	private BorderPane bp;
	private Button btnSend;
	private TextField tf;
	private Stage stageForClose;
	
	
	public SenderView(Stage ShowStage) {
		String background = "-fx-background-color: #FFFFFF";
		Text sendMessage = new Text("Write A Message:");
		sendMessage.setFont(new Font(20));
		sendMessage.setFill(Color.DARKBLUE);
		HBox hbTitle = new HBox(sendMessage);
		HBox.setMargin(hbTitle, new Insets(40, 0, 0, 0));
		hbTitle.setAlignment(Pos.CENTER);
		
		tf = new TextField();
		HBox hbText = new HBox( tf);
		hbText.setAlignment(Pos.CENTER);
		HBox.setMargin(tf, new Insets(50, 50, 20, 20));

		
		btnSend =new Button("Send Message");
		HBox hbSend = new HBox(btnSend);
		vbSend = new VBox();
		vbTitle = new VBox();
		hbSend.setAlignment(Pos.CENTER);
		HBox.setMargin(btnSend, new Insets(50, 50, 20, 20));
		vbSend.getChildren().addAll(hbText, hbSend);
		vbTitle.getChildren().add(hbTitle);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbSend);
		
		Scene scene = new Scene(bp, 400, 350);
		ShowStage.setTitle("Send A Message To Clients");
		ShowStage.setScene(scene);
		ShowStage.show();
		stageForClose=ShowStage;
		
	}

	
	public Stage getStageForClose() {
		return stageForClose;
	}


	public Button getBtnSend() {
		return btnSend;
	}


	public TextField getTextField() {
		return tf;
	}
	
	
		
}
