
package com.banco.models.base;
public interface ICuentaBancaria {
    public boolean depositar(double valor);
    public boolean retirar(double valor);
    public double consultarSaldo();
}
