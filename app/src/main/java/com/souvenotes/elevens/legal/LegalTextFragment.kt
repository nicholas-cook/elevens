package com.souvenotes.elevens.legal

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.souvenotes.elevens.R
import com.souvenotes.elevens.databinding.FragmentLegalTextBinding
import com.souvenotes.elevens.utils.fragmentViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LegalTextFragment : Fragment(R.layout.fragment_legal_text) {

    private val binding by fragmentViewBinding { FragmentLegalTextBinding.bind(it) }

    private val args: LegalTextFragmentArgs by navArgs()

    private val viewModel: LegalTextViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding.toolbar) {
            setNavigationOnClickListener { findNavController().popBackStack() }
            title = args.title
        }

        binding.legalText.movementMethod = LinkMovementMethod.getInstance()

        viewModel.loadFile(args.filename)
        setUpObservers()
    }

    private fun setUpObservers() = with(viewModel) {
        loading.observe(
            viewLifecycleOwner,
            {
                binding.progressBar.isVisible = it ?: false
            }
        )

        legalText.observe(
            viewLifecycleOwner,
            {
                binding.legalText.text = it
            }
        )

        showError.observe(
            viewLifecycleOwner,
            {
                showErrorDialog()
            }
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext(), R.style.ElevensAlertDialog)
            .setCancelable(false)
            .setMessage(R.string.legal_text_error_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}