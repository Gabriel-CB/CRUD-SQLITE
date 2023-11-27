package com.example.permuta_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.permuta_crud.databinding.ActivityDeleteSuplierBinding
import com.example.permuta_crud.databinding.FragmentListFornecedoresBinding



class DeleteSuplier : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_suplier)

        var id:EditText = findViewById(R.id.id);
        id.setText(intent.getStringExtra("id").toString());

        var db = DBHelper(this);

        val btnDelete: Button = findViewById(R.id.deleteFornecedor);
        val btnCancel: Button = findViewById(R.id.cancel);

        btnDelete.setOnClickListener({
            db.delete("fornecedores",id.getText().toString(),this);
            finish();
        })

        btnCancel.setOnClickListener({
            finish();
        })

    }
}