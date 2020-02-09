/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Cle;


import Crypto.Abstraction.Cle;
import static Crypto.Abstraction.Constantes.*;
import java.security.SecureRandom;

/**
 *
 * @author Aurélien Bolkaerts
 */


public class MatrixKey implements Cle{
    private int[][] matrice;
    private int type;
    
    private static final int[] PREMIERS = {3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25}; //Nombre premiers avec 26
    
    public final void setMatrice(int[][] matrice){
        if(matrice==null || matrice.length!=2 || matrice[0].length!=2){
            return;
        }
        this.matrice = matrice;
    }
    
    public int[][] getMatrice(){
        return matrice;
    }
    
    private int[][] generateMatrice(){
        int generated[][] = new int[2][2];
        int a,b,c,d;
        SecureRandom sr = new SecureRandom();
        a = PREMIERS[sr.nextInt(PREMIERS.length-1)];
        
        do{
            b = sr.nextInt(25);
            c = sr.nextInt(25);
            d = sr.nextInt(25);
        }while((a*d-b*c)%26==0 ||(a*d-b*c)%13==0 || (a*d-b*c)%2 == 0);// Il faut que la matrice soit réversible 
        
        generated[0][0]=a;
        generated[0][1]=b;
        generated[1][0]=c;
        generated[1][1]=d;
        
        return generated;
    }
    
    public MatrixKey(){
        setMatrice(generateMatrice());
        setType(CIPHER);
    }
    
    public MatrixKey(int[][] key){
        if(key==null || key.length!=2 || key[0].length!=2){
            return;
        }
        setMatrice(key);
        setType(AUTHENTICATION);
    }
    
    @Override
    public int getLongueur() {
        return 16;
    }

    private void setType(int type){
        this.type=type;
    }
    
    @Override
    public int getType() {
        return type;
    }
    
}
