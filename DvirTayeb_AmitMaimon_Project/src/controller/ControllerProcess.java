package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import commands.ChooseSortCommand;
import commands.DeleteAllProductsCommand;
import commands.DeleteCommand;
import commands.DeleteLastProductCommand;
import commands.InsertCommand;
import commands.ObserverCommand;
import commands.SearchCommand;
import commands.ShowCommand;
import commands.ShowProfitsCommand;
import commands.StoreCommand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.StoreManagement;
import model.TheSender;
import model.Product;
import model.Client;
import model.Barcode;
import view.ProductView;
import view.SearchProductView;
import view.SenderView;
import view.ShowProfitsView;
import view.DeleteProductView;
import view.InsertProductView;
import view.ObserversClientsView;
import view.StoreView;

public class ControllerProcess {
	private StoreManagement storeM;
	private StoreView storeView;
	private ProductView showProducts;

	private InsertProductView insertProductView;
	private DeleteProductView deleteProductView;
	private SearchProductView searchProduct;
	private static Alert insertSuccesAlert;
	private static Alert insertFailedAlert;
	private static Alert filexistsAlert;
	private static Alert sortErrorAlert;
	private static Alert deleteSuccesAlert;
	private static Alert deleteFailedAlert;
	private static Alert nothingToDeleteAlert;
	private static Alert messageSentAlert;

	private InsertCommand insertCommand;
	private ChooseSortCommand sortCommand;
	private ShowCommand showCommand;
	private StoreCommand storeCommand;
	private DeleteCommand deleteCommand;
	private DeleteLastProductCommand deleteLastProductCommand;
	private DeleteAllProductsCommand deleteAllCommand;
	private ShowProfitsCommand showProfitsCommand;
	private SearchCommand searchCommand;
	private ObserverCommand observerCommand;

	private int nameCounter;

