package com.example.gestaocombustivel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.gestaocombustivel.adapters.ListRVAdapter;
import com.example.gestaocombustivel.dao.GestaoAutonomiaDAO;

public class ListaActivity extends AppCompatActivity {
    private ListRVAdapter adapter;
    private GestaoAutonomiaDAO gestao = GestaoAutonomiaDAO.getInstance();
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        rv = findViewById(R.id.rvLista);
        adapter = new ListRVAdapter(this);

        rv.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        super.recreate();
    }
}
