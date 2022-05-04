package com.example.supplements;

import com.example.supplements.slice.registerSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class register extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(registerSlice.class.getName());
    }
}
