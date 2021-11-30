package com.example.numerosromanosconversor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.numerosromanosconversor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var aRomanos = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSeleccionar.setOnClickListener{
            it.scaleX *= -1
            if(it.scaleX > 0) {
                //Decimales a Romanos
                aRomanos = true
                binding.etNumero.inputType = InputType.TYPE_CLASS_NUMBER
                binding.etNumero.hint = "Escribe un número decimal"

            }else if(it.scaleX < 0){
                //Romanos a Decimales
                aRomanos = false
                binding.etNumero.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
                binding.etNumero.hint = "Escribe un número romano"
            }
        }

        binding.etNumero.addTextChangedListener {
            Log.d("Pruebas", "Texto cambiado a ${binding.etNumero.text}")
            val numero:Int? = binding.etNumero.text.toString().toIntOrNull()
            var resultado:String
            if(aRomanos){
                resultado = Calculo.convertirDecARom(numero!!)
                binding.tvResultado.setText(resultado)
            }

        }

    }




}