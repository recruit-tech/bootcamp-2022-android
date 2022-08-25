package com.example.mygithubapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mygithubapp.R
import com.example.mygithubapp.databinding.FragmentHomeBinding
import com.example.mygithubapp.ui.results.ResultsFragment
import com.example.mygithubapp.ui.results.ResultsFragmentArgs
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
//    private val homeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val searchWord: EditText = binding.inputSearchWord
        val searchButton: Button = binding.userSearchButton
        searchButton.setOnClickListener{
            val action = HomeFragmentDirections.actionNavigationHomeToResultsFragment(searchWord.text.toString())
            this.findNavController().navigate(action)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}