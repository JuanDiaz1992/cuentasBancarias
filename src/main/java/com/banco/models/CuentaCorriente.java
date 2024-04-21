
package com.banco.models;
import com.banco.models.base.CuentaBase;

public class CuentaCorriente extends CuentaBase {

    protected double limiteSobreGiro;
    protected boolean isUsedSobregiro = false;
    public CuentaCorriente(double limiteSobreGiro, int document, String password){
        super(document, password);
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public void retirarSobreGiro(){
        if (!this.isUsedSobregiro){
            this.saldo -= this.limiteSobreGiro;
            if (this.saldo<0){
                this.isUsedSobregiro = true;
            }
            System.out.println("Retiro exitoso, tu saldo actual es de: "+this.saldo);
        }else {
            System.out.println("No puedes realizar el retiro del sobregiro.");
        }
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public void setLimiteSobreGiro(double limiteSobreGiro) {
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public boolean isUsedSobregiro() {
        return isUsedSobregiro;
    }

    public void setUsedSobregiro(boolean usedSobregiro) {
        isUsedSobregiro = usedSobregiro;
    }
}
