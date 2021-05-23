package cn.smu.Bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("hdtable")
public class Hdtable {
    @TableId
    int id;
    String hdtext;

    public Hdtable() {
    }

    public Hdtable(int id, String hdtext) {
        this.id = id;
        this.hdtext = hdtext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHdtext() {
        return hdtext;
    }

    public void setHdtext(String hdtext) {
        this.hdtext = hdtext;
    }

    @Override
    public String toString() {
        return "Hdtable{" +
                "id=" + id +
                ", hdtext='" + hdtext + '\'' +
                '}';
    }
}
