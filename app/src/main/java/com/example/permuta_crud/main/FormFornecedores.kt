package com.example.permuta_crud.main

import android.R.attr.data
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.permuta_crud.DBHelper
import com.example.permuta_crud.databinding.ActivityFormFornecedoresBinding


class FormFornecedores : AppCompatActivity() {

    private lateinit var binding: ActivityFormFornecedoresBinding

    private var db: DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormFornecedoresBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.name.setText(intent.getStringExtra("name").toString());
        binding.id.setText(intent.getStringExtra("id").toString());

        binding.exitInfo.setOnClickListener({
            finish();
        })

        binding.salvarFornecedor.setOnClickListener {
            var campos: ArrayList<String> = ArrayList<String>();
            var valores: ArrayList<String> = ArrayList<String>();

            var id = binding.id.text.toString();
            var name = binding.name.text.toString();

            if(name == ""){
                Toast.makeText(
                    this.getBaseContext(), "Prencha todos os campos primeiro!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{

                db = DBHelper(this);

                if(id !=""){
                    campos.add("id");
                    valores.add(id);
                    campos.add("nome");
                    valores.add(name);

                    db!!.update("fornecedores", campos, valores, this)
                }else {
                    campos.add("nome");
                    valores.add(name);

                    db!!.insert("fornecedores", campos, valores, this)
                }
            finish();
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        finish();
    }
}