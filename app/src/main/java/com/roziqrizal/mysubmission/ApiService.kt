package com.roziqrizal.mysubmission

import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getGithubUsers(
        @Query("q") username: String
    ): Call<ResponseSearch>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getGithubUsersDetail(
        @Path("username") username: String
    ): Call<ResponseGithubUsersDetail>

    //
}