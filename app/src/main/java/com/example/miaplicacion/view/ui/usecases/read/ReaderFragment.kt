package com.example.miaplicacion.view.ui.usecases.read

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.miaplicacion.R
import com.example.miaplicacion.aplication.miAplicacionApplication
import com.example.miaplicacion.databinding.FragmentCardBinding
import com.example.miaplicacion.databinding.FragmentReaderBinding
import com.example.miaplicacion.viewmodel.extensionfunctions.toBitmap
import com.example.miaplicacion.viewmodel.read.FragmentCardViewModel
import com.example.miaplicacion.viewmodel.read.ReaderViewModel

class ReaderFragment : Fragment() {


    private var rawValue: String? = null
    private var barcodeType: String? = null
    private lateinit var binding: FragmentReaderBinding

    companion object {
        fun newInstance() = ReaderFragment()
    }

    private val readerViewModel: ReaderViewModel by activityViewModels() {
        ReaderViewModel.ReaderViewModelFactory((activity?.application as miAplicacionApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let{
            rawValue = it.getString("rawValue")
            barcodeType = it.getString("barcodeType")
        }
        Log.d("Check reader",rawValue.toString())
        readerViewModel.setByteArray(rawValue?:"error",barcodeType?:"error")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReaderBinding.inflate(inflater,container,false)
        readerViewModel.byteArray.observe(viewLifecycleOwner, Observer {

            binding.ivReader.setImageBitmap(it.toBitmap())

        })
        return binding.root
    }


}