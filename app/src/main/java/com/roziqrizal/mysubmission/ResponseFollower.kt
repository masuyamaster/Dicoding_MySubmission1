package com.roziqrizal.mysubmission

import com.google.gson.annotations.SerializedName

data class ResponseFollower(

	@field:SerializedName("ResponseFollower")
	val responseFollower: ArrayList<ResponseFollowerItem>
)

data class ResponseFollowerItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String = "",

	@field:SerializedName("avatar_url")
	val avatarUrl: String = "",

	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@field:SerializedName("starred_url")
	val starredUrl: String? = null,

	@field:SerializedName("followers_url")
	val followersUrl: String? = null

)
