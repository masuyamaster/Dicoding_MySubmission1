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

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun  getUsersDetailFollower(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollowerItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun  getUsersDetailFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollowerItem>>

    //@GET("/Routes") void getRoutes(Callback<List<Route>> routesCallback);

    //
}