package com.example.supplements;

import com.example.supplements.slice.Main4AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Main4Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(Main4AbilitySlice.class.getName());
    }
}
