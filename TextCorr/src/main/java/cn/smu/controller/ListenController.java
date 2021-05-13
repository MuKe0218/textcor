package cn.smu.controller;

import cn.smu.Bean.Fname;
import cn.smu.Dao.FnameMapper;
import cn.smu.Demo.Lischtxt11;
import cn.smu.Handle.Lischtxt;
import cn.smu.Handle.Texttwenty;
import cn.smu.Service.TextService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping("/Listen")
public class ListenController {
    @Autowired
    TextService textService;
    @Autowired
    FnameMapper fnameMapper;
    //音频路径因实际进行更改
    @PostMapping("/listen")//次级
    @ResponseBody
    public String Listen(@RequestParam("file") MultipartFile listen) throws Exception {//接收音频
        if (listen.isEmpty()) {
            return "上传音频失败，请重新选择音频";
        }else {
            String fileName = listen.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) {
                path = new File("");

            }
            File upload = new File(path.getAbsolutePath(),"static/"+fileName);
            System.out.println(path.getAbsolutePath());
            if (!upload.exists()){
                upload.mkdirs();
            }
            listen.transferTo(upload);
            new Thread(() -> {
                Fname fname=new Fname();
                fnameMapper.update(fname,new UpdateWrapper<Fname>().set("filename",fileName).eq("id",1));
            }).start();
            return fileName;
        }
    }

    String string2=null;
    @PostMapping("/listen2")//次级
    @ResponseBody
    public String Listen2() throws Exception {//音频转文本
        Fname fname= fnameMapper.selectById(1);
        System.out.println(fname);
        if (fname.getFilename()==null){
            string2="没有这个稿子";
        }else{
            System.out.println(fname.getFilename());
            Lischtxt11 lischtxt11=new Lischtxt11();
            string2=lischtxt11.listxt(fname.getFilename());
            string2=string2.replace("，","。");
            new Thread(()->{
                textService.setdtext(string2);
            }).start();
        }
        System.out.println(string2);
        return string2;
    }
}
