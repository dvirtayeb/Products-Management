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

public class DeleteProductView {
	
	private VBox vbTitle;
	private VBox vbDelete;
	private BorderPane bp;
	private Button btnDelete;
	TextField tf;
	
	
	public DeleteProductView(Stage ShowStage) {
		String background = "-fx-background-color: #FFFFFF";
		Text deleteProduct = new Text("Delete Product:");
		deleteProduct.setFont(new Font(20));
		deleteProduct.setFill(Color.DARKBLUE);
		
		Text text = new Text("Barcode Name:  ");
		tf = new TextField();
		HBox hbText = new HBox(text, tf);
		hbText.setAlignment(Pos.CENTER);
		HBox.setMargin(tf, new Insets(50, 50, 20, 20));
		HBox.setMargin(text, new Insets(50, 50, 20, 20));
		btnDelete =new Button("Delete Product");
		HBox hbDelete = new HBox(btnDelete);
		vbDelete = new VBox();
		vbTitle = new VBox();
		hbDelete.setAlignment(Pos.CENTER);
		HBox.setMargin(btnDelete, new Insets(50, 50, 20, 20));
		vbDelete.getChildren().addAll(hbText, hbDelete);
		vbTitle.getChildren().add(deleteProduct);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbDelete);
		
		// New Scene
		Scene scene = new Scene(bp, 400, 250);
		ShowStage.setTitle("Show Page");
		ShowStage.setScene(scene);
		ShowStage.show();
	}

	public Button getDelete() {
		return btnDelete;
	}

	public TextField getTf() {
		return tf;
	}

	
}
