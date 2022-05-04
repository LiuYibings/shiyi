package com.example.supplements;

import com.example.supplements.slice.MainAbilitySlice;
import com.example.supplements.slice.frontOneSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class frontOne extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
    }
}
