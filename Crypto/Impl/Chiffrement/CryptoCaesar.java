/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Chiffrement;

import Crypto.Impl.Cle.CleCaesar;
import Crypto.Abstraction.Chiffrement;
import Crypto.Abstraction.Cle;

/**
 *
 * @author Julie
 */
public class CryptoCaesar implements Chiffrement {
    private CleCaesar decalage;
    
    @Override
    public void init(Cle k) {
        if(k instanceof CleCaesar == true)
            decalage = (CleCaesar)k; // faire du dynamic cast
        else
            System.err.println("Erreur de cle");
    }

    @Override
    public String crypte(String plainText) {
        StringBuilder tableauCryptage = new StringBuilder(plainText.length());
        char[] lettresPlainText = plainText.toCharArray();
        for (int i=0;i<plainText.length();i++)
        {
            int resultat = 0;
            if (lettresPlainText[i] >= 'a' && lettresPlainText[i] <= 'z')
            {  
                resultat = lettresPlainText[i] + decalage.getDecalage();
                if(resultat > 122)
                {
                    resultat -= 26;
                }
                tableauCryptage.append((char)resultat);
            } else if (lettresPlainText[i] >= 'A' && lettresPlainText[i] <= 'Z')
            {
                if (lettresPlainText[i] >= 'a' && lettresPlainText[i] <= 'z')
                {  
                    resultat = lettresPlainText[i] + decalage.getDecalage();
                    if(resultat > 122)
                    {
                        resultat -= 26;
                    }
                    tableauCryptage.append((char)resultat);
                }
            } else 
            {
                tableauCryptage.append(lettresPlainText);
            }
        }
        return tableauCryptage.toString();
    }

    @Override
    public String decrypte(String cipherText) {
        StringBuilder tableauCryptage = new StringBuilder(cipherText.length());
        char[] lettresPlainText = cipherText.toCharArray();
        for (int i=0;i<cipherText.length();i++)
        {
            int resultat = 0;
            if (lettresPlainText[i] >= 'a' && lettresPlainText[i] <= 'z')
            {  
                resultat = lettresPlainText[i] - decalage.getDecalage();
                if(resultat < 97)
                {
                    resultat += 26;
                }
                tableauCryptage.append((char)resultat);
            } else if (lettresPlainText[i] >= 'A' && lettresPlainText[i] <= 'Z')
            {
                if (lettresPlainText[i] >= 'a' && lettresPlainText[i] <= 'z')
                {  
                    resultat = lettresPlainText[i] - decalage.getDecalage();
                    if(resultat < 65)
                    {
                        resultat -= 26;
                    }
                    tableauCryptage.append((char)resultat);
                }
            } else 
            {
                tableauCryptage.append(lettresPlainText);
            }
        }
        return tableauCryptage.toString();
    }

    @Override
    public String getProvider() {
        return "TriumviratPrior";
    }

    @Override
    public String getName() {
        return "CryptoCaesar";
    }
    
}
