package cn.smu.Service;

import cn.smu.Bean.OldNew;
import cn.smu.Dao.TextMapper;
import cn.smu.Service.Impl.TextImpl;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TextService implements TextImpl{
    @Autowired
    TextMapper textMapper;
    OldNew oldNew=new OldNew();
    @Override
    public void setytext(String ytext) {
        oldNew.setYtext( ytext);
        textMapper.update(oldNew,new UpdateWrapper<OldNew>().set("ytext",oldNew.getYtext()).eq("id",1));
    }

    @Override
    public void setdtext(String dtext) {
        oldNew.setDtext(dtext);
        textMapper.update(oldNew,new UpdateWrapper<OldNew>().set("dtext",oldNew.getDtext()).eq("id",1));
    }

    @Override
    public OldNew sel() {
        oldNew=textMapper.selectById(1);
        return oldNew;
    }

    @Override
    public void del() {
        textMapper.update(oldNew,new UpdateWrapper<OldNew>().set("ytext",null).set("dtext",null).eq("id",1));
    }
}
