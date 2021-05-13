package cn.smu.Bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("fname")
public class Fname {
    @TableId
    Integer id;
    String yfilename;
    String filename;

    public Fname() {
    }

    public Fname(Integer id, String yfilename, String filename) {
        this.id = id;
        this.yfilename = yfilename;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYfilename() {
        return yfilename;
    }

    public void setYfilename(String yfilename) {
        this.yfilename = yfilename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "Fname{" +
                "id=" + id +
                ", yfilename='" + yfilename + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
