package com.banco.scripts;

import java.util.Scanner;

public class Scripts {
    public static void pressEnter(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Presiona Enter para continúar");
        scanner.nextLine();
    }
}
