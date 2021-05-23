package cn.smu.Handle;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// F:\\2021年作业存放空间\\IDEA Project\\demo\\[Chinese (Traditional)] Trying to make money as a freelance artist [DownSub.com].srt

public class Readtxt {
    //public static void main(String[] args) throws ParseException, InterruptedException {
       /* List dataList = readTXT("D:\\Git\\kqlgit\\TextCorr\\src\\main\\resources\\[Chinese (Traditional)] Trying to make money as a freelance artist [DownSub.com].srt");
        for (int i = 0; i < dataList.size(); i++) {

            Map data= (Map) dataList.get(i);
            String time = (String) data.get("time");
            System.out.println(time);//时间戳
            String value = (String) data.get("value");
            System.out.println(value);//字幕内容
            int maxSplit = 2;

            String[] sourceStrArray = time.split(" --> ", maxSplit);

//            for (int j = 0; j < sourceStrArray.length; j++) {
//                System.out.println(sourceStrArray[j]);
//            }

            long difftime = getDiffTime(sourceStrArray[1],sourceStrArray[0]);
            System.out.println(difftime); //时间戳毫秒数

//            test.timedelay(difftime);

            //解决进程占用问题
            Thread.sleep(500);
            //            test.timedelay(difftime);

        }*/
    //}

    public long getDiffTime(String endDate, String newDate) throws ParseException {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 *60;
        long nm = 1000 * 60;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss,SSS");
        Date d1 = df.parse(endDate);
        Date d2 = df.parse(newDate);
        long diff = d1.getTime() - d2.getTime();
        long day = diff/nd;
        long hour = diff/nh;
        long minute = diff/nm;
        return diff;
//        log.info("相差的天数为："+day);
//        log.info("相差的小时为："+hour);
//        log.info("相差的分钟数为："+minute);

    }


    public List readTXT(String FileName) {
        List list = new ArrayList();

        File f=new File(FileName);
        try {

            BufferedReader buf=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            int i=0;
            while (true){
                String str=buf.readLine();
                if (str==null)break;
//                System.out.println(str);
                Map data = new HashMap();


                if (!str.equals("")) {
//                    System.out.println("start");
                    int j=0;
//                    System.out.println("number:  "+str);
                    data.put("num",str);
                    str = buf.readLine();
//                    System.out.println("time:  "+str);
                    data.put("time",str);
                    str = buf.readLine();
//
                    while (true){
                        j++;
//                        System.out.println("value"+j+":  "+str);
                        if (j==1){
                            data.put("vlaue",str);
                        }
                        else {
                            data.put("vlaue",data.get("vlaue")+"\n"+str);
                        }

                        str = buf.readLine();

                        if (str.equals("")) {
                            list.add(data);
                            break;
                        }
                    }
                }

            }
//            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
