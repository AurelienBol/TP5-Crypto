/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hill;

import Abstraction.Chiffrement;
import Abstraction.Cle;
import java.security.SecureRandom;

/**
 *
 * @author Aurélien Bolkaerts
 */
public class HillCipher implements Chiffrement {

    private MatrixKey key;
    
    @Override
    public void init(Cle k) {
        if(k instanceof MatrixKey == true)
            key = (MatrixKey)k;
        else
            System.err.println("Erreur de cle");
    }

    @Override
    public String crypte(String plainText) {
        int[][] cutPlainText = cutString(plainText, 2);
        int[][] cutCipherText = new int[cutPlainText.length][cutPlainText[0].length];
        
        //Produit matriciel pour chaque groupe de deux lettres
        for(int i =0;i<cutPlainText.length;i++){
            cutCipherText[i] = produitMatrice(cutPlainText[i],key.getMatrice());
        }
        String cipherText = matriceToString(cutCipherText);
        return cipherText;
    }

    @Override
    public String decrypte(String cipherText) {
        int[][] cutCipherText = cutString(cipherText,2);
        int[][] decryptKey = keyInverse();
        int[][] cutPlainText = new int[cutCipherText.length][cutCipherText[0].length];
        for(int i =0; i<cutCipherText.length;i++){
            cutPlainText[i] = produitMatrice(cutCipherText[i],decryptKey);
        }
        String plainText = matriceToString(cutPlainText);
        return plainText;
    }

    @Override
    public String getProvider() {
        return "LinearAlgebra";
    }

    @Override
    public String getName() {
        return "HillCipher";
    }
    
    
    private int[][] cutString(String text, int size){
        int nbrBloc = text.length()/size;
        if(text.length()%size>0) nbrBloc ++;
        
        //System.out.println("CutString : " + nbrBloc + " x " + size);
        
        int cutString[][] = new int[nbrBloc][size];
        int pos = 0;
        int i,j = 0;
        for(i =0; i<nbrBloc; i++){
            for(j=0; j<size;j++){
                cutString[i][j] = text.charAt(pos)-65;
                pos++;
                if(pos>=text.length()) break;
            }
            if(pos>=text.length()) break;
        }
        
        //On complète le dernier bloc si nécessaire
        SecureRandom sr = new SecureRandom();
        while(pos < text.length()){
            cutString[i][j] = sr.nextInt(25);
            j++;
        }
        return cutString;
    }
    
    private int[] produitMatrice(int[] matriceA, int[][] matriceB){     
        int matriceOut[]=new int[matriceA.length];
        for(int i =0 ; i < matriceB.length;i++){
            for(int j=0;j<matriceA.length;j++){
                matriceOut[i] += matriceA[j] * matriceB[i][j];
            }
            matriceOut[i]%=26;
            //System.out.println(matriceOut[i] + " -> " + (char)(matriceOut[i]+65));
        }
        return matriceOut;
    }

    private void printMatrice(int[][]matrice){
        System.out.println("Matrice " + matrice.length + " x " + matrice[0].length);
        for(int i = 0;i<matrice.length;i++){
            for(int j = 0; j< matrice[0].length;j++)
                System.out.print("["+matrice[i][j]+"]");
            System.out.println();
        }
    }
    
    private String matriceToString(int[][] cutCipherText) {
        StringBuilder sb = new StringBuilder();
        int c = 65;
        for(int i =0; i<cutCipherText.length; i++){
            for(int j = 0;j<cutCipherText[0].length;j++)
                sb.append((char)(cutCipherText[i][j] + c));
        }
        return sb.toString();
    }
    
    private int[][] keyInverse(){
        int matrice[][] = key.getMatrice();
        int matriceInverse[][] = new int[matrice.length][matrice[0].length];
        int det = (matrice[0][0]*matrice[1][1])-(matrice[0][1]*matrice[1][0]);
        
        int invDet = (bruteForceModuloInverse(det,26));
        
        //System.out.println(invDet);
        
        //System.out.println("Calcul de la matrice inverse : ");
        
        /*
        (a b)-1        -1    (d -b)
        (c d)   = (det)    * (-c a)
        */
        
        
        //(det)^-1 * d
        //System.out.println(invDet + " x " + matrice[1][1] + " = " + invDet * matrice[1][1] + " -> " + (invDet * matrice[1][1])%26);
        matriceInverse[0][0] =(invDet * matrice[1][1])%26;
        if(matriceInverse[0][0] < 0 ) matriceInverse[0][0] += 26;
        
        //(det)^-1 * -b
        //System.out.println(invDet + " x -" + matrice[0][1] + " = " + -invDet * matrice[0][1] + " -> " + (-invDet * matrice[0][1])%26);
        matriceInverse[0][1] = (-invDet * matrice[0][1])%26;
        if(matriceInverse[0][1] < 0 ) matriceInverse[0][1] += 26;
        
        //(det)^-1 * -c
        //System.out.println(invDet + " x -" + matrice[1][0] + " = " + -invDet * matrice[1][0] + " -> " + (-invDet * matrice[1][0])%26);
        matriceInverse[1][0] =( -invDet * matrice[1][0])%26;
        if(matriceInverse[1][0] < 0 ) matriceInverse[1][0] += 26;
        
        //(det)^-1 * a
        //System.out.println(invDet + " x " + matrice[0][0] + " = " + invDet * matrice[0][0] + " -> " + (invDet * matrice[0][0])%26);
        matriceInverse[1][1] =(invDet * matrice[0][0])%26;
        if(matriceInverse[1][1] < 0 ) matriceInverse[1][1] += 26;
        
        
        //System.out.println("Clé inverse : " );
        //printMatrice(matriceInverse);
        return matriceInverse;
    }
    
    private int bruteForceModuloInverse(int value, int modulo){
        //System.out.println("Recherche de l'inverse modulo 26 de " + value);
        while(value<0){
            value = 26 + value;
            //System.out.println("New value : " + value);
        }
        int inv;
        for(inv = 1;inv<modulo;inv++){
            if((value * inv)%modulo == 1){
                //System.out.println("Modulo inverse trouvé!");
                break;
            }
        }
        return inv;
    }
}