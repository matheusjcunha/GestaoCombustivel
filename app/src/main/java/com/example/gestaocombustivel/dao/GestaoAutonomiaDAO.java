package com.example.gestaocombustivel.dao;

import com.example.gestaocombustivel.bean.Abastacimento;
import java.util.ArrayList;
import java.util.function.Consumer;

import io.realm.Realm;
import io.realm.RealmObject;

public class GestaoAutonomiaDAO extends RealmObject {
    private final Realm db;

    public GestaoAutonomiaDAO(){
        db = Realm.getDefaultInstance();
    }

    //Inseri um novo abastecimento
    public void insert(Abastacimento a){
        db.beginTransaction();
        db.insert(a);
        db.commitTransaction();
    }

    //Altera um abastecimento
    public void update(Abastacimento a){
        db.beginTransaction();
        db.insertOrUpdate(a);
        db.commitTransaction();
    }

    //Resgata a quilometragem atual do carro
    public double getQuilometragemAtual(){
        Number id = db.where(Abastacimento.class).max("quilometragemAtual");
        Abastacimento a = db.where(Abastacimento.class).equalTo("id",id.intValue()).findFirst();
        return a.getQuilometragemAtual();
    }

    //Resgata todos os abastecimentos
    public ArrayList<Abastacimento> getAllAbastecimento(){
        return (ArrayList<Abastacimento>) db.where(Abastacimento.class).findAll().iterator();
    }

    public Abastacimento getAbastecimento(int id){
        return db.where(Abastacimento.class).equalTo("id",id).findFirst();
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
        db.beginTransaction();
        db.delete(a.getClass());
        db.commitTransaction();
    }
}
