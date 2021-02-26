package view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Client;
import model.Product;

public class ProductView {
	TableView<Product> tableViewProducts;
	TableView<Client> tableViewClients;
	ArrayList<TableColumn<Product, String>> tableColumnsProductsList;
	ArrayList<TableColumn<Client, String>> tableColumnsClientList;
	int productIndexCounter;
	int clientIndexCounter;

	private VBox vbTitle;
	private VBox vb;
	private BorderPane bp;

	public ProductView(Stage ShowStage) {
		tableColumnsProductsList = new ArrayList<>();
		tableColumnsClientList = new ArrayList<>();
		String background = "-fx-background-color: #FFFFFF";
		Text showProduct = new Text("show Products:");
		showProduct.setFont(new Font(20));
		showProduct.setFill(Color.DARKBLUE);

		tableViewProducts = new TableView<>();
		tableViewProducts.setMaxSize(405, 200);
		tableViewClients = new TableView<>(); 
		tableViewClients.setMaxSize(305, 200);
		HBox tables = new HBox(tableViewProducts, tableViewClients);
		tables.setAlignment(Pos.CENTER);

		addColumn("Product Name", "name");
		addColumn("Cost Manager", "costPriceManager");
		addColumn("Cost Client", "costPriceClient");
		addColumn("Client Name", "name");
		addColumn("Client phone", "phoneNumber");
		addColumn("Sale-Update", "saleUpdate");
		addColumn("Barcode", "barCode");

		vb = new VBox();
		vb.getChildren().add(tables);

		vbTitle = new VBox();
		vbTitle.getChildren().add(showProduct);
		vbTitle.setAlignment(Pos.TOP_CENTER);

		bp = new BorderPane();
		bp.setStyle(background);
		bp.setTop(vbTitle);
		bp.setCenter(vb);

		// New Scene
		Scene scene = new Scene(bp, 710, 300);
		ShowStage.setTitle("Show Page");
		ShowStage.setScene(scene);
		ShowStage.show();
	}

	private void addColumn(String value, String type) {
		if ((productIndexCounter < 3 && clientIndexCounter == 0)
				|| (productIndexCounter == 3 && clientIndexCounter == 3)) {
			tableColumnsProductsList.add(new TableColumn<>(value));
			tableColumnsProductsList.get(productIndexCounter)
					.setCellValueFactory(new PropertyValueFactory<Product, String>(type));
			tableColumnsProductsList.get(productIndexCounter).setMinWidth(100);
			tableViewProducts.getColumns().add(tableColumnsProductsList.get(productIndexCounter));
			tableColumnsProductsList.get(productIndexCounter).setSortable(false);
			productIndexCounter++;
		} else {
			tableColumnsClientList.add(new TableColumn<>(value));
			tableColumnsClientList.get(clientIndexCounter)
					.setCellValueFactory(new PropertyValueFactory<Client, String>(type));
			tableColumnsClientList.get(clientIndexCounter).setMinWidth(100);
			tableViewClients.getColumns().add(tableColumnsClientList.get(clientIndexCounter));
			tableColumnsClientList.get(clientIndexCounter).setSortable(false);
			clientIndexCounter++;
		}
		
	}

	public TableView<Product> getTableViewProducts() {
		return tableViewProducts;
	}

	public TableView<Client> getTableViewClients() {
		return tableViewClients;
	}

	public void setTableViewProducts(TableView<Product> tableViewProducts) {
		this.tableViewProducts = tableViewProducts;
	}

}
