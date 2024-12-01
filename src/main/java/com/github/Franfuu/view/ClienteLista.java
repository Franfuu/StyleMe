package com.github.Franfuu.view;

import com.github.Franfuu.App;
import com.github.Franfuu.model.dao.PeluqueroDAO;
import com.github.Franfuu.model.entity.Peluquero;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClienteLista extends Controller implements Initializable {

    @FXML
    private Label clientNameLabel;

    @FXML
    private TableView<Peluquero> peluqueroTable;

    @FXML
    private TableColumn<Peluquero, String> nameColumn;

    @FXML
    private TableColumn<Peluquero, String> specialtyColumn;

    @FXML
    private TableColumn<Peluquero, String> phoneColumn;

    private ObservableList<Peluquero> peluqueroList;

    @Override
    public void onOpen(Object input) throws Exception {
        if (input instanceof String) {
            clientNameLabel.setText((String) input);
        }
        loadPeluqueroData();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("Especialidad"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefono"));

        peluqueroTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && peluqueroTable.getSelectionModel().getSelectedItem() != null) {
                Peluquero selectedPeluquero = peluqueroTable.getSelectionModel().getSelectedItem();
                try {
                    App.currentController.changeScene(Scenes.CLIENTECITA, selectedPeluquero);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadPeluqueroData() {
        PeluqueroDAO peluqueroDAO = new PeluqueroDAO();
        List<Peluquero> peluqueros = peluqueroDAO.findAll();
        peluqueroList = FXCollections.observableArrayList(peluqueros);
        peluqueroTable.setItems(peluqueroList);
    }
}