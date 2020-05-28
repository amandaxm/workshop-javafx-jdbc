package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;

	@FXML
	private MenuItem menuItemSobre;

	@FXML // eventos
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuVendedor");

	}

	@FXML // eventos
	public void onMenuItemItemDepartamentoAction() {
		//funcao para inicializar o controlador
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller)->{
			
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();	
		
			
			
			
			
		});
	}

	@FXML // eventos
	public void onMenuItemSobreAction() {
		//x que leva em nada
		loadView("/gui/About.fxml", x->{});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	private synchronized <T>void loadView(String absoluteName, Consumer<T> initializingAction) {
		// carregar tela
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			Scene mainScene = Main.getMainScene();
			// get root pega primeiro elemento da view
			// pega referencia para vbox
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			// primeiro filho
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			// limpar todos filho mainView
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			//comando para ativar a função passada por parametro
			T controller =loader.getController();
			//vai chamar o controler
			initializingAction.accept(controller);
		} catch (IOException e) {
			Alerts.showAlert("IoException", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}
	

}
