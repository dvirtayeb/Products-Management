package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;


public class StoreCommand implements Command{
	Command showCommand, insertCommand, sortCommand, deleteCommand, deleteAllCommand;

	public StoreCommand(Command showCommand,Command insertCommand, Command sortCommand, Command deleteCommand, Command deleteAllCommand) throws Exception {
		this.showCommand = showCommand;
		this.insertCommand = insertCommand;
		this.sortCommand = sortCommand;
		this.deleteCommand = deleteCommand;
		this.deleteAllCommand = deleteAllCommand;
	}

	@Override
	public void execute(String nameCommand, Button button, EventHandler<ActionEvent> event) throws Exception {
		if(nameCommand.equals(((ShowCommand) showCommand).getName()))
			showCommand.execute(nameCommand, button, event);
		else if(nameCommand.equals(((InsertCommand) insertCommand).getName()))
			insertCommand.execute(nameCommand, button, event);
		else if(nameCommand.equals(((DeleteCommand) deleteCommand).getName()))
			deleteCommand.execute(nameCommand, button, event);
		else
			deleteAllCommand.execute(nameCommand, button, event);
	}

	@Override
	public void execute(String nameCommand, ChoiceBox<String> choiceBox, EventHandler<ActionEvent> event) throws Exception {
		sortCommand.execute(nameCommand, choiceBox, event);
		
	}
}
