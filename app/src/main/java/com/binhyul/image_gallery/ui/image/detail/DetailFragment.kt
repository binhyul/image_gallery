package com.binhyul.image_gallery.ui.image.detail

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.binhyul.component.loadUrlImage
import com.binhyul.image_gallery.databinding.FragDetailBinding
import com.binhyul.image_gallery.domain.model.ImageModel
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        setLoading()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imageDetail
                .collect { model ->
                    loadData(model)
                }
        }
    }

    private fun loadData(model: ImageModel) {
        binding.apply {
            ivImg.loadUrlImage(
                url = model.url,
                imageLoadedCallback = {

                }
            )
            tvName.text = model.title
        }
    }

    private fun setLoading() {
        binding.loadingView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
                binding.ivImg.visibility = View.INVISIBLE
                binding.tvName.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(p0: Animator) {
                binding.ivImg.visibility = View.VISIBLE
                binding.tvName.visibility = View.VISIBLE
                binding.loadingView.visibility = View.GONE
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
        binding.loadingView.playAnimation()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}