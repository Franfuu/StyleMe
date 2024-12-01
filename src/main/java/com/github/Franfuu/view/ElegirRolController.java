package com.github.Franfuu.view;

import com.github.Franfuu.App;
import javafx.fxml.FXML;

public class ElegirRolController extends Controller {

    @FXML
    private void onPeluquero() {
        try {
            App.currentController.changeScene(Scenes.INIPELU, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCliente() {
        try {
            App.currentController.changeScene(Scenes.INICLI, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(Object input) throws Exception {

    }

    @Override
    public void onClose(Object output) {

    }
}