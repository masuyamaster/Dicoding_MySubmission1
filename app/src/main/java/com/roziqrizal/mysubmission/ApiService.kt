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