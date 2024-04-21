package com.banco.models;
import com.banco.models.base.CuentaBase;

public class CuentaAhorro extends CuentaBase {

    private double tasaInteres;
    public CuentaAhorro(double tasaInteres, int document,String password){
        super(document,password);
        this.tasaInteres = tasaInteres;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(int tasaInteres) {
        this.tasaInteres = tasaInteres;
    }


}
