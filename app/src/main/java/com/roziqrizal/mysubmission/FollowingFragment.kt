package com.roziqrizal.mysubmission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roziqrizal.mysubmission.databinding.FragmentFollowingBinding
import com.roziqrizal.mysubmission.viewmodel.ModelFollowerFragment
import com.roziqrizal.mysubmission.viewmodel.ModelFollowingFragment

private const val ARG_PARAM1 = "param1"
private var _binding: FragmentFollowingBinding? = null
private val binding get() = _binding!!

private lateinit var followingViewModel: ModelFollowingFragment

class FollowingFragment : Fragment() {
    private var param1: String? = null
    private lateinit var rvFollowing: RecyclerView

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
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        rvFollowing = binding.rvFollowing
        rvFollowing.setHasFixedSize(true)
        rvFollowing.layoutManager = LinearLayoutManager(context)
        showLoadingDetail(true)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ModelFollowingFragment::class.java)

        followingViewModel.getFollowing(param1.toString())

        followingViewModel.isLoadingFollowing.observe(this.viewLifecycleOwner, {
            if(!it){
                rvFollowing.adapter = ListUserFollower(followingViewModel.listFollowing)
                showLoadingDetail(it)
            }
        })


        println("following fragment")
        println("result = "+UserProfileActivity.listFollowing)
        val listHeroAdapter = ListUserFollowing(UserProfileActivity.listFollowing)
        rvFollowing.adapter = listHeroAdapter

        return binding.root
    }

    companion object {
        fun newInstance(param1: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private fun showLoadingDetail(isLoading: Boolean) {
        binding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}