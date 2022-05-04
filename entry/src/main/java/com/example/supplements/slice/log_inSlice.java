package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import com.example.supplements.beans.ResultVo;
import com.example.supplements.beans.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.List;

public class log_inSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        Gson gson = new Gson();
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_log_in);
        Text mPhone = findComponentById(ResourceTable.Id_phone);
        Text mPwd = findComponentById(ResourceTable.Id_log_in_pwd);
        Button componentById = findComponentById(ResourceTable.Id_btn_register);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.supplements")
                        .withAbilityName("com.example.supplements.register")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
        findComponentById(ResourceTable.Id_btn_login).setClickedListener(component -> {
            TaskDispatcher globalTaskDispatcher = this.getGlobalTaskDispatcher(TaskPriority.DEFAULT);
            globalTaskDispatcher.asyncDispatch(() -> {
                String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/user/" + mPhone.getText());
                ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                if (resultVo.getFlag() == true) {
                    String s1 = gson.toJson(resultVo.getData());
                    List<user> o = gson.fromJson(s1, new TypeToken<List<user>>() {
                    }.getType());
                    getUITaskDispatcher().syncDispatch(() -> {
                        for (user use : o) {
                            if (mPwd.getText().equals(String.valueOf(use.getPassword()))) {
                                intent.setParam("id", String.valueOf(use.getUserid()));
                                intent.setParam("name", String.valueOf(use.getName()));
                                intent.setParam("telephone",String.valueOf(use.getTelemphone()));
                                intent.setParam("qq",String.valueOf(use.getQq()));
                                intent.setParam("emil",String.valueOf(use.getEmil()));
                                intent.setParam("address",String.valueOf(use.getAddress()));
                                intent.setParam("weixing",String.valueOf(use.getWeixing()));
                                Operation operation = new Intent.OperationBuilder()
                                        .withDeviceId("")
                                        .withBundleName("com.example.supplements")
                                        .withAbilityName("com.example.supplements.frontOne")
                                        .build();
                                intent.setOperation(operation);
                                startAbility(intent);
                            } else {
                                showDialog("请输入正确的名称和密码");
                            }
                        }
                    });
                }
            });
        });

    }

    @Override
    public void onActive() {
        super.onActive();
    }

    private void showDialog(String msg) {
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_layout_toast, null, false);
        Text msgText = (Text) layout.findComponentById(ResourceTable.Id_msg);
        msgText.setText(msg);
        new ToastDialog(this)
                .setContentCustomComponent(layout)
                .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                .show();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
