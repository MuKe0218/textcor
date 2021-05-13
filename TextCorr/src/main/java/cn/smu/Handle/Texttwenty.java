package cn.smu.Handle;

public class Texttwenty {
    public String Texttw(String string2){
        char[] cnSignArr = "。？！，、；：“” ‘’「」『』（）〔〕【】—﹏…～·《》〈〉".toCharArray();
        char[] enSignArr = "`!@#$%^&*()_+~-=[];',./{}|:\"<>?".toCharArray();
        for (int i = 0; i < cnSignArr.length; i++) {
            string2 = string2.replace("" + cnSignArr[i], "");
        }
        for (int i = 0; i < enSignArr.length; i++) {
            string2 = string2.replace("" + enSignArr[i], "");
        }
       /* int len=string2.length();
        int k=0;
        float num=(float) len/20;
        int fk=len/20;
        String string=null;
        String[] str=new String[fk+1];
        if (len<20){
            string=string2+"。";
        }else{
            for (int i=0;i<fk;i++){
                str[i]=string2.substring(k,(i+1)*20)+"。";
                k=(i+1)*20;
            }
            System.out.println(len);
            if (num>fk){
                str[fk]=string2.substring(k,len)+"。";
                //System.out.println(str[fk]);
            }
            string=str[0];
            for (int i=1;i<fk+1;i++){
                string=string+str[i];
            }
        }*/
        return string2;
    }
}
