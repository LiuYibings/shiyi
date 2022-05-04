package com.example.supplements.provider;

import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.LoadImageUtil;
import com.example.supplements.beans.IndexImg;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class ThingIndexImgPageSliderProvider extends PageSliderProvider {
    private List<String> list;
    private AbilitySlice abilitySlice;

    public ThingIndexImgPageSliderProvider(List<String> list, AbilitySlice abilitySlice) {
        this.list = list;
        this.abilitySlice = abilitySlice;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
//        获取轮廓图模板
        DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(abilitySlice).parse(ResourceTable.Layout_index_img_template, null, false);
        Image Image = parse.findComponentById(ResourceTable.Id_index_image);
//        获取轮廓图数据:此对象中存储的是图片的网络地址
        String indexImg = list.get(i);
        String netImgUrl= "http://162.14.74.164:8888/photo/"+indexImg;   //http://162.14.74.164:8888/image/1
        //        渲染数据:如何将一个网络图片绑定到HOS应用组将中？？？  ----网络图片载入
        LoadImageUtil.loadImg(abilitySlice,netImgUrl,Image);
//        将图片模板绑定到pageSlider
        componentContainer.addComponent(parse);
        return null;
    }

    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {

    }

    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        return false;
    }
}
