package cn.smu;

import cn.smu.Dao.HdtableMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest {
    @Test
    public void test1(){
        System.out.println("欢迎来到聆音");
    }
    @Autowired
    HdtableMapper hdtableMapper;
    @Test
    public void test2(){//产生随机数
        int max = 20;
        int min = 1;
        Random random = new Random();
        int s = random.nextInt(max - min + 1) + min;
        System.out.println(s);
        System.out.println(hdtableMapper.selectById(s).getHdtext());
    }
}