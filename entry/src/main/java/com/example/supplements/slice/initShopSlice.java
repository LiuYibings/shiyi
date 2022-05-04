package com.example.supplements.slice;

import com.example.supplements.MainAbility;
import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.HttpRequestUtil;
import com.example.supplements.Utils.LogUtils;
import com.example.supplements.beans.LocationBean;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.bundle.IBundleManager;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.*;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.photokit.metadata.AVStorage;
import ohos.utils.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class initShopSlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_init_shop);
        initComponents();
        register(this);
        Locator locator = new Locator(this);
        RequestParam requestParam = new RequestParam(RequestParam.SCENE_NAVIGATION);
        //获取存储权限
        requestPermissionsFromUser(new String[]{"ohos.permission.READ_USER_STORAGE"}, imgRequestCode);
        Button btnChooseImg = (Button) findComponentById(ResourceTable.Id_btnChooseImg);
        btnChooseImg.setClickedListener(c -> {
            //选择图片
            selectPic();
        });
        //                    获取table中显示的每个商品的模
//        获取发布按钮
        Button componentById1 = findComponentById(ResourceTable.Id_add);
        componentById1.setClickedListener((Component) -> {
            //标题
            String id = (String) intent.getParams().getParam("id");
            Text title = findComponentById(ResourceTable.Id_thing_title);
            //内容
            Text remark = findComponentById(ResourceTable.Id_thing_remark);
            //地址
            Text address = findComponentById(ResourceTable.Id_thing_address);
            //价格
            Text price = findComponentById(ResourceTable.Id_thing_price);
            //uerid发布人的id
            LogUtils.info("thingtitle", title.getText().toString());
            System.out.println(title.getText());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpRequestUtil.sendGetRequest(new initShopSlice(), "http://162.14.74.164:8888/release/" + title.getText() + "/" + remark.getText() + "/" + address.getText() + "/" + price.getText() + "/" + id);
                }
            }).start();
            //            getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
//                //后端接口都将不同图片格式转换为jpeg
//                try {
//                    for (Uri two : one) {
//                        socket = new Socket("162.14.74.164", 9029);
//                        outputStream = socket.getOutputStream();
//                        byte[] bytes = new byte[1024 * 8];
//                        int len;
//                        InputStream fd = null;
//                        fd = helper.obtainInputStream(two);
//                        while ((len = fd.read(bytes)) != -1) {
//                            outputStream.write(bytes, 0, len);
//                            outputStream.flush();
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (DataAbilityRemoteException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        socket.close();
//                        one.clear();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        });
    }

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0001, "选择图片测试");
    private final int imgRequestCode = 1123;
    Uri uri;
    InputStream fd = null;
    List<Uri> one = new ArrayList<>();
    Socket socket = null;
    OutputStream outputStream;
    DataAbilityHelper helper;

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

    private Text locationInfoText;

    private void initComponents() {
        Component startLocatingButton = findComponentById(ResourceTable.Id_start_locating);
        startLocatingButton.setClickedListener(component -> registerLocationEvent());
        geoAddressInfoText = (Text) findComponentById(ResourceTable.Id_thing_address);
    }

    private void notifyLocationChange(LocationBean locationDetails) {
        update(locationDetails);
    }

    private void update(LocationBean locationDetails) {
        geoAddressInfoText.setText(locationDetails.getAdministrative() + locationDetails.getLocality() + locationDetails.getRoadName() + locationDetails.getSubAdministrative());
        locator.stopLocating(locatorResult);
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

    private void unregisterLocationEvent() {
        if (locator != null) {
            locator.stopLocating(locatorResult);
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
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == imgRequestCode) {
            HiLog.info(label, "选择图片getUriString:" + resultData.getUriString());
            //选择的Img对应的Uri
            String chooseImgUri = resultData.getUriString();
            HiLog.info(label, "选择图片getScheme:" + chooseImgUri.substring(chooseImgUri.lastIndexOf('/')));
            //定义数据能力帮助对象
            helper = DataAbilityHelper.creator(getContext());
            //定义图片来源对象
            ImageSource imageSource = null;
            //获取选择的Img对应的Id
            String chooseImgId = null;
            //如果是选择文件则getUriString结果为content://com.android.providers.media.documents/document/image%3A30，其中%3A是":"的URL编码结果，后面的数字就是image对应的Id
            //如果选择的是图库则getUriString结果为content://media/external/images/media/30，最后就是image对应的Id
            //这里需要判断是选择了文件还是图库
            if (chooseImgUri.lastIndexOf("%3A") != -1) {
                chooseImgId = chooseImgUri.substring(chooseImgUri.lastIndexOf("%3A") + 3);
            } else {
                chooseImgId = chooseImgUri.substring(chooseImgUri.lastIndexOf('/') + 1);
            }
            //获取图片对应的uri，由于获取到的前缀是content，我们替换成对应的dataability前缀
            uri = Uri.appendEncodedPathToUri(AVStorage.Images.Media.EXTERNAL_DATA_ABILITY_URI, chooseImgId);
            HiLog.info(label, "选择图片dataability路径:" + uri.toString());
            try {
                one.add(uri);
                //读取图片
                fd = helper.obtainInputStream(uri);
                imageSource = ImageSource.create(fd, null);
                //创建位图
                DirectionalLayout componentById1 = findComponentById(ResourceTable.Id_status_photos);
                DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_thing_photo, null, false);
                Image showChooseImg = parse.findComponentById(ResourceTable.Id_showChooseImg);
                showChooseImg.setCornerRadius(50f);
                PixelMap pixelMap = imageSource.createPixelmap(null);
                //设置图片控件对应的位图
                showChooseImg.setPixelMap(pixelMap);
                componentById1.addComponent(parse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectPic() {
        Intent intent = new Intent();
        Operation opt = new Intent.OperationBuilder().withAction("android.intent.action.GET_CONTENT").build();
        intent.setOperation(opt);
        intent.addFlags(Intent.FLAG_NOT_OHOS_COMPONENT);
        intent.setType("image/*");
        startAbilityForResult(intent, imgRequestCode);
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
