package com.example.productcrud.server

import com.example.productcrud.models.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductServer {

    @GET("produtos/{id}")
    fun findById(@Path("id") id: Int): Call<Product>

    @GET("produtos")
    fun findAll(): Call<List<Product>>

    @POST("produtos")
    fun addProduct(@Body product: Product): Call<Product>

    @PUT("produtos/{id}")
    fun updateProduct(@Path("id") id: Int, @Body product: Product): Call<Product>

    @DELETE("produtos/{id}")
    fun deleteProduct(@Path("id") id: Int): Call<String>

}