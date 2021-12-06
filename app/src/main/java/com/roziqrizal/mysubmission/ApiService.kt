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