package com.example.supplements;

import com.example.supplements.slice.initShopSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class initShop extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(initShopSlice.class.getName());
    }
}
