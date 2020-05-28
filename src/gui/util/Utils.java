package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	//palco atual, clicar no botao e abrir outra janela
	public static Stage currentStage(ActionEvent event) {
		
		return (Stage)((Node) event.getSource()).getScene().getWindow();
	}
}
