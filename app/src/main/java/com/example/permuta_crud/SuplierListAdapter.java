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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.permuta_crud.main.FormFornecedores;

import java.util.ArrayList;

public class SuplierListAdapter extends RecyclerView.Adapter<SuplierListAdapter.SuplierViewHolder> {

    ArrayList<Suplier> dados = new ArrayList<>();
    Context ctx;
    public SuplierListAdapter(ArrayList<Suplier> dados, Context ctx ) {

        this.dados = dados;
        this.ctx=ctx;
    }

    @NonNull
    @Override
    public SuplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suplier, parent, false);
        SuplierViewHolder pvh = new SuplierViewHolder(v);

        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SuplierViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(dados.get(position).name);
        holder.id.setText(dados.get(position).id);

        CardView cardView = holder.itemView.findViewById(R.id.cardInfo);
        Button btnDelete = holder.itemView.findViewById(R.id.deleteFornecedor);

        RecyclerView rv = holder.itemView.findViewById(R.id.formFornecedores);

        DBHelper db = new DBHelper(ctx);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), FormFornecedores.class);


                intent.putExtra(
                        "name",
                        dados.get(position).name
                );
                intent.putExtra(
                        "id",
                        dados.get(position).id
                );

                startActivity(v.getContext(), intent, null);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DeleteSuplier.class);

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

    public static class SuplierViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView name;
        public SuplierViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
        }
    }
}
