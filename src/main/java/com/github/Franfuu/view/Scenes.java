package com.github.Franfuu.view;

public enum Scenes {
    WELCOME("view/Welcome.fxml"),
     //PAGINA PRINCIPAL
    ROOT("view/layout.fxml");

    private final String url;

    Scenes(String url) {
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}
