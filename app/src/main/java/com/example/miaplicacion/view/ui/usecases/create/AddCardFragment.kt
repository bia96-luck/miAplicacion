package com.example.miaplicacion.view.ui.usecases.create

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import com.example.miaplicacion.R
import com.example.miaplicacion.aplication.miAplicacionApplication
import com.example.miaplicacion.databinding.FragmentAddCardBinding
import com.example.miaplicacion.viewmodel.create.ModifyCardViewModel

class AddCardFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddCardFragment()
    }

    private lateinit var viewModel: ModifyCardViewModel
    private lateinit var binding: FragmentAddCardBinding

    private val fragmentModifyCardViewModel: ModifyCardViewModel by activityViewModels() {
        ModifyCardViewModel.FragmentAddCardViewModelFactory((activity?.application
                as miAplicacionApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCardBinding.inflate(inflater,container,false)
        val spinner: Spinner = binding.spAddCardStoreType
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
        binding.btnScanNewCard.setOnClickListener {

            val storeName = binding.etAddCardName.text.toString()
            val type = spinner.selectedItem.toString()
            val notes = binding.etAddCardNotes.text.toString()

            if (TextUtils.isEmpty(storeName)){
                binding.etAddCardName.error = "Este campo no debe quedarse vacío"
            }else openActivity(storeName,type,notes)
        }
        return binding.root
    }


    private fun openActivity(storeName: String, type: String, notes: String) {

        val intent = Intent(requireContext(),CameraScannerActivity::class.java)

        intent.putExtra("storeName",storeName)
        intent.putExtra("type",type)
        intent.putExtra("notes",notes)

        startActivity(intent)

    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        p0?.getItemAtPosition(0).toString()
    }

}