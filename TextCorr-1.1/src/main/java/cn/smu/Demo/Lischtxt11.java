package cn.smu.Demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Lischtxt11 {
    private static final String APP_ID = "ee067d05";
    private static final String SECRET_KEY = "b49ed0950ec214845f61a52a5a86eced";
    private static MP3File mp3File;
    //private static final String AUDIO_FILE_PATH =  ApplicationTest.class.getResource("/").getPath()+"/audio/lfasr.wav";
    //private static final String AUDIO_FILE_PATH ="C:/Users/LENOVO/IdeaProjects/TextCorr/src/main/resources/audio/";
    public String listxt(String fileName) throws Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        String AUDIO_FILE_PATH =path.getAbsolutePath()+"/static/"+fileName;
        System.out.println(fileName);
        System.out.println(AUDIO_FILE_PATH);
        mp3File = new MP3File(AUDIO_FILE_PATH);//封装好的类
        MP3AudioHeader header = mp3File.getMP3AudioHeader();
        System.out.println("时长: " + header.getTrackLength()); //获得时长
        String line = "/usr/local/ffmpeg/ffmpeg-4.4-amd64-static/ffmpeg -i %s -f segment -segment_time 300 -c copy %s";
       line=String.format(line,AUDIO_FILE_PATH,"/home/TextCorr-1.0/fglisten/g%2d.mp3");
        //String line = "D:\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe -i %s -f segment -segment_time 90 -c copy %s";
        //line=String.format(line,AUDIO_FILE_PATH,"D:\\音频分割\\g%2d.mp3");
        System.out.println(line);
        CommandLine cmdLine = CommandLine.parse(line);
        DefaultExecutor executor = new DefaultExecutor();
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LfasrClient lfasrClient=null;
        String[] gstr=new String[header.getTrackLength()/90+1];
        int j=0;
        while (j<header.getTrackLength()/90+1) {
            //File file=new File("D:\\音频分割\\g0"+i+".mp3");
            //String gpath = "D:\\音频分割\\g0"+j+".mp3";
            String gpath = path.getAbsolutePath()+"/fglisten/g0" + j + ".mp3";
            System.out.println(gpath);
            if (j == 0) {
                lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
            }
            try {
                Message task = lfasrClient.upload(gpath);
                String taskId = task.getData();
                int status = 0;
                while (status != 9) {
                    Message message = lfasrClient.getProgress(taskId);
                    JSONObject object = JSON.parseObject(message.getData());
                    status = object.getInteger("status");
                    System.out.println(message.getData());
                    TimeUnit.SECONDS.sleep(2);
                }
                Message result = lfasrClient.getResult(taskId);
                String[] string = result.getData().split("[{|}]");
                String str = string[1].split("\"")[11].toString();
                for (int i = 3; i < string.length - 1; i++) {
                    int res = i % 2;
                    if (res != 0) {
                        String[] string1 = string[i].split("\"");
                        str = str + string1[11];
                    }
                }
                System.out.println(str);
                gstr[j]=str;
                j++;
            } catch (Exception e) {
                gstr[j]="-";
                j++;
                // e.printStackTrace();
            }
        }
        String string=gstr[0];
        for (int i=1;i<j;i++){
            string=string+gstr[i];
        }
        return string;

    }
}
