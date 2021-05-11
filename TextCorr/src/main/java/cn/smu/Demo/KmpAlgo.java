package cn.smu.Demo;

public class KmpAlgo {
    //寻找待匹配串的部分匹配值，放在next数组中
    static void getNext(String pattern,int[] next){
        int j = 0;
        int k = -1;
        next[0] = -1;
        int len = pattern.length();
        while(j < len-1){
            if(k == -1 || pattern.charAt(j) == pattern.charAt(j)){
                j++;
                k++;
                next[j] = k;
            }else{
                k = next[k];
            }
        }
        
    }
    
    static int kmp(String s,String pattern){
        int i = 0;
        int j = 0;
        int slen = s.length();
        int plen = pattern.length();
        int[] next = new int[plen];
        getNext(pattern,next);
        while(i < slen && j < plen){
            if(s.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
                
            }else if(next[j] == -1){
                i++;
                j = 0;
            }else{
                j = next[j];
            }
            if(j == plen){
                return i-j;
            }
        }
        return -1;
        
    }
    /**
     *@param
     */
    public static void main(String[] args){
        String str = "ABCDABDEYGF";
        String pat = "ABCDABD";
        //KmpAlgo.kmp(str, pat);
        System.out.println(KmpAlgo.kmp(str, pat));
    }
 
}
