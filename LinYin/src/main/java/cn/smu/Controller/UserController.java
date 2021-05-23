package cn.smu.Controller;

import cn.smu.Bean.User;
import cn.smu.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")//次级
    @ResponseBody
   public Boolean login(@RequestBody String str, HttpServletRequest request){//登录
        //true登录成功
        //false登录失败
        String[] string=str.split("\"");
        User user=new User();
        user.setUserid(string[3]);
        user.setUserpwd(string[7]);
        System.out.println(user);
        if (userService.loginuser(user)){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return true;
        }else{
            return false;
        }

    }

    @PostMapping("/register")
    @ResponseBody
    public Boolean register(@RequestBody User user){//注册
        //true注册成功
        //false注册失败，这个用户已被注册
        return userService.resuser(user);
    }

}
