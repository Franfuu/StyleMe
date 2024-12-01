package com.github.Franfuu.view;

import com.github.Franfuu.App;
import javafx.fxml.Initializable;


public abstract class Controller  {
    App app;

    public void setApp(App app) {
        this.app = app;
    }

    public abstract void onOpen(Object input) throws Exception;

    public abstract void onClose(Object output);

}


