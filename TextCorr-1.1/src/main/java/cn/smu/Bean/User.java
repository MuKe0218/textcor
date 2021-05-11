package cn.smu.Bean;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("usertable")
public class User {
    @TableId
    Integer id;
    String userid;
    String userpwd;
    Integer state;

    public User() {
    }

    public User(Integer id, String userid, String userpwd, Integer state) {
        this.id = id;
        this.userid = userid;
        this.userpwd = userpwd;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", userpwd='" + userpwd + '\'' +
                ", state=" + state +
                '}';
    }
}
