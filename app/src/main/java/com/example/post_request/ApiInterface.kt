package com.example.post_request

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("test/")
    fun getData():Call<recipe>

    @POST( "test/")
    fun addData(@Body userData:RecipeItem): retrofit2.Call<RecipeItem>

    @PUT( "test/{id}")
    fun updateData(@Path("id") id :Int, @Body userData:RecipeItem): retrofit2.Call<RecipeItem>

    @DELETE( "test/{id}")
    fun deleteData(@Path ("id") id :Int):Call<Void>
}