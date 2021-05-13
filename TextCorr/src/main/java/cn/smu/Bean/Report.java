package cn.smu.Bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("reptext")
public class Report {
    @TableId
    int id;
    String pipei;
    int wrong;
    int much;
    int less;

    public Report() {
    }

    public Report(int id, String pipei, int wrong, int much, int less) {
        this.id = id;
        this.pipei = pipei;
        this.wrong = wrong;
        this.much = much;
        this.less = less;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPipei() {
        return pipei;
    }

    public void setPipei(String pipei) {
        this.pipei = pipei;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public int getMuch() {
        return much;
    }

    public void setMuch(int much) {
        this.much = much;
    }

    public int getLess() {
        return less;
    }

    public void setLess(int less) {
        this.less = less;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", pipei='" + pipei + '\'' +
                ", wrong=" + wrong +
                ", much=" + much +
                ", less=" + less +
                '}';
    }
}
