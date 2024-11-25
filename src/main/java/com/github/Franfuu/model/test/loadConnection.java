package com.github.Franfuu.model.test;

import com.github.Franfuu.model.connection.ConnectionProperties;
import com.github.Franfuu.utils.XMLManager;

public class loadConnection {
    public static void main(String[] args) {
        ConnectionProperties c = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(c);
    }
}
