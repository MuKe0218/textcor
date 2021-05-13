package cn.smu.Service;

import cn.smu.Bean.User;
import cn.smu.Dao.UserMapper;
import cn.smu.Service.Impl.UserImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserService implements UserImpl{

    @Autowired
    UserMapper userMapper;
    @Override
    public Boolean loginuser(User user) {
        User user1=userMapper.selectOne(new QueryWrapper<User>().eq("userid",user.getUserid()).eq("userpwd",user.getUserpwd()));
        if(user1==null){
            return false;//没有此用户
        }else{
            return true;//有此用户
        }
    }

    @Override
    public Boolean resuser(User user) {
        User user1=userMapper.selectOne(new QueryWrapper<User>().eq("userid",user.getUserid()));
        if(user1==null){
            userMapper.insert(user);
            return true;//注册成功
        }else{
            return false;//注册失败，这个用户已被注册
        }
    }
}
