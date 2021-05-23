package cn.smu.Bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("fname")
public class Fname {
    @TableId
    Integer id;
    String yfilename;
    String filename;
    String timename;
    public Fname() {
    }

    public Fname(Integer id, String yfilename, String filename, String timename) {
        this.id = id;
        this.yfilename = yfilename;
        this.filename = filename;
        this.timename = timename;
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

    public String getTimename() {
        return timename;
    }

    public void setTimename(String timename) {
        this.timename = timename;
    }

    @Override
    public String toString() {
        return "Fname{" +
                "id=" + id +
                ", yfilename='" + yfilename + '\'' +
                ", filename='" + filename + '\'' +
                ", timename='" + timename + '\'' +
                '}';
    }
}
