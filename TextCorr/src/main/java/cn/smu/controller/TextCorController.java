package cn.smu.controller;
import cn.smu.Bean.Fname;
import cn.smu.Bean.OldNew;
import cn.smu.Bean.Report;
import cn.smu.Dao.FnameMapper;
import cn.smu.Dao.ReportMapper;
import cn.smu.Handle.DuiBi;
import cn.smu.Handle.Texttwenty;
import cn.smu.Service.TextService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Controller
@RequestMapping("/Text")
public class TextCorController {
    @Autowired
    TextService textService;
    @Autowired
    ReportMapper reportMapper;
    @Autowired
    FnameMapper fnameMapper;
    @PostMapping("/textcor")//次级
    @ResponseBody
    public OldNew TextCor1() throws Exception{
        OldNew oldNew=textService.sel();
        DuiBi duiBi=new DuiBi();
        if (oldNew.getYtext()==null||oldNew.getDtext()==null){
            oldNew.setYtext("null");
            oldNew.setDtext("null");
        }else{
            oldNew=duiBi.work(oldNew.getYtext(),oldNew.getDtext());
        }
        return oldNew;
    }

    String ystring;
    @PostMapping( "/upload")
    @ResponseBody
    public Object upload(@RequestParam("file") MultipartFile file) throws Exception{
        String[] ystr=new String[10000];
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }else{
            String fileName = file.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) {
                path = new File("");

            }
            File upload = new File(path.getAbsolutePath(),"static/"+fileName);
            if (!upload.exists()){
                upload.mkdirs();
            }
            file.transferTo(upload);
            System.out.println(path.getAbsolutePath());
            String getfilePath = path.getAbsolutePath()+"/static/"+fileName;
            File getfile = new File(getfilePath);
            int j=0;
            if (getfile.isFile() && getfile.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(getfile), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    ystr[j]=lineTxt;
                    j++;
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
            if (ystr[0]==null){
                ystring=null;
            }else{
                ystring=ystr[0];
            }
            for (int i=1;i<j;i++){
                if (String.valueOf(ystring.charAt(ystring.length()-1)).equals("。")){
                    ystring=ystring+ystr[i];
               }else{
                ystring=ystring+"。"+ystr[i];
               }
            }
            new Thread(() -> {
                if (String.valueOf(ystring.charAt(ystring.length()-1)).equals("。")==false)
                    ystring=ystring+"。";
                textService.setytext(ystring);
            }).start();
            new Thread(() -> {
                Fname fname=new Fname();
                fnameMapper.update(fname,new UpdateWrapper<Fname>().set("yfilename",fileName).eq("id",1));
            }).start();
            return fileName;
        }
    }

    @PostMapping( "/totext")
    @ResponseBody
    public String totext() throws Exception{
            String ystring=textService.sel().getYtext();
           ystring=ystring.replace("，","。");
            return ystring;
    }

    @PostMapping( "/jsontext")
    @ResponseBody
    public String jsontext() throws Exception {
        BufferedReader br = null;
        //String path = "datas.json";
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!path.exists()) {
            path = new File("");
        }
        String line=null;
        br = new BufferedReader(new InputStreamReader(JsonTest.class.getClassLoader().getResourceAsStream(path.getAbsolutePath()+"/static/datas.json")));
        line = br.readLine();
        return line;
    }

    @PostMapping( "/reptext")
    @ResponseBody
    public Report reptext() throws Exception {
        Report report=new Report();
        report=reportMapper.selectById(1);
        return report;
    }
}
