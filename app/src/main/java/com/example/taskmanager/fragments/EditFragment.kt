package com.example.taskmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskmanager.databinding.FragmentEditCreateBinding

class EditFragment : Fragment(){

    private lateinit var binding: FragmentEditCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentEditCreateBinding.inflate(inflater, container, false).also {
            binding = it;
        }.root;
    }
}