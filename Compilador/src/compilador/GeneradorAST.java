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
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;
    private final Stack<Nodo> pila;
    private boolean vflag = false, rflag = false, iflag = false, oflag = false, aflag = false;
    private String iden;
    private Object val;
    private int idv = 0;
    
    TablaSimbolos ts = new TablaSimbolos();
    
    public GeneradorAST(List<Token> postfija){
        this.postfija = postfija;
        this.pila = new Stack<>();
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
                    System.out.println("Entro");
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
                    }
                    pila.push(n);
                    System.out.println("Paso");
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
                idv = 0;
                
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
                        /*
                        En el caso del VAR, es necesario eliminar el igual que
                        pudiera aparecer en la raíz del nodo n.
                         */
                        if(n.getValue().tipo == TipoToken.igual){
                            padre.insertarHijos(n.getHijos());
                            
                            SolverAritmetico sa = new SolverAritmetico(n);
                        
                            val = sa.resolver();
                            
                        }
                        else{
                            padre.insertarSiguienteHijo(n);
                            
                            val = null;
                        }
                        
                        
                        
                        
                        System.out.println(iden + " " + val);
                        
                        ts.asignar(iden, val);
                        //ts.obtener(iden);
                        
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else if(padre.getValue().tipo == TipoToken.imprimir){
                        padre.insertarSiguienteHijo(n);
                        pilaPadres.pop();
                        padre = pilaPadres.peek();
                    }
                    else {
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        //Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz);

        return programa;
    }
}
