/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author alramirez
 */
public class Mesa {
    
    public String nombre;
    public Parametros[] parametros;
    public Cliente[] candidatos;
    public String invitados;
    
    public Mesa(String nombre, Parametros[] parametros) {
        this.nombre = nombre;
        this.parametros = parametros;        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Parametros[] getParametros() {
        return parametros;
    }

    public void setParametros(Parametros[] parametros) {
        this.parametros = parametros;
    }
    
    public void addCandidatos(Cliente[] c){
        this.candidatos = c;        
    }
    
    public Cliente[] getCandidatos(){
        return candidatos;
    }
    
    public String getInvitados() {
        return invitados;
    }

    public void setInvitados(String invitados) {
        this.invitados = invitados;
    }
    
    public void obtenerInvitados() throws IOException{
        int mujeres = 0;
        int hombres = 0;
        Boolean mujer = false;
        Boolean hombre = false;
        String invitados = "";
        
        int requerido = (int) this.encontrarParametros();
        String[] empresas = new String[candidatos.length];
        
        if(requerido >= 2){        
        
            Cliente[] todos = new Cliente[requerido*2];
            int j = 0;

            for(int x = 0; x < candidatos.length; x++){
                mujer = false;
                hombre = false;           

                boolean contains = Arrays.stream(empresas).anyMatch(candidatos[x].getCompany()::equals);
                if(!contains){
                    if(candidatos[x].getMale() == 0){
                        mujer = true;
                    }
                    if(candidatos[x].getMale() == 1){
                        hombre = true;
                    }
//                    System.out.println("Es mujer="+mujer+" Es hombre="+hombre+ " nmujeres=>"+mujeres+" nhombres"+hombres+ " j=>"+j);

                    if(mujeres< requerido && mujer){
//                        System.out.println("VALOR DE J EN MUJERES"+j);
                        todos[j] = candidatos[x];
                        mujeres++;
                    }else if(mujeres == requerido && mujer){
                        continue;
                    }           

                    if(hombres< requerido && hombre){                
//                        System.out.println("VALOR DE J EN HOMBRES"+j);
                        todos[j] = candidatos[x];
                        hombres++;
                    }else if(hombres == requerido && hombre){
                        continue;
                    }
                    j++;
                }
                empresas[x] = candidatos[x].getCompany();
            }
            
            for(int x = 0; x < todos.length; x++){
                if(invitados!="")
                    invitados+=",";
                invitados+=todos[x].getCode();

            }
            System.out.println(invitados);
        }else{
            System.out.println("LA MESA "+this.getNombre() + " ESTA CANCELADA");
            invitados = "";
        }
        this.setInvitados(invitados);
    }
    
    public double encontrarParametros() throws IOException{
        int mujeres = 0;
        int hombres = 0;
        
        int max = 8;
        int min = 4;
        String[] empresas = new String[candidatos.length];
        
        for(int x = 0; x < candidatos.length; x++){
            boolean contains = Arrays.stream(empresas).anyMatch(candidatos[x].getCompany()::equals);
            if(!contains){
                if(candidatos[x].getMale()==0){
                    mujeres++;
                }
                if(candidatos[x].getMale()==1){                
                    hombres++;
                }                
            }
            empresas[x] = candidatos[x].getCompany();
        }
        
        System.out.println("Mesa=>"+this.getNombre()+" hay "+mujeres + " MUJERES");
        System.out.println("Mesa=>"+this.getNombre()+" hay "+hombres + " HOMBRES");
//        System.out.println("nrequerido"+nrequerido);
        int requerido = 0;
        int nrequerido = 0;
        
        while(max>=min){
            requerido = max/2;
//            System.out.println("requerido"+nrequerido);
            if(mujeres >= requerido && hombres >= requerido){
                nrequerido = requerido;
                break;
            }
            max = max-1;            
        }
        System.out.println("Mesa=>"+this.getNombre()+" se requieren "+nrequerido+" personas de cada sexo");
        
        return nrequerido;
    }
}   