package apps.roadm.app.src.main.java.org.onosproject.roadm;
import java.io.*;
import java.util.*;
public class test1 {
    public static Object getKey(Map map, Object value){
        List<Object> keyList = new ArrayList<>();
        for(Object key: map.keySet()){
            if(map.get(key).equals(value)){
                keyList.add(key);
            }
        }
        return keyList.get(0);
    }
    public static Object getchengshi() throws IOException {
        List<Object> mm1=new ArrayList<>();
        String filePath3="chengshi11";
        String filePath4="chengshi22";
        //创建HashMAP
        HashMap<String, String> chengshi1 = new HashMap<String, String>();
        HashMap<String, String> chengshi2 = new HashMap<String, String>();

        FileInputStream fin3 = new FileInputStream(filePath3);
        FileInputStream fin4 = new FileInputStream(filePath4);
        InputStreamReader reader3 = new InputStreamReader(fin3);
        InputStreamReader reader4 = new InputStreamReader(fin4);
        BufferedReader buffReader3 = new BufferedReader(reader3);
        BufferedReader buffReader4 = new BufferedReader(reader4);
        String strTmp3 = "";
        String strTmp4 = "";
        while((strTmp3 = buffReader3.readLine()) != null){

            StringBuffer mm = new StringBuffer(strTmp3);
            int m1=mm.indexOf("-");
            String nn=mm.substring(m1-1, mm.length());
            String nnn=mm.substring(0,m1-2);
            chengshi1.put(nnn,nn);
        }
        while((strTmp4 = buffReader4.readLine()) != null){
            StringBuffer mm = new StringBuffer(strTmp4);
            int m1=mm.indexOf("\t");
//            System.out.println(mm);
            String nn=mm.substring(m1+1, mm.length());
            String nnn=mm.substring(0,m1);
            chengshi2.put(nnn,nn);
        }
        mm1.add(chengshi1);
        mm1.add(chengshi2);
        return mm1;
    }
}
