package com.lucasaugustocastro.animejotter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.ActivityMainBinding
import com.lucasaugustocastro.animejotter.fragment.WatchedFragment
import com.lucasaugustocastro.animejotter.fragment.adapter.WatchedAdapter
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private var adapter: WatchedAdapter? = null
    private val bottomNav by lazy { binding.bottomNavigation }


    private val addWatchedAnime = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            result.data?.let {intent ->
                showMessage("Cidade ${intent.getStringExtra("city")} adicionado com sucesso!")
            }
            val watchedFragment = navHost.childFragmentManager.fragments.firstOrNull { it is WatchedFragment } as? WatchedFragment
            watchedFragment?.getAdapter()?.notifyDataSetChanged()

        } else {
            showMessage("Operação cancelada com sucesso!")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DataStore.setContext(this)
        checkLogin()
        setupBottonNavigation()
        configFab()


    }

    private fun checkLogin(){

        var isLogin = checkToken()
        if (!isLogin) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
    private fun checkToken(): Boolean{
        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sharedPreferences =
            this.getSharedPreferences("com.lucasaugustocastro.animejotter.userToken", Context.MODE_PRIVATE)
        val expireInStr = sharedPreferences?.getString("expireIn", "1970-01-01 00:00:00")
        val calendar = Calendar.getInstance()
        val dateTimeNow = calendar.time
        val expireIn = expireInStr?.let { formato.parse(it) }
        if (expireIn != null) {
            return expireIn > dateTimeNow
        }
        return false
    }
    private fun setupBottonNavigation(){
        navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        bottomNav.setupWithNavController(navController)
    }
    private fun configFab(){
        binding.fab.setOnClickListener{
            Intent(this, SearchTitleActivity::class.java).run {
                addWatchedAnime.launch(this)
            }
        }
    }

    private fun showMessage(message:String){
        Snackbar.make(
            this,
            binding.layout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}