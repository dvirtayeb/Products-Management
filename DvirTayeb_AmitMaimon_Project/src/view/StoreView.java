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
	
	//btns
	private Button btnShowProducts;
	private Button btnInsertProduct;
	private Button btnDeleteProduct;
	private Button btnDeleteAllProduct;
	private Button btnDeleteLastProduct;
	private Button btnShowProfits;
	private Button btnShowSpecificProduct;
	private Button btnDiscountMessage;
	private Button btnShowIntrestedClients;
	
	private ChoiceBox<String> sortChoiceBox;

	public StoreView(Stage primaryStage) {
		String background = "-fx-background-color: #FFFFFF";
		// Title:
		Text storeTitle = new Text("Product store:   ");
		storeTitle.setFont(new Font(20));
		storeTitle.setFill(Color.DARKBLUE);
		HBox hbTitle = new HBox(storeTitle);
		HBox.setMargin(storeTitle, new Insets(40, 0, 0, 0));
		hbTitle.setAlignment(Pos.TOP_CENTER);

		// Buttons:
		btnShowProducts = new Button("Show Products");
		HBox hbShowInfo = new HBox(btnShowProducts);
		HBox.setMargin(btnShowProducts, new Insets(20, 0, 0, 0));
		hbShowInfo.setAlignment(Pos.TOP_CENTER);
		
		btnShowSpecificProduct = new Button("Search Product");
		HBox hbShowSpecificInfo = new HBox(btnShowSpecificProduct);
		HBox.setMargin(btnShowSpecificProduct, new Insets(20, 0, 0, 0));
		hbShowSpecificInfo.setAlignment(Pos.TOP_CENTER);
		
		btnInsertProduct = new Button("Insert Products");
		HBox hbInsertInfo = new HBox(btnInsertProduct);
		HBox.setMargin(btnInsertProduct, new Insets(20, 0, 0, 0));
		hbInsertInfo.setAlignment(Pos.TOP_CENTER);
		
		btnDeleteProduct = new Button("Delete Product");
		HBox hbDeleteProduct = new HBox(btnDeleteProduct);
		HBox.setMargin(btnDeleteProduct, new Insets(20, 0, 0, 0));
		hbDeleteProduct.setAlignment(Pos.TOP_CENTER);

		btnDeleteAllProduct = new Button("Delete All Product");
		HBox hbDeleteAllProduct = new HBox(btnDeleteAllProduct);
		HBox.setMargin(btnDeleteAllProduct, new Insets(20, 0, 0, 0));
		hbDeleteAllProduct.setAlignment(Pos.TOP_CENTER);

		btnShowProfits = new Button("Show Store Profits");
		HBox hbShowProfits = new HBox(btnShowProfits);
		HBox.setMargin(btnShowProfits, new Insets(20, 0, 0, 0));
		hbShowProfits.setAlignment(Pos.TOP_CENTER);

		btnDeleteLastProduct = new Button("Undo Last Product Insertion");
		HBox hbDeleteLastProduct = new HBox(btnDeleteLastProduct);
		HBox.setMargin(btnDeleteLastProduct, new Insets(20, 0, 0, 0));
		hbDeleteLastProduct.setAlignment(Pos.TOP_CENTER);
		btnDeleteLastProduct.setDisable(true);
		
		btnDiscountMessage= new Button("Send Discount Message");
		HBox hbDiscountMessage = new HBox(btnDiscountMessage);
		HBox.setMargin(btnDiscountMessage, new Insets(20, 0, 0, 0));
		hbDiscountMessage.setAlignment(Pos.TOP_CENTER);
		
		btnShowIntrestedClients= new Button("Show Clients Intrested in Discounts");
		HBox hbShowIntrestedClients = new HBox(btnShowIntrestedClients);
		HBox.setMargin(btnShowIntrestedClients, new Insets(20, 0, 0, 0));
		hbShowIntrestedClients.setAlignment(Pos.TOP_CENTER);
		btnShowIntrestedClients.setDisable(true);
		
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
		HBox.setMargin(chooseSort, new Insets(20, 0, 0, 0));
		HBox.setMargin(sortChoiceBox, new Insets(20, 0, 0, 0));

		VBox vbCenter = new VBox();
		vbCenter.getChildren().addAll(hbShowInfo, hbShowSpecificInfo, hbInsertInfo, hbDeleteLastProduct, hbSortBox,
				hbDeleteProduct, hbDeleteAllProduct, hbShowProfits,hbDiscountMessage,hbShowIntrestedClients);
		// BorderPane:
		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vbCenter);
		Scene scene = new Scene(bp, 700, 650);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public Button getBtnDiscountMessage() {
		return btnDiscountMessage;
	}

	public Button getBtnShowIntrestedClients() {
		return btnShowIntrestedClients;
	}

	public Button getBtnDeleteLastProduct() {
		return btnDeleteLastProduct;
	}

	public Button getBtnShowProfits() {
		return btnShowProfits;
	}

	public Button getBtnShowSpecificProduct() {
		return btnShowSpecificProduct;
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

	public Button getBtnDeleteAll() {
		return btnDeleteAllProduct;
	}

}
