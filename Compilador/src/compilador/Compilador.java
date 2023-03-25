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
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author bruni
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
    boolean flag = true; //Bandera para validar archivo introducido y parametros con clase validar
        String doc = "", lect = "" ; //Nombre del documento y lectura del documento
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
            File file = new File("src/Compilador/Lectura/"+doc); //Iniciar el archivo
            FileReader fr = new FileReader(file); //Leer el archivo
            BufferedReader br = new BufferedReader(fr); //Buffer
            while ((lect = br.readLine()) != null) //Extraccion de texto
            System.out.println(lect);//Imprecion de texto en consola
            
            
        }
    }
    
}
