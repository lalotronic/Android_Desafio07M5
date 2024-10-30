package com.desafiolatam.viewmodel

import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.desafiolatam.viewmodel.databinding.FragmentLowerBinding
import android.widget.Toast
import androidx.core.content.ContextCompat

class LowerFragment : Fragment() {


    private lateinit var binding: FragmentLowerBinding
    val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLowerBinding.inflate(layoutInflater, container, false)
        // Establecer el listener para el TextView
        // Establecer el listener para el TextView
        binding.tvLowerClick.setOnClickListener {
            // Verifica si el juego ha terminado antes de procesar el clic
            if (!viewModel.isGameFinished()) {
                viewModel.increaseLower()
                viewModel.decrementUpper()
            }

            else {
                // Opcional: Puedes deshabilitar el TextView o mostrar un mensaje
                binding.tvLowerClick.isEnabled = false // Deshabilitar el clic
                // También podrías mostrar un mensaje o un Toast aquí
                showResetDialog()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.lowerCounterStateFlow.collect {
                binding.tvLowerCounter.text = it.toString()
            }
        }

        return binding.root
    }
    private fun showResetDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ganaste Negro")
            .setMessage("¿Deseas resetear los contadores?")
            .setPositiveButton("Sí") { _, _ ->
                viewModel.resetScores() // Resetear los contadores en el ViewModel
                binding.tvLowerClick.isEnabled = true // Rehabilitar el clic
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