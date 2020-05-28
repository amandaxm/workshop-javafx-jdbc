package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable, DataChangeListener {

	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;

	@FXML

	private TableColumn<Seller, Seller> tableColumnREMOVE;
	@FXML

	private TableColumn<Seller, Seller> tableColumnEDIT;

	
	
	@FXML
	private Button btNovo;

	private ObservableList<Seller> obsList;

	public void onBtNovoAction(ActionEvent event) {
		// pegar tela atual
		Stage parentStage = Utils.currentStage(event);
		Seller obj = new Seller();
		createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);

	}

	public void setSellerService(SellerService service) {
		this.service = service;

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializableNodes();
	}

	private void initializableNodes() {
		// iniciar colunas
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		// acompanhar janela
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servi�o esta null");
		}
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	public void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {

//		try {
//			// carregar para preencher departamentp
//			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
//			Pane pane = loader.load();
//
//			SellerFormController controller = loader.getController();
//			controller.setSeller(obj);
//			controller.setSellerService(new SellerService());
//			controller.UpdateFormData();
//			controller.subscribeDataChangeListener(this);
//			Stage dialogStage = new Stage();
//			dialogStage.setTitle("Entre com o departamento");
//			dialogStage.setScene(new Scene(pane));
//			dialogStage.setResizable(false);
//			dialogStage.initOwner(parentStage);
//			// tela travada
//			dialogStage.initModality(Modality.WINDOW_MODAL);
//			dialogStage.showAndWait();
//		} catch (IOException e) {
//			Alerts.showAlert("IO Exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
//
//		}
	}

	@Override

	public void onDataChanged() {

		updateTableView();

	}

	private void initEditButtons() {

		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {

			private final Button button = new Button("edit");

			@Override
			protected void updateItem(Seller obj, boolean empty) {

				super.updateItem(obj, empty);

				if (obj == null) {

					setGraphic(null);

					return;
				}

				setGraphic(button);

				button.setOnAction(

						event -> createDialogForm(obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));

			}

		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);

				if (obj == null) {
					setGraphic(null);
					return;
				}

				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Seller obj) {
		Optional<ButtonType>result = Alerts.showConfirmation("Confirmation","Certeza que quer deletar?");
		
		if(result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Servico esta nulo");
			}
			try {
				service.remove(obj);
				updateTableView();	
			
			}catch(DbIntegrityException e) {
				
			Alerts.showAlert("Erro ao deletar", null, e.getMessage(), AlertType.ERROR);
			}
			}
	}
}