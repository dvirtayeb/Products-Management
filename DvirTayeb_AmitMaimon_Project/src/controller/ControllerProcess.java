package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Client;
import model.DeleteCommand;
import model.InsertCommand;
import model.Product;
import model.ShowCommand;
import model.StoreCommand;
import model.StoreManagement;
import model.Barcode;
import model.ChooseSortCommand;
import view.ProductView;
import view.DeleteProductView;
import view.InsertProductView;
import view.StoreView;

public class ControllerProcess {
	private StoreManagement storeM;
	private StoreView storeView;
	private ProductView showProducts;

	private InsertProductView insertProductView;
	private DeleteProductView deleteProductView;
	private static Alert insertSuccesAlert;
	private static Alert insertFailedAlert;
	private static Alert filexistsAlert;
	private static Alert sortErrorAlert;
	private static Alert deleteSuccesAlert;
	private static Alert deleteFailedAlert;
	
	private InsertCommand insertCommand;
	private ChooseSortCommand sortCommand;
	private ShowCommand showCommand;
	private StoreCommand storeCommand;
	private DeleteCommand deleteCommand;


	public ControllerProcess(StoreManagement storeManagement, StoreView storeView, Stage stage) throws Exception {
		storeM = storeManagement;
		this.storeView = storeView;
		// Alerts:
		sortErrorAlert = new Alert(AlertType.INFORMATION, "Please choose Sort", ButtonType.OK);
		sortErrorAlert.setHeaderText("Cannot open Insert Prodcut!");
		filexistsAlert = new Alert(AlertType.INFORMATION, "to see them click on 'Show Products'", ButtonType.OK);
		filexistsAlert.setHeaderText("There is Products already in the System");
		insertSuccesAlert = new Alert(AlertType.INFORMATION, "The Product Added!", ButtonType.OK);
		insertFailedAlert = new Alert(AlertType.INFORMATION, "The Product Not Added!", ButtonType.OK);
		deleteSuccesAlert = new Alert(AlertType.INFORMATION, "The Product Not found!", ButtonType.OK);
		deleteFailedAlert = new Alert(AlertType.INFORMATION, "The Product Deleted!", ButtonType.OK);
		// Commands:
		showCommand = new ShowCommand("Show",storeView);
		insertCommand = new InsertCommand("Insert", storeView);
		sortCommand = new ChooseSortCommand("Sort", storeView);
		deleteCommand = new DeleteCommand("Delete", storeView);
		storeCommand = new StoreCommand(showCommand,insertCommand, sortCommand, deleteCommand);

		if (storeM.isAppendableProductFile() != false) {
			// read from file and save to hashMap
			storeM.initProductsFromFile();
			filexistsAlert.show();
		}
		// Show Products: (work only when clicked on page)
		EventHandler<ActionEvent> eventShowProducts = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					showProducts = new ProductView(new Stage());
					loadProductsToPage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		storeCommand.execute(showCommand.getName(),storeView.getBtnShow(), eventShowProducts);

		// Insert Product: (work only when clicked on page)
		EventHandler<ActionEvent> eventInsertProducts = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (storeM.getSelectedSort() != -1) {
						insertProductView = new InsertProductView(new Stage());
						insertProductView.getSave().setOnAction(e -> {
							try {
								if (saveProductFromPage()) {
									//  ---------------> memento undo kapara!.
									storeM.sort();
									storeM.saveProductsToFile();
									insertSuccesAlert.show();
									// clear the page:
									cleanPage();
								} else {
									cleanPage();
									throw new IOException();
								}
							} catch (IOException e1) {
								System.out.println("the function saveProdcutToMap not succes");
							} catch (Exception e1) {
								System.out.println("the user didn't selected Sorting");
								insertFailedAlert.show();
								e1.getMessage();
							}
						});
					} else
						throw new Exception();
				} catch (Exception e1) {
					System.out.println("the user didn't selected Sorting");
					sortErrorAlert.show();
				}
			}
		};
		storeCommand.execute(insertCommand.getName(), storeView.getBtnInsert(), eventInsertProducts);
		
		// Delete Product:
		EventHandler<ActionEvent> eventDeleteProduct = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					deleteProductView = new DeleteProductView(new Stage());
					deleteProductView.getDelete().setOnAction(e ->{
						try {
							if(storeM.removeProductFromFile(deleteProductView.getTf().getText()))
							{
								deleteFailedAlert.show();
							}else {
								deleteSuccesAlert.show();
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
		};
		storeCommand.execute(deleteCommand.getName(), storeView.getBtnDelete(), eventDeleteProduct);
		
		// Selected Sort: (work only when selected)
		EventHandler<ActionEvent> eventSort = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setSort();
			}
		};
		storeCommand.execute(sortCommand.getName(), storeView.getSortChoiceBox(), eventSort);

	}

	public void loadProductsToPage() throws Exception { // show products from Map
		int selected = storeM.getSelectedSort();
		switch (selected) {
		case 0:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByAscending().entrySet()) {
				showProducts.getTableViewProducts().getItems().add(product.getValue());
				showProducts.getTableViewClients().getItems().add(product.getValue().getClient());
			}
			break;
		case 1:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByDescending().entrySet()) {
				showProducts.getTableViewProducts().getItems().add(product.getValue());
				showProducts.getTableViewClients().getItems().add(product.getValue().getClient());
			}
			break;
		case 2:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByInserting().entrySet()) {
				showProducts.getTableViewProducts().getItems().add(product.getValue());
				showProducts.getTableViewClients().getItems().add(product.getValue().getClient());
			}
			break;

		default:
			for (Map.Entry<String, Product> product : storeM.getProductMap().entrySet()) {
				showProducts.getTableViewProducts().getItems().add(product.getValue());
				showProducts.getTableViewClients().getItems().add(product.getValue().getClient());
			}
		}
	}

	// save Products from the 'Insert Page' into the hashMap:
	public boolean saveProductFromPage() throws FileNotFoundException, IOException {
		try {
			ArrayList<String> tempValueList = new ArrayList<>();
			ArrayList<Integer> tempPriceList = new ArrayList<>();
			int costPManager = -1;
			int costPClient = -1;
			addTotempArr(tempValueList, tempPriceList, costPManager, costPClient);
			Client client = new Client(tempValueList.get(4), tempValueList.get(5),
					insertProductView.getSaleUpdate());
			Barcode barcode = new Barcode(tempValueList.get(3));
			Product product = new Product(tempValueList.get(0), tempPriceList.get(0), tempPriceList.get(1), client,
					barcode.toString());
			storeM.addProduct(product);
			return true;
		} catch (NumberFormatException number) {
			System.out.println("you didn't insert correct number");
			insertFailedAlert.setTitle("Wrong Input");
			insertFailedAlert.setContentText("you didn't insert correct number");
			insertFailedAlert.show();
		} catch (IOException e) {
			System.out.println("IOException Error");
			insertFailedAlert.setTitle("Exception");
			insertFailedAlert.setContentText("The product not saved in the file");
			insertFailedAlert.show();
			e.getMessage();
		} catch (Exception e) {
			System.out.println("General Exception");
			insertFailedAlert.setTitle("Wrong Input");
			insertFailedAlert.setContentText("you didn't insert correct");
			insertFailedAlert.show();
			e.getMessage();
		}
		return false;
	}

	// read the values from App and insert to temp Arrays:
	private void addTotempArr(ArrayList<String> tempProduct, ArrayList<Integer> tempPrice, int costPManager,
			int costPClient) throws Exception {
		for (int i = 0; i < insertProductView.getTextFieldList().size(); i++) {
			String barcode = insertProductView.getTextFieldList().get(3).getText();
			String str = insertProductView.getTextList().get(i).getText();
			String strField = insertProductView.getTextFieldList().get(i).getText();
			if (barcode == null || barcode.equals("")) {
				throw new Exception("you didn't insert barCode correctly");
			} else if ((strField.equals("")) && (str.equals("Cost Price To manager:  "))) {
				costPManager = 0;
				tempPrice.add(costPManager);
				tempProduct.add("0");
			} else if ((strField.equals("")) && (str.equals("Cost Price To Client:  "))) {
				costPClient = 0;
				tempPrice.add(costPClient);
				tempProduct.add("0");
			} else {
				if (str.equals("Cost Price To manager:  ")) {
					costPManager = Integer.parseInt(insertProductView.getTextFieldList().get(i).getText());
					if (costPManager < 0)
						throw new NumberFormatException();
					tempPrice.add(costPManager);
				} else if (str.equals("Cost Price To Client:  ")) {
					costPClient = Integer.parseInt(insertProductView.getTextFieldList().get(i).getText());
					if (costPClient < 0)
						throw new NumberFormatException();
					tempPrice.add(costPClient);
				}
				tempProduct.add(insertProductView.getTextFieldList().get(i).getText());

			}
		}
	}

	public void cleanPage() {
		insertProductView.clearTextFieldList();
		insertProductView.clearSaleUpdate();
	}
	
	public void setSort() {
		storeM.setSelectedSort(storeView.getSortChoiceBox().getSelectionModel().getSelectedIndex());
		int selected = storeM.getSelectedSort();
		if (selected == 0) {
			storeM.setSortedByAscending(new TreeMap<String, Product>());
			storeView.getSortChoiceBox().setDisable(true);
		} else if (selected == 1) {
			storeM.setSortedByDescending(new TreeMap<String, Product>(Collections.reverseOrder()));
			storeView.getSortChoiceBox().setDisable(true);
		} else {
			storeM.setSortedByInserting(new LinkedHashMap<String, Product>());
			storeView.getSortChoiceBox().setDisable(true);
		}
	}

	public void setInsertProductView(InsertProductView insertProductView) {
		this.insertProductView = insertProductView;
	}

	public void setShowProducts(ProductView showProducts) {
		this.showProducts = showProducts;
	}

	public InsertProductView getInsertProductView() {
		return insertProductView;
	}

	public Alert getInsertSuccesAlert() {
		return insertSuccesAlert;
	}

	public Alert getInsertFailedAlert() {
		return insertFailedAlert;
	}

	public StoreManagement getStoreM() {
		return storeM;
	}

}
