/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 *
 * @author bruni
 */
public class Compilador {
    
    static boolean existenErrores = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        if(args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");

            // Convención defininida en el archivo "system.h" de UNIX
            System.exit(64);
        } else if(args.length == 1){
            ejecutarArchivo(args[0]);
        } else{
            ejecutarPrompt();
        }
    
    }
        
    private static void ejecutarArchivo(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()), null);

        // Se indica que existe un error
        if(existenErrores) System.exit(65);
    }

    private static void ejecutarPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        
        TablaSimbolos ts = new TablaSimbolos(); 
        
        Map<String, Object> valores =ts.trasladar();
        
        for(;;){
            System.out.print(">>>");
            String linea = reader.readLine();
            if(linea == null) break; // Presionar Ctrl + D
            ejecutar(linea, valores);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source, Map<String, Object> valores) throws IOException{
        Scanner scanner = new Scanner(source);
        
        List<compilador.Token> tokens = scanner.scanTokens();

        for(compilador.Token token : tokens){
            System.out.println(token);
        }
        
        Parser parser = new Parser(tokens);
        parser.parse();
        
        
        GeneradorPostfija gpf = new GeneradorPostfija(tokens);
        List<Token> postfija = gpf.convertir();
        
        TablaSimbolos ts = new TablaSimbolos();
        
        valores =ts.trasladar();
        
        //System.out.println("---------------------------------------");
        for(Token token : postfija){
            System.out.println(token);
            /*try{
                if(token.tipo.equals(t.id) && token.literal == null){
                ts.existeIdentificador(token.literal.toString());
                ts.obtener(token.literal.toString());
                ts.asignar(token.literal.toString(), token.literal);
                }
            }catch(Exception e){
                System.out.println("..");
            }*/
            
            
        }
        System.out.println("---------------------------------------");
        
        GeneradorAST gast = new GeneradorAST(postfija, valores);
        Arbol programa = gast.generarAST();
        valores = gast.obtval();
        programa.recorrer();
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores:
    Interprete.error(....);
     */
    static void error(int linea, String mensaje){
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje){
        System.err.println(
                "[linea " + linea + "] Error " + donde + ": " + mensaje
        );
        existenErrores = true;
    }
    
}
