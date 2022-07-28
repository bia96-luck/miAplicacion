package com.example.miaplicacion.view.ui.usecases.read

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miaplicacion.R
import com.example.miaplicacion.aplication.miAplicacionApplication
import com.example.miaplicacion.database.user.Cards
import com.example.miaplicacion.databinding.FragmentCardBinding
import com.example.miaplicacion.view.adapters.CardAdapter
import com.example.miaplicacion.view.tost
import com.example.miaplicacion.viewmodel.read.FragmentCardViewModel

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val fragmentCardViewModel: FragmentCardViewModel by activityViewModels() {
        FragmentCardViewModel.FragmentCardViewModelFactory((activity?.application as miAplicacionApplication).repository)
    }
    private lateinit var cardsList: List<Cards>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        _binding = FragmentCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = setAdapter()

        adapter.setOnItemClickListener(object:CardAdapter.OnItemClickListener{

            override fun onItemClick(position: Int) {
                val rawValue = cardsList[position].rawValue
                val barcodeType = cardsList[position].barcodeType
                fragmentCardViewModel.useCard(cardsList[position])
                val bundle = Bundle()

                bundle.putString("rawValue",rawValue)
                bundle.putString("barcodeType",barcodeType)

                view?.findNavController()?.navigate(R.id.readerFragment,bundle)
            }

            override fun onItemLongClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }

            override fun onFavClick(position: Int) {
                fragmentCardViewModel.setFav(cardsList[position])
                requireContext().tost(cardsList[position].cardsFav.toString())
            }


        })

        binding.fabAddCard.setOnClickListener {
            /*val cards = Cards(
                null,
                ((Calendar.getInstance()).time),
                "rawValue",
                "barcodeType",
                "storeName",
                "storeNotes",
                ((Calendar.getInstance()).time),
                0
            )*/

            view?.findNavController()?.navigate(R.id.addCardFragment)

        }



        val textView: TextView = binding.textHome
        fragmentCardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        fragmentCardViewModel.allCards.observe(viewLifecycleOwner,Observer{
            adapter.setData(it)
            cardsList = it
            adapter.notifyDataSetChanged()
        })

        return root
    }

    private fun setAdapter():CardAdapter {
        val adapter = CardAdapter(fragmentCardViewModel.allCards.value, requireContext())

        val recyclerView = binding.fragmentCardRecycle
        val manager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
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