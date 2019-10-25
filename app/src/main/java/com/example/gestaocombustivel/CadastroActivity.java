package com.example.gestaocombustivel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.gestaocombustivel.bean.Abastacimento;
import com.example.gestaocombustivel.dao.GestaoAutonomiaDAO;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private ImageView ivFoto;
    private String caminhoDaFoto;

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
        ivFoto = findViewById(R.id.ivFoto);

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
                if (id >= 0) {
                    //Resgata o objeto do ID atual
                    Abastacimento a = gestao.getAllAbastecimento().get(id);

                    //Seta os textos com os dados já armazenados
                    etQuilo.setText(String.valueOf(a.getQuilometragemAtual()));
                    etLitro.setText(String.valueOf(a.getLitrosAbastecidos()));
                    etData.setText(String.valueOf(a.getDataAbastecimento()));
                    spPosto.setPrompt(a.getPosto());

                    if(a.getCaminhoFoto() != null){
                        ImageView ivFotografia = findViewById(R.id.ivFoto);
                        ivFotografia.setImageURI(Uri.parse(a.getCaminhoFoto()));
                    }

                    id = a.getId();

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

        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        gestao.delete(id);
        finish();
    }

    //Efetua ação do botão salvar
    public void actionButtonSalvar(View v) throws ParseException {
        boolean sucess = true;

        if(etQuilo.getText().toString().trim().isEmpty() || etLitro.getText().toString().trim().isEmpty() || etData.getText().toString().trim().isEmpty() ){
            Toast.makeText(this,"Campos obrigatórios não preenchidos.",Toast.LENGTH_LONG).show();
            return;
        }

        //Valida a quilometragem se é menor que a ultima cadastrada
        if(Double.parseDouble(etQuilo.getText().toString()) < gestao.getQuilometragemAtual()){
            Toast.makeText(this,"A quilometragem não pode ser menor que a última cadastrada.",Toast.LENGTH_LONG).show();
            sucess = false;
        }

        //Se as validações estiverem OK, salva no banco
        if(sucess){
            postoEscolhido = postos[spPosto.getSelectedItemPosition()];

            Abastacimento a = new Abastacimento();
            a.setQuilometragemAtual(Double.parseDouble(etQuilo.getText().toString()));
            a.setLitrosAbastecidos(Double.parseDouble(etLitro.getText().toString()));
            a.setDataAbastecimento(new SimpleDateFormat("dd/MM/yyyy").parse(etData.getText().toString()));
            a.setPosto(postoEscolhido);
            a.setCaminhoFoto(caminhoDaFoto);

            switch(option){
                //Inclusão gera ID novo
                case 3:
                    a.setId(UUID.randomUUID().hashCode());
                    Toast.makeText(this,String.valueOf(a.getId()), Toast.LENGTH_LONG).show();
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

    private File criarArquivoParaSalvarFoto() throws IOException {
        String nomeFoto = UUID.randomUUID().toString();
        //getExternalStoragePublicDirectory()
        //    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fotografia = File.createTempFile(nomeFoto,".jpg",diretorio);
        caminhoDaFoto = fotografia.getAbsolutePath();
        return fotografia;
    }

    public void abrirCamera(View v){
        Intent intecaoAbrirCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File arquivoDaFoto = null;
        try {
            arquivoDaFoto = criarArquivoParaSalvarFoto();
        } catch (IOException ex) {
            Toast.makeText(this, "Não foi possível criar arquivo para foto", Toast.LENGTH_LONG).show();
        }
        if (arquivoDaFoto != null) {
            Uri fotoURI = FileProvider.getUriForFile(this,
                    "com.example.a02_listas.fileprovider",
                    arquivoDaFoto);
            intecaoAbrirCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
            startActivityForResult(intecaoAbrirCamera, 30);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 30){
            if(resultCode == RESULT_OK){
                if(caminhoDaFoto != null){
                    ImageView ivFotografia = findViewById(R.id.ivFoto);
                    ivFotografia.setImageURI(Uri.parse(caminhoDaFoto));
                }
            }
        }
    }
}
