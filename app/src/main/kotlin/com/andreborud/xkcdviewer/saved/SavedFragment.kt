package com.andreborud.xkcdviewer.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.databinding.FragmentSavedBinding
import com.andreborud.xkcdviewer.extensions.viewBinding

class SavedFragment: Fragment(R.layout.fragment_saved) {

    private val viewModel: SavedViewModel by viewModels()
    private val binding by viewBinding(FragmentSavedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}