package com.roziqrizal.mysubmission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.roziqrizal.mysubmission.databinding.FragmentFollowerBinding

private const val ARG_PARAM1 = "param1"
private var _binding: FragmentFollowerBinding? = null
private val binding get() = _binding!!


class FollowerFragment : Fragment() {
    private var param1: String? = null
    private lateinit var rvFollower: RecyclerView

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
        rvFollower = binding.rvFollower
        rvFollower.setHasFixedSize(true)
        rvFollower.layoutManager = LinearLayoutManager(context)
        val listHeroAdapter = ListUserFollower(UserProfileActivity.listFollower)
        rvFollower.adapter = listHeroAdapter

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
}