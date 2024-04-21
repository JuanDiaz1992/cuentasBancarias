
package com.banco.models;

import com.banco.models.base.CuentaBase;
import java.util.*;

public class GestorCuentas{

    private static final HashMap<Integer, CuentaBase> cuentas = new HashMap<>();
    
    
    public static void crearCuenta(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 Crear cuenta corriente");
        System.out.println("2 Crear cuenta de ahorros");
        System.out.print("Elija una opción: "); 
        int option = scanner.nextInt();
        String nameClass = "";
        if (option == 1){
            nameClass = "CuentaCorriente";
        }else if(option == 2){
            nameClass = "CuentaAhorro";
        }else{
            System.out.println("Elija una opción valida");
        }
        System.out.print("Ingrese el documento del usuario: ");
        int document = scanner.nextInt();
        if (document<1000){
            System.out.println("Documento invalido, intentelo de nuevo");
        }else {
            boolean exist = false;
            if (!cuentas.isEmpty()){
                for (Map.Entry<Integer,CuentaBase>entry:cuentas.entrySet()){
                    if (entry.getValue().getDocument() == document && entry.getValue().getClass().getSimpleName().equals(nameClass)) {
                        System.out.println("El documento ingresado ya tiene una cuenta, con el número: " + entry.getValue().getId());
                        exist = true;
                    }
                }}
            if (!exist){
                CuentaBase newCuenta;
                System.out.print("Ingrese una contraseña: ");
                scanner.nextLine();
                String password = scanner.nextLine();
                if (nameClass.equals("CuentaCorriente")) {
                    newCuenta = new CuentaCorriente(150000, document, password);
                }else{
                    newCuenta = new CuentaAhorro(0.05, document, password);
                }
                Integer id = (Integer) newCuenta.getId();
                cuentas.put(id, newCuenta);
                System.out.println("Cuenta creada con el id " + newCuenta.getId());
            }
        }


    }

    public static CuentaBase buscarCuenta(int id,int tipoCuenta,String password){
        String nameClass = "";
        if (tipoCuenta == 1){
            nameClass = "CuentaCorriente";
        }else if(tipoCuenta == 2){
            nameClass = "CuentaAhorro";
        }
        CuentaBase cuentaBase = cuentas.get(id);
        if (cuentaBase != null && cuentaBase.getClass().getSimpleName().equals(nameClass) && cuentaBase.getPassword().equals(password)){
            return cuentaBase;
        }else {
            return null;
        }

    }

    public static void depositar(CuentaBase cuentaBase, int typeCuenta) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de saldo a depositar: ");
        double cantdad = scanner.nextInt();
        if(cuentaBase.depositar(cantdad)){
        System.out.println("Saldo registrado correctamente, nuevo saldo: "+cuentaBase.consultarSaldo());
        int cuentaAhorroOption = 1;
        if (cuentaBase.consultarSaldo() + cantdad >=0 && typeCuenta == cuentaAhorroOption){
            CuentaCorriente cuentaAhorro = (CuentaCorriente) cuentaBase;
            cuentaAhorro.setUsedSobregiro(false);
        }
        }else{
            System.out.println("Ah ocurrido un error, intentelo de nuevo");
        }


    }


    public static void retirar(CuentaBase cuentaBase) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: ");
        double cantdad = scanner.nextInt();
        if(cuentaBase.retirar(cantdad)){
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


    public static void calcularInteres(CuentaAhorro cuentaBase, int meses){
        if (cuentaBase.consultarSaldo() > 0){
            double interesAcomulado = cuentaBase.consultarSaldo()*cuentaBase.getTasaInteres()*meses/12;
            String resultadoFormateado = String.format("%.2f", interesAcomulado);
            System.out.print("El valor del interés acomulado por "+meses+" es de: "+resultadoFormateado);

        }else {
            System.out.println("Tu cuenta no tiene saldo");
        }

    }
    public static void retirarSobregiro(CuentaBase cuentaBase){
        CuentaCorriente cuentaCorriente = (CuentaCorriente) cuentaBase;
        cuentaCorriente.retirarSobreGiro();
    }
}
