package com.example.gestaocombustivel.Views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestaocombustivel.ListaActivity;
import com.example.gestaocombustivel.R;

public class AbastecimentoViewHolder extends RecyclerView.ViewHolder {
    public final ImageView posto;
    public final TextView data;
    public final TextView informacao;
    private int id;

    public AbastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);
        posto = itemView.findViewById(R.id.ivPosto);
        data = itemView.findViewById(R.id.tvDataAbastecimento);
        informacao = itemView.findViewById(R.id.tvInfAbastecimento);

        //Evento de clique
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListaActivity) v.getContext()).updateAbastecimento(id);
            }
        });
    }

    //Seta o ID do objeto atual de abastecimento
    public void setId(int id){
        this.id = id;
    }
}
