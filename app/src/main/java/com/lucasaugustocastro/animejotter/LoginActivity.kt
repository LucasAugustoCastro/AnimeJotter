package com.lucasaugustocastro.animejotter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.lucasaugustocastro.animejotter.entities.User
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.ActivityLoginBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val btnLogin by lazy { binding.btnLogin }
    private val email by lazy { binding.txtEmail.text.toString() }
    private val password by lazy { binding.txtPassword.text.toString() }
    private val txtRegister by lazy { binding.txtRegister }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureLoginButton()
        configureRegisterText()
    }

    private fun configureLoginButton() {
        btnLogin.setOnClickListener{
            if (email == "" || password == "") {
                showMessage("Todos os campos devem ser preenchido")
            }

            val user = DataStore.getCustomerByEmail(email)
            if (user != null){
                if ((user.password ) == password){
                    saveCustomerToken(user)
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                } else {
                    showMessage("Email/Senha incorretos")
                }
            } else {
                showMessage("Email/Senha incorretos")
            }
        }
    }

    private fun configureRegisterText(){
        txtRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun saveCustomerToken(user:User){
        val sharedPreferences =
            this.getSharedPreferences("com.lucasaugustocastro.animejotter.userToken", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("expireIn", getStringDate())
        editor.putLong("user_id", user.id!!)
        editor.apply()
    }
    private fun getStringDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val datetimeNow = calendar.time
        return dateFormat.format(datetimeNow)

    }

    private fun showMessage(message: String){
        Snackbar.make(
            this,
            binding.layout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}