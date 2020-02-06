/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Abstraction;

import Abstraction.Provider;
import Abstraction.Services;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author Camille
 */
public class CryptoManager extends Services
{
    private static Properties cles = new Properties();

    private CryptoManager(){}

    public static Cle genereCle(String nomProv)
    {
            String nomClasse = cles.getProperty(nomProv);
            if(nomClasse != null)
            {
                try {
                    Class c = Class.forName(nomClasse);
                    return (Cle) c.getDeclaredConstructor().newInstance();
                }
                catch (Exception ex) {
                    System.err.println("Erreur d'instantiation de "+nomClasse+": "+ex.getMessage());
                }
            }

            return null;
    }

    static
    {
        //providers
        try {
            Properties props = new Properties();
            //TO DO: FICHIER PROPERTIES
            FileInputStream fisProv = new FileInputStream("FICHIER");
            props.load(fisProv);

            Enumeration e = props.propertyNames();
            while(e.hasMoreElements())
            {
                String name = (String) e.nextElement();
                String nomClasse = props.getProperty(name);
                if(nomClasse != null)
                {
                    Class c;
                    try {
                        c = Class.forName(nomClasse);
                        Provider prov = (Provider) c.newInstance();
                        providers.put(name,prov);
                    } catch (Exception ex) {
                        System.err.println("Erreur à l'instanciation de "+nomClasse+" : "+ex.getMessage());
                    }
                }
            }
        }
        catch (IOException ex) {
            System.err.println("Erreur d'I/O: "+ex.getMessage());
        }
        
        //cles
        try {
            FileInputStream fisCles = new FileInputStream("FICHIER2");
            cles.load(fisCles);
        }
        catch (IOException ex) {
            System.err.println("Erreur d'I/O: "+ex.getMessage());
        }
    }
}
