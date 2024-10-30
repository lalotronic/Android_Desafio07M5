package com.desafiolatam.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.desafiolatam.viewmodel.databinding.FragmentUpperBinding


class UpperFragment : Fragment() {

    private lateinit var binding: FragmentUpperBinding
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpperBinding.inflate(layoutInflater, container, false)
        binding.tvUpperClick.setOnClickListener {
            // Verifica si el juego ha terminado antes de procesar el clic
            if (!viewModel.isGameFinished()) {
                viewModel.increaseUpper()
                viewModel.decrementLower()
            }
            else {
                // Opcional: Puedes deshabilitar el TextView o mostrar un mensaje
                binding.tvUpperClick.isEnabled = false // Deshabilitar el clic
                // También podrías mostrar un mensaje o un Toast aquí
                showResetDialog()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.upperCounterStateFlow.collect {
                binding.tvUpperCounter.text = it.toString()
            }
        }
        return binding.root
    }
    private fun showResetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ganaste Blanco")
            .setMessage("¿Deseas resetear los contadores?")
            .setPositiveButton("Sí") { _, _ ->
                viewModel.resetScores() // Resetear los contadores en el ViewModel
                binding.tvUpperClick.isEnabled = true // Rehabilitar el clic
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // Cerrar el diálogo
            }

        val dialog = builder.create()
        dialog.show()

        // Cambiar colores de los botones
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_light))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_light))
    }
}