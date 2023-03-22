/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.Scanner;

/**
 *
 * @author bruni
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    boolean flag = true;
        String doc = "";
        Scanner sc = new Scanner(System.in);
        doc = sc.next();
        System.out.println(doc);
        
        
        
    }
    
    public boolean validarDoc(String nom){
		String reg = "[A-Za-z. _]*";
		if(nom.matches(reg)){
			return true;
		}else{
			return false;
		}
    }
    
    public boolean validarNom(String nom){
		String reg = "[A-Za-záéíóúÁÉÍÓÚñÑ ]*";
		if(!contieneNum(nom) && nom.length() < 40 && nom.length() > 3 && nom.matches(reg)){
			return true;
		}else{
			return false;
		}
    }
    
    public boolean contieneNum(String num){
		for(int i = 0; i < num.length(); i++){
			char c = num.charAt(i);
			if(Character.isDigit(c)){
				return true;
			}
		}
		return false;
    }
    
}
