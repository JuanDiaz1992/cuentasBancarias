
package com.banco.controller;

import com.banco.models.CuentaAhorro;
import com.banco.models.CuentaCorriente;
import com.banco.models.GestorCuentas;
import com.banco.models.base.CuentaBase;
import com.banco.scripts.Scripts;

import java.util.*;

public class BanckController {
    
    public static void main(){
        Scanner scanner = new Scanner(System.in);
        int option;
        do{
            System.out.println("////////////////////////////");
            System.out.println("1) Crear cuenta");
            System.out.println("2) Ingresar a su cuenta");
            System.out.println("0) Salir");
            System.out.print("Elija una de las opciones: ");
            option = scanner.nextInt();
            switch (option){
                case 1:
                    System.out.println("Agregar nueva cuenta");
                    GestorCuentas.crearCuenta();
                    Scripts.pressEnter();
                    break;
                case 2:
                    System.out.println("Ingrese el tipo de su cuenta");
                    System.out.println("1) Cuenta corriente");
                    System.out.println("2) Cuenta de ahorros");
                    System.out.print("Elija: ");
                    int typeObjetc = scanner.nextInt();
                    if (typeObjetc>2 || typeObjetc<0) {
                        break;
                    }
                    System.out.print("Ingrese el número de cuenta (ID): ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Ingrese su contraseña: ");
                    String password = scanner.nextLine();
                    CuentaBase cuentaBase = GestorCuentas.buscarCuenta(id,typeObjetc,password);

                    if (cuentaBase!=null){
                        int optionCuenta;
                        do {
                            System.out.println("////////////////////////////");
                            System.out.println("1) Ver saldo");
                            System.out.println("2) Retirar saldo");
                            System.out.println("3) Agregar saldo");
                            if (typeObjetc == 1) {
                                System.out.println("4) Ver valor sobregiro");
                            } else {
                                System.out.println("4) Ver interés acomulado");
                            }
                            System.out.println("0) Volver al menú principal");
                            System.out.print("Elija una de las opciones: ");
                            optionCuenta = scanner.nextInt();
                            switch (optionCuenta){
                                case 1:
                                    System.out.println("Ver saldo");
                                    GestorCuentas.consultarSaldo(cuentaBase);
                                    Scripts.pressEnter();
                                    break;
                                case 2:
                                    System.out.println("Retirar saldo");
                                    if (typeObjetc == 1){
                                        System.out.println("1) Retiro saldo en cuenta");
                                        System.out.println("2) Retiro de sobregiro");
                                        System.out.print("Elija una de las opciones: ");
                                        int optionRetiro = scanner.nextInt();
                                        if (optionRetiro == 1){
                                            GestorCuentas.retirar(cuentaBase);
                                        } else if (optionRetiro == 2) {
                                            GestorCuentas.retirarSobregiro(cuentaBase);
                                        }
                                    }else if(typeObjetc == 2){
                                        GestorCuentas.retirar(cuentaBase);
                                    }
                                    Scripts.pressEnter();
                                    break;
                                case 3:
                                    System.out.println("Depositar");
                                    GestorCuentas.depositar(cuentaBase,typeObjetc);
                                    Scripts.pressEnter();
                                    break;
                                case 4:

                                    if (typeObjetc == 1){
                                        System.out.println("Ver valor sobregiro");
                                        CuentaCorriente cuentaCorriente = (CuentaCorriente) cuentaBase;
                                        System.out.println("Tu limite de sobregiro es de: "+cuentaCorriente.getLimiteSobreGiro());
                                        System.out.println("Para usarlo ve a la opción 2 y seleciona Retiro de sobregiro.");
                                    }else if (typeObjetc == 2){
                                        System.out.println("Ver interés acomulado");
                                        System.out.print("Indique la cantidad de meses de 1 a 12: ");
                                        int cantMeses = scanner.nextInt();
                                        CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBase;
                                        GestorCuentas.calcularInteres(cuentaAhorro,cantMeses);
                                    }else{
                                        System.out.println("Opción invalida");
                                    }
                                    Scripts.pressEnter();
                                    break;
                            }
                        }while (optionCuenta != 0);

                    }else {
                        System.out.println("Datos de ingreso invalidos");
                    }
                    {
                        System.out.println("Ingrese una opción valida.");
                    }

                    break;

                case 0:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción invalida");
                    
                    }
            
        }while(option !=0);
    }
    
}
