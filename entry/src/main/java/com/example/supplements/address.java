package com.example.supplements;

import com.example.supplements.slice.addressSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class address extends Ability {

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(addressSlice.class.getName());
    }
}
