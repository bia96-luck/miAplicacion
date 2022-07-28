package com.example.miaplicacion.view.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miaplicacion.R
import com.example.miaplicacion.aplication.miAplicacionApplication
import com.example.miaplicacion.database.user.Cards
import com.example.miaplicacion.databinding.FragmentFavsBinding
import com.example.miaplicacion.view.adapters.CardAdapter
import com.example.miaplicacion.viewmodel.read.FavFragmentViewModel

class FavFragment : Fragment() {

    private var _binding: FragmentFavsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var cardsList: List<Cards>

    private val favFragmentViewModel: FavFragmentViewModel by activityViewModels() {
        FavFragmentViewModel.Factory((activity?.application as miAplicacionApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFavsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textFav
        favFragmentViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val adapter = setAdapter()

        favFragmentViewModel.favCards.observe(viewLifecycleOwner, Observer{
            adapter.setData(it)
            cardsList = it
            adapter.notifyDataSetChanged()
        })

        return root


    }

    private fun setAdapter(): CardAdapter {
        val adapter = CardAdapter(favFragmentViewModel.favCards.value, requireContext())

        val recyclerView = binding.fragmentCardRecycle
        val manager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
        val space = resources.getDimensionPixelSize(R.dimen.recycleSpace)/2

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager
        recyclerView.setPadding(20,20,20,500)
        recyclerView.clipToPadding = false
        recyclerView.addItemDecoration(object: RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(space,space,space,space)
            }
        })
        return adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}