package com.github.Franfuu.view;

import com.github.Franfuu.App;
import javafx.fxml.FXML;

public class ElegirRolController extends Controller {

    // Método llamado cuando se hace clic en el botón "Peluquero"
    @FXML
    private void onPeluquero() {
        try {
            // Cambiar la escena a la escena "INIPELU"
            App.currentController.changeScene(Scenes.INIPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método llamado cuando se hace clic en el botón "Cliente"
    @FXML
    private void onCliente() {
        try {
            // Cambiar la escena a la escena "INICLI"
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método llamado cuando se abre la vista
    @Override
    public void onOpen(Object input) throws Exception {
        // No se necesitan acciones específicas al abrir esta vista
    }

    // Método llamado cuando se cierra la vista
    @Override
    public void onClose(Object output) {
        // No se necesitan acciones específicas al cerrar esta vista
    }
}