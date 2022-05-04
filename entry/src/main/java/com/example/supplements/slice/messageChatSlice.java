package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import com.example.supplements.Utils.LoadImageUtil;
import com.example.supplements.beans.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class messageChatSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        Gson gson = new Gson();
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_one1);
        String userid = (String) intent.getParams().getParam("userid");
        String thingid = (String) intent.getParams().getParam("thingid");
        String userByid = (String) intent.getParams().getParam("userById");
        String userByid2 = (String) intent.getParams().getParam("userById2");
        //用户名称
        Text componentById = findComponentById(ResourceTable.Id_one_username);
        componentById.setText(userid.toString());
        //设置用户的id
        Text componentById2 = findComponentById(ResourceTable.Id_one_userid);
        componentById2.setText(userByid);
        //积分设置
        Text componentById3 = findComponentById(ResourceTable.Id_one_thing_price);
        //标题的设置
        Text componentById4 = findComponentById(ResourceTable.Id_one_thing_title);
        AtomicInteger idone = new AtomicInteger();
        //设置别人发的信息
        //        获取推荐商品数据，显示到ability_main_index.xml布局文件中
        DirectionalLayout productListTable1 = findComponentById(ResourceTable.Id_one_ohter_chat);
        DirectionalLayout productListTable2 = findComponentById(ResourceTable.Id_one_my_chat);
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/caths/" + userByid + "/" + userByid2);
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
            if (resultVo.getFlag() == true) {
                String s1 = gson.toJson(resultVo.getData());
                System.out.println(s1);
                List<cath> o = gson.fromJson(s1, new TypeToken<List<cath>>() {
                }.getType());
//                将获取到的商品列表显示到table布局中
                getUITaskDispatcher().syncDispatch(() -> {
                    productListTable1.removeAllComponents();
                    productListTable2.removeAllComponents();
                    for (cath product : o) {
                        idone.set(product.getIdone());
                        List<params> params = product.getParams();
                        for (params param : params) {
                            if (param.getWho() == 1) {
                                DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_one_user_message, null, false);
                                Text componentById1 = parse.findComponentById(ResourceTable.Id_orther_user);
                                componentById1.setText(param.getOne());
                                productListTable1.addComponent(parse);
                            } else {
                                DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_one_myuser_message, null, false);
                                Text componentById1 = parse.findComponentById(ResourceTable.Id_my_message);
                                componentById1.setText(param.getOne());
                                productListTable2.addComponent(parse);
                            }
                        }
                    }
                });
            } else {
//                showDialog("获取信息错误");
            }
        });
        //用户发送消息
        Button componentById6 = findComponentById(ResourceTable.Id_message_but);
        componentById6.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Text componentById5 = findComponentById(ResourceTable.Id_one_img2);
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                    HttpRequestUtil.sendGetRequest(new registerSlice(), "http://162.14.74.164:8888/cathsin/"+componentById5.getText()+"/1/"+idone);
                });
            }
        });


//        获取推荐商品数据，显示到ability_main_index.xml布局文件中
        DirectionalLayout productListTable = findComponentById(ResourceTable.Id_one_thing_photo);
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/thingById/" + thingid);
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
            if (resultVo.getFlag()) {
                String s1 = gson.toJson(resultVo.getData());
                System.out.println(s1);
                List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                }.getType());
//                将获取到的商品列表显示到table布局中
                getUITaskDispatcher().syncDispatch(() -> {
                    productListTable.removeAllComponents();
                    for (Product product : o) {
                        //设置积分
                        componentById3.setText(product.getPrice());
                        //设置标题
                        componentById4.setText(product.getTitle());
//                    获取table中显示的每个商品的模板
                        DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_one_thing_photo, null, false);
                        Image componentById1 = parse.findComponentById(ResourceTable.Id_one_photo);
                        List<photos> photos1 = product.getPhotos();
                        String photos = null;
                        for (photos photos01 : photos1) {
                            photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                        }
                        LoadImageUtil.loadImg(this, photos, componentById1);
//                    将完成数据渲染的模板添加到table中
                        productListTable.addComponent(parse);
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
