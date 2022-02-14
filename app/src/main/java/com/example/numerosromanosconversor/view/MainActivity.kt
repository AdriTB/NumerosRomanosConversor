package com.example.numerosromanosconversor.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.widget.addTextChangedListener
import com.example.numerosromanosconversor.R
import com.example.numerosromanosconversor.databinding.ActivityMainBinding
import com.example.numerosromanosconversor.model.Calculo

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var calculo: Calculo
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

        val prefs = this.getSharedPreferences(
            "com.example.NumerosRomanosConversor",
            Context.MODE_PRIVATE
        )
        val temaConfigurado = prefs.getInt(KEY_TEMA, 0)

        setTheme(temaConfigurado)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calculo = Calculo()

        selConvARomanos()

        //Listeners
        binding.btSeleccionar.setOnClickListener{
            it.scaleX *= -1
            if(it.scaleX > 0) {
                //Decimales a Romanos
                selConvARomanos()
            }else if(it.scaleX < 0){
                //Romanos a Decimales
                selConvAArabigos()
            }
        }

        binding.etNumero.addTextChangedListener {
            var resultado: String?
            if (aRomanos) { // De arabigos a romanos
                val numero: Int? = binding.etNumero.text.toString().toIntOrNull()
                if(numero != null){
                    //Obtener
                    resultado = calculo.arabigosToRomanos(numero)
                    //Mostrar
                    binding.tvResultado.text = resultado
                }else{
                    binding.tvResultado.text = ""
                }

            } else if(!aRomanos) {  // De romanos a arabigos
                val numeroR: String = binding.etNumero.text.toString()
                if(numeroR.isNotEmpty()) {
                    //Obtener
                    resultado = calculo.romanosToArabigos(numeroR).toString()
                    //Mostrar
                    binding.tvResultado.text = resultado
                }else{
                    binding.tvResultado.text = ""
                }
            }
        }

        binding.tvResultado.setOnClickListener {
            if (binding.tvResultado.text.isNotEmpty() && calculo.resultadoValido){
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

    private fun selConvARomanos(){
        binding.etNumero.setText("")
        aRomanos = true
        binding.etNumero.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etNumero.setHint(R.string.pide_naturales)
    }
    private fun selConvAArabigos(){
        binding.etNumero.setText("")
        aRomanos = false
        binding.etNumero.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        binding.etNumero.setHint(R.string.pide_romanos)
    }









}