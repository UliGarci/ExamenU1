package Client;

import Model.Beanrfc;
import Model.Daorfc;
import Server.Methods;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException, XmlRpcException {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        PropertyHandlerMapping mapping = new PropertyHandlerMapping();
        mapping.addHandler("Methods", Methods.class);
        Daorfc daorfc = new Daorfc();
        Methods m = new Methods();
        int id=0;
        Scanner sc = new Scanner(System.in);
        int op;
        do{
            System.out.println("***** MENU *****\nSeleccione una opcion:");
            System.out.println("1.Registrar\n2.Consultar\n3.Modificar\n4.Eliminar\n5.Salir");
            op=sc.nextInt();
            switch (op) {
                case 1:
                    System.out.println("Ingrese su nombre:");
                    String nombre = sc.next();
                    System.out.println("Ingrese su apellido paterno:");
                    String apellidopaterno = sc.next();
                    System.out.println("Ingrese su apellido materno");
                    String apellidomaterno = sc.next();
                    int validcurp;
                    String curp;
                    do {
                        System.out.println("Ingrese su CURP:");
                        curp = sc.next().toUpperCase();
                        validcurp = curp.length();
                        System.out.println(validcurp);
                    } while (validcurp!=18);

                    System.out.println("Ingrese su fecha de nacimiento: (aaaa-mm-dd)");
                    String fecha = sc.next();
                    String rfc = m.crearRFC(nombre, apellidopaterno, apellidomaterno, fecha).toUpperCase();
                    System.out.println("RFC: "+rfc);
                    daorfc.addperson(id, nombre, apellidopaterno, apellidomaterno, curp, fecha, rfc);
                    break;
                case 2:
                    System.out.println("1.Consulta general\n2.Consulta especifica");
                    op = sc.nextInt();
                    switch (op) {
                        case 1:
                            daorfc.listarpersonas();
                            break;
                        case 2:
                            System.out.println("Ingrese su CURP:");
                            String c = sc.next().toUpperCase();
                            daorfc.buscarpersona(c);
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Ingrese su curp:");
                    String s = sc.next();
                    Beanrfc b = new Beanrfc();
                            System.out.println("Ingrese su nombre:");
                             String n=sc.next();
                            System.out.println("Ingrese su apellido paterno");
                            String ap=sc.next();
                            System.out.println("Ingrese su apellido materno:");
                            String am=sc.next();
                            System.out.println("Ingrese su CURP:");
                            String c=sc.next();
                            System.out.println("Ingrese su fecha de nacimiento: (aaaa-mm-dd)");
                            String f=sc.next();
                            daorfc.modifipersona(n,ap,am,c,f,s);
                            break;
                case 4:
                    System.out.println("Ingrese la CURP");
                    String a = sc.next();
                    daorfc.eliminarpersona(a);
                    break;
                case 5:
                    System.out.println("CHAO :)");
                    op = 0;
                    break;
            }
        }while(op!=0);
    }
}
