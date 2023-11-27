package com.example.permuta_crud.main

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.permuta_crud.DBHelper
import com.example.permuta_crud.R

import com.example.permuta_crud.SuplierListAdapter
import com.example.permuta_crud.databinding.ActivityFormProdutosBinding
import java.util.Objects

class FormProdutos : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFormProdutosBinding
    var autoCompleteTxt: AutoCompleteTextView? = null
    var adapterItems: ArrayAdapter<String>? = null

    private var db: DBHelper? = null
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormProdutosBinding.inflate(layoutInflater)
        db = DBHelper(this);
        setContentView(binding.root)

        binding.name.setText(intent.getStringExtra("name").toString());
        binding.id.setText(intent.getStringExtra("id").toString());
        if(intent.getStringExtra("suplier_id").toString() != "") {
            binding.suplierId.setText(
                intent.getStringExtra("suplier_id")
                    .toString() + " - " + intent.getStringExtra("suplier_name").toString()
            );
        }
        var dados: ArrayList<String> = db!!.listSuplierForInput(intent.getStringExtra("suplier_id"));

        autoCompleteTxt = findViewById<AutoCompleteTextView>(R.id.suplierId)

        adapterItems = ArrayAdapter<String>(this, R.layout.list_item, dados)
        autoCompleteTxt?.setAdapter(adapterItems)

        autoCompleteTxt?.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(applicationContext, "Item: $item", Toast.LENGTH_SHORT).show()
        })

        binding.salvarProduto.setOnClickListener({
            var campos: ArrayList<String> = ArrayList<String>();
            var valores: ArrayList<String> = ArrayList<String>();

            var id = binding.id.text.toString();
            var name = binding.name.text.toString();
            var suplier_id = binding.suplierId.text.toString().split(" - ")[0];

            if(name == "" || suplier_id == ""){
                Toast.makeText(
                    this.getBaseContext(), "Prencha todos os campos primeiro!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{

                if(id != ""){
                    campos.add("id");
                    valores.add(id);
                    campos.add("nome");
                    valores.add(name);
                    campos.add("fornecedor_id");
                    valores.add(suplier_id);

                    db!!.update("produtos", campos, valores, this)
                }else {
                    campos.add("nome");
                    valores.add(name);
                    campos.add("fornecedor_id");
                    valores.add(suplier_id);

                    db!!.insert("produtos", campos, valores, this)
                }
                finish();
            }
        })

        binding.exitInfo.setOnClickListener({
            finish();
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        finish();
    }
}