package com.example.gestaocombustivel.dao;

import com.example.gestaocombustivel.bean.Abastacimento;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class GestaoAutonomiaDAO {
    private Realm realm;

    //Inseri um novo abastecimento
    public void insert(Abastacimento a){
        realm.beginTransaction();
        realm.insert(a);
        realm.commitTransaction();
    }

    //Altera um abastecimento
    public void update(Abastacimento a){
        realm.beginTransaction();
        realm.insertOrUpdate(a);
        realm.commitTransaction();
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
        realm.delete(a.getClass());
        realm.commitTransaction();
    }

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
    }
}
