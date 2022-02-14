package com.example.numerosromanosconversor.model

import android.util.Log
import com.example.numerosromanosconversor.R
import com.example.numerosromanosconversor.view.App

class Calculo(
    var arabigos: Arabigos = Arabigos(),
    var romanos: Romanos = Romanos()
) {
    var resultadoValido = false

    fun arabigosToRomanos(numDec: Int): String {
        var resultado = romanos.convertirARomanos(numDec)
        resultado = checkErrorArabigo(resultado)
        return resultado
    }

    fun romanosToArabigos(numRomano: String): String {
        var resultado = arabigos.convAArabigos(numRomano)
        return checkErrorRomano(numRomano, resultado)
    }

    private fun checkErrorRomano(romanoAConvertir:String, resultadoObtenido:Int?): String{
        //Letra o letras no interpretables en números romanos
        if (resultadoObtenido == null) {
            resultadoValido = false
            return App.getContext().getString(R.string.letra_erronea)
        }

        //Checkea con la función contraria de la clase
        val resultadoEsperado = arabigosToRomanos(resultadoObtenido)
        //Si da el mismo resultado esta correcto

        if(resultadoEsperado.lowercase() == romanoAConvertir.lowercase()) {
            resultadoValido = true
            return resultadoObtenido.toString()
        }
        else {
            resultadoValido = false
            return App.getContext().getString(R.string.no_valido)
        }
    }

    private fun checkErrorArabigo(resultado: String) :String{
        return when (resultado) {
            "error_5000" -> {
                Log.d("errores", "MAYOR DE 5000")
                resultadoValido = false
                App.getContext().getString(R.string.no_superior_5000)
            }
            "error_negativo" -> {
                Log.d("errores", "NEGATIVO")
                resultadoValido = false
                App.getContext().getString(R.string.no_negativo)
            }
            else -> {
                resultadoValido = true
                resultado
            }
        }
    }
}


