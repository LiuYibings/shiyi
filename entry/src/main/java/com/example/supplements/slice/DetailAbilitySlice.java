package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import com.example.supplements.Utils.LoadImageUtil;
import com.example.supplements.beans.Product;
import com.example.supplements.beans.ResultVo;
import com.example.supplements.beans.photos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;

public class DetailAbilitySlice extends AbilitySlice {
    Gson gson = new Gson();

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        setUIContent(ResourceTable.Layout_post_things);
        Image componentById3 = findComponentById(ResourceTable.Id_o3_3thing);
        componentById3.setCornerRadius(1000f);
        //获取传递的商品ID
        String address = (String) intent.getParams().getParam("address");
        String productId01 = (String) intent.getParams().getParam("title");
        String productId03 = (String) intent.getParams().getParam("price");
        String productId04 = (String) intent.getParams().getParam("remark");
        String productId05 = (String) intent.getParams().getParam("photoid");


        Text componentById = findComponentById(ResourceTable.Id_two_price);
        Text componentById01 = findComponentById(ResourceTable.Id_two_title);
        Text componentById03 = findComponentById(ResourceTable.Id_two_remark);
        Text address1 = findComponentById(ResourceTable.Id_post_thing_address);
//        Image image = findComponentById(ResourceTable.Id_index_image_things);
//        //根据商品ID查询商品详情(基本信息，套餐信息，详情，图片，评论)
//        //根据商品ID查询商品信息，商品信息中包含套餐信息和商品图片
//        getGlobalTaskDispatcher(TaskPriority.DEFAULT).syncDispatch(()->{
        componentById.setText(productId03);
        componentById01.setText(productId01);
        componentById03.setText(productId04);
        address1.setText(address);
        //将图片加载到布局中
        DirectionalLayout componentById4 = findComponentById(ResourceTable.Id_photos_post);
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/thingById/" + productId05);
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            if (resultVo.getFlag()) {
                String s1 = gson.toJson(resultVo.getData());
                List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                }.getType());
                getUITaskDispatcher().syncDispatch(() -> {
                    componentById4.removeAllComponents();
                    for (Product product : o) {
                        List<photos> photos1 = product.getPhotos();
                        String photos = null;
                        for (photos photos01 : photos1) {
                            DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_photos_post, null, false);
                            Image componentById2 = parse.findComponentById(ResourceTable.Id_posts);
                            photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                            LoadImageUtil.loadImg(this, photos, componentById2);
                            componentById4.addComponent(parse);
                        }
                    }
                });
            }
        });


        //通过点击地图照片进入地图里面
        DirectionalLayout componentById1 = findComponentById(ResourceTable.Id_map_image);
        componentById1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.supplements")
                        .withAbilityName("com.example.supplements.Main3Ability")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
    }
}