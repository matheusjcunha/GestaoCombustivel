package com.example.gestaocombustivel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gestaocombustivel.adapters.ListRVAdapter;
import com.example.gestaocombustivel.bean.Abastacimento;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {
    private ArrayList<Abastacimento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        RecyclerView rv = findViewById(R.id.rvLista);

        rv.setAdapter(new ListRVAdapter(lista,this));
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layout);
    }

    //Alteração/Exclusão de cadastro
    public void updateAbastecimento(int id){
        Intent intencao = new Intent(this, CadastroActivity.class);
        intencao.putExtra("opc", 4);
        intencao.putExtra("id",id);
        startActivity(intencao);
    }

    //Efetua as ações de click do botão float (Inclusão)
    public void actionButtonFloat(View v) {
        Intent intencao = new Intent(this, CadastroActivity.class);
        intencao.putExtra("opc", 3);
        startActivity(intencao);
    }
}
