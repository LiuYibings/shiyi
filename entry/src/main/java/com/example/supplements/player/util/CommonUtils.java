/*
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

package com.example.supplements.player.util;

import com.example.supplements.Utils.LogUtil;
import com.example.supplements.player.constant.Constants;
import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;

import java.io.IOException;

/**
 * Common util
 *
 * @since 2021-04-04
 */
public class    CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    private CommonUtils() {
    }

    /**
     * 通过资源ID获取颜色
     *
     * @param context comtext
     * @param resourceId id
     * @return int
     */
    public static int getColor(Context context, int resourceId) {
        try {
            return context.getResourceManager().getElement(resourceId).getColor();
        } catch (IOException | NotExistException | WrongTypeException e) {
            LogUtil.error(TAG, e.getMessage());
        }
        return Constants.NUMBER_NEGATIVE_1;
    }
}
