/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author alramirez
 */
public class Transacciones {
    
    public void obtenerEncriptados(Connection conn, HashMap<String, String> encriptados) throws ClassNotFoundException, SQLException, IOException{
        try {            
            
            String SQL = "SELECT id, code FROM client where encrypt = 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                encriptados.put(rs.getString("id"), rs.getString("code"));
                System.out.println(rs.getString("id")+ rs.getString("code"));
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarEncriptados(Connection conn, HashMap<String, String> encriptados) {
        try {
            String queryUpdate = "";
            String ids = "";

            for (String clave : encriptados.keySet()) {
                String valor = encriptados.get(clave);
                Cliente c = new Cliente(Integer.parseInt(clave), valor, 0, 0, "", "", 1);
                if (ids != "") {
                    ids += " , ";
                }

                queryUpdate += " WHEN id=" + clave + " THEN " + c.getCode();
                ids += clave;
                System.out.println("Codeencriptado=>" + c.getCode());
                System.out.println("Clave: " + clave + ", valor: " + valor);
            }

            queryUpdate = "UPDATE client SET code= CASE " + queryUpdate + " ELSE code END, encrypt= 0 WHERE id in (" + ids + ")";
            System.out.println("Query update:"+queryUpdate);

            String SQL = queryUpdate;
            Statement stmt = conn.createStatement();
            int updatedCount = stmt.executeUpdate(SQL);

            System.out.println("Código actualizado en " + updatedCount  + " cliente(s).");            

//            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer cantidadClientesOpcionados(Connection conn, String Sql) throws ClassNotFoundException, SQLException, IOException{
        int size = 0;
        try {
            String SQL = Sql;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            

            while (rs.next()) {
                size++;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    } 
    
    public Cliente[] obtenerClientesOpcionados(Connection conn, String Sql, int nClientes) throws ClassNotFoundException, SQLException, IOException{
        Cliente[] clientes = new Cliente[nClientes];
        try {            

            String SQL = Sql;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            
            int i = 0;
            

            while (rs.next()) {                    
                Cliente c = new Cliente(Integer.parseInt(rs.getString("id")), rs.getString("code"), Integer.parseInt(rs.getString("male")), Integer.parseInt(rs.getString("type")), rs.getString("location"), rs.getString("company"), Integer.parseInt(rs.getString("encrypt")));
                clientes[i] = c;
                    i++;
            }    
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    } 
}