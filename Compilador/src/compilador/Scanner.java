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

/**
 *
 * @author bruni
 */
public class Scanner {
    
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
        palabrasReservadas.put("EOF", TipoToken.EOF);
        
        
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens() throws FileNotFoundException, IOException{
        //Aquí va el corazón del scanner.
        int i, j, k=0, line, c=0;
        boolean flag = true; //Bandera para validar archivo introducido y parametros con clase validar
        String doc = "", lect = "", prov , revi = ""; //Nombre del documento y lectura del documento
        System.out.println("Introdusca el nombre del archivo con formato: [NOMBRE].TXT");
        java.util.Scanner sc = new java.util.Scanner(System.in);
        doc = sc.next(); //Recopilar nombre del documento desde la consola
        System.out.println(doc);
        Validaciones Validar = new Validaciones(); //Abrir la clase validaciones
        flag = Validar.validarDoc(doc); //Validar el nombre del archivo con exprecion regular
        if(flag==false){
            System.out.println("Introdusca un archivo valido");
        }else{
            System.out.println("Buscando archivo...");
            try {
                File file = new File("src/Compilador/Lectura/"+doc); //Iniciar el archivo
            FileReader fr = new FileReader(file); //Leer el archivo
            BufferedReader br = new BufferedReader(fr); //Buffer
            while ((revi = br.readLine()) != null){lect = lect + revi;} //Extraccion de texto
            System.out.println(lect);//Imprecion de texto en consola
            flag = false;
            System.out.println(lect.length());
            line = 1;
            for(i=0;i<lect.length();i++){
                System.out.println(flag);
                prov = String.valueOf(lect.charAt(i));
                System.out.println(prov);
                if(Validar.validarNum(prov)){
                    System.out.println("VN");
                    //Si hay numeros para variables
                    flag = true;
                    c++;
                }
                if(Validar.validarVar(prov)){
                    System.out.println("VV1");
                    //Diferenciar variables de parabras reservadas
                    k++;
                    prov = String.valueOf(lect.charAt(i+1));
                    if(!Validar.validarVar(prov)){
                        System.out.println("VV2");
                        //Si ya no hay letras o numeros
                        if(flag && c<k && k!=1){
                            //Es una variable con numero
                            prov = "";
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                            
                        }else if(flag && c==k && k!=1){
                            prov = "";
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.num, "Numero", prov, line));
                            
                        }else if(!flag && c<k && k==1){
                            prov = String.valueOf(lect.charAt(i));
                            line++;
                            tokens.add(new Token(TipoToken.cr, "Char", prov, line));
                        }else if(!flag && c<k && k!=1){
                            prov = "";
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            System.out.println(prov);
                            line++;
                            switch(prov){
                                    case "if" :
                                        tokens.add(new Token(TipoToken.si, "if", prov, line));
                                        flag = true;
                                    break;
                                    case "class" :
                                        tokens.add(new Token(TipoToken.clase, "clase", prov, line));
                                        flag = true;
                                    break;
                                    case "else" :
                                        tokens.add(new Token(TipoToken.contra, "else", prov, line));
                                        flag = true;
                                    break;
                                    case "false" :
                                        tokens.add(new Token(TipoToken.falso, "false", prov, line));
                                        flag = true;
                                    break;
                                    case "while" :
                                        tokens.add(new Token(TipoToken.mientras, "while", prov, line));
                                        flag = true;
                                    break;
                                    case "function" :
                                        tokens.add(new Token(TipoToken.fun, "function", prov, line));
                                        flag = true;
                                    break;
                                    case "null" :
                                        tokens.add(new Token(TipoToken.nulo, "null", prov, line));
                                        flag = true;
                                    break;
                                    case "or" :
                                        tokens.add(new Token(TipoToken.o, "or", prov, line));
                                        flag = true;
                                    break;
                                    case "print" :
                                        tokens.add(new Token(TipoToken.imprimir, "print", prov, line));
                                        flag = true;
                                    break;
                                    case "return" :
                                        tokens.add(new Token(TipoToken.retornar, "return", prov, line));
                                        flag = true;
                                    break;
                                    case "super" :
                                        tokens.add(new Token(TipoToken.sup, "super", prov, line));
                                        flag = true;
                                    break;
                                    case "this" :
                                        tokens.add(new Token(TipoToken.este, "this", prov, line));
                                        flag = true;
                                    break;
                                    case "true" :
                                        tokens.add(new Token(TipoToken.verdad, "true", prov, line));
                                        flag = true;
                                    break;
                                    case "for" :
                                        tokens.add(new Token(TipoToken.para, "for", prov, line));
                                        flag = true;
                                    break;
                                    
                            }
                                    if(!flag && i>0 && String.valueOf(lect.charAt(i-k)).equals("\"")){
                                        tokens.add(new Token(TipoToken.str, "String", prov, line));
                                    }else if((!flag && i==0) || (!flag && i>0 && !String.valueOf(lect.charAt(i-k)).equals("\""))){
                                        tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                                    }
                        }
                        k=0;
                        flag = false;
                    }
                }else{
                    //Otros atributos
                    prov = prov + String.valueOf(lect.charAt(i+1));
                    switch(prov){
                        case "!=":
                            tokens.add(new Token(TipoToken.dif, "!=", prov, line));
                            flag = true;
                        break;
                        case "==":
                            tokens.add(new Token(TipoToken.igual, "==", prov, line));
                            flag = true;
                        break;
                        case "<=":
                            tokens.add(new Token(TipoToken.menori, "<=", prov, line));
                            flag = true;
                        break;
                        case ">=":
                            tokens.add(new Token(TipoToken.mayori, ">=", prov, line));
                            flag = true;
                        break;
                        }
                    
                    if(!flag){
                        prov = String.valueOf(lect.charAt(i+1));
                        switch(prov){
                        case "{":
                            tokens.add(new Token(TipoToken.cor1, "{", prov, line));
                            flag = true;
                        break;
                        case "}":
                            tokens.add(new Token(TipoToken.cor2, "}", prov, line));
                            flag = true;
                        break;
                        case "(":
                            tokens.add(new Token(TipoToken.par1, "(", prov, line));
                            flag = true;
                        break;
                        case ")":
                            tokens.add(new Token(TipoToken.par2, ")", prov, line));
                            flag = true;
                        break;
                        case ".":
                            tokens.add(new Token(TipoToken.punto, ".", prov, line));
                            flag = true;
                        break;
                        case ",":
                            tokens.add(new Token(TipoToken.coma, ",", prov, line));
                            flag = true;
                        break;
                        case ";":
                            tokens.add(new Token(TipoToken.puntocoma, ";", prov, line));
                            flag = true;
                        break;
                        case "-":
                            tokens.add(new Token(TipoToken.menos, "-", prov, line));
                            flag = true;
                        break;
                        case "+":
                            tokens.add(new Token(TipoToken.mas, "+", prov, line));
                            flag = true;
                        break;
                        case "*":
                            tokens.add(new Token(TipoToken.por, "*", prov, line));
                            flag = true;
                        break;
                        case "/":
                            tokens.add(new Token(TipoToken.div, "/", prov, line));
                            flag = true;
                        break;
                        case "!":
                            tokens.add(new Token(TipoToken.ex, "!", prov, line));
                            flag = true;
                        break;
                        case "=":
                            tokens.add(new Token(TipoToken.asignar, "=", prov, line));
                            flag = true;
                        break;
                        case "<":
                            tokens.add(new Token(TipoToken.menor, "<", prov, line));
                            flag = true;
                        break;
                        case ">":
                            tokens.add(new Token(TipoToken.mayor, ">", prov, line));
                            flag = true;
                        break;
                    }
                    }
                    k=0;
                    flag = false;
                }
            }
              }
              catch(Exception e) {
                  System.out.println("Algo sucedio mal");
              }
            
        }
    
        
        
        tokens.add(new Token(TipoToken.EOF, "EOF", null, linea));

        return tokens;
    }
}