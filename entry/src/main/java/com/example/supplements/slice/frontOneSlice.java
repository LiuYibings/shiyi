package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.Text;

public class frontOneSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_front_one);
        Image componentById = findComponentById(ResourceTable.Id_front_one_btu);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                Operation build = new Intent.OperationBuilder()
                        .withDeviceId("")       //要跳转到那个设备上，如果传递一个没有内容的字符串， 表示跳转到本机
                        .withBundleName("com.example.supplements")   //我要跳转到那个页面上，写入的是一个包名,必须和config.json的app的包名一致
                        .withAbilityName("com.example.supplements.Log_in")  //要跳转的页面,必须和config.json的
                        .build();//将上面三个信息进行打包
                //将打包的东西设置到意图中
                intent.setOperation(build);
                //跳转页面开始
                startAbility(intent);
            }
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
