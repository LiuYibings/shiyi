package com.example.supplements;/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License,Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.example.supplements.Utils.LogUtil;
import com.example.supplements.Utils.PermissionsUtils;
import com.example.supplements.slice.Main3AbilitySlice;
import com.example.supplements.slice.WatchAbilitySlice;
import com.example.supplements.Utils.LogUtils;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.IntentParams;
import ohos.data.distributed.common.KvManagerConfig;
import ohos.data.distributed.common.KvManagerFactory;
import ohos.rpc.RemoteException;
import ohos.security.SystemPermission;

/**
 * MainAbility
 *
 * @since 2021-03-12
 */
public class Main3Ability extends Ability implements IAbilityContinuation {
    private static final String TAG = Main3Ability.class.getSimpleName();

    private static final String DEVICE_TYPE_WATCH = "109";
    private final String[] requestPermissions = {SystemPermission.DISTRIBUTED_DATASYNC, SystemPermission.LOCATION};
    @Override
    public void onStart(Intent intent) {
        try {
            LogUtil.info("appid",getBundleManager().getBundleInfo(getBundleName(), 0).getAppId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        super.onStart(intent);
        String deviceType =
                KvManagerFactory.getInstance().createKvManager(new KvManagerConfig(this)).getLocalDeviceInfo().getType();
        if (deviceType.equals(DEVICE_TYPE_WATCH)) {
            super.setMainRoute(WatchAbilitySlice.class.getName());
        } else {
            super.setMainRoute(Main3AbilitySlice.class.getName());
        }
        PermissionsUtils.getInstance().requestPermissions(this, requestPermissions);
        String appId = null;
        try {
            appId = getBundleManager().getBundleInfo(getBundleName(), 0).getAppId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LogUtils.info(TAG, "translate:" +appId);
    }

    @Override
    public void onRequestPermissionsFromUserResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionsUtils.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onStartContinuation() {
        LogUtils.info(TAG, "onStartContinuation");
        return true;
    }

    @Override
    public boolean onSaveData(IntentParams intentParams) {
        LogUtils.info(TAG, "onSaveData");
        return true;
    }

    @Override
    public boolean onRestoreData(IntentParams intentParams) {
        LogUtils.info(TAG, "onRestoreData");
        return true;
    }

    @Override
    public void onCompleteContinuation(int position) {
        LogUtils.info(TAG, "onCompleteContinuation");
    }

    @Override
    public void onRemoteTerminated() {
        LogUtils.info(TAG, "onRemoteTerminated");
    }
}
