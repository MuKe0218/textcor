package cn.smu.Demo;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class Linstenwork {
    public static void main(String[] args) {

        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(path.getAbsolutePath());

    }
}
