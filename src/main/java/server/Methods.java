package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Methods {
    public String homoclave(){
        String homoclave="";
        String alfanum="ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        for(int i=0;i<3;i++){
            homoclave=homoclave+alfanum.charAt((int)(Math.random()*34+1));
            System.out.println(homoclave);
        }
        return homoclave;
    }
    public String rfcGenerator(String name, String ap1, String ap2,String curp, String fechaNaC,String rfc){
        rfc=ap1.substring(0,2)+ap2.charAt(0)+name.charAt(0)+fechaNaC.substring(8,10)+fechaNaC.substring(3,5)+fechaNaC.substring(0,2)+homoclave();
        return rfc;
    }
    public int listSize(String noNeed){
        daoRFC daorfc= new daoRFC();
        List<BeanPerson>listPersons= daorfc.listPersons();
        return listPersons.size();
    }
    public String indexPerson(String i){
        int index=Integer.parseInt(i);
        daoRFC daorfc=new daoRFC();
        List<BeanPerson> listPerson=daorfc.listPersons();
        BeanPerson person=listPerson.get(index);
        return "Nombre: "+person.getName()+"\nApellido Paterno: "+person.getApellido1()+"\nApellidoMaterno: "+person.getApelllido2()+"\nCURP: "+person.getCurp()+"\nFecha de nacimiento: "+person.getFechaNac()+"\nRFC: "+person.getRfc();
    }

    public boolean updatePerson(String name, String ap1, String ap2,String curp, String fechaNaC,String rfc, String oldCurp){
        boolean result=false;
        daoRFC daorfc= new daoRFC();
        rfc=rfcGenerator(name,ap1,ap2,curp,fechaNaC,"");
        result=daorfc.updatePerson(rfc,name,ap1,ap2,curp,fechaNaC,oldCurp);
        if(result){
            result=daorfc.updateCurp(curp,rfc);
        }
        return result;
    }
    public boolean newPerson(String name, String ap1, String ap2,String curp, String fechaNaC,String rfc){
        daoRFC daorfc= new daoRFC();
        boolean result= daorfc.newPerson(rfc.toUpperCase(),name,ap1,ap2,curp.toUpperCase(),fechaNaC);
        return result;
    }
    public String consultar(String curp) throws SQLException {
        daoRFC daorfc= new daoRFC();
        String datos=daorfc.consultPerson(curp.toUpperCase());
        return datos;
    }

    public boolean deletePerson(String curp){
        daoRFC daorfc= new daoRFC();
        boolean result=daorfc.deletePerson(curp.toUpperCase());
        return result;
    }

}
