package com.nurtaz.dev.detectlanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.nurtaz.dev.detectlanguage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val langText : String = binding.etSend.text.toString()

            if (langText.equals("")){
                Toast.makeText(this@MainActivity,"Please enter text",Toast.LENGTH_SHORT).show()
            }else{
                detectLanguage(langText)
            }
        }
    }
    private fun detectLanguage(langText:String){
        val languageIdentifier = LanguageIdentification.getClient()
        languageIdentifier.identifyLanguage(langText)
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Log.i("TAG", "Can't identify language.")
                    binding.textView.text = "Can't identify language."
                } else {
                    Log.i("TAG", "Language: $languageCode")
                    binding.textView.text = "Language: $languageCode"
                }
            }
            .addOnFailureListener {
                // Model couldn’t be loaded or other internal error.
                // ...
                binding.textView.text = "Exception: ${it.message}"
            }

    }
}