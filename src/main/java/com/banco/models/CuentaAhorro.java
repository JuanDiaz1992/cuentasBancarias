package com.banco.models;
import com.banco.models.base.CuentaBase;

public class CuentaAhorro extends CuentaBase {
    private double tasaInteres;
    public CuentaAhorro(int document,String password, int id, double saldo,int typeCuenta, int tasaInteres){
        super(document,password,id, saldo, typeCuenta);
        this.tasaInteres = tasaInteres;
    }
    public CuentaAhorro(){}

    public double getTasaInteres() {
        return this.tasaInteres;
    }

    public void setTasaInteres(int tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public String calcularInteres(int meses){
        if (this.saldo > 0){
            double interesAcomulado = this.saldo* (this.tasaInteres / 100.0) *meses/12;
            String resultadoFormateado = String.format("%.2f", interesAcomulado);
            return resultadoFormateado;
        }else{
            return null;
        }

    }

}
