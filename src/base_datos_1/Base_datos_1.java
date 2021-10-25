/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author alramirez
 */
public class Base_datos_1 {

    public static String sSQL1;
    public static String sSQL2;
    public static Connection conn;
    public static HashMap<String, String> encriptados;

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        try {

            sSQL1 = "SELECT c.*, acc.suma_balance\n"
                    + "FROM client c\n"
                    + "	INNER JOIN (\n"
                    + "		SELECT a.client_id, sum(balance) as suma_balance\n"
                    + "		FROM client c\n"
                    + "			LEFT JOIN account a ON (substr(c.code, 2)*1 - 10000) = a.client_id\n"
                    + "		group by a.client_id\n"
                    + "		ORDER BY 2 DESC\n"
                    + "    ) as acc ON acc.client_id = (substr(c.code, 2)*1 - 10000)\n";

            /*Conexión a la base de datos*/
            Connection conn = null;
            conn = ConnectionManager.getConnection();

            /*Transacciones con la base de datos*/
            Transacciones t = new Transacciones();

            /*Encontrar los clientes con code encriptado y almacenarlos en un Objeto*/
            encriptados = new HashMap<>();
            t.obtenerEncriptados(conn, encriptados);

            for (String clave : encriptados.keySet()) {
                String valor = encriptados.get(clave);
                System.out.println("Clave: " + clave + ", valor: " + valor);
            }

            /*Si el número de encriptados es superior a cero, se desencriptan y se actualiza la base de datos*/
            if (encriptados.size() > 0) {
                t.actualizarEncriptados(conn, encriptados);
            }

            /*Lee el archivo y genera un arreglo con el número de mesas solicitadas*/
            String rutaArchivo = "/home/alramirez/Desarrollo/java/BANCOLOMBIA/entrada.txt";
            LeerArchivo file = new LeerArchivo(rutaArchivo);
            Mesa[] mesas = new Mesa[file.getNumMesas()];

            for (int x = 0; x < file.getNombresMesas().length; x++) {
                Parametros[] params = file.getParametrosByMesa(file.getNombresMesas()[x]);
                mesas[x] = new Mesa(file.getNombresMesas()[x], params);
            }

            /*Se itera sobre el arreglo de cada mesa para obtener los clientes asignados a cada una*/
            String filtro = "";
            for (int z = 0; z < mesas.length; z++) {
                for (int x = 0; x < mesas[z].getParametros().length; x++) {
                    String signo = "=";
                    if (mesas[z].getParametros()[x].getParametro().equals("RI")) {
                        signo = ">";
                    }
                    if (mesas[z].getParametros()[x].getParametro().equals("RF")) {
                        signo = "<";
                    }
                    if (filtro != "") {
                        filtro += " AND ";
                    }
                    filtro += mesas[z].getParametros()[x].getFiltro() + signo + mesas[z].getParametros()[x].getValor();

                }

                sSQL2 = sSQL1;
                sSQL1 = sSQL1 + " WHERE " + filtro + " ORDER BY 8 DESC";

                System.out.println("SQL generado para la Mesa=>" + mesas[z].getNombre() + " es:");
                System.out.println(sSQL1);

                /*Se obtienen la cantidad de clientes almacenados y se almacenan en un arreglo tipo Cliente*/
                int nClientes = 0;
                nClientes = t.cantidadClientesOpcionados(conn, sSQL1);
                System.out.println("Cantidad de clientes opcionados para la Mesa=>" + mesas[z].getNombre() + " es: " + nClientes);

                Cliente[] clientes;
                clientes = t.obtenerClientesOpcionados(conn, sSQL1, nClientes);

                for (int x = 0; x < clientes.length; x++) {
                    System.out.println("Cliente Id=>" + clientes[x].id + " code=>" + clientes[x].code + " male=>" + clientes[x].male);
                }

                /*Se agregan los candidatos al objeto Mesa*/
                mesas[z].addCandidatos(clientes);

                sSQL1 = sSQL2;
                filtro = "";

            }
            

            /*Se obtienen los invitados para cada mesa*/
            String strSalida = "";
            PrintWriter writer = new PrintWriter("/home/alramirez/Desarrollo/java/BANCOLOMBIA/salida.txt", "UTF-8");
            for (int z = 0; z < mesas.length; z++) {
                mesas[z].obtenerInvitados();
                writer.println("<" + mesas[z].getNombre() + ">");
                strSalida += "<" + mesas[z].getNombre() + ">\n";
                if (mesas[z].getInvitados().equals("")) {
                    writer.println("CANCELADA");
                    strSalida += "CANCELADA\n";
                } else {
                    writer.println(mesas[z].getInvitados());
                    strSalida += mesas[z].getInvitados() + "\n";
                }
                System.out.println("********************************************************************************");
            }
            System.out.print(strSalida);
            writer.close();            

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}