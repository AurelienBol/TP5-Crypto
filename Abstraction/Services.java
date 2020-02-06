/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Abstraction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Camille
 */
public class Services
{
    protected static ConcurrentHashMap<String,Provider> providers = new ConcurrentHashMap<String,Provider>();

    protected Services(){}

    public static void register(Provider p)
    {
            providers.putIfAbsent(p.getNom(),p);
    }

    public static Service newInstance(String nom)   throws IllegalArgumentException
    {
            Provider p = providers.get(nom);

            if(p == null)
                    throw new IllegalArgumentException("Provider " + nom + " non trouvé");

            return p.newService();
    }

    static
    {
        try {
            Properties props = new Properties();
            //TO DO: FICHIER PROPERTIES
            FileInputStream fis = new FileInputStream("FICHIER");
            props.load(fis);

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
    }
}
