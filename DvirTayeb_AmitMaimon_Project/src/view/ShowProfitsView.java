package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowProfitsView {
	
	private VBox vbTitle;
	private VBox vbString;
	private BorderPane bp;
	private Text strText;
	


	public ShowProfitsView(Stage ShowStage) {
		String background = "-fx-background-color: #FFFFFF";
		Text showProfit = new Text("Show Profit:");
		showProfit.setFont(new Font(20));
		showProfit.setFill(Color.DARKBLUE);
		strText=new Text("");
		
		
		vbString = new VBox();
		vbString.getChildren().add(strText);
		vbString.setAlignment(Pos.CENTER);

		
		vbTitle = new VBox();
		vbTitle.getChildren().add(showProfit);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbString);
		
		Scene scene = new Scene(bp, 700, 700);
		ShowStage.setTitle("Show Profit");
		ShowStage.setScene(scene);
		ShowStage.show();
		
	}
	public Text getStrText() {
		return strText;
	}
	
}
