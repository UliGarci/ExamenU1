package Server;

import java.util.Random;

public class Methods {
    String homo = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public String crearRFC(String nombre, String apellidopaterno, String apellidomaterno, String fecha){
        String rfc="";
        rfc+=apellidopaterno.charAt(0);
        rfc+=apellidopaterno.charAt(1);
        rfc+=apellidomaterno.charAt(0);
        rfc+=nombre.charAt(0);
        rfc+=fecha.charAt(2);
        rfc+=fecha.charAt(3);
        rfc+=fecha.charAt(5);
        rfc+=fecha.charAt(6);
        rfc+=fecha.charAt(8);
        rfc+=fecha.charAt(9);
        rfc+= homoclave();
        rfc=rfc.toUpperCase();
        return rfc;
    }

    //AGREGA LOS ULTIMOS 3 DIGITOS DEL RFC
    public String homoclave(){
        String h="";
        for(int i=0;i<3;i++){
            int alea= (int) (Math.random()*homo.length());
            char letraonum = homo.charAt(alea);
            h+=letraonum;
        }
        return h;
    }
}
