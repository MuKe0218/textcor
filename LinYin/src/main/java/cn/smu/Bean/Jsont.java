package cn.smu.Bean;

public class Jsont {
    String bg;
    String ed;
    String onebest;
    String speaker;

    public Jsont() {
    }

    public Jsont(String bg, String ed, String onebest, String speaker) {
        this.bg = bg;
        this.ed = ed;
        this.onebest = onebest;
        this.speaker = speaker;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getEd() {
        return ed;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public String getOnebest() {
        return onebest;
    }

    public void setOnebest(String onebest) {
        this.onebest = onebest;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    @Override
    public String toString() {
        return "Jsont{" +
                "bg='" + bg + '\'' +
                ", ed='" + ed + '\'' +
                ", onebest='" + onebest + '\'' +
                ", speaker='" + speaker + '\'' +
                '}';
    }
}
