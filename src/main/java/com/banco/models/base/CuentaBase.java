package com.banco.models.base;

import com.banco.scripts.ReadAndWriteJson;

import java.util.Objects;

public class CuentaBase implements ICuentaBancaria {
    private int typeCuenta;
    private int id;
    private int document;
    private String password;
    public double saldo;
    public CuentaBase(int document,String password, int id, double saldo, int typeCuenta){
        this.document = document;
        this.password = password;
        this.id = id;
        this.saldo = saldo;
        this.typeCuenta = typeCuenta;
    }
    public CuentaBase(){}
    @Override
    public boolean depositar(double valor) {
        if(valor>0){
            this.saldo = this.saldo+valor;
            Double doubleCant = this.saldo;
            ReadAndWriteJson.editJson(this.id,"saldo",doubleCant);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean retirar(double valor) {
        if(this.saldo>0 && this.saldo >= valor){
            this.saldo = this.saldo-valor;
            Double doubleCant = this.saldo;
            ReadAndWriteJson.editJson(this.id,"saldo",doubleCant);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public double consultarSaldo(){
        return this.saldo;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeCuenta() {
        return typeCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBase that)) return false;
        return getDocument() == that.getDocument() && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDocument());
    }
}
