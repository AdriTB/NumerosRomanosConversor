package com.example.numerosromanosconversor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.widget.addTextChangedListener
import com.example.numerosromanosconversor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var resultadoValido: Boolean = false
    private var aRomanos = true

    companion object{
        const val KEY_TEMA = "Theme"
        private const val TEMA_DEF = R.style.Theme_NumerosRomanosConversor
        private const val TEMA_NIGHT = R.style.Theme_NumerosRomanosConversorNight
        private const val TEMA_ROJO = R.style.Theme_NumerosRomanosConversorRojo
        private const val TEMA_OCRE= R.style.Theme_NumerosRomanosConversorOcre
        private const val TEMA_VERDE = R.style.Theme_NumerosRomanosConversorVerde
        private val temas = listOf(TEMA_DEF, TEMA_NIGHT, TEMA_ROJO, TEMA_OCRE, TEMA_VERDE)
        private var indiceTema = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setDefaultNightMode(MODE_NIGHT_NO)

        val prefs = this.getSharedPreferences("com.example.NumerosRomanosConversor", Context.MODE_PRIVATE)
        val temaConfigurado = prefs.getInt(KEY_TEMA, 0)

        setTheme(temaConfigurado)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        convertirARomanos()

        //Listeners
        binding.btSeleccionar.setOnClickListener{
            it.scaleX *= -1
            if(it.scaleX > 0) {
                //Decimales a Romanos
                convertirARomanos()
            }else if(it.scaleX < 0){
                //Romanos a Decimales
                convertirADecimales()
            }
        }

        binding.etNumero.addTextChangedListener {
            //Log.d("Pruebas", "Texto cambiado a ${binding.etNumero.text}")
            var resultado: String?
            resultadoValido = false
            if (aRomanos) { // De arabigos a romanos
                val numero: Int? = binding.etNumero.text.toString().toIntOrNull()
                if(numero != null){
                    //Obtener
                    resultado = Calculo.convertirDecARom(numero)
                    //Comprobar
                    resultado = comprobarNumeroNatural(resultado)
                    //Mostrar
                    binding.tvResultado.text = resultado
                }else{
                    binding.tvResultado.text = ""
                }

            } else if(!aRomanos) {  // De romanos a arabigos
                val numeroR: String = binding.etNumero.text.toString()
                if(numeroR.isNotEmpty()) {
                    //Obtener
                    resultado = Calculo.convertirRomDec(numeroR)?.toString()
                    //Comprobar
                    resultado = comprobarNumeroRomano(resultado)
                    //Mostrar
                    binding.tvResultado.text = resultado
                }else{
                    binding.tvResultado.text = ""
                }
            }
        }

        binding.tvResultado.setOnClickListener {
            if (binding.tvResultado.text.isNotEmpty() && resultadoValido){
                val clipboard:ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val data = ClipData.newPlainText("resultado", binding.tvResultado.text)
                clipboard.setPrimaryClip(data)
                Toast.makeText(this, R.string.copiado, Toast.LENGTH_LONG).show()
            }
        }

        binding.btTheme.setOnClickListener {
            indiceTema = temas.indexOf(prefs.getInt(KEY_TEMA, 0))   //Obtiene el tema configurado actual
            indiceTema += 1
            if (indiceTema >= temas.size) indiceTema = 0
            val temaNuevo = temas[indiceTema]
            prefs.edit().putInt(KEY_TEMA, temaNuevo).apply()
            recreate()
        }

        binding.btHelp.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle(R.string.titulo_ayuda)
                .setMessage(R.string.mensaje_ayuda)
                .setCancelable(true)
                .create()
            dialog.show()
        }
    }

    private fun convertirARomanos(){
        binding.etNumero.setText("")
        aRomanos = true
        binding.etNumero.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etNumero.setHint(R.string.pide_naturales)
    }
    private fun convertirADecimales(){
        binding.etNumero.setText("")
        aRomanos = false
        binding.etNumero.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        binding.etNumero.setHint(R.string.pide_romanos)
    }

    private fun comprobarNumeroNatural(resultado: String) :String{
        return when (resultado) {
            "error_5000" -> {
                Log.d("errores", "MAYOR DE 5000")
                getString(R.string.no_superior_5000)
            }
            "error_negativo" -> {
                Log.d("errores", "NEGATIVO")
                getString(R.string.no_negativo)
            }
            else -> {
                resultadoValido = true
                resultado
            }
        }
    }

    private fun comprobarNumeroRomano(resultado: String?) :String{
        return if (resultado == null) {
            Log.d("errores", "NO VALIDO")
            getString(R.string.no_valido)
        } else {
            resultadoValido = true
            resultado
        }
    }





}