package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;
	
	@FXML
	private Label lblError;
	
	@FXML
	private Button btSave;
	
	
	@FXML
	private Button btCancel;
	
	public void onBtSaveAction(ActionEvent event) {
		System.out.println("save");
	}
		
	public void onBtCancelAction(ActionEvent event) {
		System.out.println("cancel");
		
	}
			
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		 initializeNodes();
	}
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtId, 30);

	}
	

}
