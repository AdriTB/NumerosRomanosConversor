package com.example.numerosromanosconversor

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
                return "0 no es representable en números romanos"
            }

            if (numDec > 4999){
                return "Debe ser inferior al 4999"
            }

            if (numDec < 0){
                return "No puede ser menor que 1"
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

    }





}


