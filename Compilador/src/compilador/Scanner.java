/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.FileNotFoundException;
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

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("program", TipoToken.programa);
        palabrasReservadas.put("class", TipoToken.clase);
        palabrasReservadas.put("else", TipoToken.contra);
        palabrasReservadas.put("false", TipoToken.falso);
        palabrasReservadas.put("for", TipoToken.para);
        palabrasReservadas.put("fun", TipoToken.fun); //definir funciones
        palabrasReservadas.put("if", TipoToken.si);
        palabrasReservadas.put("null", TipoToken.nulo);
        palabrasReservadas.put("or", TipoToken.o);
        palabrasReservadas.put("and", TipoToken.y);
        palabrasReservadas.put("print", TipoToken.imprimir);
        palabrasReservadas.put("return", TipoToken.retornar);
        palabrasReservadas.put("super", TipoToken.sup);
        palabrasReservadas.put("this", TipoToken.este);
        palabrasReservadas.put("true", TipoToken.verdad);
        palabrasReservadas.put("var", TipoToken.var); //definir variables
        palabrasReservadas.put("while", TipoToken.mientras);
        palabrasReservadas.put("id", TipoToken.ide);
        palabrasReservadas.put("{", TipoToken.cor1);
        palabrasReservadas.put("}", TipoToken.cor2);
        palabrasReservadas.put("(", TipoToken.par1);
        palabrasReservadas.put(")", TipoToken.par2);
        palabrasReservadas.put("[", TipoToken.lla1);
        palabrasReservadas.put("]", TipoToken.lla2);
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
        this.source = source + " ";
    }

    List<Token> scanTokens(){
        int estado = 0, ccount = 0;
        char caracter = 0;
        String lexema = "";
        boolean comflag = true, cflag = false;

        for(int i=0; i<source.length(); i++){
            caracter = source.charAt(i);
            
            switch (estado){
                case 0:
                    if(caracter == '*'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.por, "*", null));
                    }
                    else if(caracter == '+'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.mas, "+", null));
                    }
                    else if(caracter == '-'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.menos, "-", null));
                    }
                    else if(caracter == '/'){
                        if(i<source.length()){
                            if(source.charAt(i+1) == '*'){
                                comflag = false;
                            }else if(source.charAt(i-1) == '*'){
                                comflag = true;
                            }else{
                                if(comflag)
                                tokens.add(new Token(TipoToken.div, "/", null));
                            }
                        }
                    }
                    else if(caracter == '('){
                        if(comflag)
                        tokens.add(new Token(TipoToken.par1, "(", null));
                    }
                    else if(caracter == ')'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.par2, ")", null));
                    }
                    else if(caracter == '='){
                        if(comflag)
                        tokens.add(new Token(TipoToken.asignar, "=", null));
                    }
                    else if(caracter == '{'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.cor1, "{", null));
                    }
                    else if(caracter == '}'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.cor2, "}", null));
                    }
                    else if(caracter == ';'){
                        if(comflag)
                        tokens.add(new Token(TipoToken.puntocoma, ";", null));
                    }
                    else if(Character.isAlphabetic(caracter)){
                        estado = 1;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '\"'){
                        estado = 1;
                        ccount++;
                        cflag = true;
                        lexema = lexema + caracter;
                    }/*else if(caracter == ' ' && cflag && ccount<=2){
                        estado = 1;
                        lexema = lexema + caracter;
                    }*/
                    else if(Character.isDigit(caracter)){
                        estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '>'){
                        estado = 8;
                    }
                    break;

                case 1:
                    if(Character.isAlphabetic(caracter) || Character.isDigit(caracter) || (cflag && caracter == ' ') || caracter == '\"'){
                        lexema = lexema + caracter;
                        //System.out.println(ccount);
                        if(caracter == '\"'){
                            ccount++;
                            if(ccount==2){
                                cflag = false;
                                //System.out.println(ccount);
                            }
                        }
                    }
                    else{
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(ccount==2){
                            if(comflag)
                            tokens.add(new Token(TipoToken.str, "STR", lexema));
                        }else if(tt == null){
                            if(comflag)
                            tokens.add(new Token(TipoToken.ide, "IDE", lexema));
                        }else{
                            if(comflag)
                            tokens.add(new Token(tt, null, lexema));
                            cflag=false;
                            ccount = 0;
                        }
                        ccount = 0;
                        cflag=false;
                        estado = 0;
                        i--;
                        lexema = "";
                    }
                    break;
                case 2:
                    if(Character.isDigit(caracter)){
                        estado = 2;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == '.'){
                        estado = 3;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 5;
                        lexema = lexema + caracter;
                    }
                    else{
                        if(comflag)
                        tokens.add(new Token(TipoToken.num, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 3:
                    if(Character.isDigit(caracter)){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else{
                        //Lanzar error
                    }
                    break;
                case 4:
                    if(Character.isDigit(caracter)){
                        estado = 4;
                        lexema = lexema + caracter;
                    }
                    else if(caracter == 'E'){
                        estado = 5;
                        lexema = lexema + caracter;
                    }
                    else{
                        if(comflag)
                        tokens.add(new Token(TipoToken.num, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 5:
                    if(caracter == '+' || caracter == '-'){
                        estado = 6;
                        lexema = lexema + caracter;
                    }
                    else if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        // Lanzar error
                    }
                    break;
                case 6:
                    if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        // Lanzar error
                    }
                    break;
                case 7:
                    if(Character.isDigit(caracter)){
                        estado = 7;
                        lexema = lexema + caracter;
                    }
                    else{
                        if(comflag)
                        tokens.add(new Token(TipoToken.num, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 8:
                    if(caracter == '='){
                        if(comflag)
                        tokens.add(new Token(TipoToken.mayori, ">=", null));
                    }
                    else{
                        if(comflag)
                        tokens.add(new Token(TipoToken.mayor, ">", null));
                        i--;
                    }
                    estado = 0;
                    break;
            }
        
        }
        tokens.add(new Token(TipoToken.EOF, "", null));

        return tokens;
    }

}

    /*
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("program", TipoToken.programa);
        palabrasReservadas.put("class", TipoToken.clase);
        palabrasReservadas.put("else", TipoToken.contra);
        palabrasReservadas.put("false", TipoToken.falso);
        palabrasReservadas.put("for", TipoToken.para);
        palabrasReservadas.put("fun", TipoToken.fun); //definir funciones
        palabrasReservadas.put("if", TipoToken.si);
        palabrasReservadas.put("null", TipoToken.nulo);
        palabrasReservadas.put("or", TipoToken.o);
        palabrasReservadas.put("and", TipoToken.y);
        palabrasReservadas.put("print", TipoToken.imprimir);
        palabrasReservadas.put("return", TipoToken.retornar);
        palabrasReservadas.put("super", TipoToken.sup);
        palabrasReservadas.put("this", TipoToken.este);
        palabrasReservadas.put("true", TipoToken.verdad);
        palabrasReservadas.put("var", TipoToken.var); //definir variables
        palabrasReservadas.put("while", TipoToken.mientras);
        palabrasReservadas.put("id", TipoToken.ide);
        palabrasReservadas.put("{", TipoToken.cor1);
        palabrasReservadas.put("}", TipoToken.cor2);
        palabrasReservadas.put("(", TipoToken.par1);
        palabrasReservadas.put(")", TipoToken.par2);
        palabrasReservadas.put("[", TipoToken.lla1);
        palabrasReservadas.put("]", TipoToken.lla2);
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
        boolean flag = true, cflag = false, pflag = false; //Bandera para validar archivo introducido y parametros con clase validar
        String lect = "", prov ; //Nombre del documento y lectura del documento
        
        
            
        lect = source;
            
        Validaciones Validar = new Validaciones(); //Abrir la clase validaciones
            
        
        
            
            try {

            //System.out.println(lect);//Imprecion de texto en consola
            flag = false;
            
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
                if(pflag){
                    c++;
                    pflag = false;
                }
                
                if(String.valueOf(lect.charAt(i)).equals(".")){
                    pflag = true;
                }
                if(Validar.validarVar(prov) || prov.equals('.')){
                    //Diferenciar variables de parabras reservadas
                    k++;
                    
                    if(lect.length()-1<=i){
                        prov = "´";
                    }else{
                        prov = String.valueOf(lect.charAt(i+1));
                    }
                    
                    if(prov.equals('.')){
                        pflag = true;
                    }
                    
                    if(!Validar.validarVar(prov) && !prov.equals('.')){
                        
                        //Si ya no hay letras o numeros
                        if(flag && c<k){
                            //Es una variable con numero
                            prov = "";
                            if(String.valueOf(lect.charAt(i-k)).equals("\"") || String.valueOf(lect.charAt(i)).equals("\"")){
                                for(j=0;j<k;j++){
                                    prov = String.valueOf(lect.charAt(i-j)) + prov;
                                }
                                tokens.add(new Token(TipoToken.str, "STR", prov , line));
                            }else{
                                for(j=0;j<k;j++){
                                    prov = String.valueOf(lect.charAt(i-j)) + prov;
                                }
                                line++;;
                                tokens.add(new Token(TipoToken.ide, "IDE", prov, line));
                            }
                        }else if(flag && c==k && !pflag){
                            //System.out.println(String.valueOf(lect.charAt(i-1))); //Arreglar esto que lo captura en -1
                            
                            /*if(String.valueOf(lect.charAt(i-1)).equals(".") && c==1 && k==1 && Validar.validarNum(String.valueOf(lect.charAt(i+1)))){
                                tokens.add(new Token(TipoToken.punto, ".", null, line));
                            }
                            else{
                            prov="";
                            for(j=0;j<c;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.num, "NUM", prov, line));
                            //}
                        }else if(flag && c==k && pflag){
                            //System.out.println(String.valueOf(lect.charAt(i-1))); //Arreglar esto que lo captura en -1
                            
                            prov="";
                            for(j=0;j<c;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            line++;
                            tokens.add(new Token(TipoToken.num, "NUM", prov, line));
                            //}
                        }else if(!flag && k==1 && !String.valueOf(lect.charAt(i-k)).equals("(") && !String.valueOf(lect.charAt(i-k)).equals("\"")){
                            if(i>0){
                                    prov = String.valueOf(lect.charAt(i));
                                    line++;
                                    tokens.add(new Token(TipoToken.ide, "IDE", prov , line));
                            }
                            
                        }/*else if(!flag && !String.valueOf(lect.charAt(i-k)).equals("(") && String.valueOf(lect.charAt(i-k)).equals("\"")){
                            if(i>0){
                                    prov = String.valueOf(lect.charAt(i));
                                    line++;
                                    tokens.add(new Token(TipoToken.str, "STR", prov , line));
                            }
                        }
                        else{
                            prov = "";
                            
                            for(j=0;j<k;j++){
                                prov = String.valueOf(lect.charAt(i-j)) + prov;
                            }
                            
                            line++;
                            
                            switch(prov){
                                    case "if" :
                                        tokens.add(new Token(TipoToken.si, "IF", null, line));
                                        flag = true;
                                    break;
                                    case "class" :
                                        tokens.add(new Token(TipoToken.clase, "CLASE", null, line));
                                        flag = true;
                                    break;
                                    case "else" :
                                        tokens.add(new Token(TipoToken.contra, "ELSE", null, line));
                                        flag = true;
                                    break;
                                    case "false" :
                                        tokens.add(new Token(TipoToken.falso, "FALSE", null, line));
                                        flag = true;
                                    break;
                                    case "while" :
                                        tokens.add(new Token(TipoToken.mientras, "WHILE", null, line));
                                        flag = true;
                                    break;
                                    case "fun" :
                                        tokens.add(new Token(TipoToken.fun, "FUN", null, line));
                                        flag = true;
                                    break;
                                    case "null" :
                                        tokens.add(new Token(TipoToken.nulo, "NULL", null, line));
                                        flag = true;
                                    break;
                                    case "or" :
                                        tokens.add(new Token(TipoToken.o, "OR", null, line));
                                        flag = true;
                                    break;
                                    case "and" :
                                        tokens.add(new Token(TipoToken.y, "AND", null, line));
                                        flag = true;
                                    break;
                                    case "print" :
                                        tokens.add(new Token(TipoToken.imprimir, "PRINT", null, line));
                                        flag = true;
                                    break;
                                    case "return" :
                                        tokens.add(new Token(TipoToken.retornar, "RETURN", null, line));
                                        flag = true;
                                    break;
                                    case "super" :
                                        tokens.add(new Token(TipoToken.sup, "SUPER", null, line));
                                        flag = true;
                                    break;
                                    case "this" :
                                        tokens.add(new Token(TipoToken.este, "THIS", null, line));
                                        flag = true;
                                    break;
                                    case "true" :
                                        tokens.add(new Token(TipoToken.verdad, "TRUE", null, line));
                                        flag = true;
                                    break;
                                    case "for" :
                                        tokens.add(new Token(TipoToken.para, "FOR", null, line));
                                        flag = true;
                                    break;
                                    case "program" :
                                        tokens.add(new Token(TipoToken.programa, "PROGRAM", null, line));
                                        flag = true;
                                    break;
                                    case "var" :
                                        tokens.add(new Token(TipoToken.var, "VAR", null, line));
                                        flag = true;
                                    break;
                                    
                            }
                            
                                    if(!flag && i>k){
                                        
                                        if(String.valueOf(lect.charAt(i-k)).equals("\"") && String.valueOf(lect.charAt(i+1)).equals("\"")){
                                            tokens.add(new Token(TipoToken.str, "STR", prov , line));
                                        }else{
                                            tokens.add(new Token(TipoToken.ide, "IDE", prov, line));
                                        }
                                    }else if(!flag && i>=0){
                                        tokens.add(new Token(TipoToken.ide, "IDE", prov, line));
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
                        case "":
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
                        case "[":
                            tokens.add(new Token(TipoToken.lla1, "[", null, line));
                            flag = true;
                        break;
                        case "]":
                            tokens.add(new Token(TipoToken.lla2, "]", null, line));
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
                            //tokens.add(new Token(TipoToken.comdo, "\"", null, line));
                            flag = true;
                        break;
                        case "'":
                            //tokens.add(new Token(TipoToken.comsi, "'", null, line));
                            flag = true;
                        break;
                        
                        }
                        if(!flag){
                            if(!(prov.equals("	") || prov.equals(" "))){
                                //System.out.println("ERROR");
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
                  throw new RuntimeException("Error lexico");
              }
            
        
    
        
        
        tokens.add(new Token(TipoToken.EOF, "EOF", null, linea));
        //System.out.println("aqio");
        return tokens;
    }
    
    
}*/