package commands;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import view.StoreView;

public class ShowProfitsCommand implements Command {

	StoreView storeView;
	String name;

	public ShowProfitsCommand(String name, StoreView storeView) {
		this.name = name;
		this.storeView = storeView;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void execute(String nameCommand, Button button, EventHandler<ActionEvent> event) throws Exception {
		storeView.addEventToSubmit(event, button);
	}


	@Override
	public void execute(String nameCommand, ChoiceBox<String> choiceBox, EventHandler<ActionEvent> event) throws Exception {
		
	}
}

