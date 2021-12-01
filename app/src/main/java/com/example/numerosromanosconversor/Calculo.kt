package com.example.numerosromanosconversor

import android.util.Log
import java.util.*

class Calculo() {
    companion object{
        val numerosRomanos:Map<Char,Int> = mapOf(
            'i' to 1,
            'v' to  5,
            'x' to 10,
            'l' to 50,
            'c' to 100,
            'd' to 500,
            'm' to 1000
        )

        fun convertirDecARom(numDec:Int):String{
            if (numDec == 0){
                return "N"
            }

            if (numDec > 4999){
                return "Escribe un número inferior a 5000"
            }

            if (numDec < 0){
                return "No puede ser negativo"
            }

            var numero_romano = ""
            var resto = 0
            val millares:Int = numDec / 1000
            resto = numDec - (millares * 1000)
            val centenas:Int = resto / 100
            resto = resto - (centenas * 100)
            val decenas:Int = resto / 10
            resto = resto - (decenas * 10)
            val unidades:Int = resto

            var millaresRom:String = 'M'.toString().repeat(millares)
            var unidadesRom:String = ""

            var centenasRom = ""
            if (centenas == 9){
                centenasRom = "CM"
            }else if (centenas == 4){
                centenasRom = "CD"
            }else{
                centenasRom = ('D'.toString().repeat(centenas / 5)) + ('C'.toString().repeat(centenas % 5))
            }
            var decenasRom = ""
            if (decenas == 9){
                decenasRom = "XC"
            }else if (decenas == 4){
                decenasRom = "XL"
            }else{
                decenasRom = ('L'.toString().repeat(decenas / 5)) + ('X'.toString().repeat(decenas % 5))

            }
            if (unidades == 9){
                unidadesRom = "IX"
            }else if (unidades == 4){
                unidadesRom = "IV"
            }else{
                unidadesRom = ('V'.toString().repeat(unidades / 5)) + ('I'.toString().repeat(unidades % 5))
            }

            numero_romano = millaresRom + centenasRom + decenasRom + unidadesRom
            return numero_romano
        }

        fun convertirRomDec(numeroR: String): Int? {
            val numeroR = numeroR.lowercase()
            var numeroD = 0

            if (numeroR.matches(Regex("(v).*\\1"))){
                return null
            }

            if(numeroR == "n"){
                return numeroD
            }

            var cont:Int = 0
            while(cont < numeroR.length){
                var valorActual:Int?
                var valorSiguiente:Int? = 0

                //Si alguna letra no forma parte de los números romanos devuelve nulo
                if(numerosRomanos.containsKey(numeroR[cont])){
                    valorActual = numerosRomanos.get(numeroR[cont])
                }else{
                    return null
                }

                //Si NO es el último valor de la cadena recoge el valor del siguiente número romano
                if(cont < numeroR.length -1 && numerosRomanos.containsKey(numeroR[cont+1])){
                    valorSiguiente = numerosRomanos.get(numeroR[cont + 1])
                }

                //Si el siguiente es mayor resta, si es menor suma el valor al número decimal
                if(valorSiguiente!! > valorActual!!){
                    if(valorSiguiente >= valorActual * 50){
                        return null
                    }
                    numeroD += valorSiguiente - valorActual
                    cont++
                }
                else{
                    numeroD += valorActual
                }
                cont++
            }


            return numeroD

        }

    }





}


