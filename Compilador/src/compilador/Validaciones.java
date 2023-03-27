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
public class Validaciones {
    public boolean validarDoc(String nom){
		String reg = "[A-Za-z. _]*";
		if(nom.matches(reg)){
			return true;
		}else{
			return false;
		}
    }
    
    public boolean validarVar(String nom){
		String reg = "[a-zA-Z0-9]*";
		if(nom.matches(reg)){
			return true;
		}else{
			return false;
		}
    }
    
    public boolean validarNum(String nom){
		String reg = "[0-9]*";
		if(nom.matches(reg)){
			return true;
		}else{
			return false;
		}
    }
    
    
		
}
