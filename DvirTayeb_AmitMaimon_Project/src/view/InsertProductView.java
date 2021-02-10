package view;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

public class InsertProductView {
	
	private ArrayList<Text> textList;
	private ArrayList<TextField> textFieldList;
	private ArrayList<HBox> hBoxList;
	private ArrayList<Tooltip> toolTipList;
	private VBox vbTitle;
	private VBox vb;
	private BorderPane bp;
	private int indexCounter;
	private CheckBox saleUpdate;
	private Button save;
	public InsertProductView(Stage insertStage) {
		textList = new ArrayList<Text>();
		textFieldList = new ArrayList<TextField>();
		hBoxList = new ArrayList<HBox>();
		toolTipList = new ArrayList<Tooltip>();
		String background = "-fx-background-color: #FFFFFF";
		Text InsertProduct = new Text("Insert Products:");
		InsertProduct.setFont(new Font(20));
		InsertProduct.setFill(Color.DARKBLUE);
		
		createBox("Name of product", 50, "example:'bamba'");
		createBox("Cost Price To manager", 22, "example:'100'");
		createBox("Cost Price To Client", 38, "example:'150'");
		createBox("BarCode of Product", 36, "example:'123'");
		createBox("Client name", 78, "example:'Dvir'");
		createBox("Client phone Number", 28, "example:'052332111'");
		for (int i = 0; i < toolTipList.size(); i++) {
			textFieldList.get(i).setTooltip(toolTipList.get(i));
		}
		
		saleUpdate = new CheckBox("send Sale-Update?");
		HBox saleBox = new HBox(saleUpdate);
		saleBox.setAlignment(Pos.CENTER);
	
		vb = new VBox();
		for (int i = 0; i < hBoxList.size(); i++) {
			vb.getChildren().add(hBoxList.get(i));
		}
		vb.getChildren().add(saleBox);
		save =new Button("Save Product");
		HBox hbSave = new HBox(save);
		hbSave.setAlignment(Pos.CENTER);
		HBox.setMargin(save, new Insets(50, 50, 20, 20));
		vb.getChildren().add(hbSave);
		vbTitle = new VBox();
		vbTitle.getChildren().add(InsertProduct);
		vbTitle.setAlignment(Pos.TOP_CENTER);

		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vb);
		
		// New Scene
		Scene scene = new Scene(bp, 500, 300);
		insertStage.setTitle("Insert Page");
		insertStage.setScene(scene);
		insertStage.show();
		
	}
	
	public Button getSave() {
		return save;
	}

	public boolean getSaleUpdate() {
		boolean sale = saleUpdate.isSelected();
		return sale;
	}
	
	public void clearSaleUpdate() {
		this.saleUpdate.setSelected(false);
	}

	public void clearTextFieldList() {
		for (int i = 0; i < textFieldList.size(); i++) {
			textFieldList.get(i).clear();
		}
	}

	public ArrayList<Text> getTextList() {
		return textList;
	}

	public ArrayList<TextField> getTextFieldList() {
		return textFieldList;
	}

	public ArrayList<HBox> gethBoxList() {
		return hBoxList;
	}
	
	public void addEventToSubmit(EventHandler<ActionEvent> event, Button bot) {
		bot.setOnAction(event);
	}

	public void createBox(String value, int length, String example) {
		Text text = new Text(value+":  ");
		TextField tf = new TextField();
		toolTipList.add(new Tooltip(example));
		textList.add(text);
		textFieldList.add(tf);
		hBoxList.add(new HBox());
		HBox.setMargin(textFieldList.get(indexCounter), new Insets(0, 0, 0, length));
		hBoxList.get(indexCounter).setAlignment(Pos.CENTER);
		hBoxList.get(indexCounter).getChildren().addAll(textList.get(indexCounter), textFieldList.get(indexCounter));
		indexCounter++;
	}
}
