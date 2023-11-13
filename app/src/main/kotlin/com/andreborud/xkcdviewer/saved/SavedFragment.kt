package com.andreborud.xkcdviewer.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andreborud.common.XkcdComic
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.databinding.FragmentSavedBinding
import com.andreborud.xkcdviewer.extensions.show
import com.andreborud.xkcdviewer.extensions.viewBinding
import com.andreborud.xkcdviewer.saved.SavedState.OnComicsLoaded
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment: Fragment(R.layout.fragment_saved) {

    private val viewModel: SavedViewModel by viewModels()
    private val binding by viewBinding(FragmentSavedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loading.show(true)

        // Setup the state collector to listen to state changes from the viewModel
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is OnComicsLoaded -> {
                        val adapter = SavedAdapter(state.comics, ::onItemClick, ::onSavedClick)
                        binding.recyclerView.adapter = adapter
                        binding.loading.show(false)
                        binding.empty.show(state.comics.isEmpty())
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onUser(SavedIntent.Refresh)
    }

    private fun onItemClick(comic: XkcdComic) {
        val action = SavedFragmentDirections.actionSavedFragmentToComicsFragment(comic)
        findNavController().navigate(action)
    }

    private fun onSavedClick(comic: XkcdComic) {
        binding.loading.show(true)
        viewModel.onUser(SavedIntent.Remove(comic))
    }
}