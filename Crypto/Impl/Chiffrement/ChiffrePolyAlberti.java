/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Chiffrement;

import Crypto.Abstraction.Chiffrement;
import Crypto.Abstraction.Cle;
import Crypto.Impl.Cle.ClePolyAlberti;

/**
 *
 * @author renardN
 */
public class ChiffrePolyAlberti implements Chiffrement {
    private ClePolyAlberti key;
    @Override
    public void init(Cle k) {
        if(k instanceof ClePolyAlberti == true)
            key = (ClePolyAlberti)k; 
        else
            System.err.println("Erreur de type de Cle");
    }

    @Override
    public String crypte(String plainText) {
        // Tableau de char pour le plainText
        char tabPlainText[] = new char[plainText.length()];
        plainText.getChars(0, plainText.length(), tabPlainText, 0);
        // Tableau de char contenant les caractères de la clé 
        char tabCrypteCle[] = new char[key.getLongueur()];
        key.getCalage().getChars(0, key.getLongueur(), tabCrypteCle, 0);
        String crypteText = new String();
        // Initialisation du décalage suivant le premier caractère de la clé (on cale le caractère sur A(65))
        int asciiCalage = (int) tabCrypteCle[0] - 65;
        for (int i = 0; i < plainText.length(); i++)
        {
            //Modification du calage suivant la fréquence ==> Si f == 1 => on change à chaque fois
            if (i%key.getFrequence()+1 == key.getFrequence())
            {
                asciiCalage = (int) tabCrypteCle[i%key.getLongueur()] - 65;
            }
            // Ajout du calage au plainText
            int asciiCrypteText = asciiCalage + (int)tabPlainText[i];
            // Si la valeur dépasse 90 continue à partir de A (65) donc on fait (-90+65 == -25)
            if (asciiCrypteText > 90)
                asciiCrypteText = asciiCrypteText - 25;
            crypteText += Character.toString((char) asciiCrypteText);
        }
        return crypteText;
    }

    @Override
    public String decrypte(String cipherText) {
        // Tableau de char pour le cipherText
        char[] tabCipherText = new char[cipherText.length()];
        cipherText.getChars(0, cipherText.length(), tabCipherText, 0);
        // Tableau de char contenant les caractères de la clé 
        char tabCrypteCle[] = new char[key.getLongueur()];
        key.getCalage().getChars(0, key.getLongueur(), tabCrypteCle, 0);
        String plainText = new String();
        // Initialisation du décalage suivant le premier caractère de la clé (on cale le caractère sur A(65))
        int asciiCalage = (int) tabCrypteCle[0] - 65;
        for (int i = 0; i < cipherText.length(); i++)
        {
            //Modification du calage suivant la fréquence ==> Si f == 1 => on change à chaque fois
            if (i%key.getFrequence()+1 == key.getFrequence())
            {
                asciiCalage = (int) tabCrypteCle[i%key.getLongueur()] - 65;
            }
            // Soustraction du calage au cipherText pour trouver le caractère en clair
            int asciiPlainText =  (int)tabCipherText[i] - asciiCalage;
            // Si la valeur se trouve en dessous de 65,on continue à partir de Z (90) donc on fait (90-65 == 25)
            if (asciiPlainText < 65)
                asciiPlainText = asciiPlainText + 25;
            plainText += Character.toString((char) asciiPlainText);
        }
        return plainText;
    }

    @Override
    public String getProvider() {
        return "LeonBattistaAlberti";
    }

    @Override
    public String getName() {
        return "ChiffrePolyAlberti";
    }
    
    
}
