package com.example.post_request

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("test/")
    fun getData():Call<recipe>
    @POST( "test/")
    fun addData(@Body userData:recipeItem): retrofit2.Call<recipeItem>

}