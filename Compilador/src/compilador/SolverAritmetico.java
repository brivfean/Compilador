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
public class SolverAritmetico {

    private final Nodo nodo;

    TablaSimbolos ts = new TablaSimbolos();
    
    public SolverAritmetico(Nodo nodo) {
        this.nodo = nodo;
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            System.out.println("1");
            if(n.getValue().tipo == TipoToken.num || n.getValue().tipo == TipoToken.str){
                System.out.println("2");
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.ide){
                // Ver la tabla de símbolos
                System.out.println("3");
                if(ts.existeIdentificador(n.getValue().lexema)){
                    throw new RuntimeException("Variable '" + n.getValue().lexema + "' no se encuantra declarada.");
                }
                System.out.println("4");
            }
        }
        System.out.println(n.getHijos().get(0));
        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        System.out.println("5");
        
        Object resultadoIzquierdo = resolver(izq);
        Object resultadoDerecho = resolver(der);

        System.out.println("6");
        
        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case mas:
                    return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                case menos:
                    return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                case por:
                    return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                case div:
                    return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            if (n.getValue().tipo == TipoToken.mas){
                // Ejecutar la concatenación
            }
        }
        else{
            // Error por diferencia de tipos
        }

        return null;
    }
}
