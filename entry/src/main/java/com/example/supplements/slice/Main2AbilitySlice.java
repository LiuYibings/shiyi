package com.example.supplements.slice;

import com.example.supplements.ResourceTable;

import com.example.supplements.Utils.LogUtil;
import com.example.supplements.player.HmPlayer;
import com.example.supplements.player.api.ImplPlayer;
import com.example.supplements.player.constant.Constants;
import com.example.supplements.player.view.PlayerLoading;
import com.example.supplements.player.view.PlayerView;
import com.example.supplements.player.view.SimplePlayerController;
import com.example.supplements.provider.VideoPageProvider;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.PageSlider;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;

import java.util.ArrayList;

public class Main2AbilitySlice extends AbilitySlice {
    private static final String TAG = SimplePlayerAbilitySlice.class.getSimpleName();
    private ImplPlayer[] players;
    private PlayerView playerView;
    private PlayerLoading loadingView;
    private SimplePlayerController controllerView;
    private String[] urls = new String[]
            {"entry/resources/base/media/1.mp4",
                    "entry/resources/base/media/2.mp4",
                    "entry/resources/base/media/3.mp4",
                    "entry/resources/base/media/4.mp4"};
    int index = 0;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main1);
        players = new ImplPlayer[4];
        for(int i = 0; i  < 4; i++){
            players[i] = new HmPlayer.Builder(getContext()).setFilePath(urls[i]).create();
            players[i].getLifecycle().onStart();
        }
        initPageSlider();
    }

    private void initPageSlider() {
        PageSlider pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page_slider);
        VideoPageProvider videoPageProvider = new VideoPageProvider(getData(), this,players);
        pageSlider.setProvider(videoPageProvider);
        pageSlider.setReboundEffect(true);
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            @Override
            public void onPageSliding(int itemPos, float itemPosOffset, int itemPosPixles) {
            }
            @Override
            public void onPageSlideStateChanged(int state) {

            }
            @Override
            public void onPageChosen(int itemPos) {
                index = itemPos;
                if(itemPos>0){
                    players[itemPos-1].getLifecycle().onBackground();
                }
                if(itemPos<players.length-1){
                    players[itemPos+1].getLifecycle().onBackground();
                }
                players[itemPos].getLifecycle().onForeground();
//                getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> players[itemPos].play(), Constants.NUMBER_1000);
            }
        });
    }
    private ArrayList<VideoPageProvider.DataItem> getData() {
        ArrayList<VideoPageProvider.DataItem> dataItems = new ArrayList<>();
        dataItems.add(new VideoPageProvider.DataItem("Page A"));
        dataItems.add(new VideoPageProvider.DataItem("Page B"));
        dataItems.add(new VideoPageProvider.DataItem("Page C"));
        dataItems.add(new VideoPageProvider.DataItem("Page A"));
        return dataItems;
    }

    Context getMainContext(){
        return this;
    }

    @Override
    public void onActive() {
        super.onActive();
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).delayDispatch(() -> players[index].play(), Constants.NUMBER_1000);
    }

    @Override
    public void onForeground(Intent intent) {
        players[index].getLifecycle().onForeground();
        super.onForeground(intent);
    }

    @Override
    protected void onBackground() {
        LogUtil.info(TAG, "onBackground is called");
        players[index].getLifecycle().onBackground();
        super.onBackground();
    }

    @Override
    protected void onStop() {
        LogUtil.info(TAG, "onStop is called");
        loadingView.unbind();
        controllerView.unbind();
        playerView.unbind();
        players[index].getLifecycle().onStop();
        super.onStop();
    }
}
