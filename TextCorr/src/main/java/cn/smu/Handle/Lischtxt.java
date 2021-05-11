package cn.smu.Handle;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Lischtxt{
    private static final String APP_ID = "ee067d05";
    private static final String SECRET_KEY = "b49ed0950ec214845f61a52a5a86eced";
    //private static final String AUDIO_FILE_PATH =  ApplicationTest.class.getResource("/").getPath()+"/audio/lfasr.wav";
    //private static final String AUDIO_FILE_PATH ="C:/Users/LENOVO/IdeaProjects/TextCorr/src/main/resources/audio/";
    public String listxt(String fileName) throws Exception{
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");

        }
        String AUDIO_FILE_PATH =path.getAbsolutePath()+"/static/"+fileName;//部署到服务器上要改成\static\
        System.out.println(path.getPath());
        System.out.println(AUDIO_FILE_PATH);
        //1、创建客户端实例
        System.out.println(APP_ID+"--"+SECRET_KEY);
        LfasrClient lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
        //2、上传
        Message task = lfasrClient.upload(AUDIO_FILE_PATH);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);
        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            JSONObject object = JSON.parseObject(message.getData());
            status = object.getInteger("status");
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        String[] string=result.getData().split("[{|}]");
        String str=string[1].split("\"")[11].toString();
        for(int i=3;i<string.length-1;i++){
            int res=i%2;
            if(res!=0){
                String[] string1=string[i].split("\"");
                str=str+string1[11];
            }
        }
        System.out.println(str);
        //System.exit(0);
        return str;

    }
}
