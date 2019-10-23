package com.example.gestaocombustivel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gestaocombustivel.dao.GestaoAutonomiaDAO;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GestaoAutonomiaDAO gestao = GestaoAutonomiaDAO.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvAutonomia = findViewById(R.id.tvAutonomia);

        tvAutonomia.setText(String.valueOf(gestao.getAutonomia()));
        tvAutonomia.refreshDrawableState();
    }

    public void acaoButtonAbastecimentos(View v) {
        Intent intencao = new Intent(this,ListaActivity.class);
        startActivity(intencao);
    }
}
