package com.github.Franfuu.view;

public enum Scenes {
    WELCOME("view/Welcome.fxml"),
     //PAGINA PRINCIPAL
    ROOT("view/layout.fxml"),

    //PAGINA DE REGISTRO DE ADMINISTRADOR
    ELEGIROL("view/ElegirRol.fxml"),
    REGPELU("view/PeluqueroRegistro.fxml"),
    INIPELU("view/PeluqueroSesion.fxml"),
    REGCLI("view/ClienteRegistro.fxml"),
    INICLI("view/ClienteSesion.fxml"),
    CLIENTELISTA("view/ClienteLista.fxml"),
    CLIENTECITA("view/ClienteCita.fxml"),
    CITASERVICIO("view/CitaServicio.fxml"),
    CREARSERVICIO("view/CrearServicio.fxml"),
    PELUCITA("view/PeluqueroCita.fxml"),
    PELUSERVICIO("view/PeluqueroServicio.fxml"),
    EESERVICIO("view/EditarEliminarServicio.fxml"),
    EECITA("view/EditarEliminarCita.fxml"),
    ELIMINARSERVICIO("view/EliminarServicio.fxml"),
    CLIENTEEECITA( "view/ClienteEECitas.fxml"),;


    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
