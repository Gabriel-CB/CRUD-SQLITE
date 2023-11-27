package com.example.permuta_crud

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permuta_crud.databinding.FragmentListFornecedoresBinding
import com.example.permuta_crud.main.FormFornecedores
import java.util.ArrayList

private lateinit var binding: FragmentListFornecedoresBinding
class ListFornecedores : Fragment() {

    private var db: DBHelper? = null
    private lateinit var rv: RecyclerView

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListFornecedoresBinding.inflate(layoutInflater);
        val fragment = inflater.inflate(R.layout.fragment_list_fornecedores, container, false);

        val btnAdd = fragment.findViewById<Button>(R.id.addFornecedor);

        this.rv = fragment.findViewById(R.id.rv);
        val llm = LinearLayoutManager(fragment.context)

        rv.layoutManager = llm
        db = DBHelper(context)

        var dados: ArrayList<Suplier>? = db!!.listSuplier();

        val adapter = SuplierListAdapter(
            dados,
            requireActivity().applicationContext
        )
        rv.adapter = adapter

        btnAdd.setOnClickListener {
            var intent:Intent = Intent(context, FormFornecedores::class.java);

            intent.putExtra(
                "name",
               ""
            )
            intent.putExtra(
                "id",
                ""
            )

            startActivity(intent);
        }

        // Inflate the layout for this fragment
        return fragment

    }

    override fun onResume() {

        super.onResume()

        var dados: ArrayList<Suplier>? = db!!.listSuplier();

        val adapter = SuplierListAdapter(
            dados,
            requireActivity().applicationContext
        )
        this.rv.adapter = adapter

    }
}