package com.binhyul.image_gallery.ui.image.horizontal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.binhyul.image_gallery.databinding.FragImageHorizontalBinding
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.ui.image.gallery.ImageCardController
import com.binhyul.image_gallery.ui.image.gallery.model.GallerySideEffect
import com.binhyul.image_gallery.ui.image.horizontal.adpater.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HorizontalGalleryFragment : Fragment() {

    private var _binding: FragImageHorizontalBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HorizontalGalleryViewModel by viewModels()

    private val imageCardController = object : ImageCardController {
        override fun onClickImage(image: ImageModel) {
            viewModel.moveDetailPage(image)
        }
    }
    private val imageAdapter: ImageAdapter = ImageAdapter(imageCardController)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragImageHorizontalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeSideEffect()
        initImageListView()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collectLatest {
                setLoading(it)
            }
        }
    }


    private fun observeSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sideEffect
                .collectLatest { sideEffect ->
                    when (sideEffect) {
                        is GallerySideEffect.MoveDetailPage -> {
                            findNavController().navigate(
                                HorizontalGalleryFragmentDirections.actionNavigationHorizontalToDetailFragment(
                                    sideEffect.model
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun initImageListView() {
        binding.imageList.adapter = imageAdapter
        binding.imageList.itemAnimator = null
        binding.imageList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = layoutManager!!.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (dx > 0 && totalItemCount <= lastVisibleItemPosition + 5) {
                    viewModel.loadImage()
                }
            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.imageList)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.images.collect {
                imageAdapter.submitList(it)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        if (!loading) {
            binding.loadingView.onEnd()
        } else {
            binding.loadingView.onLoading()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}