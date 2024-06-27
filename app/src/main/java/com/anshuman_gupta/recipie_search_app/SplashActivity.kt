package com.anshuman_gupta.recipie_search_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anshuman_gupta.recipie_search_app.Signup.SignUpActivity
import com.anshuman_gupta.recipie_search_app.databinding.ActivitySignUpBinding
import com.anshuman_gupta.recipie_search_app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = getSharedPreferences("user", MODE_PRIVATE)
        val str = pref.getString("user","")

        if(str == ""){
            binding.sign.visibility = View.VISIBLE
        }else{
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },1500)
        }
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}