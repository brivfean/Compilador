/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author bruni
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
    boolean flag = true; //Bandera para validar archivo introducido y parametros con clase validar
        String doc = ""; //Nombre del documento
        System.out.println("Introdusca el nombre del archivo con formato: [NOMBRE].TXT");
        Scanner sc = new Scanner(System.in);
        doc = sc.next(); //Recopilar nombre del documento desde la consola
        System.out.println(doc);
        Validaciones Validar = new Validaciones(); //Abrir la clase validaciones
        flag = Validar.validarDoc(doc); //Validar el nombre del archivo con exprecion regular
        if(flag==false){
            System.out.println("Introdusca un archivo valido");
        }else{
            System.out.println("Buscando archivo...");
            File file = new File(doc); //Iniciar el archivo
            FileReader fr = new FileReader(file); //Leer el archivo
            BufferedReader br = new BufferedReader(fr);
            
        }
    }
    
}
