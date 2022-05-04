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

package com.example.supplements.player.api;

import com.example.supplements.player.HmPlayer;
import com.example.supplements.player.constant.PlayerStatu;
import ohos.agp.graphics.Surface;


/**
 * IPlayer interface
 *
 * @since 2021-04-04
 */
public interface ImplPlayer {
    /**
     * addSurface
     *
     * @param surface surface
     */
    void addSurface(Surface surface);

    /**
     * addPlayerStatuCallback
     *
     * @param callback callback
     */
    void addPlayerStatuCallback(StatuChangeListener callback);

    /**
     * removePlayerStatuCallback
     *
     * @param callback callback
     */
    void removePlayerStatuCallback(StatuChangeListener callback);

    /**
     * addPlayerViewCallback
     *
     * @param callback callback
     */
    void addPlayerViewCallback(ScreenChangeListener callback);

    /**
     * removePlayerViewCallback
     *
     * @param callback callback
     */
    void removePlayerViewCallback(ScreenChangeListener callback);

    /**
     * play
     */
    void play();

    /**
     * replay
     */
    void replay();

    /**
     * reload
     *
     * @param filepath filepath
     * @param startMillisecond startMillisecond
     */
    void reload(String filepath, int startMillisecond);

    /**
     * resume
     */
    void resume();

    /**
     * pause
     */
    void pause();

    /**
     * getCurrentPosition
     *
     * @return current position
     */
    int getCurrentPosition();

    /**
     * getDuration
     *
     * @return duration
     */
    int getDuration();

    /**
     * getVolume
     *
     * @return float
     */
    float getVolume();

    /**
     * set play volume
     *
     * @param volume 0~1
     */
    void setVolume(float volume);

    /**
     * set play speed
     *
     * @param speed 0~12
     */
    void setPlaySpeed(float speed);

    /**
     * getVideoScale
     *
     * @return double
     */
    double getVideoScale();

    /**
     * rewindTo
     *
     * @param startMicrosecond startMicrosecond(ms)
     */
    void rewindTo(int startMicrosecond);

    /**
     * isPlaying
     *
     * @return isPlaying
     */
    boolean isPlaying();

    /**
     * stop
     */
    void stop();

    /**
     * release
     */
    void release();

    /**
     * getLifecycle
     *
     * @return ImplLifecycle
     */
    ImplLifecycle getLifecycle();

    /**
     * getBuilder
     *
     * @return Builder
     */
    HmPlayer.Builder getBuilder();

    /**
     * getPlayerStatu
     *
     * @return PlayerStatu
     */
    PlayerStatu getPlayerStatu();

    /**
     * resizeScreen
     *
     * @param width width
     * @param height height
     */
    void resizeScreen(int width, int height);

    /**
     * openGesture
     *
     * @param isOpen isOpen
     */
    void openGesture(boolean isOpen);

    /**
     * openGesture
     *
     * @return isGestureOpen
     */
    boolean isGestureOpen();
}
