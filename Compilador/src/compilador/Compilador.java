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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import jdk.nashorn.internal.parser.Token;

/**
 *
 * @author bruni
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("class", TipoToken.clase);
        palabrasReservadas.put("else", TipoToken.contra);
        palabrasReservadas.put("false", TipoToken.falso);
        palabrasReservadas.put("for", TipoToken.para);
        palabrasReservadas.put("fun", TipoToken.fun); //definir funciones
        palabrasReservadas.put("if", TipoToken.si);
        palabrasReservadas.put("null", TipoToken.nulo);
        palabrasReservadas.put("or", TipoToken.o);
        palabrasReservadas.put("print", TipoToken.imprimir);
        palabrasReservadas.put("return", TipoToken.retornar);
        palabrasReservadas.put("super", TipoToken.sup);
        palabrasReservadas.put("this", TipoToken.este);
        palabrasReservadas.put("true", TipoToken.verdad);
        palabrasReservadas.put("var", TipoToken.var); //definir variables
        palabrasReservadas.put("while", TipoToken.mientras);
        palabrasReservadas.put("{", TipoToken.cor1);
        palabrasReservadas.put("}", TipoToken.cor2);
        palabrasReservadas.put("(", TipoToken.par1);
        palabrasReservadas.put(")", TipoToken.par2);
        palabrasReservadas.put(".", TipoToken.punto);
        palabrasReservadas.put(",", TipoToken.coma);
        palabrasReservadas.put(";", TipoToken.puntocoma);
        palabrasReservadas.put("-", TipoToken.menos);
        palabrasReservadas.put("+", TipoToken.mas);
        palabrasReservadas.put("*", TipoToken.por);
        palabrasReservadas.put("/", TipoToken.div);
        palabrasReservadas.put("!", TipoToken.ex);
        palabrasReservadas.put("!=", TipoToken.dif);
        palabrasReservadas.put("=", TipoToken.asignar);
        palabrasReservadas.put("==", TipoToken.igual);
        palabrasReservadas.put("<", TipoToken.menor);
        palabrasReservadas.put(">", TipoToken.mayor);
        palabrasReservadas.put("<=", TipoToken.menori);
        palabrasReservadas.put(">=", TipoToken.mayori);
        
        
    }

    Compilador(String source){
        this.source = source;
    }

    List<Token> scanTokens() throws FileNotFoundException, IOException{
        //Aquí va el corazón del scanner.
        int i, j, k=0, line;
        boolean flag = true; //Bandera para validar archivo introducido y parametros con clase validar
        String doc = "", lect = "", prov ; //Nombre del documento y lectura del documento
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
            flag = false;
            for(i=0;i<lect.length();i++){
                line = 1;
                prov = String.valueOf(lect.charAt(i));
                if(Validar.validarNum(prov)){
                    //Si hay numeros para variables
                    flag = true;
                }
                if(Validar.validarVar(prov)){
                    //Diferenciar variables de parabras reservadas
                    k++;
                    prov = String.valueOf(lect.charAt(i+1));
                    if(!Validar.validarVar(prov)){
                        //Si ya no hay letras o numeros
                        if(flag){
                            //Es una variable con numero
                            prov = "";
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.var, prov, prov, line));
                            
                        }else{
                            
                            
                            
                        }
                        k=0;
                    }
                }else{
                    //Otros atributos
                }
            }
            
            
        }
    
        
        
        tokens.add(new Token(TipoToken.EOF, "", null, linea));

        return tokens;
    }
        
    
    
}
