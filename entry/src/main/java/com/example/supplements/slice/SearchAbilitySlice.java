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
import ohos.app.dispatcher.task.TaskPriority;

import java.util.ArrayList;
import java.util.List;

public class SearchAbilitySlice extends AbilitySlice {
    private Gson gson = new Gson();

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_search);
        Button componentById = findComponentById(ResourceTable.Id_search_btn);
        TextField componentById1 = findComponentById(ResourceTable.Id_search_textfield);
        componentById.setClickedListener(component -> {
//            获取输入的搜索关键字
            String text = componentById1.getText();
            getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
//                请求接口进行查询
                String url = "http://162.14.74.164:8888/thingGetName/" + text;
                String s = HttpRequestUtil.sendGetRequest(this, url);
//                处理数据
                ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                if (resultVo.getFlag()) {
                    String s1 = gson.toJson(resultVo.getData());
                    List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                    }.getType());
//                    将list集合中的商品信息显示
                    TableLayout componentById2 = findComponentById(ResourceTable.Id_search_result_tablelayout);
                    getUITaskDispatcher().asyncDispatch(() -> {
                        componentById2.removeAllComponents();
                        for (Product product : o) {
//                            获取table中显示的每个商品的模板
                            DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_product_list_item_template, null, false);
                            //                    将每个商品的信息设置到模板组件
                            Image image = parse.findComponentById(ResourceTable.Id_product_image);
                            Text title = parse.findComponentById(ResourceTable.Id_product_name_title);
                            Text price = parse.findComponentById(ResourceTable.Id_product_price_text);
                            Text address = parse.findComponentById(ResourceTable.Id_product_address_text);
                            System.out.println(product);
                            System.out.println(product.getPhotos());
//                    image组件加载图片
                            List<photos> photos1 = product.getPhotos();
                            ArrayList<String> strings = new ArrayList<String>();
                            for (photos photos01 : photos1) {
                                strings.add(String.valueOf(photos01.getId()));
                                String imgUrl = "http://162.14.74.164:8888/photo/" + photos01.getPhotoid();
                                LoadImageUtil.loadImg(this, imgUrl, image);
                            }
                            title.setText(product.getTitle());
                            price.setText(product.getPrice());
                            address.setText(product.getAddress());
                            componentById2.addComponent(parse);
//                    添加点击事件，调整并将ID传递给详情页面
                            parse.setClickedListener(new Component.ClickedListener() {
                                @Override
                                public void onClick(Component component) {
                                    Intent intent = new Intent();
                                    intent.setParam("photoid", String.valueOf(product.getId()));
                                    intent.setParam("photo", String.valueOf(product.getPhotos()));
                                    intent.setParam("title", String.valueOf(product.getTitle()));
                                    intent.setParam("date", String.valueOf(product.getDate()));
                                    intent.setParam("price", String.valueOf(product.getPrice()));
                                    intent.setParam("remark", String.valueOf(product.getRemark()));
//                            for (photos photos01:photos1) {
//                                intent.setParam("photos",String.valueOf("http://162.14.74.164:8888/photo/" + photos01.getPhotoid()));
//                            }
                                    present(new DetailAbilitySlice(), intent);
                                }
                            });
                        }
                    });
                }
            });
        });
    }
}
