package com.example.numerosromanosconversor.model

import android.util.Log

class Arabigos {
    fun convAArabigos(numRomano : String) : Int? {
        val numRomano = numRomano.lowercase()

        //El número es cero
        if (numRomano == "n") return 0

        //Alguna letra no existe en romanos
        for (letra in numRomano) {
            Romanos.numerosRomanos.get(letra) ?: return null
        }

        //Calculo númerico
        var resultado = 0
        for (i in 0..numRomano.length - 1) {
            var vSiguiente = 0
            var vActual: Int
            vActual = Romanos.numerosRomanos[numRomano[i]]!!
            if (i < numRomano.length - 1) vSiguiente = Romanos.numerosRomanos[numRomano[i + 1]]!!
            //Suma
            if (vSiguiente > vActual) {
                resultado -= vActual
            } else {
                resultado += vActual
            }
        }
        return resultado
    }
}