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
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.ArrayList;
import java.util.List;

public class catchuser extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        String id = (String) intent.getParams().getParam("id");
        Gson gson = new Gson();
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_classification_thing);
        TableLayout productListTable = findComponentById(ResourceTable.Id_search_result_tablelayout_class);
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/thingcatch/" + id);
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
            if (resultVo.getFlag() == true) {
                String s1 = gson.toJson(resultVo.getData());
                System.out.println(s1);
                List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                }.getType());
//                将获取到的商品列表显示到table布局中
                getUITaskDispatcher().syncDispatch(() -> {
                    productListTable.removeAllComponents();
                    for (Product product : o) {
//                    获取table中显示的每个商品的模板
                        DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_product_list_item_template, null, false);
//                    将每个商品的信息设置到模板组件
                        Image image = parse.findComponentById(ResourceTable.Id_product_image);
                        Text title = parse.findComponentById(ResourceTable.Id_product_name_title);
                        Text price = parse.findComponentById(ResourceTable.Id_product_price_text);
                        Text address = parse.findComponentById(ResourceTable.Id_product_address_text);
                        title.setText(product.getTitle());
                        price.setText(product.getPrice());
                        address.setText(product.getAddress());
                        List<photos> photos1 = product.getPhotos();
                        String photos = null;
                        ArrayList<String> strings = new ArrayList<String>();
                        for (photos photos01 : photos1) {
                            strings.add(String.valueOf(photos01.getId()));
                            photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                        }
                        LoadImageUtil.loadImg(this, photos, image);
//                    将完成数据渲染的模板添加到table中
                        productListTable.addComponent(parse);
//                    添加商品项的点击事件，跳转并将商品ID传递给详情页面
                        parse.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                Intent intent = new Intent();
                                intent.setParam("productId", String.valueOf(product.getId()));
                                intent.setParam("photo", String.valueOf(product.getPhotos()));
                                intent.setParam("title", String.valueOf(product.getTitle()));
                                intent.setParam("date", String.valueOf(product.getDate()));
                                intent.setParam("price", String.valueOf(product.getPrice()));
                                intent.setParam("remark", String.valueOf(product.getRemark()));
                                intent.setParam("photoid", String.valueOf(strings));
//                            for (photos photos01:photos1) {
//                                intent.setParam("photos",String.valueOf("http://162.14.74.164:8888/photo/" + photos01.getPhotoid()));
//                            }
                                present(new DetailAbilitySlice(), intent);
                            }
                        });
                    }
                });
            } else {
                showDialog("获取信息错误");
            }
        });
    }

    //    土司弹框
    private void showDialog(String msg) {
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_layout_toast, null, false);
        Text msgText = (Text) layout.findComponentById(ResourceTable.Id_msg);
        msgText.setText(msg);
        new ToastDialog(this)
                .setDuration(5000)
                .setContentCustomComponent(layout)
                .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                .show();
    }
}

