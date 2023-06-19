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
import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private final Token value;
    private List<Nodo> hijos;

    public Nodo(Token value){
        this.value = value;
    }

    public void insertarHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else{
            hijos.add(0, n);
        }
    }

    public void insertarSiguienteHijo(Nodo n){
        if(hijos == null){
            hijos = new ArrayList<>();
            hijos.add(n);
        }
        else{
            hijos.add(n);
        }
    }

    public void insertarHijos(List<Nodo> nodosHijos){
        if(hijos == null){
            hijos = new ArrayList<>();
        }

        for(Nodo n : nodosHijos){
            hijos.add(n);
        }
    }

    public Token getValue(){
        return value;
    }

    public List<Nodo> getHijos(){
        return hijos;
    }
}
