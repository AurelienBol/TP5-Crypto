/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Abstraction;

import Abstraction.Service;

/**
 *
 * @author Thomas
 */
public interface Chiffrement extends Service
{
    void init (Cle k);
    String crypte (String plainText);
    String decrypte (String cipherText);
    String getProvider();
}
