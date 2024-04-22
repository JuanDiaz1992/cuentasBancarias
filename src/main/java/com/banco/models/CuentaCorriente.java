
package com.banco.models;
import com.banco.models.base.CuentaBase;
import com.banco.scripts.ReadAndWriteJson;

public class CuentaCorriente extends CuentaBase {
    protected double limiteSobreGiro;
    protected boolean usedSobregiro;
    public CuentaCorriente(int document, String password,int id, double saldo, int typeCuenta, boolean usedSobregiro, double limiteSobreGiro){
        super(document, password, id, saldo, typeCuenta);
        this.limiteSobreGiro = limiteSobreGiro;
        this.usedSobregiro = usedSobregiro;
    }
    public CuentaCorriente(){}

    public void retirarSobreGiro(){
        if (!this.usedSobregiro && this.saldo>=0){
            this.saldo -= this.limiteSobreGiro;
            this.usedSobregiro = true;
            Boolean state = true;
            ReadAndWriteJson.editJson(this.getId(),"saldo", this.saldo);
            ReadAndWriteJson.editJson(this.getId(),"usedSobregiro", state);
            System.out.println("Retiro exitoso, tu saldo actual es de: "+this.saldo);
        }else {
            System.out.println("No puedes realizar el retiro del sobregiro hasta que pagues ."+this.saldo);
        }
    }

    public double getLimiteSobreGiro() {
        return limiteSobreGiro;
    }

    public void setLimiteSobreGiro(double limiteSobreGiro) {
        this.limiteSobreGiro = limiteSobreGiro;
    }

    public boolean isUsedSobregiro() {
        return usedSobregiro;
    }

    public void setUsedSobregiro(boolean usedSobregiro) {
        usedSobregiro = usedSobregiro;
        boolean bolean = false;
        ReadAndWriteJson.editJson(this.getId(),"usedSobregiro", bolean);
    }
}
