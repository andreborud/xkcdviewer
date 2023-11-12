package com.andreborud.xkcdviewer.comics

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.andreborud.xkcdviewer.R
import com.andreborud.xkcdviewer.databinding.FragmentComicsBinding
import com.andreborud.xkcdviewer.extensions.show
import com.andreborud.xkcdviewer.extensions.viewBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComicsFragment: Fragment(R.layout.fragment_comics) {

    private val viewModel: ComicsViewModel by viewModels()
    private val binding by viewBinding(FragmentComicsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.comicView.minimumScale = 0.7f
        disableUi()

        binding.next.setOnClickListener {
            disableUi()
            viewModel.onUser(intent = ComicsIntent.DownloadNext)
        }

        binding.previous.setOnClickListener {
            disableUi()
            viewModel.onUser(intent = ComicsIntent.DownloadPrevious)
        }

        binding.share.setOnClickListener {
            viewModel.onUser(intent = ComicsIntent.Share)
        }

        binding.save.setOnClickListener {
            viewModel.onUser(intent = ComicsIntent.Save)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is ComicsState.OnComicDownloaded -> {
                        loadComicImage(state.comic.img)
                        binding.title.text = state.comic.title
                        binding.comicView.setScale(0.8f, true)
                        binding.next.show(!state.isLatest)
                        binding.previous.show(!state.isFirst)
                        binding.save.setImageResource(if (state.isSaved) R.drawable.ic_bookmark_saved else R.drawable.ic_bookmark)
                    }
                    ComicsState.OnSaved -> {
                        binding.save.setImageResource(R.drawable.ic_bookmark_saved)
                    }
                    ComicsState.OnUnsaved -> {
                        binding.save.setImageResource(R.drawable.ic_bookmark)
                    }
                    is ComicsState.OnError -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        enableUi()
                    }
                    is ComicsState.OnShare -> {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            val textToShare = getString(R.string.share_message, state.link)
                            putExtra(Intent.EXTRA_TEXT, textToShare)
                        }
                        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_title)))
                    }
                }
            }
        }
    }

    private fun loadComicImage(url: String) {
        Picasso.get()
            .load(url)
            .into(binding.comicView, object : Callback {
                override fun onSuccess() {
                    binding.comicView.setScale(0.8f, true)
                    enableUi()
                }

                override fun onError(e: Exception?) { }
            })
    }

    private fun disableUi() {
        binding.loading.show(true)
        binding.next.isEnabled = false
        binding.previous.isEnabled = false
        binding.share.isEnabled = false
        binding.save.isEnabled = false
    }

    private fun enableUi() {
        binding.loading.show(false)
        binding.next.isEnabled = true
        binding.previous.isEnabled = true
        binding.share.isEnabled = true
        binding.save.isEnabled = true
    }
}