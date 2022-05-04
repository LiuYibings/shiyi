package com.example.supplements.provider;

import com.example.supplements.ResourceTable;
import com.example.supplements.player.api.ImplPlayer;
import com.example.supplements.player.view.PlayerLoading;
import com.example.supplements.player.view.PlayerView;
import com.example.supplements.player.view.SimplePlayerController;
import com.example.supplements.slice.SimplePlayerAbilitySlice;
import ohos.agp.components.*;
import ohos.app.Context;

import java.util.List;

public class VideoPageProvider extends PageSliderProvider {
    private static final String TAG = SimplePlayerAbilitySlice.class.getSimpleName();
    private ImplPlayer[] player;
    private PlayerView playerView;
    private PlayerLoading loadingView;
    private SimplePlayerController controllerView;
    private String url = "entry/resources/base/media/gubeishuizhen.mp4";

    //数据实体类
    public static class DataItem{
        String mText;
        public DataItem(String txt) {
            mText = txt;
        }
    }
    // 数据源，每个页面对应list中的一项
    private List<DataItem> list;
    private Context mContext;

    public VideoPageProvider(List<DataItem> list, Context context, ImplPlayer[] player) {
        this.list = list;
        this.mContext = context;
        this.player = player;
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object createPageInContainer(ComponentContainer componentContainer, int i) {
//        final DataItem data = list.get(i);

        Component loadingContainer =
                LayoutScatter.getInstance(mContext).parse(ResourceTable.Layout_simple_video_play_layout, null, false);
        if (loadingContainer.findComponentById(ResourceTable.Id_player_view) instanceof PlayerView) {
            playerView = (PlayerView) loadingContainer.findComponentById(ResourceTable.Id_player_view);
        }
        if (loadingContainer.findComponentById(ResourceTable.Id_loading_view) instanceof PlayerLoading) {
            loadingView = (PlayerLoading) loadingContainer.findComponentById(ResourceTable.Id_loading_view);
        }
        if (loadingContainer.findComponentById(ResourceTable.Id_controller_view) instanceof SimplePlayerController) {
            controllerView = (SimplePlayerController) loadingContainer.findComponentById(ResourceTable.Id_controller_view);
        }
        playerView.bind(player[i]);
        loadingView.bind(player[i]);
        controllerView.bind(player[i]);

        componentContainer.addComponent(loadingContainer);
        return loadingContainer;
    }
    @Override
    public void destroyPageFromContainer(ComponentContainer componentContainer, int i, Object o) {
        componentContainer.removeComponent((Component) o);
    }
    @Override
    public boolean isPageMatchToObject(Component component, Object o) {
        //可添加具体处理逻辑
        return true;
    }
}