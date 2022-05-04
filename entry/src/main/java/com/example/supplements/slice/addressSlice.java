package com.example.supplements.slice;

import com.example.supplements.MainAbility;
import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import com.example.supplements.Utils.LoadImageUtil;
import com.example.supplements.beans.LocationBean;
import com.example.supplements.beans.Product;
import com.example.supplements.beans.ResultVo;
import com.example.supplements.beans.photos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.bundle.IBundleManager;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class addressSlice extends AbilitySlice {

    private static final String TAG = MainAbility.class.getSimpleName();

    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, TAG);

    private static final int EVENT_ID = 0x12;

    private static final String PERM_LOCATION = "ohos.permission.LOCATION";

    private final LocatorResult locatorResult = new LocatorResult();

    private Context context;

    private Locator locator;

    private GeoConvert geoConvert;

    private List<GeoAddress> gaList;

    private LocationBean locationDetails;

    private Text geoAddressInfoText;
    private String addressString;


    private void initComponents() {
        Component startLocatingButton = findComponentById(ResourceTable.Id_address_text);
        startLocatingButton.setClickedListener(component -> registerLocationEvent());
        geoAddressInfoText = (Text) findComponentById(ResourceTable.Id_text_address_text);
    }

    private void notifyLocationChange(LocationBean locationDetails) {
        update(locationDetails);
    }

    private void update(LocationBean locationDetails) {
        geoAddressInfoText.setText(locationDetails.getAdministrative() + locationDetails.getLocality() + locationDetails.getRoadName() + locationDetails.getSubAdministrative());
        addressString = locationDetails.getAdministrative() + locationDetails.getLocality() + locationDetails.getRoadName() + locationDetails.getSubAdministrative();
        locator.stopLocating(locatorResult);
        //        geoAddressInfoText.append(
//                "SubAdministrative : " + locationDetails.getSubAdministrative() + System.lineSeparator());
//        geoAddressInfoText.append("RoadName : " + locationDetails.getRoadName() + System.lineSeparator());
//        geoAddressInfoText.append("Locality : " + locationDetails.getLocality() + System.lineSeparator());
//        geoAddressInfoText.append("Administrative : " + locationDetails.getAdministrative() + System.lineSeparator());
//        geoAddressInfoText.append("CountryName : " + locationDetails.getCountryName());
    }

    private final EventHandler handler = new EventHandler(EventRunner.current()) {
        @Override
        protected void processEvent(InnerEvent event) {
            if (event.eventId == EVENT_ID) {
                notifyLocationChange(locationDetails);
            }
        }
    };

    private void register(Context ability) {
        context = ability;
        requestPermission();
    }

    private void registerLocationEvent() {
        if (hasPermissionGranted()) {
            int timeInterval = 0;
            int distanceInterval = 0;
            locator = new Locator(context);
            RequestParam requestParam = new RequestParam(RequestParam.PRIORITY_ACCURACY, timeInterval, distanceInterval);
            locator.startLocating(requestParam, locatorResult);
        }
    }


    private boolean hasPermissionGranted() {
        return context.verifySelfPermission(PERM_LOCATION) == IBundleManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (context.verifySelfPermission(PERM_LOCATION) != IBundleManager.PERMISSION_GRANTED) {
            context.requestPermissionsFromUser(new String[]{PERM_LOCATION}, 0);
        }
    }

    private class LocatorResult implements LocatorCallback {
        @Override
        public void onLocationReport(Location location) {
            HiLog.info(LABEL_LOG, "%{public}s",
                    "onLocationReport : " + location.getLatitude() + "-" + location.getAltitude());
            setLocation(location);
        }

        @Override
        public void onStatusChanged(int statusCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "MyLocatorCallback onStatusChanged : " + statusCode);
        }

        @Override
        public void onErrorReport(int errorCode) {
            HiLog.info(LABEL_LOG, "%{public}s", "MyLocatorCallback onErrorReport : " + errorCode);
        }
    }

    private void setLocation(Location location) {
        if (location != null) {
            Date date = new Date(location.getTimeStamp());
            locationDetails = new LocationBean();
            locationDetails.setTime(date.toString());
            locationDetails.setLatitude(location.getLatitude());
            locationDetails.setLongitude(location.getLongitude());
            locationDetails.setPrecision(location.getAccuracy());
            locationDetails.setSpeed(location.getSpeed());
            locationDetails.setDirection(location.getDirection());
            fillGeoInfo(locationDetails, location.getLatitude(), location.getLongitude());
            handler.sendEvent(EVENT_ID);
            gaList.clear();
        } else {
            HiLog.info(LABEL_LOG, "%{public}s", "EventNotifier or Location response is null");
            new ToastDialog(context).setText("EventNotifier or Location response is null").show();
        }
    }

    private void fillGeoInfo(LocationBean locationDetails, double geoLatitude, double geoLongitude) {
        if (geoConvert == null) {
            geoConvert = new GeoConvert();
        }
        if (geoConvert.isGeoAvailable()) {
            try {
                gaList = geoConvert.getAddressFromLocation(geoLatitude, geoLongitude, 1);
                if (!gaList.isEmpty()) {
                    GeoAddress geoAddress = gaList.get(0);
                    setGeo(locationDetails, geoAddress);
                }
            } catch (IllegalArgumentException | IOException e) {
                HiLog.error(LABEL_LOG, "%{public}s", "fillGeoInfo exception");
            }
        }
    }

    private void setGeo(LocationBean locationDetails, GeoAddress geoAddress) {
        locationDetails.setRoadName(checkIfNullOrEmpty(geoAddress.getRoadName()));
        locationDetails.setLocality(checkIfNullOrEmpty(geoAddress.getLocality()));
        locationDetails.setSubAdministrative(checkIfNullOrEmpty(geoAddress.getSubAdministrativeArea()));
        locationDetails.setAdministrative(checkIfNullOrEmpty(geoAddress.getAdministrativeArea()));
        locationDetails.setCountryName(checkIfNullOrEmpty(geoAddress.getCountryName()));
    }

    private String checkIfNullOrEmpty(String value) {
        if (value == null || value.isEmpty()) {
            return "NA";
        }
        return value;
    }


    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_addressone);
        initComponents();
        register(this);

        Button componentById = findComponentById(ResourceTable.Id_address_Button);
        Gson gson = new Gson();
        TableLayout productListTable = findComponentById(ResourceTable.Id_address_list_table);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                    String s = HttpRequestUtil.sendGetRequest(addressSlice.this, "http://162.14.74.164:8888/nearby/" + addressString);
                    ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                    System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
                    if (resultVo.getFlag() == true) {
                        String s1 = gson.toJson(resultVo.getData());
                        System.out.println(s1);
                        List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                        }.getType());
