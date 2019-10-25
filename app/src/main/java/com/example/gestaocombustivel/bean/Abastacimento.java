package com.example.gestaocombustivel.bean;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Abastacimento extends RealmObject {

    @PrimaryKey
    private int id;
    private double quilometragemAtual;
    private double litrosAbastecidos;
    private Date dataAbastecimento;
    @Ignore
    private Calendar calendarAbastecimento;
    private String posto;
    private String caminhoFoto;

    public double getQuilometragemAtual() { return quilometragemAtual; }
    public void setQuilometragemAtual(double quilometragemAtual) { this.quilometragemAtual = quilometragemAtual; }
    public double getLitrosAbastecidos() {
        return litrosAbastecidos;
    }
    public void setLitrosAbastecidos(double litrosAbastecidos) { this.litrosAbastecidos = litrosAbastecidos; }
    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }
    public void setDataAbastecimento(Date dataAbastecimento) { this.dataAbastecimento = dataAbastecimento; }
    public String getPosto() {
        return posto;
    }
    public void setPosto(String posto) {
        this.posto = posto;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Calendar getCalendarAbastecimento() {
        return calendarAbastecimento;
    }
    public void setCalendarAbastecimento(Calendar calendarAbastecimento) { this.calendarAbastecimento = calendarAbastecimento; }
    public String getCaminhoFoto() { return caminhoFoto; }
    public void setCaminhoFoto(String caminhoFoto) { this.caminhoFoto = caminhoFoto; }
}
