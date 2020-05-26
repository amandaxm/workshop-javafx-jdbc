package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemVendedor;

	@FXML
	private MenuItem menuItemDepartamento;
	
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML//eventos
	public void onMenuItemVendedorAction() {
		System.out.println("onMenuVendedor");
		
	}
	@FXML//eventos
	public void onMenuItemItemDepartamentoAction() {
		System.out.println("onMenuDepartamento");
		
		
	}
	@FXML//eventos
	public void onMenuItemSobreAction() {
		System.out.println("onMenuSobre");
		
		
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}

}

