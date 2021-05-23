package cn.smu.Controller;

import cn.smu.Bean.Fname;
import cn.smu.Bean.Jsont;
import cn.smu.Dao.FnameMapper;
import cn.smu.Handle.Lischtxt;
import cn.smu.Handle.Readtxt;
import cn.smu.Service.TextService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

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
            Lischtxt lischtxt11=new Lischtxt();
            string2=lischtxt11.listxt(fname.getFilename());
            //string2=string2.replace("，","。");
            new Thread(()->{
                textService.setdtext(string2);
            }).start();
        }
        System.out.println(string2);
        return string2;
    }

    @PostMapping("/listentime")//时间戳
    @ResponseBody
    public String Listentime(@RequestParam("file") MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            return "上传失败";
        }else {
            String fileName = file.getOriginalFilename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/" + fileName);
            System.out.println(path.getAbsolutePath());
            if (!upload.exists()) {
                upload.mkdirs();
            }
            file.transferTo(upload);
            Fname fname=new Fname();
            fnameMapper.update(fname,new UpdateWrapper<Fname>().set("timename",fileName).eq("id",1));
            return fileName;
        }
    }
    @PostMapping("/listentext")
    @ResponseBody
    public List<Jsont> Listentext() throws Exception{//时间戳返回文本
            String fileName=fnameMapper.selectById(1).getTimename();
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            System.out.println(path.getAbsolutePath());
            Readtxt readtxt=new Readtxt();
            //List dataList = readtxt.readTXT("D:\\Git\\kqlgit\\TextCorr\\src\\main\\resources\\[Chinese (Traditional)] Trying to make money as a freelance artist [DownSub.com].srt");
            List dataList=readtxt.readTXT(path.getAbsolutePath()+"/static/"+fileName);
            //List dataList = readTXT("[Chinese (Traditional)] Trying to make money as a freelance artist [DownSub.com].srt");
            System.out.println(dataList);
            for (int i = 0; i < dataList.size(); i++) {

                Map data= (Map) dataList.get(i);
                String time = (String) data.get("time");
//            System.out.println(time);//时间戳
                String value = (String) data.get("value");
//            System.out.println(value);//字幕内容
                int maxSplit = 2;

                String[] sourceStrArray = time.split(" --> ", maxSplit);
                String starttime = sourceStrArray[0];
                String endtime = sourceStrArray[1];
                long difftime = readtxt.getDiffTime(sourceStrArray[1],sourceStrArray[0]);
//            System.out.println(difftime); //时间戳毫秒数
                data.put("starttime",starttime);
                data.put("endtime",endtime);
                data.put("difftime",difftime);
                data.remove("time");
            }
        //JSONArray jsonArray=JSONArray.f
        //System.out.println(jsonArray);
        return dataList;
    }
}
