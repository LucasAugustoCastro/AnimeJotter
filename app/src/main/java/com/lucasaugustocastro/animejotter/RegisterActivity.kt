package com.lucasaugustocastro.animejotter

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.lucasaugustocastro.animejotter.entities.User
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val name by lazy { binding.txtName.text.toString() }
    private val email by lazy { binding.txtEmail.text.toString() }
    private val password by lazy { binding.txtPassword.text.toString()}
    private val btnRegister by lazy { binding.btnRegister }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRegisterButton()
    }


    private fun configureRegisterButton(){
        btnRegister.setOnClickListener{
            if (name == "" || email == "" || password == ""){
                showMessage("Todos os campos devem ser preenchido")
            }
            val user = User(name, email, password)
            try {
                val customer_id = DataStore.saveCustomer(user)
                finish()
            } catch (ex: SQLiteConstraintException){
                showMessage("Email já cadastro, use outro email ou realize o login")
            }

        }
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