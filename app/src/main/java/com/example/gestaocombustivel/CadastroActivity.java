package com.example.gestaocombustivel;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.gestaocombustivel.bean.Abastacimento;
import com.example.gestaocombustivel.dao.GestaoAutonomiaDAO;
import java.sql.Date;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {
    private GestaoAutonomiaDAO gestao = GestaoAutonomiaDAO.getInstance();
    private int id;
    private int option;
    private EditText etQuilo;
    private EditText etLitro;
    private EditText etData;
    private Spinner spPosto;
    private String postoEscolhido = "Texaco";
    private String postos[] = {"Texaco","Shell","Petrobras","Ipiranga","Outros"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Resgata os valores de intenção e id componentes
        id = getIntent().getIntExtra("id", 0);
        option = getIntent().getIntExtra("opc", 0);
        etQuilo = findViewById(R.id.etQuilometragem);
        etLitro = findViewById(R.id.etLitros);
        etData = findViewById(R.id.etData);
        spPosto = findViewById(R.id.spPosto);
        Button btSalvar = findViewById(R.id.btSalvar);
        Button btExcluir = findViewById(R.id.btExcluir);

        //Verifica a opção sendo feita
        switch (option) {
            //Inclusão
            case 3:
                //Esconde o botão excluir
                btExcluir.setVisibility(View.INVISIBLE);
                btExcluir.refreshDrawableState();
                break;

            //Alteração
            case 4:
                if (id > 0) {
                    //Resgata o objeto do ID atual
                    Abastacimento a = gestao.getAbastecimento(id);

                    //Seta os textos com os dados já armazenados
                    etQuilo.setText(String.valueOf(a.getQuilometragemAtual()));
                    etLitro.setText(String.valueOf(a.getLitrosAbastecidos()));
                    etData.setText(String.valueOf(a.getDataAbastecimento()));
                    spPosto.setPromptId(getIndexElementPostos(a.getPosto()));

                    //refresh em tela
                    etQuilo.refreshDrawableState();
                    etLitro.refreshDrawableState();
                    etData.refreshDrawableState();
                    spPosto.refreshDrawableState();
                }
                break;
            //Finaliza a activity caso não seja nenhuma das opções
            default:
                finish();
        }

        //Listeners
        spPosto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                postoEscolhido = postos[position];
            }
        });
    }

    //Pesquisa o indice correspondente ao valor informado por parametro na array postos
    private int getIndexElementPostos(String value){
        int index = 0;

        for(int i = 0; i < postos.length; i++){
            if(value.trim() == postos[i].trim()){
                index = i;
            }
        }

        return index;
    }

    //Efetua a ação do botão excluir
    public void actionButtonExcluir(View v){
        Abastacimento a = new Abastacimento();
        a.setId(id);
        a.setQuilometragemAtual(Double.parseDouble(etQuilo.getText().toString()));
        a.setLitrosAbastecidos(Double.parseDouble(etLitro.getText().toString()));
        a.setDataAbastecimento(Date.valueOf(etData.getText().toString()));
        a.setPosto(postoEscolhido);
        gestao.delete(a);
        finish();
    }

    //Efetua ação do botão salvar
    public void actionButtonSalvar(View v){
        boolean sucess = true;

        //Valida a quilometragem se é menor que a ultima cadastrada
        if(Double.parseDouble(etQuilo.getText().toString()) < gestao.getQuilometragemAtual()){
            Toast.makeText(this,"A quilometragem não pode ser menor que a última cadastrada.",Toast.LENGTH_LONG);
            sucess = false;
        }

        //Se as validações estiverem OK, salva no banco
        if(sucess){
            Abastacimento a = new Abastacimento();
            a.setQuilometragemAtual(Double.parseDouble(etQuilo.getText().toString()));
            a.setLitrosAbastecidos(Double.parseDouble(etLitro.getText().toString()));
            a.setDataAbastecimento(Date.valueOf(etData.getText().toString()));
            a.setPosto(postoEscolhido);

            switch(option){
                //Inclusão gera ID novo
                case 3:
                    a.setId(Integer.parseInt(UUID.randomUUID().toString()));
                    gestao.insert(a);
                    break;
                //Atualiza registro com o id atual
                case 4:
                    a.setId(id);
                    gestao.update(a);
                    break;
            }

            finish();
        }
    }
}
