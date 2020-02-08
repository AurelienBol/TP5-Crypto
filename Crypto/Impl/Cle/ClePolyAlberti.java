/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Cle;

import Crypto.Abstraction.Cle;
import Crypto.Abstraction.Constantes;
import java.security.SecureRandom;

/**
 *
 * @author renardN
 * 
 * Classe permettant la génération d'une cle Alberti
 */
public class ClePolyAlberti implements Cle {
    /**
     * longueur de la clé.
     */
    private int longueur;
    /**
     * calage de la clé.
     */
    private String calage = new String();
    /**
     * fréquence de changement de calage de la clé.
     */
    private int frequence;
    
    public ClePolyAlberti ()
    {
        longueur = 16; // Initialisation avec une cle de longueur de 16 bytes
        SecureRandom random = new SecureRandom(); // Nombte aléatoire pour le calage
        // boucle permettant de calculer le calage 
        for (int i = 0; i < longueur; i++)
        {
            int randTemp = random.nextInt();
            // si le nombre aléatoire utilisé pour le calage est négatif, on l'inverse!
            if (randTemp < 0)
                randTemp = randTemp *(-1);
            // balisage de 65 à 90 (A à Z en ASCII) de la variable temp utilisée pour déterminé le calage 
            int temp = (randTemp%26)+65;
            calage += Character.toString((char) temp);
        }
        // Boucle pour le calcul de la fréquence (si == 0 => recherche un nombre aléatoire
        do
        {
            random = new SecureRandom();
            int randTemp = random.nextInt();
            frequence = randTemp%8;
            // Si fréquence négative, on l'inverse pour la rendre positive 
            if (frequence < 0)
            {
                frequence = frequence * (-1);
            }
        }
        while (frequence == 0);
    }

    @Override
    public int getLongueur() {
        return longueur;
    }

    @Override
    public int getType() {
        return Constantes.CIPHER;
    }
    
    public String getCalage()
    {
        return calage;
    }
    
    public int getFrequence()
    {
        return frequence;
    }
}
