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
		String reg = "[A-Z1-9]*";
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
