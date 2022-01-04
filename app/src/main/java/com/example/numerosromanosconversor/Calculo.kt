package com.example.numerosromanosconversor

import android.util.Log

class Calculo {
    companion object {
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

            if (numDec > 4999) return "error_5000"

            if (numDec < 0) return "error_negativo"

            val numeroRomano: String
            var resto: Int
            val millares: Int = numDec / 1000
            resto = numDec - (millares * 1000)
            val centenas: Int = resto / 100
            resto -= (centenas * 100)
            val decenas: Int = resto / 10
            resto -= (decenas * 10)
            val unidades: Int = resto

            //Millares
            val millaresRom: String = 'M'.toString().repeat(millares)
            val unidadesRom: String

            //Centenas
            val centenasRom: String
            if (centenas == 9) centenasRom = "CM"
            else if (centenas == 4) centenasRom = "CD"
            else centenasRom =
                'D'.toString().repeat(centenas / 5) + 'C'.toString().repeat(centenas % 5)

            //Decenas
            val decenasRom: String
            if (decenas == 9) decenasRom = "XC"
            else if (decenas == 4) decenasRom = "XL"
            else decenasRom =
                'L'.toString().repeat(decenas / 5) + 'X'.toString().repeat(decenas % 5)

            //Unidades
            if (unidades == 9) unidadesRom = "IX"
            else if (unidades == 4) unidadesRom = "IV"
            else unidadesRom =
                'V'.toString().repeat(unidades / 5) + 'I'.toString().repeat(unidades % 5)

            numeroRomano = millaresRom + centenasRom + decenasRom + unidadesRom
            return numeroRomano
        }

        fun convertirRomDec(numeroR: String): Int? {
            val numRomano = numeroR.lowercase()

            //El número es cero
            if (numRomano == "n") return 0

            //Alguna letra no existe en romanos
            for (letra in numRomano) {
                numerosRomanos.get(letra) ?: return null
            }

            //Calculo númerico
            var resultado = 0
            for (i in 0..numRomano.length - 1) {
                var vSiguiente = 0
                var vActual: Int
                vActual = numerosRomanos[numRomano[i]]!!
                if (i < numRomano.length - 1) vSiguiente = numerosRomanos[numRomano[i + 1]]!!
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
                "calculo",
                "El resultado obtenido es $resultado y en números romanos debería ser $checkResultado"
            )
            return if (checkResultado.lowercase().equals(numRomano)) {
                resultado
            } else {
                null
            }
        }
    }
}


