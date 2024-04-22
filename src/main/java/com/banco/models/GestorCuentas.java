
package com.banco.models;

import com.banco.models.base.CuentaBase;
import com.banco.scripts.ReadAndWriteJson;


import java.util.*;


public class GestorCuentas{
    public static void crearCuenta(){
        List <CuentaBase> cuentasLis = ReadAndWriteJson.readJson();


        Scanner scanner = new Scanner(System.in);
        int option;
        String nameClass = "";
        do {
            System.out.println("1 Crear cuenta corriente");
            System.out.println("2 Crear cuenta de ahorros");
            System.out.print("Elija una opción: ");
            option = scanner.nextInt();
            switch (option){
                case 1:
                    nameClass = "CuentaCorriente";
                    System.out.println("**************Cuenta Corriente**************");
                    option = 0;
                    break;
                case 2:
                    nameClass = "CuentaAhorro";
                    System.out.println("**************Cuenta de ahorros**************");
                    option = 0;
                    break;
                default:
                    System.out.println("************Elija una opción valida**************");
            }

        }while (option!=0);
        System.out.print("Ingrese el documento del usuario: ");
        int document = scanner.nextInt();
        if (document<1000){
            System.out.println("Documento invalido, intentelo de nuevo");
        }else {
            boolean exist = false;
            if (!cuentasLis.isEmpty()){
                for (CuentaBase cuentaBase: cuentasLis){
                    if (cuentaBase.getDocument() == document && cuentaBase.getClass().getSimpleName().equals(nameClass)) {
                        System.out.println("************** Precaución **************");
                        System.out.println("El documento ingresado ya tiene una cuenta con el ID: " + cuentaBase.getId());
                        exist = true;
                    }
                }}
            if (!exist){
                CuentaBase newCuenta1;
                System.out.print("Ingrese una contraseña: ");
                scanner.nextLine();
                String password = scanner.nextLine();

                Integer maxId = cuentasLis.isEmpty() ? 1 : Integer.MIN_VALUE;;
                for (CuentaBase cuenta : cuentasLis) {
                    if (cuenta.getId() > maxId) {
                        maxId = cuenta.getId();
                    }
                }
                maxId = maxId+1;
                if (nameClass.equals("CuentaCorriente")) {
                    newCuenta1 = new CuentaCorriente(document, password,maxId,0.00,1,false, 150000);
                }else{
                    newCuenta1= new CuentaAhorro(document, password, maxId,0.00,2, 5);
                }
                ReadAndWriteJson.writeJson(newCuenta1);
                System.out.println("Cuenta creada con el id " + newCuenta1.getId());

            }
        }


    }

    public static CuentaBase buscarCuenta(int id, int typeObjetc ,String password){
        List <CuentaBase> cuentasLis = ReadAndWriteJson.readJson();
        for (CuentaBase cuentaBase: cuentasLis){
            if (cuentaBase.getId() == id){
                if (cuentaBase.getTypeCuenta() == typeObjetc && cuentaBase.getPassword().equals(password)){
                    return cuentaBase;
                }
            }

        }
        return null;
    }

    public static void depositar(CuentaBase cuentaBase, int typeCuenta) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de saldo a depositar: ");
        double cantidad = scanner.nextInt();
        if(cuentaBase.depositar(cantidad)){
        System.out.println("Saldo registrado correctamente, nuevo saldo: "+cuentaBase.consultarSaldo());
        int cuentaAhorroOption = 1;
        if (cuentaBase.consultarSaldo() + cantidad >=0 && typeCuenta == cuentaAhorroOption){
            CuentaCorriente cuentaCorriente = (CuentaCorriente) cuentaBase;
            cuentaCorriente.setUsedSobregiro(false);
        }
        }else{
            System.out.println("Ah ocurrido un error, intentelo de nuevo");
        }


    }


    public static void retirar(CuentaBase cuentaBase) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: ");
        double cantidad = scanner.nextInt();
        if(cuentaBase.retirar(cantidad)){
        System.out.println("Saldo retirado correctamente, le queda: "+ cuentaBase.consultarSaldo());
        }else{
            System.out.println("Saldo insuficiente");
        }
    }
         


    public static void consultarSaldo(CuentaBase cuentaBase) {
        if (cuentaBase.consultarSaldo()<0){
            System.out.println("Recuerda que usaste el sobre giro por lo que tienes un saldo pendiente de: "+cuentaBase.consultarSaldo());
        }else {
            System.out.println("Saldo saldo actual: "+cuentaBase.consultarSaldo());
        }

    }


    public static void calcularInteres(CuentaAhorro cuentaAhorro, int meses){
        String interes = cuentaAhorro.calcularInteres(meses);
        if (interes != null){
            System.out.println("El valor del interés acomulado por "+meses+" es de: "+ interes);
        }else {
            System.out.println("Tu cuenta no tiene saldo");
        }

    }
    public static void retirarSobregiro(CuentaBase cuentaBase){
        CuentaCorriente cuentaCorriente = (CuentaCorriente) cuentaBase;
        cuentaCorriente.retirarSobreGiro();

    }
}
