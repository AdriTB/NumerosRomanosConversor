package com.example.numerosromanosconversor

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.widget.addTextChangedListener
import com.example.numerosromanosconversor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var aRomanos = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        convertirARomanos()

        binding.btSeleccionar.setOnClickListener{
            binding.etNumero.setText("")
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
            val clipboard:ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("resultado", binding.tvResultado.text)
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Resultado copiado", Toast.LENGTH_LONG).show()
        }

        binding.btTheme.setOnClickListener {
            if (this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
        }
    }

    fun convertirARomanos(){
        aRomanos = true
        binding.etNumero.inputType = InputType.TYPE_CLASS_NUMBER
        binding.etNumero.hint = "Escribe un número natural"
    }
    fun convertirADecimales(){
        aRomanos = false
        binding.etNumero.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        binding.etNumero.hint = "Escribe un número romano"
    }





}