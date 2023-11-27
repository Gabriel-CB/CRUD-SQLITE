package com.example.permuta_crud;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.permuta_crud.main.FormFornecedores;
import com.example.permuta_crud.main.FormProdutos;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.PersonViewHolder> {

    ArrayList<Product> dados = new ArrayList<>();
    Context ctx;
    public ProductListAdapter(ArrayList<Product> dados, Context ctx ) {

        this.dados = dados;
        this.ctx=ctx;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(dados.get(position).id);
        holder.name.setText(dados.get(position).name);
        holder.suplier_id.setText(dados.get(position).suplier_id);
        holder.suplier_name.setText(dados.get(position).suplier_name);

        CardView cardView = holder.itemView.findViewById(R.id.cardInfo);
        Button btnDelete = holder.itemView.findViewById(R.id.deleteProdutct);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), FormProdutos.class);

                intent.putExtra(
                        "name",
                        dados.get(position).name
                );
                intent.putExtra(
                        "id",
                        dados.get(position).id
                );
                intent.putExtra(
                        "suplier_id",
                        dados.get(position).suplier_id
                );
                intent.putExtra(
                        "suplier_name",
                        dados.get(position).suplier_name
                );

                startActivity(v.getContext(), intent, null);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteProduct.class);

                intent.putExtra(
                        "id",
                        dados.get(position).id
                );

                startActivity(v.getContext(), intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        TextView suplier_id;
        TextView suplier_name;
        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            suplier_id = itemView.findViewById(R.id.suplierId);
            suplier_name = itemView.findViewById(R.id.suplierName);

        }
    }
}
