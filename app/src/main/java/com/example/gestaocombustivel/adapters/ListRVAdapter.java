package com.example.gestaocombustivel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaocombustivel.R;
import com.example.gestaocombustivel.Views.AbastecimentoViewHolder;
import com.example.gestaocombustivel.bean.Abastacimento;
import java.util.ArrayList;


public class ListRVAdapter extends RecyclerView.Adapter {
    private ArrayList<Abastacimento> abastacimentos;
    private Context context;

    public ListRVAdapter(ArrayList<Abastacimento> abastecimento,Context context){
        this.abastacimentos = abastecimento;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycle_list,parent,false);
        return new AbastecimentoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String informacao;
        AbastecimentoViewHolder viewHolder = (AbastecimentoViewHolder) holder;
        Abastacimento a = abastacimentos.get(position);

        //Seta os dados do ViewHolder implementado
        viewHolder.data.setText(a.getDataAbastecimento().toString());
        informacao = "Abastecidos " + String.valueOf(a.getLitrosAbastecidos()) + " litros.";
        informacao += "Km veículo " + String.valueOf(a.getQuilometragemAtual());
        viewHolder.informacao.setText(informacao);
        viewHolder.setId(a.getId());

        //Verifica qual imagem do posto será setada
        switch(a.getPosto().trim()){
            case "Texaco":
                viewHolder.posto.setImageResource(R.drawable.ic_launcher_background);
                break;
            case "Shell":
                break;
            case "Petrobras":
                break;
            case "Ipiranga":
                break;
            case "Outros":
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return abastacimentos.size();
    }
}
