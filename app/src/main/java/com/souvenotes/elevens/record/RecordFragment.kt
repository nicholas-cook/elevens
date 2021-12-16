package com.souvenotes.elevens.record

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.souvenotes.elevens.R
import com.souvenotes.elevens.databinding.FragmentRecordBinding
import com.souvenotes.elevens.utils.fragmentViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecordFragment : Fragment(R.layout.fragment_record) {

    private val binding: FragmentRecordBinding by fragmentViewBinding {
        FragmentRecordBinding.bind(it)
    }

    private val viewModel: RecordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        binding.resetRecordButton.setOnClickListener {
            showConfirmationDialog()
        }

        setUpObservers()
    }

    private fun setUpObservers() = with(viewModel) {
        totalWins.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding.winsNumber.text = it.toString()
                }
            }
        )

        totalLosses.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding.lossesNumber.text = it.toString()
                }
            }
        )

        totalGames.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding.totalGamesNumber.text = it.toString()
                }
            }
        )

        totalResets.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding.totalResetsNumber.text = it.toString()
                }
            }
        )
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext(), R.style.ElevensAlertDialog)
            .setCancelable(false)
            .setMessage(R.string.reset_record_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                viewModel.resetRecord()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}