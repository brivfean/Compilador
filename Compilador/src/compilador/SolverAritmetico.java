/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bruni
 */
public class SolverAritmetico {

    private final Nodo nodo;

    TablaSimbolos ts = new TablaSimbolos();
    Map<String, Object> values =ts.trasladar();
    
    private String prov;
    private int ver = 0;
    Nodo izq;
    Nodo der;
    Nodo tre;
    Object resultadoIzquierdo;
    Object resultadoDerecho;
    Object resultadoTre;
    boolean nflag = false, sflag = false, bflag;
    double prv = 0;
    
    public SolverAritmetico(Nodo nodo, Map<String, Object> values) {
        this.nodo = nodo;
        this.values = values;
        //System.out.println(nodo.getValue());
    }

    public Object resolver(){
        return resolver(nodo);
    }
    private Object resolver(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            //System.out.println("1");
            if(n.getValue().tipo == TipoToken.num || n.getValue().tipo == TipoToken.str){
                if(n.getValue().tipo == TipoToken.num){
                    nflag = true;
                }else{
                    nflag = false;
                }
                //System.out.println(nflag);
                if(n.getValue().tipo == TipoToken.str){
                    sflag = true;
                }else{
                    sflag = false;
                }
                //System.out.println(sflag);
                //System.out.println("2");
                //System.out.println(n.getValue().literal);
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.ide){
                // Ver la tabla de símbolos
                //System.out.println("3");
                //System.out.println(n.getValue().literal.toString() + " " + ts.existeIdentificador(n.getValue().literal.toString()));
                System.out.println(values);
                if(n.getValue().tipo == TipoToken.str && n.getValue().tipo == TipoToken.num){
                    if(!values.containsKey(n.getValue().literal.toString())){
                        throw new RuntimeException("Variable '" + n.getValue().literal + "' no se encuantra declarada.");
                    }
                }
                
                //System.out.println("4");
                try{
                    Double.parseDouble(values.get(n.getValue().literal.toString()).toString());
                    nflag = true;
                    sflag = false;
                }catch(Exception e){
                    nflag = false;
                    sflag = true;
                }
                try{
                    return values.get(n.getValue().literal.toString());
                }catch(Exception e){
                    return n.getValue().lexema;
                }
                
                
                
            }
        }
        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        if(ver==0){
            izq = n.getHijos().get(0);
            //System.out.println(izq.getValue().lexema + "-------------");
            der = n.getHijos().get(1);
            //System.out.println(der.getValue().lexema + "-------------");
            try{
                tre = n.getHijos().get(2);
            }catch(Exception e){
                tre = null;
            }
            ver++;
            //System.out.println("5");
            if(n.getValue().tipo == TipoToken.str && n.getValue().tipo == TipoToken.str){
                resultadoIzquierdo = resolver(izq);
            }else{
                resultadoIzquierdo = n.getValue().lexema;
            }
            
        }
        if(ver==1){
            //System.out.println("10");
            if(n.getValue().tipo == TipoToken.str && n.getValue().tipo == TipoToken.str){
                resultadoDerecho = resolver(der);
            }else{
                resultadoDerecho = n.getValue().lexema;
            }
            
        }else if(ver==2){
            try{
                if(n.getValue().tipo == TipoToken.str && n.getValue().tipo == TipoToken.str){
                    resultadoTre = resolver(der);
                }else{
                    resultadoTre = n.getValue().lexema;
                }
            }catch(Exception e){
                prov = "";
            }
        }
        

        
        
        //Object resultadoIzquierdo = resolver(izq);
        //System.out.println("8");
        //Object resultadoDerecho = resolver(der);

        //System.out.println("6");
        
        if(/*resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double*/nflag){
            //System.out.println("avr");
            
            switch (n.getValue().tipo){
                case mas:
                    if(prov.equals("")){
                        prv = (Double.valueOf(resultadoIzquierdo.toString()) + Double.valueOf(resultadoDerecho.toString()));
                        switch (n.getValue().tipo){
                            case mas:
                                return (Double.valueOf(resultadoTre.toString()) + prv);
                            case menos:
                                return (Double.valueOf(resultadoTre.toString()) - prv);
                            case por:
                                return (Double.valueOf(resultadoTre.toString()) * prv);
                            case div:
                                return (Double.valueOf(resultadoTre.toString()) / prv);
                        }
                    }else{
                        return (Double.valueOf(resultadoIzquierdo.toString()) + Double.valueOf(resultadoDerecho.toString()));
                    }
                case menos:
                    if(prov.equals("")){
                        prv = (Double.valueOf(resultadoIzquierdo.toString()) - Double.valueOf(resultadoDerecho.toString()));
                        switch (n.getValue().tipo){
                            case mas:
                                return (Double.valueOf(resultadoTre.toString()) + prv);
                            case menos:
                                return (Double.valueOf(resultadoTre.toString()) - prv);
                            case por:
                                return (Double.valueOf(resultadoTre.toString()) * prv);
                            case div:
                                return (Double.valueOf(resultadoTre.toString()) / prv);
                        }
                    }else{
                        return (Double.valueOf(resultadoIzquierdo.toString()) + Double.valueOf(resultadoDerecho.toString()));
                    }
                case por:
                    if(prov.equals("")){
                        prv = (Double.valueOf(resultadoIzquierdo.toString()) * Double.valueOf(resultadoDerecho.toString()));
                        switch (n.getValue().tipo){
                            case mas:
                                return (Double.valueOf(resultadoTre.toString()) + prv);
                            case menos:
                                return (Double.valueOf(resultadoTre.toString()) - prv);
                            case por:
                                return (Double.valueOf(resultadoTre.toString()) * prv);
                            case div:
                                return (Double.valueOf(resultadoTre.toString()) / prv);
                        }
                    }else{
                        return (Double.valueOf(resultadoIzquierdo.toString()) + Double.valueOf(resultadoDerecho.toString()));
                    }
                case div:
                    if(prov.equals("")){
                        prv = (Double.valueOf(resultadoIzquierdo.toString()) / Double.valueOf(resultadoDerecho.toString()));
                        switch (n.getValue().tipo){
                            case mas:
                                return (Double.valueOf(resultadoTre.toString()) + prv);
                            case menos:
                                return (Double.valueOf(resultadoTre.toString()) - prv);
                            case por:
                                return (Double.valueOf(resultadoTre.toString()) * prv);
                            case div:
                                return (Double.valueOf(resultadoTre.toString()) / prv);
                        }
                    }else{
                        return (Double.valueOf(resultadoIzquierdo.toString()) + Double.valueOf(resultadoDerecho.toString()));
                    }
            }
        }
        else if(/*resultadoIzquierdo instanceof String && resultadoDerecho instanceof String*/sflag){
            if (n.getValue().tipo == TipoToken.mas){
                // Ejecutar la concatenación
                try{
                    return resultadoIzquierdo.toString() + resultadoDerecho.toString() + resultadoTre.toString();
                }catch(Exception e){
                    System.out.println(values);
                    System.out.println(resultadoDerecho.toString());
                    return resultadoIzquierdo.toString() + resultadoDerecho.toString();
                }
                
            }
        }
        else{
            // Error por diferencia de tipos
            throw new RuntimeException("Valores incompatibles '" + resultadoIzquierdo + " " + resultadoDerecho + "' .");
        }
        //System.out.println("9");
        return null;
    }
}
