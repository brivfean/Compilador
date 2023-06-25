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
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.List;

public class Arbol {
    private final Nodo raiz;

    ArrayList<Object> variables = new ArrayList<Object>();
    
    public Arbol(Nodo raiz){
        this.raiz = raiz;
    }

    public void recorrer(){
        for(Nodo n : raiz.getHijos()){
            Token t = n.getValue();
            switch (t.tipo){
                // Operadores aritméticos
                case mas:
                case menos:
                case por:
                case div:
                    SolverAritmetico solver = new SolverAritmetico(n);
                    Object res = solver.resolver();
                    System.out.println(res);
                break;

                case var:
                    // Crear una variable. Usar tabla de simbolos
                    
                    //TablaSimbolos ts = new TablaSimbolos();
                    //ts.existeIdentificador(t.tipo.name());
                    //ts.obtener(t.tipo.name());
                    //ts.asignar(t.tipo.name(), t.lexema);
                    break;
                case ide:
                    int enc = 0;
                    for(Object str: variables) {
                        if (str.equals(t.literal)) {
                            enc++;
                        }
                    }
                    if(enc==0){
                    variables.add(t.literal);
                    enc=0;
                    }else{
                        System.out.println("Error semantico variable " + t.literal + " ya existe");
                        exit(0);
                    }
                    break;
                case si:
                    break;

            }
        }
    }

}

