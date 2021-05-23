package cn.smu.Service.Impl;

import cn.smu.Bean.OldNew;

public interface TextImpl {
    void setytext(String ytext);
    void setdtext(String dtext);
    OldNew sel();
    void del();
}
