package com.lucasaugustocastro.animejotter.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.TitleDetailsActivity
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.FragmentFavoriteBinding
import com.lucasaugustocastro.animejotter.fragment.adapter.FavoriteAdapter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val recyclerView by lazy {binding.recycleView}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(
            inflater,container,false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    fun getAdapter(): FavoriteAdapter {
        return adapter
    }

    private fun setAdapter(){
        val customerId = getCustomerId() ?: -1

        DataStore.getFavoriteTitles(customerId)
        adapter = FavoriteAdapter(DataStore.favoriteTitles)
        recyclerView.adapter = adapter

        adapter.setOnClickListener(object :
            FavoriteAdapter.OnClickListener {
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
    companion object {

        @JvmStatic
        fun newInstance() =
            WatchedFragment().apply {
                arguments = Bundle()
            }
    }
}