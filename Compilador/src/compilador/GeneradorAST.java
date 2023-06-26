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
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;
    private final Stack<Nodo> pila;
    private boolean vflag = false, rflag = false, iflag = false, oflag = false, aflag = false;
    private String iden;
    private Object val = null;
    private int idv = 0;
    
    TablaSimbolos ts = new TablaSimbolos();
    //Map<String, Object> valores;
    
    public Object ManTS(Object aid){
        if(!ts.existeIdentificador(aid.toString())){
            throw new RuntimeException("Variable '" + aid + "' no se encuantra declarada.");
        }
        ts.obtener(aid.toString());
        return aid;
    }
    
    public GeneradorAST(List<Token> postfija/*, Map<String, Object> valores*/){
        this.postfija = postfija;
        this.pila = new Stack<>();
        //this.valores = valores;
    }

    public Arbol generarAST() {
        Stack<Nodo> pilaPadres = new Stack<>();
        Nodo raiz = new Nodo(null);
        pilaPadres.push(raiz);

        Nodo padre = raiz;

        for(Token t : postfija){
            if(t.tipo == TipoToken.EOF){
                break;
            }

            if(t.esPalabraReservada()){
                Nodo n = new Nodo(t);

                padre = pilaPadres.peek();
                padre.insertarSiguienteHijo(n);

                pilaPadres.push(n);
                padre = n;
                
                vflag = true;

            }
            else if(t.esOperando()){
                Nodo n = new Nodo(t);
                idv++;
                if(!vflag)
                {
                    padre = pilaPadres.peek();
                    padre.insertarSiguienteHijo(n);

                    pilaPadres.push(n);
                    padre = n;
                    //System.out.println("Entro");
                    if(t.tipo == TipoToken.num && idv==1){
                        throw new RuntimeException("Numero no puede asignarse valor '" + t.lexema + "'.");
                    }
                    if(ts.existeIdentificador(t.lexema)){
                        throw new RuntimeException("Redefinicion de variable '" + t.lexema + "'.");
                    }
                    vflag = true;
                }else{
                    
                    if(ts.existeIdentificador(t.lexema)){
                        throw new RuntimeException("Redefinicion de variable '" + t.lexema + "'.");
                    }
                    if(idv==1){
                        iden = t.literal.toString();
                        //System.out.println(iden);
                    }
                    pila.push(n);
                    //System.out.println("Paso");
                }
                
            }
            
            else if(t.esOperador()){
                int aridad = t.aridad();
                Nodo n = new Nodo(t);
                for(int i=1; i<=aridad; i++){
                    Nodo nodoAux = pila.pop();
                    n.insertarHijo(nodoAux);
                }
                pila.push(n);
            }
            else if(t.tipo == TipoToken.puntocoma){

                vflag = false;
                
                
                if (pila.isEmpty()){
                    /*
                    Si la pila esta vacía es porque t es un punto y coma
                    que cierra una estructura de control
                     */
                    pilaPadres.pop();
                    padre = pilaPadres.peek();
                }
                else{
                    Nodo n = pila.pop();

                    if(padre.getValue().tipo == TipoToken.var){ //-----------------------------------------------
                        
                        /*if(n.getValue().tipo == TipoToken.asignar){
                            padre.insertarHijos(n.getHijos());
                            
                            
                        }
                        else{*/
                            //padre.insertarSiguienteHijo(n);
                            
                            if(idv>2){
                                padre.insertarHijos(n.getHijos());
                                System.out.println("++++++++++++++++++++++++++++++++++++");
                                System.out.println(padre);
                                SolverAritmetico sa = new SolverAritmetico(n, ts.trasladar());
                                System.out.println(val + "      -----------------------");
                                val = sa.resolver();
                                System.out.println(val);
                                ts.asignar(iden, val);
                            }else if(idv == 1){
                                padre.insertarSiguienteHijo(n);
                                val = null;
                                ts.asignar(iden, val);
                            }else{
                                padre.insertarSiguienteHijo(n);
                                if(n.getValue().tipo == TipoToken.ide){
                                    if(ts.existeIdentificador(t.lexema)){
                                        throw new RuntimeException("Redefinicion de variable '" + t.lexema + "'.");
                                    }else{
                                        val = ts.obtener(n.getValue().literal.toString());
                                        ts.asignar(iden, val);
                                    }
                                }else{
                                    val = n.getValue().literal;
                                    ts.asignar(iden, val);
                                }
                                
                            }
                            
                        //}
                        ts.p();
                        System.out.println(iden + " -> " + val);
                        
                        val = null;
                        
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }else if(padre.getValue().tipo == TipoToken.imprimir){
                        padre.insertarSiguienteHijo(n);
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else {
                        padre.insertarSiguienteHijo(n);
                    }
                    idv = 0;
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        //Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz);

        return programa;
    }
}
