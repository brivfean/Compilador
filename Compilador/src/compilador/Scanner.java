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
import static java.lang.System.exit;
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
        palabrasReservadas.put("\"", TipoToken.comdo);
        palabrasReservadas.put("'", TipoToken.comsi);
        palabrasReservadas.put("EOF", TipoToken.EOF);
        
        
    }

    Scanner(String source){
        this.source = source;
    }

    List<Token> scanTokens() throws FileNotFoundException, IOException{
        //Aquí va el corazón del scanner.
        int i, j, k=0, line, c=0;
        boolean flag = true, cflag = false; //Bandera para validar archivo introducido y parametros con clase validar
        String doc = "", lect = "", prov , revi = "", esc="3"; //Nombre del documento y lectura del documento
        java.util.Scanner sc = new java.util.Scanner(System.in);
        //System.out.println("Salir ingrese 0");
        //System.out.println("Ingresar codigo desde la consola ingrese 1");
        //System.out.println("Ingresar txt ingrese otro simbolo");
            
        lect = source;
            
        Validaciones Validar = new Validaciones(); //Abrir la clase validaciones
            
        
        
            
            try {

            System.out.println(lect);//Imprecion de texto en consola
            flag = false;
            System.out.println("El numero de simbolos es: "+lect.length());
            line = 1;
            for(i=0;i<lect.length();i++){
                
                prov = String.valueOf(lect.charAt(i));
                if(cflag){
                    
                    if(prov.equals("*")){
                        
                        if(lect.length()-1>i){
                            
                            if(String.valueOf(lect.charAt(i+1)).equals("/")){
                                cflag = false;
                                i++;
                                prov = String.valueOf(lect.charAt(i));
                                
                            }
                        }    
                    } 
                 }else{
                if(Validar.validarNum(prov)){
                    //Si hay numeros para variables
                    flag = true;
                    c++;
                    
                }
                if(Validar.validarVar(prov)){
                    //Diferenciar variables de parabras reservadas
                    k++;
                    if(lect.length()-1<=i){
                        prov = "´";
                    }else{
                        prov = String.valueOf(lect.charAt(i+1));
                    }
                    
                    if(!Validar.validarVar(prov)){
                        
                        //Si ya no hay letras o numeros
                        if(flag && c<k){
                            //Es una variable con numero
                            prov = "";
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                            
                        }else if(flag && c==k){
                            
                            for(j=0;j<c;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.num, "Numero", prov, line));
                            
                        }else if(!flag && k==1){
                            if(i>0){
                                if(String.valueOf(lect.charAt(i-k)).equals("'")){
                                    prov = String.valueOf(lect.charAt(i));
                                    line++;
                                    tokens.add(new Token(TipoToken.cr, "Char", prov, line));
                                }else{
                                    prov = String.valueOf(lect.charAt(i));
                                    line++;
                                    tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                                }
                            }
                            
                        }else{
                            prov = "";
                            
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            
                            line++;
                            
                            switch(prov){
                                    case "if" :
                                        tokens.add(new Token(TipoToken.si, "if", null, line));
                                        flag = true;
                                    break;
                                    case "class" :
                                        tokens.add(new Token(TipoToken.clase, "clase", null, line));
                                        flag = true;
                                    break;
                                    case "else" :
                                        tokens.add(new Token(TipoToken.contra, "else", null, line));
                                        flag = true;
                                    break;
                                    case "false" :
                                        tokens.add(new Token(TipoToken.falso, "false", null, line));
                                        flag = true;
                                    break;
                                    case "while" :
                                        tokens.add(new Token(TipoToken.mientras, "while", null, line));
                                        flag = true;
                                    break;
                                    case "function" :
                                        tokens.add(new Token(TipoToken.fun, "function", null, line));
                                        flag = true;
                                    break;
                                    case "null" :
                                        tokens.add(new Token(TipoToken.nulo, "null", null, line));
                                        flag = true;
                                    break;
                                    case "or" :
                                        tokens.add(new Token(TipoToken.o, "or", null, line));
                                        flag = true;
                                    break;
                                    case "print" :
                                        tokens.add(new Token(TipoToken.imprimir, "print", null, line));
                                        flag = true;
                                    break;
                                    case "return" :
                                        tokens.add(new Token(TipoToken.retornar, "return", null, line));
                                        flag = true;
                                    break;
                                    case "super" :
                                        tokens.add(new Token(TipoToken.sup, "super", null, line));
                                        flag = true;
                                    break;
                                    case "this" :
                                        tokens.add(new Token(TipoToken.este, "this", null, line));
                                        flag = true;
                                    break;
                                    case "true" :
                                        tokens.add(new Token(TipoToken.verdad, "true", null, line));
                                        flag = true;
                                    break;
                                    case "for" :
                                        tokens.add(new Token(TipoToken.para, "for", null, line));
                                        flag = true;
                                    break;
                                    
                            }
                            
                                    if(!flag && i>0){
                                        System.out.println(i + " " + k);
                                        if(String.valueOf(lect.charAt(k-i)).equals("\"")){
                                            
                                            tokens.add(new Token(TipoToken.str, "String", prov, line));
                                        }else{
                                            tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                                        }
                                    }else if(!flag && i==0){
                                        tokens.add(new Token(TipoToken.var, "Variable", prov, line));
                                    }
                        }
                        k=0;
                        c=0;
                        flag = false;
                    }
                }else{
                    //Otros atributos
                    
                    if(i>=lect.length()-1){
                        
                        flag = false;
                    }else{
                        prov = prov + String.valueOf(lect.charAt(i+1));
                    }
                    
                    switch(prov){
                        case "!=":
                            tokens.add(new Token(TipoToken.dif, "!=", null, line));
                            flag = true;
                            i++;
                        break;
                        case "==":
                            tokens.add(new Token(TipoToken.igual, "==", null, line));
                            flag = true;
                            i++;
                        break;
                        case "<=":
                            tokens.add(new Token(TipoToken.menori, "<=", null, line));
                            flag = true;
                            i++;
                        break;
                        case ">=":
                            tokens.add(new Token(TipoToken.mayori, ">=", null, line));
                            flag = true;
                            i++;
                        break;
                        case "/*":
                            flag = true;
                            cflag = true;
                            i++;
                        break;
                        case "//":
                            flag = true;
                            cflag = true;
                            i++;
                        break;
                        case "*/":
                            flag = true;
                            cflag = false;
                            i++;
                        break;
                        }
                    
                    if(!flag){
                        prov = String.valueOf(lect.charAt(i));
                        
                        switch(prov){
                        case "{":
                            tokens.add(new Token(TipoToken.cor1, "{", null, line));
                            flag = true;
                        break;
                        case "}":
                            tokens.add(new Token(TipoToken.cor2, "}", null, line));
                            flag = true;
                        break;
                        case "(":
                            tokens.add(new Token(TipoToken.par1, "(", null, line));
                            flag = true;
                        break;
                        case ")":
                            tokens.add(new Token(TipoToken.par2, ")", null, line));
                            flag = true;
                        break;
                        case ".":
                            tokens.add(new Token(TipoToken.punto, ".", null, line));
                            flag = true;
                        break;
                        case ",":
                            tokens.add(new Token(TipoToken.coma, ",", null, line));
                            flag = true;
                        break;
                        case ";":
                            tokens.add(new Token(TipoToken.puntocoma, ";", null, line));
                            flag = true;
                        break;
                        case "-":
                            tokens.add(new Token(TipoToken.menos, "-", null, line));
                            flag = true;
                        break;
                        case "+":
                            tokens.add(new Token(TipoToken.mas, "+", null, line));
                            flag = true;
                        break;
                        case "*":
                            tokens.add(new Token(TipoToken.por, "*", null, line));
                            flag = true;
                        break;
                        case "/":
                            tokens.add(new Token(TipoToken.div, "/", null, line));
                            flag = true;
                        break;
                        case "!":
                            tokens.add(new Token(TipoToken.ex, "!", null, line));
                            flag = true;
                        break;
                        case "=":
                            tokens.add(new Token(TipoToken.asignar, "=", null, line));
                            flag = true;
                        break;
                        case "<":
                            tokens.add(new Token(TipoToken.menor, "<", null, line));
                            flag = true;
                        break;
                        case ">":
                            tokens.add(new Token(TipoToken.mayor, ">", null, line));
                            flag = true;
                        break;
                        case "\"":
                            tokens.add(new Token(TipoToken.comdo, "\"", null, line));
                            flag = true;
                        break;
                        case "'":
                            tokens.add(new Token(TipoToken.comsi, "'", null, line));
                            flag = true;
                        break;
                        
                        }
                        if(!flag){
                            if(!(prov.equals("	") || prov.equals(" "))){
                                System.out.println(prov + " no pertenece al alfabeto ubicado en la posicion> " + line);
                            }
                            
                        }
                    }
                    k=0;
                    c=0;
                    flag = false;
                }
            }
                //Aqui termina el ciclo
            }
              }
              catch(Exception e) {
                  System.out.println("Algo sucedio mal");
              }
            
        
    
        
        
        tokens.add(new Token(TipoToken.EOF, "EOF", null, linea));

        return tokens;
    }
}