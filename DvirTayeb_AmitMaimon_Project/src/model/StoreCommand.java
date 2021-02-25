package model;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class StoreCommand implements Command {
	Map<String, Command> commandMap;
	//Command showCommand, insertCommand, sortCommand, deleteCommand, deleteAllCommand, showPRofit;

	public StoreCommand(Command showCommand, Command insertCommand, Command sortCommand, Command deleteCommand,
			Command deleteAllCommand,Command showProfitsCommand,Command deleteLastProductCommand) throws Exception {
		commandMap=new HashMap<String, Command>();
		commandMap.put("showCommand", showCommand);
		commandMap.put("insertCommand", insertCommand);
		commandMap.put("sortCommand", sortCommand);
		commandMap.put("deleteCommand", deleteCommand);
		commandMap.put("deleteAllCommand", deleteAllCommand);
		commandMap.put("showProfitsCommand", showProfitsCommand);
		commandMap.put("deleteLastProductCommand", deleteLastProductCommand);
		

	}

	@Override
	public void execute(String nameCommand, Button button, EventHandler<ActionEvent> event) throws Exception {

		commandMap.get(nameCommand).execute(nameCommand, button, event);
	}

	@Override
	public void execute(String nameCommand, ChoiceBox<String> choiceBox, EventHandler<ActionEvent> event)
			throws Exception {
		commandMap.get(nameCommand).execute(nameCommand, choiceBox, event);

	}
}
