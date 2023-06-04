/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.List;

/**
 *
 * @author bruni
 */
public class Parser {

    private final List<Token> tokens;

    
    private final Token programa = new Token(TipoToken.programa, "PROGRAMA", null);
    private final Token si = new Token(TipoToken.si, "IF", null);
    private final Token clase = new Token(TipoToken.clase, "CLASE", null);
    private final Token contra = new Token(TipoToken.contra, "ELSE", null);
    private final Token falso = new Token(TipoToken.falso, "FALSE", null);
    private final Token mientras = new Token(TipoToken.mientras, "WHILE", null);
    private final Token fun = new Token(TipoToken.fun, "FUNCTION", null);
    private final Token nulo = new Token(TipoToken.nulo, "NULL", null);
    private final Token o = new Token(TipoToken.o, "OR", null);
    private final Token y = new Token(TipoToken.y, "AND", null);
    private final Token imprimir = new Token(TipoToken.imprimir, "PRINT", null);
    private final Token retornar = new Token(TipoToken.retornar, "RETURN", null);
    private final Token sup = new Token(TipoToken.sup, "SUPER", null);
    private final Token este = new Token(TipoToken.este, "THIS", null);
    private final Token verdad = new Token(TipoToken.verdad, "TRUE", null);
    private final Token var = new Token(TipoToken.var, "", null);
    private final Token para = new Token(TipoToken.para, "FOR", null);
    private final Token EOF = new Token(TipoToken.EOF, "", null);
    private final Token cor1 = new Token(TipoToken.cor1, "{", null);
    private final Token cor2 = new Token(TipoToken.cor2, "}", null);
    private final Token par1 = new Token(TipoToken.par1, "(", null);
    private final Token par2 = new Token(TipoToken.par2, ")", null);
    private final Token punto = new Token(TipoToken.punto, ".", null);
    private final Token coma = new Token(TipoToken.coma, ",", null);
    private final Token puntocoma = new Token(TipoToken.puntocoma, ";", null);
    private final Token menos = new Token(TipoToken.menos, "-", null);
    private final Token mas = new Token(TipoToken.mas, "+", null);
    private final Token por = new Token(TipoToken.por, "*", null);
    private final Token div = new Token(TipoToken.div, "/", null);
    private final Token ex = new Token(TipoToken.ex, "!", null);
    private final Token dif = new Token(TipoToken.dif, "!=", null);
    private final Token asignar = new Token(TipoToken.asignar, "=", null);
    private final Token igual = new Token(TipoToken.igual, "==", null);
    private final Token menor = new Token(TipoToken.menor, "<", null);
    private final Token mayor = new Token(TipoToken.mayor, ">", null);
    private final Token menori = new Token(TipoToken.menori, "<=", null);
    private final Token mayori = new Token(TipoToken.mayori, ">=", null);
    private final Token ide = new Token(TipoToken.ide, "", null);
    private final Token num = new Token(TipoToken.num, "", null);
    private final Token str = new Token(TipoToken.str, "", null);
    private final Token lla1 = new Token(TipoToken.lla1, "[", null);
    private final Token lla2 = new Token(TipoToken.lla2, "]", null);

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        PR();

        if(!hayErrores && !preanalisis.equals(EOF)){
            System.out.println("Error en la posición " + preanalisis.linea + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(EOF)){
            System.out.println("Sintaxis válida");
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    void PR(){
        if(preanalisis.equals(programa)){
            coincidir(programa);
            DECL();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba la palabra reservada PROGRAM.");
        }
    }

    void DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(clase)){
            CLASS_DECL();
            DECL();
        }
        else if(preanalisis.equals(fun)){
            FUN_DECL();
            DECL();
        }
        else if(preanalisis.equals(var)){
            VAR_DECL();
            DECL();
        }
        else if(preanalisis.equals(ex) 
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            STMT();
            DECL();
        }
        else if(preanalisis.equals(EOF)){
            System.out.println(" ");
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba CLASS, FUNCTION, VARIABLE, !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void CLASS_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(clase)){
            coincidir(clase);
            coincidir(ide);
            CLASS_INHER();
            coincidir(cor1);
            FUNCTS();
            coincidir(cor2);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba CLASS.");
        }
    }
    
    void FUNCTS(){
        if(hayErrores) return;

        if(preanalisis.equals(ide)){
            FUNCT();
        }
        else{
            //e
            
        }
    }
    
    void CLASS_INHER(){
        if(hayErrores) return;

        if(preanalisis.equals(menor)){
            coincidir(menor);
            coincidir(ide);
        }
        else{
            //e
            
        }
    }

