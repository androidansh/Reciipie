package com.anshuman_gupta.recipie_search_app.Signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import com.anshuman_gupta.recipie_search_app.MainActivity
import com.anshuman_gupta.recipie_search_app.R
import com.anshuman_gupta.recipie_search_app.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SaveSignUp.setOnClickListener {

            if(binding.signEmail.text.toString() == ""){
                Toast.makeText(this, "Email is required", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(binding.signUpUserName.text.toString() == ""){
                Toast.makeText(this, "User name is required", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val pref = getSharedPreferences("user", MODE_PRIVATE)
            pref.edit().putString("user", binding.signUpUserName.text.toString()).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}