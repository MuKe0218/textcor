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
        manuscript=manuscript.replace("，","。");
        char[] cnSignArr = "？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$%^&*()_+~-=[];',./{}|:\"<>?".toCharArray();
        for (int i = 0; i < cnSignArr.length; i++) {
            manuscript = manuscript.replace("" + cnSignArr[i], "");
            contrasttext = contrasttext.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            manuscript = manuscript.replace("" + enSignArr[i], "");
            contrasttext = contrasttext.replace("" + enSignArr[i], "");
        }

        int juhao[]=new int[manuscript.length()];
        int ju=0;
        for (int i=0;i<manuscript.length();i++){
            if (manuscript.charAt(i)=='。'){
                juhao[ju]=i;
                ju++;
            }
        }
        /*for (int i=0;i<ju;i++){
            System.out.println(juhao[i]);
        }*/
        //System.out.println(manuscript);
        //System.out.println(contrasttext);
        manuscript=manuscript.replace("。","");
        contrasttext=contrasttext.replace("。","");
       /*manuscript = manuscript.replace(" ", "");
        manuscript = manuscript.replace("\r", "");
        manuscript=manuscript.replace("\n","");*/

        //manuscript=manuscript.replace("，","。");
        //contrasttext=contrasttext.replace("，","。");
        //System.out.println(manuscript);
        //Texttwenty texttwenty=new Texttwenty();
        //manuscript=texttwenty.Texttw(manuscript);
        //contrasttext=texttwenty.Texttw(contrasttext);
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
                //System.out.println(String.valueOf(p[i]));
            }else if(p[i]=='-'){//少的
                less[ls]=i;
                ls++;
                //System.out.println(String.valueOf(s[i]));
            }else if(s[i]!=p[i]){//错的
                wrong[wsum]=i;
                wsum++;
                //System.out.println(String.valueOf(s[i])+"--"+String.valueOf(p[i]));
            }
        }
        //System.out.println(dnas1);
        //System.out.println(dnas2);
        //新增
        String[] strdnas1=new String[dnas1.length()];
        String[] strdnas2=new String[dnas2.length()];
        for (int i=0;i<dnas1.length();i++){
            strdnas1[i]=dnas1.substring(i,i+1);
        }
        for (int i=0;i<dnas2.length();i++){
            strdnas2[i]=dnas2.substring(i,i+1);
        }
        String newdans1=strdnas1[0];
        String newdans2=strdnas2[0];
        int dnask=0;
        int gd=0;
        int juhao2[]=new int[ju];
        for (int i=0;i<ju-1;i++){
            for(int o=gd;o<=juhao[i]-i;o++){
                if (strdnas1[o].equals("-")){
                    dnask++;
                }
                //System.out.println(strdnas1[o]);
            }
            gd=juhao[i];
            juhao2[i]=juhao[i]+dnask;
        }
        /*for (int i=0;i<ju-1;i++){
            System.out.println(juhao2[i]);
        }*/
        //System.out.println(dnas1.length());
        int r=0;
        int rr=0;
        for (int i=1;i<dnas2.length();i++){
            for (int l=r;l<ju;l++){
                if (i==juhao2[l]-l){
                    r=l;
                    rr=1;
                }
            }
            if (rr==1){
                newdans2=newdans2+"。"+strdnas1[i];
                rr=0;
            }else{
                newdans2=newdans2+strdnas2[i];
            }
        }
        //System.out.println(newdans2);
        String string=newdans2.replace("-","");
        //System.out.println(string);
        int[] cjuhao=new int[string.length()];
        int cju=0;
        for (int i=0;i<string.length();i++){
            if (string.charAt(i)=='。'){
                cjuhao[cju]=i;
                cju++;
            }
        }
        /*for (int i=0;i<cju;i++){
            System.out.println(cjuhao[i]);
        }*/



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
        int ykk=0;
        int flag=0;
        for (int i=1;i<manuscript.length();i++){
            for (int l=ykk;l<ju;l++){
                if (i==juhao[l]-l){
                    //System.out.println(i+":"+ystr[i]);
                    flag=1;
                    ykk=l;
                }
            }
            if (flag==1){
                ystring=ystring+"。"+ystr[i];
                flag=0;
            }else{
                ystring=ystring+ystr[i];
            }
        }
        ystring=ystring+"。";
        String cstring=cstr[0];
        int cykk=0;
        int cflag=0;
        for (int i=1;i<contrasttext.length();i++){
            for (int l=cykk;l<cju;l++){
                if (i==cjuhao[l]-l){
                    //System.out.println(i+":"+ystr[i]);
                    cflag=1;
                    cykk=l;
                }
            }
            if (cflag==1){
                cstring=cstring+"。"+cstr[i];
                cflag=0;
            }else{
                cstring=cstring+cstr[i];
            }
        }
        cstring=cstring+"。";
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
