package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StoreView {
	private BorderPane bp;
	private Button btnShowProducts;
	private Button btnInsertProduct;
	private Button btnDeleteProduct;
	private ChoiceBox<String> sortChoiceBox;
	
	public StoreView(Stage primaryStage) {
		String background = "-fx-background-color: #FFFFFF";
		// Title:
		Text storeTitle = new Text("Products store:   ");
		storeTitle.setFont(new Font(20));
		storeTitle.setFill(Color.DARKBLUE);
		HBox hbTitle = new HBox(storeTitle);
		HBox.setMargin(storeTitle, new Insets(100, 0, 0, 0));
		hbTitle.setAlignment(Pos.TOP_CENTER);
		
		// Buttons:
		btnShowProducts = new Button("Show Products");
		HBox hbShowInfo = new HBox(btnShowProducts);
		HBox.setMargin(btnShowProducts, new Insets(60, 0, 0, 0));
		hbShowInfo.setAlignment(Pos.TOP_CENTER);
		btnInsertProduct = new Button("Insert Products");
		HBox hbInsertInfo = new HBox(btnInsertProduct);
		HBox.setMargin(btnInsertProduct, new Insets(40, 0, 0, 0));
		hbInsertInfo.setAlignment(Pos.TOP_CENTER);
		btnDeleteProduct = new Button("Delete Product");
		HBox hbDeleteProduct = new HBox(btnDeleteProduct);
		HBox.setMargin(btnDeleteProduct, new Insets(40, 0, 0, 0));
		hbDeleteProduct.setAlignment(Pos.TOP_CENTER);
		VBox vbTitle = new VBox();
		vbTitle.getChildren().addAll(hbTitle);
		vbTitle.setAlignment(Pos.TOP_CENTER);
		
		Text chooseSort = new Text("Choose Sort:  ");
        sortChoiceBox = new ChoiceBox<String>();
        sortChoiceBox.getItems().add("Sort by Ascending");
        sortChoiceBox.getItems().add("Sort by Descending");
        sortChoiceBox.getItems().add("Sort by Insert Products");
        sortChoiceBox.setValue(sortChoiceBox.getValue());

        HBox hbSortBox = new HBox(chooseSort, sortChoiceBox);
        hbSortBox.setAlignment(Pos.TOP_CENTER);
        HBox.setMargin(chooseSort, new Insets(40, 0, 0, 0));
        HBox.setMargin(sortChoiceBox, new Insets(40, 0, 0, 0));
    	
		VBox vbCenter = new VBox();
		vbCenter.getChildren().addAll(hbShowInfo, hbInsertInfo, hbSortBox, hbDeleteProduct);
		// BorderPane:
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbCenter);
		Scene scene = new Scene(bp, 700, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void addEventToSubmit(EventHandler<ActionEvent> event, Button bot) {
		bot.setOnAction(event);
	}
	
	public void addEventToSubmit(EventHandler<ActionEvent> event, ChoiceBox<String> sortChoiceBox2) {
		sortChoiceBox2.setOnAction(event);
		
	}
	
	public void execute(EventHandler<ActionEvent> event) {
		
	}
	
	public Button getBtnShow() {
		return btnShowProducts;
	}
	
	public Button getBtnInsert() {
		return btnInsertProduct;
	}

	public ChoiceBox<String> getSortChoiceBox() {
		return sortChoiceBox;
	}

	public Button getBtnDelete() {
		return btnDeleteProduct;
	}
	
	

}
