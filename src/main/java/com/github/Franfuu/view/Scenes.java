package com.github.Franfuu.view;

public enum Scenes {
    WELCOME("view/Welcome.fxml"),
     //PAGINA PRINCIPAL
    ROOT("view/layout.fxml"),

    //PAGINA DE REGISTRO DE ADMINISTRADOR
    REGPELU("view/PeluqueroRegistro.fxml"),
    INIPELU("view/PeluqueroSesion.fxml"),
    REGCLI("view/ClienteRegistro.fxml"),
    INICLI("view/ClienteSesion.fxml"),
    CLIENTELISTA("view/ClienteLista.fxml"),
    CLIENTECITA("view/ClienteCita.fxml");



    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
