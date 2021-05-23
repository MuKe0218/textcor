package cn.smu.Controller;

import cn.smu.Dao.HdtableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping("/interaction")
public class ShiController {

    @Autowired
    HdtableMapper hdtableMapper;
    @PostMapping("/choicetext")
    @ResponseBody
    public String reinfo(){
        int max = 20;
        int min = 1;
        Random random = new Random();
        int s = random.nextInt(max - min + 1) + min;
        String text=hdtableMapper.selectById(s).getHdtext();
        return text;
    }
}
