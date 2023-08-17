package org.example;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class urlreader {
    public static void main(String[] args) {
        try{
            URL myurl = new URL("https://campusvirtual.escuelaing.edu.co:9876/moodle/pluginfile.php/222974/mod_resource/content/0/NamesNetworkClientService.pdf?id=1#acosta");
            System.out.println("Protocol: " + myurl.getProtocol());
            System.out.println("Host: " + myurl.getHost());
            System.out.println("Authority: " + myurl.getAuthority());
            System.out.println("Port: " + myurl.getPort());
            System.out.println("Path: " + myurl.getPath());
            System.out.println("File: " + myurl.getFile());
            System.out.println("Query: " + myurl.getQuery());
            System.out.println("Ref: " + myurl.getRef());
        }catch (MalformedURLException ex){
            Logger.getLogger(urlreader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
