/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Provider;

import Abstraction.Service;
import Crypto.Abstraction.CryptoProvider;
import Crypto.Impl.Chiffrement.HillCipher;

/**
 *
 * @author Thomas
 */
public class LinearAlgebra extends CryptoProvider
{
    public LinearAlgebra ()
    {
        nom = "LinearAlgebra";
    }
    
    public static Service newService()
    {
        return new HillCipher();
    }
}
