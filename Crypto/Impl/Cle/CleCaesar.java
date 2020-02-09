package Crypto.Impl.Cle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Crypto.Abstraction.Cle;
import static Crypto.Abstraction.Constantes.CIPHER;
import java.security.SecureRandom;

/**
 *
 * @author Julie
 */
public class CleCaesar implements Cle{
    private int decalage;

    public CleCaesar()
    {
        SecureRandom sr = new SecureRandom();
        decalage = sr.nextInt(25);
    }
    public CleCaesar(int decalage){
        if(decalage >= 0 && decalage < 26) this.decalage = decalage;
        else System.err.print("CleCaesar - Décalage en dehors des limites 0 -> 25");
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
