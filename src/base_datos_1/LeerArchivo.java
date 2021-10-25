/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author alramirez
 */
public class LeerArchivo {
    
    public String ruta;
    public String[] mesas;
    
    
    public LeerArchivo(String ruta){
    
        this.ruta = ruta;        
    }
    
    public Scanner getFile() throws FileNotFoundException{    
        Scanner input = new Scanner(new File(this.ruta));        
        return input;        
    }
    
    public Integer getNumMesas() throws FileNotFoundException{
        
        Scanner input = this.getFile();
        int nunmesas = 0;        
        
        while (input.hasNextLine()) {
            String line = input.nextLine();        
            String primerCaracter = line.substring(0,1);
            
            if(primerCaracter.equals("<")){
                nunmesas++;                
            }
        }
        return nunmesas;        
    }   
    
    public String[] getNombresMesas() throws FileNotFoundException{
        
        Scanner input = this.getFile();
        String[] mesas = new String[this.getNumMesas()];
        int j = 0;
        
        while (input.hasNextLine()) {
            String line = input.nextLine();        
            String primerCaracter = line.substring(0,1);

            if(primerCaracter.equals("<")){
                mesas[j] = line.substring(1, line.length()-1);
                j++;
            }
            
        }
        return mesas;        
    }
    
    public Integer getNumParametrosByMesa(String nombreMesa) throws FileNotFoundException{
        
        Scanner input = this.getFile();
        String nombre = "";
        Boolean contarParametros = false;
        int num =0;
        
        while (input.hasNextLine()) {
            String line = input.nextLine();        
            String primerCaracter = line.substring(0,1);

            if(primerCaracter.equals("<")){
                nombre = line.substring(1, line.length()-1);
                if(nombre.equals(nombreMesa)){
                    contarParametros = true;
                }else{
                    contarParametros = false;
                }
            }else{
                if(contarParametros)
                    num++;
            }
            
        }            
        
        return num;        
        
    }
    
    public Parametros[] getParametrosByMesa(String nombreMesa) throws FileNotFoundException{
        
        Scanner input = this.getFile();
        String[] mesas = new String[this.getNumMesas()];
        int j = 0;
        String nombre = "";
        String parametro = "";
        Integer valor = 0;
        Boolean revisarParametros = false;
        Parametros [] params = new Parametros[this.getNumParametrosByMesa(nombreMesa)];
        
        while (input.hasNextLine()) {
            String line = input.nextLine();        
            String primerCaracter = line.substring(0,1);

            if(primerCaracter.equals("<")){
                nombre = line.substring(1, line.length()-1);
                if(nombre.equals(nombreMesa)){
                    revisarParametros = true;
                }else{
                    revisarParametros = false;
                }             
            }else{
                if(revisarParametros){
                    parametro = line.substring(0, line.indexOf(":"));
                    valor = Integer.parseInt(line.substring(line.indexOf(":")+1, line.length()));                    
//                    System.out.println("mesa nombre=>"+nombreMesa + " parametros=>"+parametro + " valor=>"+valor);
                    params[j] = new Parametros(parametro, valor);
                    j++;
                }
            }
        }        
        return params;
    }
}