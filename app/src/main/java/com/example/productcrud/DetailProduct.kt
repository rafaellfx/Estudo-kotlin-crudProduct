package com.example.productcrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.productcrud.models.Product
import com.example.productcrud.server.ProductInitializa
import com.example.productcrud.server.ProductServer
import kotlinx.android.synthetic.main.activity_detail_product.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProduct : AppCompatActivity() {

    companion object {

        const val RESULT_EDIT = 1
        const val RESULT_DELETE = 2
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val produto = intent.getSerializableExtra("produto") as Product

        var id = findViewById<TextView>(R.id.edIdDetail).apply {
            setText(produto.id.toString())
        }
        var nome = findViewById<EditText>(R.id.edNomeDetail).apply {
            setText(produto.nome)
        }
        var preco = findViewById<EditText>(R.id.edPrecoDetail).apply {
            setText(produto.valor.toString())
        }

    }

    fun editarProduto(v: View?) {

        val produto = Product(
            edIdDetail.text.toString().toInt(),
            edNomeDetail.text.toString(),
            edPrecoDetail.text.toString().toFloat()
        )

        var productServer: ProductServer = ProductInitializa().retroCreate()

        productServer.updateProduct(edIdDetail.text.toString().toInt(),produto).enqueue(object: Callback<Product> {
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(!response.isSuccessful){
                    Log.e("Erro",response.code().toString())
                }
            }

        })

        val data = Intent()
        data.putExtra("produto", produto)
        setResult(RESULT_EDIT, data)
        finish()
    }

    fun excluirProduto(v: View?) {

        var productServer: ProductServer = ProductInitializa().retroCreate()

        productServer.deleteProduct(edIdDetail.text.toString().toInt()).enqueue(object: Callback<String> {

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(!response.isSuccessful){
                    Log.e("Erro",response.code().toString())
                }
            }

        })

        setResult(RESULT_DELETE)
        finish()
    }
}
