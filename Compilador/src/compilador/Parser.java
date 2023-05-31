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

    
    
    private final Token si = new Token(TipoToken.si, "", null);
    private final Token clase = new Token(TipoToken.clase, "", null);
    private final Token contra = new Token(TipoToken.contra, "", null);
    private final Token falso = new Token(TipoToken.falso, "", null);
    private final Token mientras = new Token(TipoToken.mientras, "", null);
    private final Token fun = new Token(TipoToken.fun, "", null);
    private final Token nulo = new Token(TipoToken.nulo, "", null);
    private final Token o = new Token(TipoToken.o, "", null);
    private final Token imprimir = new Token(TipoToken.imprimir, "", null);
    private final Token retornar = new Token(TipoToken.retornar, "", null);
    private final Token sup = new Token(TipoToken.sup, "", null);
    private final Token este = new Token(TipoToken.este, "", null);
    private final Token verdad = new Token(TipoToken.verdad, "", null);
    private final Token var = new Token(TipoToken.var, "", null);
    private final Token para = new Token(TipoToken.para, "", null);
    private final Token EOF = new Token(TipoToken.EOF, "", null);
    private final Token cor1 = new Token(TipoToken.cor1, "", null);
    private final Token cor2 = new Token(TipoToken.cor2, "", null);
    private final Token par1 = new Token(TipoToken.par1, "", null);
    private final Token par2 = new Token(TipoToken.par2, "", null);
    private final Token punto = new Token(TipoToken.punto, "", null);
    private final Token coma = new Token(TipoToken.coma, "", null);
    private final Token puntocoma = new Token(TipoToken.puntocoma, "", null);
    private final Token menos = new Token(TipoToken.menos, "", null);
    private final Token mas = new Token(TipoToken.mas, "", null);
    private final Token por = new Token(TipoToken.por, "", null);
    private final Token div = new Token(TipoToken.div, "", null);
    private final Token ex = new Token(TipoToken.ex, "", null);
    private final Token dif = new Token(TipoToken.dif, "", null);
    private final Token asignar = new Token(TipoToken.asignar, "", null);
    private final Token igual = new Token(TipoToken.igual, "", null);
    private final Token menor = new Token(TipoToken.menor, "", null);
    private final Token mayor = new Token(TipoToken.mayor, "", null);
    private final Token menori = new Token(TipoToken.menori, "", null);
    private final Token mayori = new Token(TipoToken.mayori, "", null);
    private final Token ide = new Token(TipoToken.ide, "", null);
    private final Token num = new Token(TipoToken.num, "", null);
    private final Token str = new Token(TipoToken.str, "", null);
    private final Token lla1 = new Token(TipoToken.lla1, "", null);
    private final Token lla2 = new Token(TipoToken.lla2, "", null);

    private int i = 0;
    private boolean hayErrores = false;

    private Token preanalisis;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public void parse(){
        i = 0;
        preanalisis = tokens.get(i);
        Q();

        if(!hayErrores && !preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }
        else if(!hayErrores && preanalisis.equals(finCadena)){
            System.out.println("Consulta válida");
        }

        /*if(!preanalisis.equals(finCadena)){
            System.out.println("Error en la posición " + preanalisis.posicion + ". No se esperaba el token " + preanalisis.tipo);
        }else if(!hayErrores){
            System.out.println("Consulta válida");
        }*/
    }

    void Q(){
        if(preanalisis.equals(select)){
            coincidir(select);
            D();
            coincidir(from);
            T();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba la palabra reservada SELECT.");
        }
    }

    void D(){
        if(hayErrores) return;

        if(preanalisis.equals(distinct)){
            coincidir(distinct);
            P();
        }
        else if(preanalisis.equals(asterisco) || preanalisis.equals(identificador)){
            P();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba DISTINCT, * o un identificador.");
        }
    }

    void P(){
        if(hayErrores) return;

        if(preanalisis.equals(asterisco)){
            coincidir(asterisco);
        }
        else if(preanalisis.equals(identificador)){
            A();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición \" + preanalisis.posicion + \". Se esperaba * o un identificador.");
        }
    }

    void A(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            A2();
            A1();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un identificador.");
        }
    }

    void A1(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            A();
        }
    }

    void A2(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            coincidir(identificador);
            A3();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un identificador.");
        }
    }

    void A3(){
        if(hayErrores) return;

        if(preanalisis.equals(punto)){
            coincidir(punto);
            coincidir(identificador);
        }
    }

    void T(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            T2();
            T1();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un identificador.");
        }
    }

    void T1(){
        if(hayErrores) return;

        if(preanalisis.equals(coma)){
            coincidir(coma);
            T();
        }
    }

    void T2(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            coincidir(identificador);
            T3();
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un identificador.");
        }
    }

    void T3(){
        if(hayErrores) return;

        if(preanalisis.equals(identificador)){
            coincidir(identificador);
        }
    }


    void coincidir(Token t){
        if(hayErrores) return;

        if(preanalisis.tipo == t.tipo){
            i++;
            preanalisis = tokens.get(i);
        }
        else{
            hayErrores = true;
            System.out.println("Error en la posición " + preanalisis.posicion + ". Se esperaba un  " + t.tipo);

        }
    }
    
}
