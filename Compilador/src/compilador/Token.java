/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author bruni
 */
public class Token {
    final TipoToken tipo;
    final String lexema;
    final Object literal;
    final int linea;

    public Token(TipoToken tipo, String lexema, Object literal, int linea) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = linea;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
        this.linea = 0;
    }

    public String toString(){
        return tipo + " " + lexema + " " + (literal == null ? " " : literal.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        if(this.tipo == ((Token)o).tipo){
            return true;
        }

        return false;
    }

    /*public String toString(){
        return tipo + " " + lexema + " ";
    }*/
    
    // Métodos auxiliares
    public boolean esOperando(){
        switch (this.tipo){
            case ide:
            case num:
            case str:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case mas:
            case menos:
            case por:
            case div:
            case igual:
            case mayor:
            case mayori:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case var:
            case si:
            case imprimir:
            case contra:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case si:
            case contra:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t){
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            case por:
            case div:
                return 3;
            case mas:
            case menos:
                return 2;
            case igual:
                return 1;
            case mayor:
            case mayori:
                return 1;
        }

        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case por:
            case div:
            case mas:
            case menos:
            case igual:
            case mayor:
            case mayori:
                return 2;
        }
        return 0;
    }
}
