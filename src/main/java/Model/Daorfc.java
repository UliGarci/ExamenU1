package Model;

import MySQLConnection.MySQLConnection;
import Server.Methods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Daorfc {
    Connection con;
    PreparedStatement pstm;
    ResultSet rs;
    CallableStatement cstm;
    Methods m = new Methods();
    String registrar="INSERT INTO datos(id,nombre,apellidopaterno,apellidomaterno,curp,fecha,rfc) VALUE(?,?,?,?,?,?,?);";
    String listar="SELECT * FROM datos";
    String eliminar="DELETE FROM datos WHERE curp = ?;";
    String consultaEspecifica="SELECT * FROM datos WHERE curp=?;";
    String modificar = "UPDATE datos SET nombre = ?, apellidopaterno = ?, apellidomaterno = ?, curp = ?, fecha = ?, rfc  = ? WHERE curp = ?;";


    //METODO EN FUNCION
    public Beanrfc addperson(int id, String nombre, String apellidopaterno, String apellidomaterno, String curp, String fecha, String rfc){
        Beanrfc beanrfc = new Beanrfc();
        try {
            con = MySQLConnection.getConnection();
            pstm = con.prepareStatement(registrar);
            pstm.setInt(1,id);
            pstm.setString(2,nombre);
            pstm.setString(3,apellidopaterno);
            pstm.setString(4,apellidomaterno);
            pstm.setString(5,curp);
            pstm.setString(6,fecha);
            pstm.setString(7,rfc);
            int result = pstm.executeUpdate();
            if (result==1){
                beanrfc.setId(id);
                beanrfc.setNombre(nombre);
                beanrfc.setApellidopaterno(apellidopaterno);
                beanrfc.setApellidomaterno(apellidomaterno);
                beanrfc.setCurp(curp);
                beanrfc.setFecha(fecha);
                beanrfc.setRfc(rfc);
            }else{
                beanrfc=null;
            }
        }catch (SQLException e){
            System.out.println("Error en el metodo addperson -> "+e.getMessage());
        }finally {
            try {
                pstm.close();
                con.close();
            }catch (SQLException e){
                System.out.println("Error en el cierre de conexiones -> "+e.getMessage());
            }
        }
        return beanrfc;
    }

    //METODO EN FUNCION
    public List<Beanrfc> listarpersonas(){
        List listapersonas = new ArrayList();
        try {
            con = MySQLConnection.getConnection();
            pstm = con.prepareStatement(listar);
            rs=pstm.executeQuery();
            while(rs.next()){
                Beanrfc beanrfc = new Beanrfc();
                beanrfc.setId(rs.getInt("id"));
                beanrfc.setNombre(rs.getString("nombre"));
                beanrfc.setApellidopaterno(rs.getString("apellidopaterno"));
                beanrfc.setApellidomaterno(rs.getString("apellidomaterno"));
                beanrfc.setCurp(rs.getString("curp"));
                beanrfc.setFecha(rs.getString("fecha"));
                beanrfc.setRfc(rs.getString("rfc"));
                listapersonas.add(beanrfc);
            }
        }catch (SQLException e){
            System.out.println("Error en el metodo listarpersonas() -> "+e.getMessage());
        }finally {
            try {
                con.close();
                pstm.close();
                rs.close();
            }catch (SQLException e){
                System.out.println("Errror en el cierre de conexiones -> "+e.getMessage());
            }
        }
        return listapersonas;
    }

    //METODO EN FUNCION
    public Beanrfc buscarpersona(String curp){
        Beanrfc beanrfc = new Beanrfc();
        try {
            con = MySQLConnection.getConnection();
            pstm=con.prepareStatement(consultaEspecifica);
            pstm.setString(1,curp);
            rs = pstm.executeQuery();
            if (rs.next()){
                beanrfc.setCurp(rs.getString("id"));
                beanrfc.setNombre(rs.getString("nombre"));
                beanrfc.setApellidopaterno("apellidopaterno");
                beanrfc.setApellidomaterno("apellidomaterno");
                beanrfc.setCurp("curp");
                beanrfc.setFecha("fecha");
                beanrfc.setRfc("rfc");
            }
        }catch (SQLException e){
            System.out.println("Error en el metodo buscarpersona() -> "+e.getMessage());
        }finally {
            try {
                rs.close();
            }catch (SQLException e){
                System.out.println("Errror en el cierre de conexiones -> "+e.getMessage());
            }
        }
        return beanrfc;
    }


    public Beanrfc modifipersona(String nombre, String ap, String am, String c, String f,String curp){
        Beanrfc beanrfc = buscarpersona(curp);
        int result=0;
        try {
            pstm = con.prepareStatement(modificar);
                    pstm.setString(1,nombre);
                    pstm.setString(2,ap);
                    pstm.setString(3,am);
                    pstm.setString(4,c);
                    pstm.setString(5,f);
                    String rfc =m.crearRFC(nombre,ap,am,f);
                    pstm.setString(6,rfc);
                    result=pstm.executeUpdate();
            if (result==1){
                System.out.println("Nuevo RFC: "+rfc);
            }else{
                beanrfc=null;
            }
        }catch (SQLException e){
            System.out.println("Error en el metodo modifipersona() -> "+e.getMessage());
        }finally {
            try {
                pstm.close();
                con.close();
            }catch (SQLException e){
                System.out.println("Errror en el cierre de conexiones -> "+e.getMessage());
            }
        }
        return beanrfc;
    }

    //METODO EN FUNCION
    public boolean eliminarpersona(String curp){
        boolean flag=false;
        try {
            con = MySQLConnection.getConnection();
            pstm = con.prepareStatement(eliminar);
            pstm.setString(1,curp);
            flag=pstm.executeUpdate()==1;
        }catch (SQLException e){
            System.out.println("Error en el metodo eliminarpersona() -> "+e.getMessage());
        }finally {
            try {
                con.close();
                pstm.close();
                rs.close();
            }catch (SQLException e){
                System.out.println("Errror en el cierre de conexiones -> "+e.getMessage());
            }
        }
        return flag;
    }

    public boolean modificarpersona(Beanrfc beanrfc){
        boolean flag =false;
        try {
            con = MySQLConnection.getConnection();
            pstm = con.prepareStatement(modificar);
            pstm.setString(1,beanrfc.getNombre());
            pstm.setString(2,beanrfc.getApellidopaterno());
            pstm.setString(3,beanrfc.getApellidomaterno());
            pstm.setString(4,beanrfc.getCurp());
            pstm.setString(5,beanrfc.getFecha());
            pstm.setString(6,beanrfc.getRfc());
            pstm.setString(7,beanrfc.getCurp());
            flag=pstm.executeUpdate()==1;
        }catch (SQLException e){
            System.out.println("Error en el metodo modificarpersona() -> "+e.getMessage());
        }finally {
            try {
                con.close();
                pstm.close();
            }catch (SQLException e){
                System.out.println("Error en el cierre de conexiones");
            }
        }
        return flag;
    }

}
