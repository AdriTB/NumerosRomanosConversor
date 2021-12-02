package com.example.numerosromanosconversor

import android.util.Log
import java.util.*

class Calculo() {
    companion object {
        val TAG = "calculo"

        val numerosRomanos: Map<Char, Int> = mapOf(
            'i' to 1,
            'v' to 5,
            'x' to 10,
            'l' to 50,
            'c' to 100,
            'd' to 500,
            'm' to 1000
        )


        fun convertirDecARom(numDec: Int): String {
            if (numDec == 0) return "N"

            if (numDec > 4999) return "Escribe un número inferior a 5000"

            if (numDec < 0) return "No puede ser negativo"

            var numero_romano = ""
            var resto = 0
            val millares: Int = numDec / 1000
            resto = numDec - (millares * 1000)
            val centenas: Int = resto / 100
            resto = resto - (centenas * 100)
            val decenas: Int = resto / 10
            resto = resto - (decenas * 10)
            val unidades: Int = resto

            //Millares
            val millaresRom: String = 'M'.toString().repeat(millares)
            var unidadesRom: String = ""

            //Centenas
            var centenasRom = ""
            if (centenas == 9) centenasRom = "CM"
            else if (centenas == 4) centenasRom = "CD"
            else centenasRom =
                'D'.toString().repeat(centenas / 5) + 'C'.toString().repeat(centenas % 5)

            //Decenas
            var decenasRom = ""
            if (decenas == 9) decenasRom = "XC"
            else if (decenas == 4) decenasRom = "XL"
            else decenasRom =
                'L'.toString().repeat(decenas / 5) + 'X'.toString().repeat(decenas % 5)

            //Unidades
            if (unidades == 9) unidadesRom = "IX"
            else if (unidades == 4) unidadesRom = "IV"
            else unidadesRom =
                'V'.toString().repeat(unidades / 5) + 'I'.toString().repeat(unidades % 5)

            numero_romano = millaresRom + centenasRom + decenasRom + unidadesRom
            return numero_romano
        }

        fun convertirRomDec(numeroR: String): Int? {
            val numeroR = numeroR.lowercase()
            var numeroD = 0

            //El número es cero
            if (numeroR.equals("n")) return 0

            //Alguna letra no existe en romanos
            for (letra in numeroR) {
                numerosRomanos.get(letra) ?: return null
            }

            //Calculo númerico
            var resultado: Int = 0
            for (i in 0..numeroR.length - 1) {
                var vSiguiente = 0
                var vActual = 0
                vActual = numerosRomanos[numeroR[i]]!!
                if (i < numeroR.length - 1) vSiguiente = numerosRomanos[numeroR[i + 1]]!!
                //Suma
                if (vSiguiente > vActual) {
                    resultado -= vActual
                } else {
                    resultado += vActual
                }
            }
            //Checkea con la función contraria de la clase
            val checkResultado = convertirDecARom(resultado)
            //Si da el mismo resultado esta correcto
            Log.d(
                TAG,
                "El resultado obtenido es $resultado y en números romanos debería ser $checkResultado"
            )
            if (checkResultado.lowercase().equals(numeroR)) {
                return resultado
            } else {
                return null
            }


        }


    }


}


