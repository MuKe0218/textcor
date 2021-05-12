package cn.smu.Handle;

import cn.smu.Bean.OldNew;
import cn.smu.Bean.Report;
import cn.smu.Dao.ReportMapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DuiBi {
    @Autowired
    private ReportMapper reportMapper;
    private static DuiBi duiBi;
    @PostConstruct
    public void init(){
        duiBi=this;
        duiBi.reportMapper=this.reportMapper;
    }
    public OldNew work(String manuscript, String contrasttext){
        /*char[] cnSignArr = "。？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$%^&*()_+~-=[];',./{}|:\"<>?".toCharArray();
        for (int i = 0; i < cnSignArr.length; i++) {
            manuscript = manuscript.replace("" + cnSignArr[i], "");
            contrasttext = contrasttext.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            manuscript = manuscript.replace("" + enSignArr[i], "");
            contrasttext = contrasttext.replace("" + enSignArr[i], "");
        }*/
       manuscript = manuscript.replace(" ", "");
        manuscript = manuscript.replace("\r", "");
        manuscript=manuscript.replace("\n","");
        //System.out.println(manuscript);
        int len=0;
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
        int[] less=new int[9000];
        int[] full=new int[9000];
        int[] wrong=new int[9000];
        int ls=0;
        int fu=0;
        int wsum=0;
        for(int i=0;i<len;i++){
            if(s[i]=='-'){//多的
                full[fu]=i;
                fu++;
                System.out.println(String.valueOf(p[i]));
            }else if(p[i]=='-'){//少的
                less[ls]=i;
                ls++;
                System.out.println(String.valueOf(s[i]));
            }else if(s[i]!=p[i]){//错的
                wrong[wsum]=i;
                wsum++;
                System.out.println(String.valueOf(s[i])+"--"+String.valueOf(p[i]));
            }
        }
        System.out.println(dnas1);
        System.out.println(dnas2);
        int clen=contrasttext.length();
        float sum=clen-(fu+wsum);
        System.out.println("匹配率为:"+String.format("%.2f",sum/clen*100).toString()+"%");
        System.out.println("错了:"+wsum+"个字");
        System.out.println("对比稿比原稿少了:"+ls+"个字");
        System.out.println("对比稿比原稿多了:"+fu+"个字");
        String str1="<span style=\"background-color: #cd2020;\">";//错了
        String str2="<span style=\"background-color: #3327c1;\">";//多了少了
        String str3="</span>";
        OldNew oldNew=new OldNew();
        int[] a=new int[ls+wsum];//用来标记原稿多的错的
        int[] b=new int[fu+wsum];//用来标记对比稿多的错的
        int k=0;
        int j=0;
        for (int i=0;i<ls;i++){//查找在原稿中对于对比稿来说少的位置
            while(j<fu){
                if (full[j]<less[i]){
                    k++;
                    j++;
                }else{
                    break;
                }
            }
            a[i]=less[i]-k;
        }
        /*for (int i=0;i<ls;i++){
            System.out.println(a[i]);
            System.out.println(manuscript.charAt(a[i]));
        }*/

        int m=0;
        int n=0;
        for (int i=0;i<fu;i++){//查找对比稿中比原稿多的位置
            while(n<ls){
                if (less[n]<full[i]){
                    m++;
                    n++;
                }else{
                    break;
                }
            }
            b[i]=full[i]-m;
        }
       /* for (int i=0;i<fu;i++){
            System.out.println(b[i]);
            System.out.println(contrasttext.charAt(b[i]));
        }*/

        int yk=0;
        int dk=0;
        int yj=0;
        int dj=0;
        int wm=ls;
        int wn=fu;
        for (int i=0;i<wsum;i++){//找错的位置
            while(yj<fu){//找原稿错的位置
                if (full[yj]<wrong[i]){
                    yk++;
                    yj++;
                }else{
                    break;
                }
            }
            a[wm]=wrong[i]-yk;
            wm++;
            while(dj<ls){//找对比稿错的位置
                if (less[dj]<wrong[i]){
                    dk++;
                    dj++;
                }else{
                    break;
                }
            }
            b[wn]=wrong[i]-dk;
            wn++;
        }
       /* for (int i=ls;i<wm;i++){
            System.out.println(a[i]);
            System.out.println(manuscript.charAt(a[i]));
        }
        for (int i=fu;i<wn;i++){
            System.out.println(b[i]);
            System.out.println(contrasttext.charAt(b[i]));
        }*/

        String[] ystr=new String[manuscript.length()];
           for(int i=0;i<manuscript.length();i++){
               ystr[i]=manuscript.substring(i,i+1);
           }
           for (int i=0;i<wm;i++){
               if (i<ls){
                   ystr[a[i]]=str2+manuscript.charAt(a[i])+str3;
               }else{
                   ystr[a[i]]=str1+manuscript.charAt(a[i])+str3;
               }
           }
       //System.out.println(ystr[6]);
        String[] cstr=new String[contrasttext.length()];
        for (int i=0;i<contrasttext.length();i++){
            cstr[i]=contrasttext.substring(i,i+1);
        }
        for (int i=0;i<wn;i++){
            if (i<fu){
                cstr[b[i]]=str2+contrasttext.charAt(b[i])+str3;
            }else{
                cstr[b[i]]=str1+contrasttext.charAt(b[i])+str3;
            }
        }
        String ystring=ystr[0];
        for (int i=1;i<manuscript.length();i++){
            ystring=ystring+ystr[i];
        }
        String cstring=cstr[0];
        for (int i=1;i<contrasttext.length();i++){
            cstring=cstring+cstr[i];
        }
        System.out.println(ystring);
        System.out.println(cstring);
        Report report=new Report();
        report.setId(1);
        if (manuscript.length()<clen){
            report.setPipei(String.format("%.2f",sum/manuscript.length()*100).toString()+"%");
        }else{
            report.setPipei(String.format("%.2f",sum/clen*100).toString()+"%");
        }
        report.setWrong(wsum);
        report.setMuch(fu);
        report.setLess(ls);
        duiBi.reportMapper.update(report,new UpdateWrapper<Report>().set("pipei",report.getPipei()).set("wrong",report.getWrong()).set("much",report.getMuch()).set("less",report.getLess()).eq("id",1));
        oldNew.setYtext(ystring);
        oldNew.setDtext(cstring);
        return oldNew;
    }
}
