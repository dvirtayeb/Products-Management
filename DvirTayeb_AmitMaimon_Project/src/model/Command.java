package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public interface Command {
	public void execute(String nameButton, Button button, EventHandler<ActionEvent> event) throws Exception;
	public void execute(String nameButton, ChoiceBox<String> choiceBox, EventHandler<ActionEvent> event) throws Exception;
}
