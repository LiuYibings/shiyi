package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Text;
import ohos.app.dispatcher.task.TaskPriority;

public class registerSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_register);
        Text componentById = findComponentById(ResourceTable.Id_register_name);
        Text componentById1 = findComponentById(ResourceTable.Id_register_password);
        Button componentById2 = findComponentById(ResourceTable.Id_but_register);
        componentById2.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                    HttpRequestUtil.sendGetRequest(new registerSlice(), "http://162.14.74.164:8888/insuser/" + componentById.getText() + "/" + componentById1.getText());
                });
                present(new log_inSlice(), intent);
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