    void FUN_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(fun)){
            coincidir(fun);
            FUNCT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba FUNCTION.");
        }
    }
    
    void FUNCT(){
        if(hayErrores) return;

        if(preanalisis.equals(ide)){
            coincidir(ide);
            coincidir(par1);
            PARAMETERS_OPC();
            coincidir(par2);
            BLOCK();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba ID.");
        }
    }
    
    void PARAMETERS_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(ide)){
            PARAMETERS();
        }
        else{
            //e
            
        }
    }
    
    void PARAMETERS(){
        if(hayErrores) return;

        if(preanalisis.equals(ide)){
            coincidir(ide);
            PARAMETERS_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba ID.");
        }
    }
    
    void PARAMETERS_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            coincidir(ide);
            PARAMETERS_2();
        }
        else{
            //e
            
        }
    }
    
    void VAR_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(fun)){
            coincidir(var);
            coincidir(ide);
            VAR_INIT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba FUNCTION.");
        }
    }
    
    void VAR_INIT(){
        if(hayErrores) return;

        if(preanalisis.equals(asignar)){
            coincidir(asignar);
            EXPR();
        }
        else{
            //e
        }
    }
    
    void STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(ex) 
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR_STMT();
        }else if(preanalisis.equals(para)){
            FOR_STMT();
        }else if(preanalisis.equals(si)){
            IF_STMT();
        }else if(preanalisis.equals(imprimir)){
            PRINT_STMT();
        }else if(preanalisis.equals(retornar)){
            RETURN_STMT();
        }else if(preanalisis.equals(mientras)){
            WHILE_STMT();
        }else if(preanalisis.equals(cor1)){
            BLOCK();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba FOR, IF, PRINT, RETURN, WHILE, {, !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void BLOCK(){
        if(hayErrores) return;

        if(preanalisis.equals(cor1)){
            coincidir(cor1);
            BLOCK_DECL();
            coincidir(cor2);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba {.");
        }
    }
    
    void BLOCK_DECL(){
        if(hayErrores) return;

        if(preanalisis.equals(ex) 
                || preanalisis.equals(clase)
                || preanalisis.equals(fun)
                || preanalisis.equals(var)
                || preanalisis.equals(EOF)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            DECL();
            BLOCK_DECL();
        }
        else{
            //e
            
        }
    }
    
    void WHILE_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(mientras)){
            coincidir(mientras);
            coincidir(par1);
            EXPR();
            coincidir(par2);
            STMT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba WHILE.");
        }
    }
    
    void RETURN_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(retornar)){
            coincidir(retornar);
            RETURN_EXP_OPC();
            coincidir(puntocoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba RETURN.");
        }
    }
    
    void RETURN_EXP_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR();
        }
        else{
            //e
            
        }
    }
    
    void PRINT_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(imprimir)){
            coincidir(imprimir);
            EXPR();
            coincidir(puntocoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba PRINT.");
        }
    }
    
    void IF_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(si)){
            coincidir(si);
            coincidir(par1);
            EXPR();
            coincidir(par2);
            STMT();
            ELSE_STMT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba IF.");
        }
    }
    
    void ELSE_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(contra)){
            coincidir(contra);
            STMT();
        }
        else{
            //e
            
        }
    }
    
    void FOR_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(para)){
            coincidir(para);
            coincidir(par1);
            FOR_STMT_1();
            FOR_STMT_2();
            FOR_STMT_3();
            coincidir(par2);
            STMT();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba FOR.");
        }
    }
    
    void FOR_STMT_3(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR();
        }
        else{
            //e
            
        }
    }
    
    void FOR_STMT_2(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR();
            coincidir(puntocoma);
        }else if(preanalisis.equals(puntocoma)){
            coincidir(puntocoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba ! o ;.");
        }
    }
    
    void FOR_STMT_1(){
        if(hayErrores) return;

        if(preanalisis.equals(var)){
            VAR_DECL();
        }else if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR_STMT();
        }else if(preanalisis.equals(puntocoma)){
            coincidir(puntocoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba VARIABLE, ;, !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void EXPR_STMT(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR();
            coincidir(puntocoma);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void EXPR(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            ASSI();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void ASSI(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            LOGIC_OR();
            ASSIGMENT_OPC();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void ASSIGMENT_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(asignar)){
            coincidir(asignar);
            EXPR();
        }
        else{
            //e
            
        }
    }
    
    void LOGIC_OR(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            LOGIC_AND();
            LOGIC_OR_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void LOGIC_OR_2(){
        if(hayErrores) return;

        if(preanalisis.equals(o)){
            coincidir(o);
            LOGIC_AND();
            LOGIC_OR_2();
        }
        else{
            //e
            
        }
    }
    
    void LOGIC_AND(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EQUALITY();
            LOGIC_AND_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void LOGIC_AND_2(){
        if(hayErrores) return;

        if(preanalisis.equals(y)){
            coincidir(y);
            EQUALITY();
            LOGIC_AND_2();
        }
        else{
            //e
            
        }
    }
    
    void EQUALITY(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            COMPARISON();
            EQUALITY_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void EQUALITY_2(){
        if(hayErrores) return;

        if(preanalisis.equals(dif)){
            coincidir(dif);
            COMPARISON();
            EQUALITY_2();
        }else if(preanalisis.equals(igual)){
            coincidir(igual);
            COMPARISON();
            EQUALITY_2();
        }
        else{
            //e
            
        }
    }
    
    void COMPARISON(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            TERM();
            COMPARISON_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void COMPARISON_2(){
        if(hayErrores) return;

        if(preanalisis.equals(mayor)){
            coincidir(mayor);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(mayori)){
            coincidir(mayori);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(menor)){
            coincidir(menor);
            TERM();
            COMPARISON_2();
        }else if(preanalisis.equals(menori)){
            coincidir(menori);
            TERM();
            COMPARISON_2();
        }
        else{
            //e
            
        }
    }
    
    void TERM(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            FACTOR();
            TERM_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void TERM_2(){
        if(hayErrores) return;

        if(preanalisis.equals(menos)){
            coincidir(menos);
            FACTOR();
            TERM_2();
        }else if(preanalisis.equals(mas)){
            coincidir(mas);
            FACTOR();
            TERM_2();
        }
        else{
            //e
            
        }
    }
    
    void FACTOR(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide)  
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            UNARY();
            FACTOR_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void FACTOR_2(){
        if(hayErrores) return;

        if(preanalisis.equals(div)){
            coincidir(div);
            UNARY();
            FACTOR_2();
        }else if(preanalisis.equals(por)){
            coincidir(por);
            UNARY();
            FACTOR_2();
        }
        else{
            //e
            
        }
    }
    
    void UNARY(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)){
            coincidir(ex);
            UNARY();
        }else if(preanalisis.equals(menos)){
            coincidir(menos);
            UNARY();
        }else if(preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            CALL();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void CALL(){
        if(hayErrores) return;

        if(preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            PRIMARY();
            CALL_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void CALL_2(){
        if(hayErrores) return;

        if(preanalisis.equals(par1)){
            coincidir(par1);
            ARGUMENTS_OPC();
            coincidir(par2);
            CALL_2();
        }else if(preanalisis.equals(punto)){
            coincidir(punto);
            coincidir(ide);
            CALL_2();
        }
        else{
            //e
            
        }
    }
    
    void ARGUMENTS_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            ARGUMENTS();
        }
        else{
            //e
            
        }
    }
    
    void ARGUMENTS(){
        if(hayErrores) return;

        if(preanalisis.equals(ex)
                || preanalisis.equals(menos) 
                || preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            EXPR();
            ARGUMENTS_2();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba !, -, TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    void ARGUMENTS_2(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            EXPR();
            ARGUMENTS_2();
        }
        else{
            //e
            
        }
    }
    
    void CALL_OPC(){
        if(hayErrores) return;

        if(preanalisis.equals(verdad) 
                || preanalisis.equals(falso) 
                || preanalisis.equals(nulo) 
                || preanalisis.equals(este) 
                || preanalisis.equals(num) 
                || preanalisis.equals(str) 
                || preanalisis.equals(ide) 
                || preanalisis.equals(par1) 
                || preanalisis.equals(sup)){
            CALL();
            coincidir(punto);
        }
        else{
            //e
            
        }
    }
    
    void PRIMARY(){
        if(hayErrores) return;

        if(preanalisis.equals(verdad)){
            coincidir(verdad);
        }else if(preanalisis.equals(falso)){
            coincidir(falso);
        }else if(preanalisis.equals(nulo)){
            coincidir(nulo);
        }else if(preanalisis.equals(este)){
            coincidir(este);
        }else if(preanalisis.equals(num)){
            coincidir(num);
        }else if(preanalisis.equals(str)){
            coincidir(str);
        }else if(preanalisis.equals(ide)){
            coincidir(ide);
        }else if(preanalisis.equals(par1)){
            coincidir(par1);
            EXPR();
            coincidir(par2);
        }else if(preanalisis.equals(sup)){
            coincidir(sup);
            coincidir(punto);
            coincidir(ide);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba TRUE, FALSE, NULL, THIS, NUMBER, STRING, ID, ( o SUPER.");
        }
    }
    
    /*
    void (){
        if(hayErrores) return;

        if(preanalisis.equals()){
            
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }
    */
    
    //---------------------------------------------------------------------------------
    
    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.linea + ". Se esperaba un  " + t.tipo);

        }
    }
    
}
