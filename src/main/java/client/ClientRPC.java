package client;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
//RODOLFO ANTONIO CARRILLO FUENTES
public class ClientRPC {
    public static void main(String[] args) throws MalformedURLException, XmlRpcException {
        Scanner scanner= new Scanner(System.in);
        XmlRpcClientConfigImpl config= new XmlRpcClientConfigImpl();
        config.setServerURL(new URL("http://localhost:1200"));
        XmlRpcClient client=new XmlRpcClient();
        client.setConfig(config);
        String nombres;
        String apelldo1;
        String apellido2;
        String curp;
        String fechaNac;
        String rfc="";
        String crp[];
        String datos[];
        String person;
        boolean response;
        int opc=0;
        while(opc!=6){
            System.out.println("Selecciona una opción");
            System.out.println("1.-Registrar datos persona");
            System.out.println("2.-Modificar datos");
            System.out.println("3.-Consultar una persona");
            System.out.println("4.-Consultar personas");
            System.out.println("5.-Eliminar persona");
            System.out.println("6.-Salir");
            opc=scanner.nextInt();
            switch (opc){
                case 1:
                    System.out.println("REGISTRAR DATOS");
                    System.out.println("Nombre");
                    nombres=scanner.next();
                    System.out.println("Primer Apellido");
                    apelldo1=scanner.next();
                    System.out.println("Segundo Apellido");
                    apellido2=scanner.next();
                    System.out.println("CURP");
                    curp=scanner.next();
                    System.out.println("Fecha de nacimiento formato dd-mm-yyyy");
                    fechaNac=scanner.next();
                    datos=new String[]{nombres,apelldo1,apellido2,curp,fechaNac,rfc};
                    rfc=(String) client.execute("Methods.rfcGenerator",datos);
                    System.out.println(rfc);
                    datos[5]=rfc;
                    response=(boolean) client.execute("Methods.newPerson",datos);
                    if(response){
                        System.out.println("Registro Exitoso");
                    }else{
                        System.out.println("ha ocurrido un error! :c");
                    }
                    break;
                case 2:
                    System.out.println("ACTUALIZAR DATOS");
                    System.out.println("Ingresa la CURP de la persona a actualizar");
                    String oldCURP=scanner.next();
                    crp=new String[]{oldCURP.toUpperCase()};
                    person=(String) client.execute("Methods.consultar",crp);
                    if(person.equals("error")){
                        System.out.println("Imposibru!");
                    }else{
                        System.out.println(person);
                        System.out.println("Nuevo nombre");
                        nombres=scanner.next();
                        System.out.println("Nuevo primer Apellido");
                        apelldo1=scanner.next();
                        System.out.println("Nuevo segundo Apellido");
                        apellido2=scanner.next();
                        System.out.println("Nueva CURP");
                        curp=scanner.next();
                        System.out.println("Nueva fecha de nacimiento formato dd-mm-yyyy");
                        fechaNac=scanner.next();
                        datos=new String[]{nombres,apelldo1,apellido2,curp,fechaNac,rfc,oldCURP};
                        response=(boolean) client.execute("Methods.updatePerson",datos);
                        if(response){
                            System.out.println("Actualización exitosa!");
                        }else {
                            System.out.println("ha ocurrido un error! :c");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Consultar Persona");
                    System.out.println("Ingresa CURP");
                    curp=scanner.next();
                    crp=new String[]{curp};
                    person=(String) client.execute("Methods.consultar",crp);
                    if(person.equals("error")){
                        System.out.println("CURP no encontrada");
                    }else{
                        System.out.println(person);
                    }
                    break;
                case 4:
                    System.out.println("Lista de personas");
                    String[] noNeed={""};
                    int size=(int)client.execute("Methods.listSize",noNeed);
                    for(int i=0;i<size;i++){
                        String index[]={String.valueOf(i)};
                        person=(String) client.execute("Methods.indexPerson",index);
                        System.out.println(person);
                        System.out.println("-------------------------------------");
                    }
                    break;
                case 5:
                    System.out.println("ELIMINAR PERSONA");
                    System.out.println("Ingresa la curp de la persona a eliminar");
                    curp=scanner.next();
                    crp=new String[]{curp};
                    response=(boolean) client.execute("Methods.deletePerson",crp);
                    if(response){
                        System.out.println("Operación Existosa");
                    }else {
                        System.out.println("Ha ocurrido un error! :c");
                    }
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Esa no es una opción");
                    break;
            }
        }

    }
}
