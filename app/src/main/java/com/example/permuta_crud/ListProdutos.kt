package com.example.permuta_crud

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permuta_crud.databinding.FragmentListProdutosBinding
import com.example.permuta_crud.main.FormProdutos
import java.util.ArrayList

private lateinit var binding: FragmentListProdutosBinding
class ListProdutos : Fragment() {

    private var db: DBHelper? = null
    private lateinit var rv: RecyclerView;

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListProdutosBinding.inflate(layoutInflater);
        val fragment = inflater.inflate(R.layout.fragment_list_produtos, container, false);
        db = DBHelper(context);

        this.rv = fragment.findViewById(R.id.rv);
        val llm = LinearLayoutManager(fragment.context)
        rv.layoutManager = llm

        val btnAdd = fragment.findViewById<Button>(R.id.addProdutos);

        var dados: ArrayList<Product>? = db!!.listProducts();

        val adapter = ProductListAdapter(
            dados,
            requireActivity().applicationContext
        )
        rv.adapter = adapter

        btnAdd.setOnClickListener {
            var intent: Intent = Intent(context, FormProdutos::class.java);

            intent.putExtra(
                "name",
                ""
            )
            intent.putExtra(
                "id",
                ""
            )
            intent.putExtra(
                "suplier_id",
                ""
            )
            intent.putExtra(
                "suplier_name",
                ""
            )

            startActivity(intent);
        }
        // Inflate the layout for this fragment
        return fragment

    }

    override fun onResume() {

        super.onResume()

        var dados: ArrayList<Product>? = db!!.listProducts();

        val adapter = ProductListAdapter(
            dados,
            requireActivity().applicationContext
        )
        this.rv.adapter = adapter

    }
}