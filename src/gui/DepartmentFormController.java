package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;

	private DepartmentService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();	@FXML
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
		// instancia do departamento
		this.entity = entity;
	}

	public void setDepartmentService(DepartmentService service) {
		// instancia do departamento
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {


		dataChangeListeners.add(listener);


	}

	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entidade esta null");

		}
		if (service == null) {
			throw new IllegalStateException("Service esta null");

		}
		try {
			entity = getFormData();
			// salvar no banco de dados
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (ValidationException e) {

			setErrorMessages(e.getErrors());

		} catch (DbException e) {
			Alerts.showAlert("Erro ao salvar obj", null, e.getMessage(), AlertType.ERROR);
		}

	}

	private void notifyDataChangeListeners() {


		for (DataChangeListener listener : dataChangeListeners) {


			listener.onDataChanged();


		}


	}

	private Department getFormData() {
		// pegar dados do forulario
		Department obj = new Department();

		ValidationException exception = new ValidationException("Validation error");

		obj.setId(Utils.tryParseToInt(txtId.getText()));
		if (txtName.getText() == null || txtName.getText().trim().equals("")) {

			exception.addError("name", "Nome nao pode ser vazio");

		}
		obj.setName(txtName.getText());
		// se tiver pelo menos um erro
		if (exception.getErrors().size() > 0) {

			throw exception;

		}

		return obj;
	}

	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();

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
		if (entity == null) {
			throw new IllegalStateException("Entidade é null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());

	}

	private void setErrorMessages(Map<String, String> errors) {

		Set<String> fields = errors.keySet();

		if (fields.contains("name")) {

			lblError.setText(errors.get("name"));

		}

	}

}
