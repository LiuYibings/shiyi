package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Image;

public class one1 extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_one1);
        Image componentById = findComponentById(ResourceTable.Id_img_thing2);
        Image componentById1 = findComponentById(ResourceTable.Id_one_img1);
        componentById.setCornerRadius(500f);
        componentById1.setCornerRadius(500f);
        Image componentById2 = findComponentById(ResourceTable.Id_img_thing3);
        Image componentById3 = findComponentById(ResourceTable.Id_one_img2);
        componentById2.setCornerRadius(500f);
        componentById3.setCornerRadius(500f);
    }
}
