package server;

import com.mysql.cj.MysqlConnection;

import javax.management.MBeanNotificationInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class daoRFC {
    public List<BeanPerson> listPersons(){
        List<BeanPerson> listPersons=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("select * from person");
            while (rs.next()){
                BeanPerson person= new BeanPerson();
                person.setName(rs.getString("name"));
                person.setRfc(rs.getString("RFC"));
                person.setApellido1(rs.getString("apellido1"));
                person.setApelllido2(rs.getString("apellido2"));
                person.setCurp(rs.getString("CURP"));
                person.setFechaNac(rs.getString("fechaNac"));
                listPersons.add(person);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listPersons;
    }
    public boolean updateCurp(String rfc, String curp){
        boolean result=false;
        try(
                Connection connection= MySQLConnection.getConnection();
                PreparedStatement pstm=connection.prepareStatement("update person set CURP=? where RFC=?;")
        ){
            pstm.setString(1,curp);
            pstm.setString(2,rfc.toUpperCase());
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean updatePerson(String rfc,String name,String ap1, String ap2,String curp,String fechaNac,String oldCurp){
        boolean result=false;
        try(
                Connection connection= MySQLConnection.getConnection();
                PreparedStatement pstm=connection.prepareStatement("update person set RFC=?,name=?,apellido1=?,apellido2=?,fechaNac=? where CURP=?;")
                ){
            pstm.setString(1,rfc.toUpperCase());
            pstm.setString(2,name);
            pstm.setString(3,ap1);
            pstm.setString(4,ap2);
            pstm.setString(5,fechaNac);
            pstm.setString(6,oldCurp.toUpperCase());
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result){
            try(
                    Connection connection= MySQLConnection.getConnection();
                    PreparedStatement pstm=connection.prepareStatement("update person set CURP where RFC=?")
                    ){
                pstm.setString(1,rfc.toUpperCase());
                result=pstm.executeUpdate()==1;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
    public boolean  deletePerson(String curp){
        boolean result=false;
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm=connection.prepareStatement("delete from person where curp=?;")
                ){
            pstm.setString(1,curp);
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean newPerson(String rfc,String name,String ap1, String ap2,String curp,String fechaNac){
        boolean result=false;
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("insert into person (RFC,name,apellido1,apellido2,CURP,fechaNac) values (?,?,?,?,?,?);");
        ){
            pstm.setString(1,rfc);
            pstm.setString(2,name);
            pstm.setString(3,ap1);
            pstm.setString(4,ap2);
            pstm.setString(5,curp);
            pstm.setString(6,fechaNac);
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public String consultPerson(String curp) throws SQLException {
        String[]datos=new String[6];
        try(
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement pstm=connection.prepareStatement("select * from person where CURP=?")
                ) {
            pstm.setString(1,curp);
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                datos[0]=rs.getString("name");
                datos[1]=rs.getString("apellido1");
                datos[2]=rs.getString("apellido2");
                datos[3]=rs.getString("CURP");
                datos[4]=rs.getString("fechaNac");
                datos[5]=rs.getString("rfc");
            }
            rs.close();
            pstm.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(datos[0]==null){
            return "error";
        }else{
            return "Nombre: "+datos[0]+"\nApellido paterno: "+datos[1]+"\nApellido Materno: "+datos[2]+"\nCURP: "+datos[3]+"\nFecha de nacimiento: "+datos[4]+"\nRFC: "+datos[5];
        }

    }
}
