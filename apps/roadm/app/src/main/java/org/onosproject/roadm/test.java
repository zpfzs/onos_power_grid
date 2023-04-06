package apps.roadm.app.src.main.java.org.onosproject.roadm;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {

    public Object getluyoubiao() throws IOException{
        List<Object> mm1=new ArrayList<>();

        HashMap<String,String> mm4= new HashMap<>();
        HashMap<String,String> mm3= new HashMap<>();

        String filePath="chengshi1";
        FileInputStream fin = new FileInputStream(filePath);
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";

        String filePath2="chengshi2";
        FileInputStream fin2 = new FileInputStream(filePath2);
        InputStreamReader reader2 = new InputStreamReader(fin2);
        BufferedReader buffReader2 = new BufferedReader(reader2);
        String strTmp2 = "";

        //城市1
        while((strTmp = buffReader.readLine()) != null){
            StringBuffer mm = new StringBuffer(strTmp);
            int m1=mm.indexOf(" ");
            int m2=mm.indexOf(" ",m1+1);
            String nnn=mm.substring(0,m1);
            String nnnn=mm.substring(m1+1,m2);

//            System.out.println(mm);
//            System.out.println(m1);
            String nn=mm.substring(m2+2, mm.length()-1);
            mm3.put(nnn+nnnn,nn);
//            if strTmp.contains("01") {
//                System.out.println();
//            }
//            System.out.println(strTmp);
//            System.out.println(nn);
        }
        //城市2
        while((strTmp2 = buffReader2.readLine()) != null){
            StringBuffer mm = new StringBuffer(strTmp2);
            int m1=mm.indexOf(" ");
            int m2=mm.indexOf(" ",m1+1);
            String nnn=mm.substring(0,m1);
            String nnnn=mm.substring(m1+1,m2);
//            System.out.println(mm);
//            System.out.println(m1);
            String nn=mm.substring(m2+2, mm.length()-1);
            mm4.put(nnn+nnnn,nn);
//            if strTmp.contains("01") {
//                System.out.println();
//            }
//            System.out.println(strTmp);
//            System.out.println(nn);
        }
        mm1.add(mm3);
        mm1.add(mm4);
        return mm1;
    }

}
