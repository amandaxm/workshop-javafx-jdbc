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
import model.entities.Department;

public class DepartmentFormController implements Initializable {

	private Department entity;
	
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
	
	public void setDepartment(Department entity) {
		//instancia do departamento
		this.entity = entity;
	}
	
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
	
	public void UpdateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entidade é null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(String.valueOf(entity.getName()));
		
	}
}
