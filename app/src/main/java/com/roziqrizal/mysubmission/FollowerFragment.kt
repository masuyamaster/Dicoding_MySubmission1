package com.roziqrizal.mysubmission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.roziqrizal.mysubmission.databinding.FragmentFollowerBinding
import com.roziqrizal.mysubmission.viewmodel.ModelFollowerFragment
import com.roziqrizal.mysubmission.viewmodel.ModelUserActivity

private const val ARG_PARAM1 = "param1"
private var _binding: FragmentFollowerBinding? = null
private val binding get() = _binding!!


class FollowerFragment : Fragment() {
    private var param1: String? = null
    private lateinit var rvFollower: RecyclerView
    private lateinit var progressBar: ProgressBar

    private lateinit var followerViewModel: ModelFollowerFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        progressBar = binding.progressBarFollower
        rvFollower = binding.rvFollower
        rvFollower.setHasFixedSize(true)
        rvFollower.layoutManager = LinearLayoutManager(context)
        showLoadingDetail(true)


        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ModelFollowerFragment::class.java)

        followerViewModel.getFollower(param1.toString())

        followerViewModel.isLoadingFollower.observe(this.viewLifecycleOwner, {
            if(!it){
                rvFollower.adapter = ListUserFollower(followerViewModel.listFollower)
                showLoadingDetail(it)
            }
        })

        return binding.root
    }

    companion object {
        fun newInstance(param1: String) =
            FollowerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private fun showLoadingDetail(isLoading: Boolean) {
        binding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}