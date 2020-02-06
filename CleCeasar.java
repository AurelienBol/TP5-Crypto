/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptoceasar;

import Crypto.Abstraction.Cle;
import static Crypto.Abstraction.Constantes.CIPHER;
import java.security.SecureRandom;

/**
 *
 * @author Julie
 */
public class CleCeasar implements Cle{
    private int decalage;

    public CleCeasar()
    {
        SecureRandom sr = new SecureRandom();
        decalage = sr.nextInt(25);
    }
    
    @Override
    public int getLongueur() {
        return 4; //4 octets 
    }

    @Override
    public int getType() {
        return CIPHER;
    }
    
    public int getDecalage()
    {
        return decalage;
    }
    
}
