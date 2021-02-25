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

public class SearchProductView {
	private VBox vbTitle;
	private VBox vbSearch;
	private BorderPane bp;
	private Button btnSearch;
	private TextField tf;
	private Text strText;
	
	
	public SearchProductView(Stage ShowStage) {
		String background = "-fx-background-color: #FFFFFF";
		Text searchProduct = new Text("Search Product:");
		searchProduct.setFont(new Font(20));
		searchProduct.setFill(Color.DARKBLUE);
		HBox hbTitle = new HBox(searchProduct);
		HBox.setMargin(hbTitle, new Insets(40, 0, 0, 0));
		hbTitle.setAlignment(Pos.CENTER);
		
		Text text = new Text("Barcode Name:  ");
		tf = new TextField();
		HBox hbText = new HBox(text, tf);
		hbText.setAlignment(Pos.CENTER);
		HBox.setMargin(tf, new Insets(50, 50, 20, 20));
		HBox.setMargin(text, new Insets(50, 50, 20, 20));
		
		strText=new Text("");		
		HBox hbShowProduct= new HBox(strText);
		hbShowProduct.setAlignment(Pos.CENTER);
		
		btnSearch =new Button("Search Product");
		HBox hbSearch = new HBox(btnSearch);
		vbSearch = new VBox();
		vbTitle = new VBox();
		hbSearch.setAlignment(Pos.CENTER);
		HBox.setMargin(btnSearch, new Insets(50, 50, 20, 20));
		vbSearch.getChildren().addAll(hbText, hbShowProduct, hbSearch);
		vbTitle.getChildren().add(hbTitle);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbSearch);
		
		// New Scene
		Scene scene = new Scene(bp, 400, 350);
		ShowStage.setTitle("Search & Show Page");
		ShowStage.setScene(scene);
		ShowStage.show();
	}

	public Button getbtnSearch() {
		return btnSearch;
	}

	public TextField getTf() {
		return tf;
	}

	public Text getStrText() {
		return strText;
	}
}
