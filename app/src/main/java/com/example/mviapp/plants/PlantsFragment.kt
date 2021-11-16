package com.example.mviapp.plants

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mviapp.databinding.FragmentPlantsBinding
import com.example.mviapp.utils.autoCleared
import com.example.mviapp.utils.getViewModelFactory

class PlantsFragment : Fragment() {

    private val viewModel: PlantsViewModel by viewModels { getViewModelFactory() }

    private var binding by autoCleared<FragmentPlantsBinding>()

    private val plantsAdapter: PlantsAdapter by lazy {
        PlantsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserver()
        viewModel.refreshData()
    }

    private fun initView() {
        binding.apply {
            plantsListRecyclerView.adapter = plantsAdapter
            floatingActionButton.setOnClickListener {
                viewModel.refreshData()
            }
        }
    }

    private fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner, {
            binding.plantsListRecyclerView.isVisible = it.plants.isNotEmpty()
            binding.plantsEmptyLayout.isVisible = it.plants.isEmpty()
            plantsAdapter.submitList(it.plants)
            plantsAdapter.notifyDataSetChanged()
        })
    }

}