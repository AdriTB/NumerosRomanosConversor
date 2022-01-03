package com.example.numerosromanosconversor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.widget.addTextChangedListener
import com.example.numerosromanosconversor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var aRomanos = true

    companion object{
        const val KEY_TEMA = "Theme"
        private const val TEMA_DEF = R.style.Theme_NumerosRomanosConversor
        private const val TEMA_NIGHT = R.style.Theme_NumerosRomanosConversorNight
        val temas = arrayOf(TEMA_DEF, TEMA_NIGHT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = this.getSharedPreferences("com.example.NumerosRomanosConversor", Context.MODE_PRIVATE)
        val temaConfigurado = prefs.getInt(KEY_TEMA, 0)

        setTheme(temaConfigurado)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        convertirARomanos()

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
            Log.d("Pruebas", "Texto cambiado a ${binding.etNumero.text}")
            var resultado: String? = ""
            if (aRomanos) {
                val numero: Int? = binding.etNumero.text.toString().toIntOrNull()
                if(numero != null){
                    resultado = Calculo.convertirDecARom(numero)
                    binding.tvResultado.setText(resultado)
                }else{
                    binding.tvResultado.setText("")
                }
            }else if(!aRomanos){
                val numeroR: String = binding.etNumero.text.toString()
                if(numeroR.isNotEmpty()){
                    resultado = Calculo.convertirRomDec(numeroR)?.toString() ?: "No es un número válido"
                    binding.tvResultado.setText(resultado)
                }else{
                    binding.tvResultado.setText("")
                }
            }
        }

        binding.tvResultado.setOnClickListener {
            if (binding.tvResultado.text.isNotEmpty()){
                val clipboard:ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val data = ClipData.newPlainText("resultado", binding.tvResultado.text)
                clipboard.setPrimaryClip(data)
                Toast.makeText(this, "Resultado copiado", Toast.LENGTH_LONG).show()
            }
        }

        binding.btTheme.setOnClickListener {
            val temaNuevo = temas[1]
            prefs.edit().putInt(KEY_TEMA, temaNuevo).apply()
            recreate()
        }
    }

    fun convertirARomanos(){
        binding.etNumero.setText("")
        aRomanos = true
        binding.etNumero.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etNumero.hint = "Escribe un número natural"
    }
    fun convertirADecimales(){
        binding.etNumero.setText("")
        aRomanos = false
        binding.etNumero.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        binding.etNumero.hint = "Escribe un número romano"
    }





}