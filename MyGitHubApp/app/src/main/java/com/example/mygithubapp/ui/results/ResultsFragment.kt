package com.example.mygithubapp.ui.results

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Looper.myLooper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithubapp.databinding.FragmentUserResultsBinding
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay

class ResultsFragment: Fragment() {

    private var _binding: FragmentUserResultsBinding? = null
    private val binding get() = _binding!!
    private val resultsViewModel by lazy { ViewModelProvider(this)[ResultsViewModel::class.java] }
    private val args: ResultsFragmentArgs by navArgs()
    private var adapter = ResultsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultsViewModel.query = args.queryUserName

        resultsViewModel.userList.observe(viewLifecycleOwner) {
            adapter.setDataSet(it)
        }

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
        binding.apply {
            resultsTitle.text = "検索結果"
            userResultsRecyclerview.adapter = adapter
            userResultsRecyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            userResultsRecyclerview.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}