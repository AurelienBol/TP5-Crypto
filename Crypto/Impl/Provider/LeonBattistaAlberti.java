/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto.Impl.Provider;

import Abstraction.Service;
import Crypto.Abstraction.CryptoProvider;
import Crypto.Impl.Chiffrement.ChiffrePolyAlberti;

/**
 *
 * @author Thomas
 */
public class LeonBattistaAlberti extends CryptoProvider
{
    public LeonBattistaAlberti ()
    {
        nom = "LeonBattistaAlberti";
    }
    
    public static Service newService()
    {
        return new ChiffrePolyAlberti();
    }
}
