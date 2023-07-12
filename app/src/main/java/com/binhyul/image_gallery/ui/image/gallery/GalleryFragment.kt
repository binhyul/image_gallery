package com.binhyul.image_gallery.ui.image.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binhyul.image_gallery.databinding.FragImageGalleryBinding
import com.binhyul.image_gallery.domain.model.ImageModel
import com.binhyul.image_gallery.ui.image.gallery.adapter.PagedImageAdapter
import com.binhyul.image_gallery.ui.image.gallery.model.GallerySideEffect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var _binding: FragImageGalleryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GalleryViewModel by viewModels()

    private val imageCardController = object : ImageCardController {
        override fun onClickImage(image: ImageModel) {
            viewModel.moveDetailPage(image)
        }
    }
    private val pagedImageAdapter: PagedImageAdapter = PagedImageAdapter(imageCardController)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragImageGalleryBinding.inflate(inflater, container, false)
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
            viewModel.sideEffect.collectLatest { sideEffect ->
                when (sideEffect) {
                    is GallerySideEffect.MoveDetailPage -> {
                        findNavController().navigate(
                            GalleryFragmentDirections.actionNavigationGalleryToDetailFragment(
                                sideEffect.model
                            )
                        )
                    }
                }
            }
        }
    }

    private fun initImageListView() {
        binding.imageList.adapter = pagedImageAdapter
        binding.imageList.itemAnimator = null
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadImage().collect {
                viewModel.loaded()
                pagedImageAdapter.submitData(it)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.imageList.isVisible = !loading
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