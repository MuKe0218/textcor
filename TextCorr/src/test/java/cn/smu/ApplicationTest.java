package cn.smu;
import cn.smu.Bean.OldNew;
import cn.smu.Dao.ReportMapper;
import cn.smu.Dao.TextMapper;
import cn.smu.Dao.UserMapper;
import cn.smu.Demo.Lischtxt11;
import cn.smu.Handle.DNASequence;
import cn.smu.Handle.DuiBi;
import cn.smu.Handle.Texttwenty;
import cn.smu.Service.TextService;
import cn.smu.Service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {

    @Test
    public void test1(){
        System.out.println("文本校正");
       }

    @Test
    public void test2() {
        String filename = "原稿";
        String filePath = "C:\\Users\\LENOVO\\IdeaProjects\\TextCorr\\target\\classes\\"+filename+".txt";
        String[] str=new String[10000];
        try {
            File file = new File(filePath);
            int j=0;
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    str[j]=lineTxt;
                    //System.out.println(lineTxt);
                    j++;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
            String string;
            if (str[0]==null){
                string=null;
            }else{
                string=str[0];
            }
            for (int i=1;i<j;i++){
                string=string+"\n"+str[i];
            }
            System.out.println(string);
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
    }

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    TextMapper textMapper;
    @Autowired
    TextService textService;
    @Test
    public void test3(){
        String contrasttext = "基本铲h球，把球踢到你正前方大约4~5米，13~15英尺的位置，然后追着球跑，弯曲左腿，" +
                "以左腿外侧铲球，用手臂保持平衡，当球触手可及时，右腿猛然发力，用脚背将球踢开，" +
                "然后尽快站起来，提示在铲球后保持左腿弯曲，这样你就可以用这条腿站起来。" +
                "最常犯的错误为找准铲球时机，踢。";//对比稿
       //contrasttext=contrasttext.replace("，","。");
        /*char[] cnSignArr = "。？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$^&()[]\\;',{}|:\"<>?".toCharArray();
        for (int i = 0; i < cnSignArr.length; i++) {
            contrasttext = contrasttext.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            contrasttext = contrasttext.replace("" + enSignArr[i], "");
        }
        int len=contrasttext.length();
        if (len<20){
            contrasttext=contrasttext+"。";
        }else{
            int k=0;
            float num=(float) len/20;
            System.out.println(num);
            int fk=len/20;
            System.out.println(fk);
            String[] str=new String[fk+1];
            for (int i=0;i<fk;i++){
                str[i]=contrasttext.substring(k,(i+1)*20)+"。";
                System.out.println(str[i]);
                k=(i+1)*20;
                //System.out.println(k);
            }
          System.out.println(len);
            if (num>fk){
                str[fk]=contrasttext.substring(k,len)+"。";
                System.out.println(str[fk]);
            }
        }*/
        Texttwenty texttwenty=new Texttwenty();
        contrasttext=texttwenty.Texttw(contrasttext);
        System.out.println(contrasttext);
    }
    private static final String APP_ID = "ee067d05";
    private static final String SECRET_KEY = "b49ed0950ec214845f61a52a5a86eced";
    //private static String mp3Path = "./target/static/杰克.mp3";
    //private static String mp3Path = "D:\\音频分割\\g02.mp3";
    private static MP3File mp3File;
    @Autowired
    ReportMapper reportMapper;
    @Test
    public void test4() throws Exception {//音频文件存储
        String mp3Path = "./target/static/cq.mp3";
      System.out.println(mp3Path);
        mp3File = new MP3File(mp3Path);//封装好的类
        MP3AudioHeader header = mp3File.getMP3AudioHeader();
        System.out.println("时长: " + header.getTrackLength()); //获得时长
        System.out.println("比特率: " + header.getBitRate()); //获得比特率
        System.out.println("音轨长度: " + header.getTrackLength()); //音轨长度
        System.out.println("格式: " + header.getFormat()); //格式，例 MPEG-1
        System.out.println("声道: " + header.getChannels()); //声道
        System.out.println("采样率: " + header.getSampleRate()); //采样率
        System.out.println("MPEG: " + header.getMpegLayer()); //MPEG
        System.out.println("MP3起始字节: " + header.getMp3StartByte()); //MP3起始字节
        System.out.println("精确的音轨长度: " + header.getPreciseTrackLength()); //精确的音轨长度
        //if (header.getTrackLength()>60){
            String line = "D:\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe -i %s -f segment -segment_time 90 -c copy %s";
            line=String.format(line,mp3Path,"D:\\音频分割\\g%2d.mp3");
            CommandLine cmdLine = CommandLine.parse(line);
            DefaultExecutor executor = new DefaultExecutor();
            try {
                executor.execute(cmdLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LfasrClient lfasrClient=null;
            int j=header.getTrackLength()+1;
            while (j<header.getTrackLength()+1){
                //File file=new File("D:\\音频分割\\g0"+i+".mp3");
                String AUDIO_FILE_PATH="D:\\音频分割\\g0"+j+".mp3";
                System.out.println(AUDIO_FILE_PATH);
                if (j==0){
                    lfasrClient = LfasrClient.getInstance(APP_ID, SECRET_KEY);
                }
                try {
                    Message task = lfasrClient.upload(AUDIO_FILE_PATH);
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
                    j++;
                } catch (Exception e) {
                    j++;
                   // e.printStackTrace();
                }
         //   }
        }
    }

    @Test
    public void test5() throws Exception{
       /* Lischtxt lischtxt=new Lischtxt();
        System.out.println(lischtxt.listxt());*/
        String str = "基本铲球 \n" +
                    "把球踢到你正前方大约4至5米（13-15英尺）的位置，然后追着球跑。弯曲左腿，以左腿外侧铲球，用手臂保持平衡当球触手可及时，右腿猛然发力，用脚背将球踢开，然后尽快站起来。\n" +
                    "提示\n" +
                    "在铲球后保持左腿弯曲，这样你就可以用这条腿站起来。\n" +
                    "最常犯的错误\n" +
                    "未找准铲球时机，踢球啥。";//原稿
        String contrasttext = "基本铲h球，把球踢到你正前方大约4~5米，13~15英尺的位置，然后追着球跑，弯曲左腿，" +
                    "以左腿外侧铲球，用手臂保持平衡，当球触手可及时，右腿猛然发力，用脚背将球踢开，" +
                    "然后尽快站起来，提示在铲球后保持左腿弯曲，这样你就可以用这条腿站起来。" +
                    "最常犯的错误为找准铲球时机，踢不到球。";//对比稿
        String text1="大家好欢迎参加未来语言的汉语零基础入门教程\n" +
                "欢迎参加未来语言的汉语教程\n" +
                "好好你好您老师再见在见\n" +
                "我他门我们你们他们他们朋友是的我的你的他的他的我们的你们的他们的他们的学生";
        String text2="大家好欢迎参加未来语言的汉语零基础入门教程欢迎参加未来语言的汉语教程你好你好您老师再见再见我他她们我们你们他们她们朋友是的我的你的他的她的我们的你们的他们的她们的学生";
        String text3="未找准铲球时机，踢不到球";
        String text4="为找准铲球时机，踢球";
        OldNew oldNew=textService.sel();;
        //System.out.println(oldNew);
        //String text5=oldNew.getYtext();
        //String text6=oldNew.getDtext();
        //System.out.println(text5);
        //System.out.println(text6);
        //oldandNew.dowork(oldNew.getYtext(),oldNew.getDtext());
        //oldandNew.dowork(str,contrasttext);
        //OldandNewdemo2 oldandNewdemo2=new OldandNewdemo2();
        //oldandNewdemo2.dowork(str,contrasttext);
        //oldandNew.dowork(text1,text2);
        //OldandNewdemo oldandNewdemo=new OldandNewdemo();
        //oldandNewdemo.dowork();
        //text1;
        char[] cnSignArr = "。？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$%^&*()_+~-=[];',./{}|:\"<>?".toCharArray();
        String manuscript = str.replace(" ", "");
        for (int i = 0; i < cnSignArr.length; i++) {
            manuscript = manuscript.replace("" + cnSignArr[i], "");
            contrasttext = contrasttext.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            manuscript = manuscript.replace("" + enSignArr[i], "");
            contrasttext = contrasttext.replace("" + enSignArr[i], "");
        }
        int len=0;
        int clen=0;
        DNASequence dnaSequence=new DNASequence(manuscript,contrasttext);
        dnaSequence.runAnalysis();
        dnaSequence.traceback();
        String dnas1;//算法处理之后的字符串1
        String dnas2;//算法处理之后的字符串2
        dnas1=dnaSequence.getString1();//获取处理后的字符串
        dnas2=dnaSequence.getString2();
        char[] s = dnas1.toCharArray();//字符串转Char数组
        char[] p = dnas2.toCharArray();
        len=dnas1.length();
        System.out.println(dnas1+"--"+len);
        for(int i=0;i<len;i++){
            if(s[i]=='-'){
               System.out.print("d");//少了
            }else if(p[i]=='-'){
                System.out.print("s");//多了
            }else if(s[i]==p[i]){
                System.out.print("√");//没少没多
            }else if(s[i]!=p[i]){
                System.out.print("w");//错了
            }else{
                System.out.print("考虑更多颜色");
            }
        }
        //System.out.println(oldandNew.dowork(str,contrasttext));
    }
    @Test
    public void jsontext() throws Exception{
       //Lischtxt11 lischtxt11=new Lischtxt11();
       //lischtxt11.listxt("cq.mp3");
       // String ystring=textService.sel().getYtext();
        //System.out.println(ystring);
       /* String s="ds";
        if (s.equals("s")){
            System.out.println("true");
        }else{
            System.out.println("f");
        }*/
        String manuscript = "基本铲球" +
                "把球踢到你正前方大约4至5米（13-15英尺）的位置，然后追着球跑。弯曲左腿，以左腿外侧铲球，用手臂保持平衡当球触手可及时，右腿猛然发力，用脚背将球踢开，然后尽快站起来。" +
                "提示" +
                "在铲球后保持左腿弯曲，这样你就可以用这条腿站起来。" +
                "最常犯的错误" +
                "未找准铲球时机，踢球啥。";//原稿
        String contrasttext = "基本铲h球，把球踢到你正前方大约4~5米，13~15英尺的位置，然后追着球跑，弯曲左腿，" +
                "以左腿外侧铲球，用手臂保持平衡，当球触手可及时，右腿猛然发力，用脚背将球踢开，" +
                "然后尽快站起来，提示在铲球后保持左腿弯曲，这样你就可以用这条腿站起来。" +
                "最常犯的错误为找准铲球时机，踢不到球。";//对比稿
       /* manuscript=manuscript.replace("，","。");
        char[] cnSignArr = "？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$%^&*()_+~-=[];',./{}|:\"<>?".toCharArray();
        for (int i = 0; i < cnSignArr.length; i++) {
            manuscript = manuscript.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            manuscript = manuscript.replace("" + enSignArr[i], "");
        }
        int juhao[]=new int[manuscript.length()];
        int ju=0;
        for (int i=0;i<manuscript.length();i++){
            if (manuscript.charAt(i)=='。'){
                juhao[ju]=i;
                ju++;
            }
        }
        System.out.println(manuscript.replace("。",""));
        for (int i=0;i<ju;i++){
            System.out.println(juhao[i]);
        }*/
       DuiBi duiBi=new DuiBi();
       System.out.print(duiBi.work(manuscript,contrasttext));

    }
}