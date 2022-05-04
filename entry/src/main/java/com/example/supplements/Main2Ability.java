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


import com.example.supplements.slice.Main2AbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

//本项目使用Apache License 2.0开源协议，Gitee链接 https://gitee.com/marshou/video-slider/blob/master
//欢迎学习使用，但再传播时需保留该说明，并在修改文件中说明
/**
 * the main page
 *
 * @since 2021-04-04
 *
 */
public class Main2Ability extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(Main2AbilitySlice.class.getName());
    }
}
