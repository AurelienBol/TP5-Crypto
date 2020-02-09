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
    private int type = BOTH;
    
    private static final int[] PREMIERS = {3,4,5,6,7,8,9,10,11,12,14,15,16,17,18,19,20,21,22,23,24,25}; //Nombre premiers avec 26
    
    public final void setMatrice(int[][] matrice){
        if(!isInvertible(matrice))return;
        this.matrice = matrice;
    }
    
    public int[][] getMatrice(){
        return matrice;
    }
    
    private int[][] generateMatrice(){
        int generated[][] = new int[2][2];
        SecureRandom sr = new SecureRandom();
        generated[0][0] = PREMIERS[sr.nextInt(PREMIERS.length-1)];
        do{
            generated[0][1] = sr.nextInt(25);
            generated[1][0] = sr.nextInt(25);
            generated[1][1] = sr.nextInt(25);
        }while(!isInvertible(generated));
        
        return generated;
    }
    
    public MatrixKey(){
        setMatrice(generateMatrice());
        setType(BOTH);
    }
    
    public MatrixKey(int[][] key){
        if(!isInvertible(key))return;
        setMatrice(key);
        setType(BOTH);
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
    
    private boolean isInvertible(int[][] matrice){
        if(matrice==null) return false;
        if(matrice.length != 2 || matrice[0].length!=2) return false;
        int a = matrice[0][0];
        int b = matrice[0][1];
        int c = matrice[1][0];
        int d = matrice [1][1];
        if((a*d-b*c)%26==0 ||(a*d-b*c)%13==0 || (a*d-b*c)%2 == 0) return false;
        else return true;
    }
}
