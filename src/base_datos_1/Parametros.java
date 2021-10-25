/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

/**
 *
 * @author alramirez
 */
public class Parametros {
    
    String parametro;
    Integer valor;

    public Parametros(String parametro, Integer valor) {
        this.parametro = parametro;
        this.valor = valor;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public String getFiltro(){
        
        String filtro = "";
        switch(this.parametro){
            case "TC":
                filtro = "type";
                break;
            case "UG":
                filtro = "location";
                break;
            case "RI":
                filtro = "suma_balance";
                break;
            case "RF":
                filtro = "suma_balance";
                break;
        }
        return filtro;
    }
    
}
