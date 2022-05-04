package com.example.supplements.widget.widget;

import com.example.supplements.ResourceTable;
import com.example.supplements.widget.controller.FormController;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.ProviderFormInfo;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ComponentProvider;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.HashMap;
import java.util.Map;

public class WidgetImpl extends FormController {
    public static final int DIMENSION_2X4 = 3;
    public static final int DIMENSION_4X4 = 4;
    private static final HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0x0, WidgetImpl.class.getName());
    private static final int DEFAULT_DIMENSION_2X2 = 2;
    private static final Map<Integer, Integer> RESOURCE_ID_MAP = new HashMap<>();

    static {
        RESOURCE_ID_MAP.put(DEFAULT_DIMENSION_2X2, ResourceTable.Layout_form_image_with_information_widget_2_2);
        RESOURCE_ID_MAP.put(DIMENSION_2X4, ResourceTable.Layout_form_image_with_information_widget_2_4);
        RESOURCE_ID_MAP.put(DIMENSION_4X4, ResourceTable.Layout_form_image_with_information_widget_4_4);
    }

    public WidgetImpl(Context context, String formName, Integer dimension) {
        super(context, formName, dimension);
    }

    @Override
    public ProviderFormInfo bindFormData(long formId) {
        HiLog.info(TAG, "bind form data when create form");
        ProviderFormInfo providerFormInfo = new ProviderFormInfo(RESOURCE_ID_MAP.get(dimension), context);
        ComponentProvider componentProvider = new ComponentProvider();
        if (dimension==DEFAULT_DIMENSION_2X2){
            componentProvider.setText(ResourceTable.Id_title,"sldjkf");
        }
        providerFormInfo.mergeActions(componentProvider);
        return providerFormInfo;
    }

    @Override
    public void updateFormData(long formId, Object... vars) {
        HiLog.info(TAG, "update form data timing, default 30 minutes");
    }

    @Override
    public void onTriggerFormEvent(long formId, String message) {
        HiLog.info(TAG, "handle card click event.");
    }

    @Override
    public Class<? extends AbilitySlice> getRoutePageSlice(Intent intent) {
        HiLog.info(TAG, "get the default page to route when you click card.");
        return null;
    }


}