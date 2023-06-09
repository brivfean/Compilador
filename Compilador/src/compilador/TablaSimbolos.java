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
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private final Map<String, Object> values = new HashMap<>();

    boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    Object obtener(String identificador) {
        
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }
    
    
    void reasignar(String identificador, Object valor, Object nvalor){
        values.replace(identificador, valor, nvalor);
    }
    
    void p(){
        System.out.println(values);
    }
    
    public Map<String, Object> trasladar() {
         return values;
    }
    
    public Map<String, Object> incorporar(Map<String, Object> valor) {
         values.putAll(valor);
         return values;
    }


}