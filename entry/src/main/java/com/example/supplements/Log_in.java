package com.example.supplements;

import com.example.supplements.slice.log_inSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class Log_in extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(log_inSlice.class.getName());
    }
}