//                将获取到的商品列表显示到table布局中
                        getUITaskDispatcher().syncDispatch(() -> {
                            for (Product product : o) {
//                    获取table中显示的每个商品的模板
                                DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(addressSlice.this).parse(ResourceTable.Layout_product_list_item_template, null, false);
//                    将每个商品的信息设置到模板组件
                                Image image = parse.findComponentById(ResourceTable.Id_product_image);
                                Text title = parse.findComponentById(ResourceTable.Id_product_name_title);
                                Text price = parse.findComponentById(ResourceTable.Id_product_price_text);
                                Text address = parse.findComponentById(ResourceTable.Id_product_address_text);
                                title.setText(product.getTitle());
                                price.setText(product.getPrice());
                                address.setText(product.getAddress());
                                List<photos> photos1 = product.getPhotos();
                                String photos = null;
                                ArrayList<String> strings = new ArrayList<String>();
                                for (photos photos01 : photos1) {
                                    strings.add(String.valueOf(photos01.getId()));
                                    photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                                }
                                LoadImageUtil.loadImg(addressSlice.this, photos, image);
//                    将完成数据渲染的模板添加到table中
                                productListTable.addComponent(parse);
//                    添加商品项的点击事件，跳转并将商品ID传递给详情页面
                                parse.setClickedListener(new Component.ClickedListener() {
                                    @Override
                                    public void onClick(Component component) {
                                        Intent intent = new Intent();
                                        intent.setParam("productId", String.valueOf(product.getId()));
                                        intent.setParam("photo", String.valueOf(product.getPhotos()));
                                        intent.setParam("title", String.valueOf(product.getTitle()));
                                        intent.setParam("date", String.valueOf(product.getDate()));
                                        intent.setParam("price", String.valueOf(product.getPrice()));
                                        intent.setParam("remark", String.valueOf(product.getRemark()));
                                        intent.setParam("photoid", String.valueOf(strings));
//                            for (photos photos01:photos1) {
//                                intent.setParam("photos",String.valueOf("http://162.14.74.164:8888/photo/" + photos01.getPhotoid()));
//                            }
                                        present(new DetailAbilitySlice(), intent);
                                    }
                                });
                            }
                        });
                    } else {
                        showDialog("获取信息错误");
                    }
                });
            }
        });
    }

    //    土司弹框
    private void showDialog(String msg) {
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_layout_toast, null, false);
        Text msgText = (Text) layout.findComponentById(ResourceTable.Id_msg);
        msgText.setText(msg);
        new ToastDialog(this)
                .setDuration(5000)
                .setContentCustomComponent(layout)
                .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                .show();
    }
}