	public ControllerProcess(StoreManagement storeManagement, StoreView storeView, Stage stage) throws Exception {
		nameCounter = 0;
		storeM = storeManagement;
		this.storeView = storeView;
		// Alerts:
		sortErrorAlert = new Alert(AlertType.INFORMATION, "Please choose Sort", ButtonType.OK);
		sortErrorAlert.setHeaderText("Cannot open Insert Prodcut!");
		filexistsAlert = new Alert(AlertType.INFORMATION, "the products you can see in the 'Show Products'",
				ButtonType.OK);
		filexistsAlert.setHeaderText("File exists!");
		insertSuccesAlert = new Alert(AlertType.INFORMATION, "The Product Added!", ButtonType.CLOSE);
		insertFailedAlert = new Alert(AlertType.INFORMATION, "The Product Not Added!", ButtonType.CLOSE);
		deleteFailedAlert = new Alert(AlertType.INFORMATION, "Product Hasn't Deleted! Barcode is not in our System!",
				ButtonType.CLOSE);
		deleteSuccesAlert = new Alert(AlertType.INFORMATION, "The Product Deleted!", ButtonType.CLOSE);
		messageSentAlert = new Alert(AlertType.INFORMATION, "Message Sent!", ButtonType.CLOSE);
		nothingToDeleteAlert = new Alert(AlertType.INFORMATION, "Nothing to Delete!", ButtonType.CLOSE);
		// Commands:
		showCommand = new ShowCommand("showCommand", storeView);
		searchCommand = new SearchCommand("searchCommand", storeView);
		insertCommand = new InsertCommand("insertCommand", storeView);
		sortCommand = new ChooseSortCommand("sortCommand", storeView);
		deleteCommand = new DeleteCommand("deleteCommand", storeView);
		deleteLastProductCommand = new DeleteLastProductCommand("deleteLastProductCommand", storeView);
		deleteAllCommand = new DeleteAllProductsCommand("deleteAllCommand", storeView);
		showProfitsCommand = new ShowProfitsCommand("showProfitsCommand", storeView);
		observerCommand = new ObserverCommand("observerCommand", storeView);
		storeCommand = new StoreCommand(showCommand, insertCommand, sortCommand, deleteCommand, deleteAllCommand,
				showProfitsCommand, deleteLastProductCommand, searchCommand, observerCommand);

		// check if file exists:
		if (storeM.isAppendableProductFile() != false) {
			// read from file and save to hashMap
			storeM.initProductsFromFile();
			filexistsAlert.show();
		}
		// Show Products:
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
		storeCommand.execute(showCommand.getName(), storeView.getBtnShow(), eventShowProducts);

		// Search Product and Show:
		EventHandler<ActionEvent> eventSearchProduct = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					searchProduct = new SearchProductView(new Stage());
					searchProduct.getbtnSearch().setOnAction(e -> {
						try {
							String product = storeM.searchProduct(searchProduct.getTf().getText());
							searchProduct.getStrText().setText(product);
							searchProduct.getTf().clear();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		storeCommand.execute(showCommand.getName(), storeView.getBtnShowSpecificProduct(), eventSearchProduct);

		// Insert Product:
		EventHandler<ActionEvent> eventInsertProducts = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (storeM.getSelectedSort() != -1) {
						insertProductView = new InsertProductView(new Stage());
						insertProductView.getSave().setOnAction(e -> {
							try {
								if (saveProductFromPage()) {
									storeView.getBtnDeleteLastProduct().setDisable(false);
									storeM.sort();
									storeM.saveProductsToFile();
									insertSuccesAlert.show();
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
				deleteSuccesAlert.setContentText("Product Deleted!");
				try {
					deleteProductView = new DeleteProductView(new Stage());
					deleteProductView.getDelete().setOnAction(e -> {
						try {
							int check = storeM.removeProductFromFile(deleteProductView.getTf().getText());
							if (check > 0) {
								deleteSuccesAlert.show();
								storeView.getBtnDeleteLastProduct().setDisable(true);
							} else if (check == 0) {
								deleteFailedAlert.show();
							} else
								nothingToDeleteAlert.show();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		};
		storeCommand.execute(deleteCommand.getName(), storeView.getBtnDelete(), eventDeleteProduct);

		EventHandler<ActionEvent> eventDeleteLastProduct = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				deleteSuccesAlert.setContentText("Product Deleted!");
				try {
					int check = storeM.deleteLastInsertion();
					if (check > 0) {
						deleteSuccesAlert.show();
					} else
						nothingToDeleteAlert.show();
					storeView.getBtnDeleteLastProduct().setDisable(true);
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		};

		storeCommand.execute(deleteLastProductCommand.getName(), storeView.getBtnDeleteLastProduct(),
				eventDeleteLastProduct);

		// Delete All Products:
		EventHandler<ActionEvent> eventDeleteAllProduct = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (storeM.removeAllProducts()) {
						deleteSuccesAlert.setContentText("All Products deleted!");
						deleteSuccesAlert.show();
					} else
						nothingToDeleteAlert.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
		storeCommand.execute(deleteAllCommand.getName(), storeView.getBtnDeleteAll(), eventDeleteAllProduct);

		// Selected Sort:
		EventHandler<ActionEvent> eventSort = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					setSort();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		storeCommand.execute(sortCommand.getName(), storeView.getSortChoiceBox(), eventSort);

		// Show Profit:
		EventHandler<ActionEvent> eventShowProfit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String profitText = storeM.showProfits();
				ShowProfitsView spView = new ShowProfitsView(new Stage());
				spView.getStrText().setText(profitText);
			}

		};
		storeCommand.execute(showProfitsCommand.getName(), storeView.getBtnShowProfits(), eventShowProfit);

		EventHandler<ActionEvent> eventSendDiscountMSG = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SenderView sView = new SenderView(new Stage());
				sView.getBtnSend().setOnAction(e -> {
					String msg = sView.getTextField().getText();
					storeM.notifyObservers(msg);
					storeView.getBtnShowIntrestedClients().setDisable(false);
					messageSentAlert.show();
					sView.getStageForClose().close();
				});

			}

		};
		storeCommand.execute(observerCommand.getName(), storeView.getBtnDiscountMessage(), eventSendDiscountMSG);

		EventHandler<ActionEvent> eventShowIntrestedClients = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				nameCounter = 0;
				ArrayList<String> names = TheSender.getClientsNames();
				ObserversClientsView obsView = new ObserversClientsView(new Stage());
				storeView.getBtnShowIntrestedClients().setDisable(true);
				Thread t = new Thread(() -> {
					try {
						for (int i = 0; i < names.size(); i++) {
							Thread.sleep(2000);
							Platform.runLater(() -> {
								obsView.addNameToText(names.get(nameCounter));
								obsView.addTextToVbox(nameCounter);
								nameCounter++;
							});
						}
						Thread.sleep(2000);
						Platform.runLater(() -> {
							obsView.addNameToText("Thats It For Today Folks!");
							obsView.addTextToVbox(nameCounter);
						});
					} catch (InterruptedException e) {
					}
				});
				t.start();
			}
		};
		storeCommand.execute(observerCommand.getName(), storeView.getBtnShowIntrestedClients(),
				eventShowIntrestedClients);

	}

	// ### Functions ###

	public void loadProductsToPage() throws Exception { // show products from Map
		int selected = storeM.getSelectedSort();
		switch (selected) {
		case 0:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByAscending().entrySet())
				addProductToTableView(product);
			break;
		case 1:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByDescending().entrySet())
				addProductToTableView(product);
			break;
		case 2:
			storeM.sort();
			for (Map.Entry<String, Product> product : storeM.getSortedByInserting().entrySet())
				addProductToTableView(product);
			break;

		default:
			for (Map.Entry<String, Product> product : storeM.getProductMap().entrySet())
				addProductToTableView(product);
		}
	}

	public void addProductToTableView(Map.Entry<String, Product> product) {
		showProducts.getTableViewProducts().getItems().add(product.getValue());
		showProducts.getTableViewClients().getItems().add(product.getValue().getClient());
	}

	// save Products from the 'Insert Page' into the hashMap:
	public boolean saveProductFromPage() throws FileNotFoundException, IOException {
		try {
			ArrayList<String> tempValueList = new ArrayList<>();
			ArrayList<Integer> tempPriceList = new ArrayList<>();
			int costPManager = -1;
			int costPClient = -1;
			addTotempArr(tempValueList, tempPriceList, costPManager, costPClient);
			Client client = new Client(tempValueList.get(4), tempValueList.get(5), insertProductView.getSaleUpdate());
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
		ArrayList<TextField> listTF = insertProductView.getTextFieldList();
		for (int i = 0; i < listTF.size(); i++) {
			String barcode = listTF.get(3).getText();
			String strText = insertProductView.getTextList().get(i).getText();
			String strField = listTF.get(i).getText();
			if (barcode == null || barcode.equals("")) {
				throw new Exception("you didn't insert barCode correctly");
			} else if ((strField.equals("")) && (strText.equals("Cost Price To manager:  "))) {
				costPManager = 0;
				tempPrice.add(costPManager);
				tempProduct.add("0");
			} else if ((strField.equals("")) && (strText.equals("Cost Price To Client:  "))) {
				costPClient = 0;
				tempPrice.add(costPClient);
				tempProduct.add("0");
			} else {
				if (strText.equals("Cost Price To manager:  ")) {
					costPManager = Integer.parseInt(strField);
					if (costPManager < 0)
						throw new NumberFormatException();
					tempPrice.add(costPManager);
				} else if (strText.equals("Cost Price To Client:  ")) {
					costPClient = Integer.parseInt(strField);
					if (costPClient < 0)
						throw new NumberFormatException();
					tempPrice.add(costPClient);
				}
				tempProduct.add(strField);

			}
		}
	}

	public void cleanPage() {
		insertProductView.clearTextFieldList();
		insertProductView.clearSaleUpdate();
	}

	public void setSort() throws Exception {
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
		storeM.sort();
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


	public StoreManagement getStoreM() {
		return storeM;
	}

}
