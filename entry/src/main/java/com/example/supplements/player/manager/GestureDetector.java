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

package com.example.supplements.player.manager;

import com.example.supplements.player.constant.Constants;
import com.example.supplements.player.constant.GestureConst;
import ohos.agp.components.VelocityDetector;
import ohos.multimodalinput.event.TouchEvent;

/**
 * GestureDetector
 *
 * @since 2021-04-09
 */
public class GestureDetector {
    /**
     * MOVING_HORIZONTAL
     */
    public static final int MOVING_HORIZONTAL = 0;
    /**
     * MOVING_VERTICAL
     */
    public static final int MOVING_VERTICAL = 1;
    private int currentMoveType;

    /**
     * OnGestureListener
     *
     * @since 2021-04-09
     */
    public interface OnGestureListener {
        /**
         * 开始滚动时回调
         *
         * @param windowX 手指移动时x位置
         * @param windowY 手指移动时y位置
         * @return is deliver
         */
        boolean onTouchBegin(float windowX, float windowY);

        /**
         * 发生滚动时回调
         *
         * @param direction 移动方向 0：横向 1：纵向
         * @param windowX 手指移动时x位置
         * @param windowY 手指移动时y位置
         * @param distance 移动距离
         * @return is deliver
         */
        boolean onTouchMoving(int direction, float windowX, float windowY, float distance);

        /**
         * 结束滚动时回调
         *
         * @param direction 移动方向 0：横向 1：纵向
         * @return is deliver
         */
        boolean onTouchCancel(int direction);
    }

    private final OnGestureListener mListener;

    private float mLastFocusX;
    private float mLastFocusY;
    private float mDownFocusX;
    private float mDownFocusY;

    private VelocityDetector mVelocityTracker;

    /**
     * GestureDetector
     *
     * @param listener listener
     */
    public GestureDetector(OnGestureListener listener) {
        mListener = listener;
    }

    /**
     * onTouchEvent
     *
     * @param ev ev
     * @return boolean
     */
    public boolean onTouchEvent(TouchEvent ev) {
        final int action = ev.getAction();
        addVelocityDetector(ev);
        float[] points = getFocusPoint(ev);
        final float focusX = points[0];
        final float focusY = points[1];
        boolean isHandled = true;
        switch (action) {
            case TouchEvent.OTHER_POINT_DOWN:
            case TouchEvent.PRIMARY_POINT_DOWN:
                currentMoveType = Constants.NUMBER_NEGATIVE_1;
                mDownFocusX = 0;
                mDownFocusY = 0;
                mListener.onTouchBegin(focusX, focusY);
                break;
            case TouchEvent.POINT_MOVE:
                if (mDownFocusX == 0 || mDownFocusY == 0) {
                    mDownFocusX = focusX;
                    mDownFocusY = focusY;
                    mLastFocusX = focusX;
                    mLastFocusY = focusY;
                }
                final int deltaX = (int) (focusX - mDownFocusX);
                final int deltaY = (int) (focusY - mDownFocusY);
                int distanceX = Math.abs(deltaX);
                int distanceY = Math.abs(deltaY);
                if (distanceX > GestureConst.getScaledTouchSlop() || distanceY > GestureConst.getScaledTouchSlop()) {
                    if (distanceX >= distanceY) {
                        currentMoveType = MOVING_HORIZONTAL;
                        float scrollX = mLastFocusX - focusX;
                        isHandled = mListener.onTouchMoving(MOVING_HORIZONTAL, focusX, focusY, scrollX);
                    } else {
                        currentMoveType = MOVING_VERTICAL;
                        float scrollY = mLastFocusY - focusY;
                        isHandled = mListener.onTouchMoving(MOVING_VERTICAL, focusX, focusY, scrollY);
                    }
                    mLastFocusX = focusX;
                    mLastFocusY = focusY;
                }
                break;
            case TouchEvent.PRIMARY_POINT_UP:
            case TouchEvent.OTHER_POINT_UP:
            case TouchEvent.CANCEL:
                isHandled = mListener.onTouchCancel(currentMoveType);
                break;
            default:
                break;
        }
        return isHandled;
    }

    private void addVelocityDetector(TouchEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityDetector.obtainInstance();
        }
        mVelocityTracker.addEvent(ev);
    }

    private float[] getFocusPoint(TouchEvent ev) {
        final boolean isPointerUp = ev.getAction() == TouchEvent.OTHER_POINT_UP;
        final int skipIndex = isPointerUp ? ev.getIndex() : Constants.NUMBER_NEGATIVE_1;
        float sumX = 0;
        float sumY = 0;
        final int count = ev.getPointerCount();
        for (int i = 0; i < count; i++) {
            if (skipIndex == i) {
                continue;
            }
            sumX += ev.getPointerPosition(i).getX();
            sumY += ev.getPointerPosition(i).getY();
        }
        final int div = isPointerUp ? count - 1 : count;
        float focusX = sumX / div;
        float focusY = sumY / div;
        return new float[]{focusX, focusY};
    }
}
