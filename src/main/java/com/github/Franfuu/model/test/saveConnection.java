package com.github.Franfuu.model.test;

import com.github.Franfuu.model.connection.ConnectionProperties;
import com.github.Franfuu.utils.XMLManager;

public class saveConnection {
    public static void main(String[] args) {
        ConnectionProperties c = new ConnectionProperties("localhost","3306","style_md","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
