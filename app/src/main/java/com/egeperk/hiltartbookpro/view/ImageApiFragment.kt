package com.egeperk.hiltartbookpro.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.egeperk.hiltartbookpro.R
import com.egeperk.hiltartbookpro.adapter.ImageRecyclerAdapter
import com.egeperk.hiltartbookpro.databinding.FragmentImageApiBinding
import com.egeperk.hiltartbookpro.util.Status
import com.egeperk.hiltartbookpro.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(val imageRecyclerAdapter: ImageRecyclerAdapter) :
    Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel: ArtViewModel

    private var fragmentImageBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentImageBinding = binding

        var job : Job? = null
        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeToObserver()

        binding.imageSearchRecyclerView.adapter = imageRecyclerAdapter
        binding.imageSearchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    fun subscribeToObserver() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {
                        imageResult -> imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()

                    fragmentImageBinding?.progressBar?.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?:"Error", Toast.LENGTH_SHORT).show()
                    fragmentImageBinding?.progressBar?.visibility = View.GONE

                }
                Status.LOADING -> {
                    fragmentImageBinding?.progressBar?.visibility = View.VISIBLE

                }
            }
        })
    }

}