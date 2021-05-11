package cn.smu.Bean;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.beans.factory.annotation.Autowired;

@TableName("newandold")
public class OldNew {
    @TableId
    Integer id;
    String ytext;
    String dtext;

    public OldNew() {
    }

    public OldNew(Integer id, String ytext, String dtext) {
        this.id = id;
        this.ytext = ytext;
        this.dtext = dtext;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYtext() {
        return ytext;
    }

    public void setYtext(String ytext) {
        this.ytext = ytext;
    }

    public String getDtext() {
        return dtext;
    }

    public void setDtext(String dttext) {
        this.dtext = dttext;
    }

    @Override
    public String toString() {
        return "OldNew{" +
                "id=" + id +
                ", ytext='" + ytext + '\'' +
                ", dtext='" + dtext + '\'' +
                '}';
    }
}
