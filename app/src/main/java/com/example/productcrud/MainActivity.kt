package com.example.productcrud

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productcrud.adapter.ProductAdapter
import com.example.productcrud.models.Product
import com.example.productcrud.server.ProductInitializa
import com.example.productcrud.server.ProductServer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class MainActivity : AppCompatActivity(),ProductAdapter.OnItemClickListener {
    private val REQ_CADASTRO = 1;
    private val REQ_DETALHE  = 2;
    private var listaProduct: ArrayList<Product> = ArrayList()
    private var posicaoAlterar=-1

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ProductAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProductAdapter(listaProduct)
        viewAdapter.onItemClickListener = this

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        _init()
    }

    private fun _init() {
        listaProduct.clear()
        var productServer: ProductServer = ProductInitializa().retroCreate()
        var productCall = productServer.findAll()
        productCall.enqueue(object: Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("Erro", t.message)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if(response.isSuccessful){

                    response.body()?.map { p ->

                                listaProduct.add(p)

                    }
                    viewAdapter.notifyDataSetChanged()
                }
                else{
                    Log.e("Erro",response.code().toString())
                }
            }
        })
    }

    override fun onItemClicked(view: View, position: Int) {
        val it = Intent(this, DetailProduct::class.java)
        this.posicaoAlterar = position
        val produtos = listaProduct.get(position)
        it.putExtra("produto", produtos)
        startActivityForResult(it, REQ_DETALHE)
    }

    fun abrirFormulario(view: View) {
        val it = Intent(this, CadastroProduto::class.java)
        startActivityForResult(it, REQ_CADASTRO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CADASTRO) {
            Log.e("TEST", Activity.RESULT_OK.toString())
            if (resultCode == Activity.RESULT_OK) {
                Log.e("TEST", "dentro")
                val produto = data?.getSerializableExtra("produto") as Product
                listaProduct.add(produto)
                viewAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Cadastro realizada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == REQ_DETALHE) {
            if (resultCode == DetailProduct.RESULT_EDIT) {
                val produto = data?.getSerializableExtra("produto") as Product
                listaProduct.set(this.posicaoAlterar, produto)
                viewAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Edicao realizada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            } else if (resultCode == DetailProduct.RESULT_DELETE) {
                listaProduct.removeAt(this.posicaoAlterar)
                viewAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Exclusao realizada com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }



}
