package com.example.gestaocombustivel.dao;

import com.example.gestaocombustivel.bean.Abastacimento;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class GestaoAutonomiaDAO {
    private Realm realm;
    private int registros;
    private int posicaoInserido;
    private int posicaoAlterado;
    private int posicaoDeletado;
    //Singletoon
    private static GestaoAutonomiaDAO INSTANCIA;


    public static GestaoAutonomiaDAO getInstance(){
        if (INSTANCIA == null){
            INSTANCIA = new GestaoAutonomiaDAO();
        }
        return INSTANCIA;
    }
    private GestaoAutonomiaDAO() {
        realm = Realm.getDefaultInstance();
        registros = this.getAllAbastecimento().size();
        this.setPosicaoInserido(0);
        this.setPosicaoAlterado(0);
        this.setPosicaoDeletado(0);
    }

    //----------------------------------------Métodos-------------------------------------------------

    //Inseri um novo abastecimento
    public void insert(Abastacimento a){
        realm.beginTransaction();
        realm.copyToRealm(a);
        realm.commitTransaction();
        posicaoInserido = this.getAllAbastecimento().size()-1;
    }

    //Altera um abastecimento
    public void update(Abastacimento a){
        realm.beginTransaction();
        realm.insertOrUpdate(a);
        realm.commitTransaction();
        posicaoAlterado = this.getAllAbastecimento().indexOf(a);

    }

    //Resgata a quilometragem atual do carro
    public double getQuilometragemAtual(){
        return realm.where(Abastacimento.class).max("quilometragemAtual").doubleValue();
    }

    //Resgata todos os abastecimentos
    public ArrayList<Abastacimento> getAllAbastecimento(){
        ArrayList<Abastacimento> lista = new ArrayList();
        RealmResults result = realm.where(Abastacimento.class).findAll();
        lista.addAll(realm.copyFromRealm(result));
        return lista;
    }

    public Abastacimento getAbastecimento(int id){
        return realm.where(Abastacimento.class).equalTo("id",id).findFirst();
    }

    //Calcula a autonomia do veículo
    public double getAutonomia() {
        double penultimaQuilometragem;
        double primeiraQuilometragem;
        double litros;

        ArrayList<Abastacimento> a = getAllAbastecimento();

        if(! a.isEmpty() && a.size() > 2) {
            penultimaQuilometragem = a.get(a.size() - 1).getQuilometragemAtual();
            primeiraQuilometragem = a.get(a.size() - 2).getQuilometragemAtual();
            litros = a.get(a.size() - 2).getLitrosAbastecidos();
        }else{
            return 0;
        }

        return (penultimaQuilometragem - primeiraQuilometragem) / litros;
    }

    //Deleta um registro
    public void delete(Abastacimento a){
        realm.beginTransaction();
        realm.where(Abastacimento.class).equalTo("id", a.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();
        posicaoDeletado = this.getAllAbastecimento().indexOf(a);
    }

    public void setRegistros(int reg){
        this.registros = reg;
    }

    public int getRegistros(){
        return this.registros;
    }

    public int getPosicaoInserido() {
        return posicaoInserido;
    }

    public void setPosicaoInserido(int posicaoInserido) {
        this.posicaoInserido = posicaoInserido;
    }

    public int getPosicaoAlterado() {
        return posicaoAlterado;
    }

    public void setPosicaoAlterado(int posicaoAlterado) {
        this.posicaoAlterado = posicaoAlterado;
    }

    public int getPosicaoDeletado() {
        return posicaoDeletado;
    }

    public void setPosicaoDeletado(int posicaoDeletado) {
        this.posicaoDeletado = posicaoDeletado;
    }
}
