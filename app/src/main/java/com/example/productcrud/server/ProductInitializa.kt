package com.example.productcrud.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductInitializa {



        fun retroCreate(): ProductServer {
            val retro = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retro.create(ProductServer::class.java)
        }



}