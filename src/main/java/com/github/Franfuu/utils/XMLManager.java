package com.github.Franfuu.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLManager {
    // Método para escribir un objeto en un archivo XML
    public static <T> boolean writeXML(T c, String filename) {
        boolean result = false;
        JAXBContext context;
        try {
            // Crear el contexto JAXB para la clase del objeto
            context = JAXBContext.newInstance(c.getClass());
            // Crear el marshaller para convertir el objeto en XML
            Marshaller m = context.createMarshaller();
            // Configurar el marshaller para que el XML esté formateado y codificado en UTF-8
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // Escribir el objeto en el archivo XML
            m.marshal(c, new File(filename));
            result = true;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Método para leer un objeto desde un archivo XML
    public static <T> T readXML(T c, String filename) {
        T result = c;
        JAXBContext context;
        try {
            // Crear el contexto JAXB para la clase del objeto
            context = JAXBContext.newInstance(c.getClass());
            // Crear el unmarshaller para convertir el XML en un objeto
            Unmarshaller um = context.createUnmarshaller();
            // Leer el objeto desde el archivo XML
            result = (T) um.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}