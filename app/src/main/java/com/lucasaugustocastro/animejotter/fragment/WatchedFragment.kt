package com.lucasaugustocastro.animejotter.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.lucasaugustocastro.animejotter.TitleDetailsActivity
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.FragmentWatchedBinding
import com.lucasaugustocastro.animejotter.fragment.adapter.WatchedAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [WatchedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WatchedFragment : Fragment() {
    private lateinit var binding: FragmentWatchedBinding
    private val recyclerView by lazy {binding.recycleView}
    private lateinit var adapter: WatchedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::binding.isInitialized){
            binding = FragmentWatchedBinding.inflate(
                inflater,container,false
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    fun getAdapter(): WatchedAdapter {
        return adapter
    }

    private fun setAdapter(){
        val customerId = getCustomerId() ?: -1

        DataStore.getWatchedTitles(customerId)
        adapter = WatchedAdapter(DataStore.watchedTitles){ watchedTitle ->
            onLongPressDetected(watchedTitle)
        }
        recyclerView.adapter = adapter

        adapter.setOnClickListener(object :
            WatchedAdapter.OnClickListener {
                override fun onClick(position: Int, model: WatchedTitle) {
                    val intent = Intent(activity, TitleDetailsActivity::class.java)
                    intent.putExtra("title_id", model.title_id)
                    startActivity(intent)

                }
            }
        )

    }

    private fun getCustomerId(): Long? {
        val sharedPreferences =
            activity?.getSharedPreferences("com.lucasaugustocastro.animejotter.userToken", Context.MODE_PRIVATE)
        return sharedPreferences?.getLong("user_id", -1)
    }

    private fun onLongPressDetected(watchedTitle: WatchedTitle) {
        AlertDialog.Builder(requireContext()).run {
            setMessage("Tem certeza que deseja remover este anime ?")
            setPositiveButton("Excluir") {_,_ ->
                DataStore.deleteWatchedTitle(watchedTitle)
                showMessage("Anime ${watchedTitle.name} excluida com sucesso")
                adapter.notifyDataSetChanged()
            }
            setNegativeButton("Cancelar", null)
            show()
        }
    }

    private fun showMessage(message: String){
        Snackbar.make(
            requireContext(),
            binding.watchedLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            WatchedFragment().apply {
                arguments = Bundle()
            }
    }
}