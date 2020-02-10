package Crypto.Impl.Cle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//SALUT

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
    public CleCaesar(int d){
        setDecalage(d);
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
    
    public final void setDecalage(int d)
    {
        if(d >= 0 && d < 26)
            decalage = d;
        else
            throw new IllegalArgumentException("Paramètre d="+d+" hors des bornes [0,26[");
    }
    
}
