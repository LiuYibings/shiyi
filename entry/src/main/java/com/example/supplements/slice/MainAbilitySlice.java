package com.example.supplements.slice;

import com.example.supplements.MainAbility;
import com.example.supplements.ResourceTable;
import com.example.supplements.Utils.*;
import com.example.supplements.beans.*;
import com.example.supplements.provider.IndexImgPageSliderProvider;
import com.example.supplements.provider.TabPageSliderProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.window.dialog.ToastDialog;
import ohos.app.Context;
import ohos.app.dispatcher.TaskDispatcher;
import ohos.app.dispatcher.task.TaskPriority;
import ohos.bundle.IBundleManager;
import ohos.event.notification.NotificationHelper;
import ohos.event.notification.NotificationRequest;
import ohos.event.notification.NotificationSlot;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.location.*;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.photokit.metadata.AVStorage;
import ohos.rpc.RemoteException;
import ohos.utils.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private Gson gson = new Gson();

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
//        publishNotification();
        //卡片服务
        //1.初始化TabList
        TabList tabList = (TabList) findComponentById(ResourceTable.Id_tab_list);
        String[] tabListTags = {"拾遗", "拾趣", "发布", "消息", "我的"};
        for (int i = 0; i < tabListTags.length; i++) {
            TabList.Tab tab = tabList.new Tab(this);
            tab.setText(tabListTags[i]);
            tabList.addTab(tab);
        }
        //2.初始化PageSlider
        List<Integer> layoutFileIds = new ArrayList<>();
        layoutFileIds.add(ResourceTable.Layout_laility_main_index);
        layoutFileIds.add(ResourceTable.Layout_laility_main_category);
        layoutFileIds.add(ResourceTable.Layout_laility_main_item_status);
        layoutFileIds.add(ResourceTable.Layout_laility_main_information);
        layoutFileIds.add(ResourceTable.Layout_laility_main_user_center);
        PageSlider pageSlider = (PageSlider) findComponentById(ResourceTable.Id_page_slider);
        pageSlider.setProvider(new TabPageSliderProvider(layoutFileIds, this));
        //3.TabList与PageSlider联动
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            public void onSelected(TabList.Tab tab) {
                //获取点击的菜单的索引
                int index = tab.getPosition();
                //设置pageSlider的索引与菜单索引⼀只
                pageSlider.setCurrentPage(index);
                if (index == 0) {
                    //⾸⻚
                    initIndex(pageSlider, intent);
                } else if (index == 1) {
                    //拾智
                    initCategory(pageSlider, intent);
                } else if (index == 2) {
                    //发布
                    initShopcart(pageSlider, intent);
                } else if (index == 3) {
                    //消息
                    initInformation(pageSlider, intent);
                } else if (index == 4) {
                    //我的
                    initUserCenter(pageSlider, intent);
                }
            }

            public void onUnselected(TabList.Tab tab) {
            }

            public void onReselected(TabList.Tab tab) {
            }
        });
        pageSlider.addPageChangedListener(new PageSlider.PageChangedListener() {
            public void onPageSliding(int i, float v, int i1) {
            }

            public void onPageSlideStateChanged(int i) {
            }

            public void onPageChosen(int i) {
                //参数i就表单当前pageSlider的索引
                if (tabList.getSelectedTabIndex() != i) {
                    tabList.selectTabAt(i);
                }
            }
        });
//4.tabList默认选中第⼀个菜单，加载PageSlider的第⼀个⻚⾯（默认）
        tabList.selectTabAt(0);
    }

    //消息
    private void initInformation(PageSlider pageSlider, Intent intent) {
        //通过用户id查找数据库里面的数据
        String id = (String) intent.getParams().getParam("id");
        //        获取推荐商品数据，显示到ability_main_index.xml布局文件中
        DirectionalLayout productListTable = findComponentById(ResourceTable.Id_cath_list);
        //获取对应的数据
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/cath/" + id);
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
            if (resultVo.getFlag() == true) {
                String s1 = gson.toJson(resultVo.getData());
                List<cath> o = gson.fromJson(s1, new TypeToken<List<cath>>() {
                }.getType());
//                将获取到的商品列表显示到table布局中
                getUITaskDispatcher().syncDispatch(() -> {
                    productListTable.removeAllComponents();
                    for (cath product : o) {
//                    获取table中显示的每个商品的模板
                        DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_cath_template, null, false);
//                    将每个商品的信息设置到模板组件
                        Image userImage = parse.findComponentById(ResourceTable.Id_information_thing);
                        Text cathTitle = parse.findComponentById(ResourceTable.Id_cath_title);
                        Text cathLabel = parse.findComponentById(ResourceTable.Id_cath_lebal);
                        Text cathDate = parse.findComponentById(ResourceTable.Id_cath_date);
                        Image cathThingImage = parse.findComponentById(ResourceTable.Id_information_thing_img);
                        cathDate.setText(product.getCathdate());
                        List<params> params = product.getParams();
                        for (params param : params) {
                            cathLabel.setText(param.getOne());
                        }
                        //设置图片角度
                        userImage.setCornerRadius(500f);
                        cathThingImage.setCornerRadius(50f);
                        //用户名称加载数据
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String username = HttpRequestUtil.sendGetRequest(MainAbilitySlice.this, "http://162.14.74.164:8888/userById/" + product.getId1());
                                ResultVo resultVoUser = gson.fromJson(username, ResultVo.class);
                                if (resultVoUser.getFlag()) {
                                    String userData = gson.toJson(resultVoUser.getData());
                                    user users = gson.fromJson(userData, user.class);
                                    getUITaskDispatcher().syncDispatch(() -> {
                                        //                将获取到的商品列表显示到table布局中
                                        cathTitle.setText(users.getName());
                                    });
                                } else {
                                    showDialog("获取信息错误");
                                }
                            }
                        }).start();
                        //加载图片
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                TableLayout productListTable = findComponentById(ResourceTable.Id_search_result_tablelayout_class);
                                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                                    String s = HttpRequestUtil.sendGetRequest(MainAbilitySlice.this, "http://162.14.74.164:8888/thingById/" + product.getThing_id());
                                    LogUtil.info("euo", s.toString());
                                    ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                                    System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
                                    LogUtil.info("euo", resultVo.toString());
                                    if (resultVo.getFlag() == true) {
                                        String s1 = gson.toJson(resultVo.getData());
                                        System.out.println(s1);
                                        List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                                        }.getType());
//                将获取到的商品列表显示到table布局中
                                        getUITaskDispatcher().syncDispatch(() -> {
                                            for (Product product : o) {
                                                List<photos> photos1 = product.getPhotos();
                                                String photos = null;
                                                for (photos photos01 : photos1) {
                                                    photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                                                }
                                                LoadImageUtil.loadImg(MainAbilitySlice.this, photos, cathThingImage);
                                            }
                                        });
                                    } else {
                                        showDialog("获取信息错误");
                                    }
                                });
                            }
                        }).start();
                        productListTable.addComponent(parse);
                        parse.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                intent.setParam("userid", String.valueOf(cathTitle.getText()));
                                intent.setParam("thingid", String.valueOf(product.getThing_id()));
                                intent.setParam("userById", String.valueOf(product.getId1()));
                                intent.setParam("userById2", String.valueOf(product.getId2()));
                                present(new messageChatSlice(), intent);
                            }
                        });
                    }
                });
            } else {
                showDialog("获取信息错误");
            }
        });
    }

    //    用户
    private void initUserCenter(PageSlider pageSlider, Intent intent) {
        //电话
        DirectionalLayout componentById3 = findComponentById(ResourceTable.Id_user_telephone);
        Text componentById = findComponentById(ResourceTable.Id_username);
        Text componentById1 = findComponentById(ResourceTable.Id_user_id);
        Image componentById2 = pageSlider.findComponentById(ResourceTable.Id_image_user);
        componentById2.setCornerRadius(1000f);
        String name = (String) intent.getParams().getParam("name");
        String id = (String) intent.getParams().getParam("id");
        //微信
        DirectionalLayout componentById8 = findComponentById(ResourceTable.Id_user_weixing);
        componentById8.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String weixing = (String) intent.getParams().getParam("weixing");
                MyDialog1.showDialog(MainAbilitySlice.this, "微信修改", id.toString(), "微信", weixing);
            }
        });
        //address
        DirectionalLayout componentById7 = findComponentById(ResourceTable.Id_user_address);
        componentById7.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String address = (String) intent.getParams().getParam("address");
                MyDialog1.showDialog(MainAbilitySlice.this, "修改地址", id.toString(), "地址", address);
            }
        });
        //emil
        DirectionalLayout componentById6 = findComponentById(ResourceTable.Id_user_emil);
        componentById6.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String emil = (String) intent.getParams().getParam("emil");
                MyDialog1.showDialog(MainAbilitySlice.this, "邮件修改", id.toString(), "邮件", emil);
            }
        });
        //qq
        DirectionalLayout componentById5 = findComponentById(ResourceTable.Id_user_qq);
        componentById5.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String qq = (String) intent.getParams().getParam("qq");
                MyDialog1.showDialog(MainAbilitySlice.this, "qq修改", id.toString(), "qq", qq);
            }
        });
        componentById3.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String telephone = (String) intent.getParams().getParam("telephone");
                MyDialog1.showDialog(MainAbilitySlice.this, "电话修改", id.toString(), "电话", telephone);
                //获取原来的电话
            }
        });
        //修改密码和用户名
        DirectionalLayout componentById4 = findComponentById(ResourceTable.Id_user_nameAndPassword);
        componentById4.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                MyDialog.showDialog(MainAbilitySlice.this, "修改用户", name, componentById1.getText().toString());
            }
        });
        //联系我们，尽心吐司弹框
        DirectionalLayout user_link = findComponentById(ResourceTable.Id_user_jieshao);
        user_link.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                showDialog("知行工作室开发软件");
            }
        });
        //团队介绍
        DirectionalLayout user_team_introduction = findComponentById(ResourceTable.Id_user_team_introduction);
        user_team_introduction.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //进行弹框
                showDialog("电话：13147264110");
            }
        });
        DirectionalLayout componentById9 = findComponentById(ResourceTable.Id_user_fankui);
        componentById9.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                MyDialog1.showDialog(MainAbilitySlice.this, "反馈", id.toString(), "信息", "");
            }
        });
        //用户知道我们的信息
        DirectionalLayout componentById10 = findComponentById(ResourceTable.Id_user_addressgetmy);
        componentById10.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                showDialog("湖北省武汉市洪山区街道新路村武汉城市职业学院");
            }
        });
        //我发布的
        DirectionalLayout user_publish = findComponentById(ResourceTable.Id_user_publish);
        Text user_id = findComponentById(ResourceTable.Id_user_id);
        user_publish.setClickedListener(component -> {
            intent.setParam("id", id);
            present(new MyRegisterSlice(), intent);
        });
        //我接榜的
        DirectionalLayout catchuser = findComponentById(ResourceTable.Id_catchuser);
        catchuser.setClickedListener(component -> {
            intent.setParam("id", id);
            present(new catchuser(), intent);
        });
        componentById.setText(name);
        componentById1.setText(id);
    }

    //发布
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

    private void initShopcart(PageSlider pageSlider, Intent intent) {
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
        Button componentById = findComponentById(ResourceTable.Id_add);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                String id = (String) intent.getParams().getParam("id");
                Text title = findComponentById(ResourceTable.Id_thing_title);
                //内容
                Text remark = findComponentById(ResourceTable.Id_thing_remark);
                //地址
                Text address = findComponentById(ResourceTable.Id_thing_address);
                //价格
                Text price = findComponentById(ResourceTable.Id_thing_price);
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                    //获取用户输入的图片，发送后端
                    //后端接口都将不同图片格式转换为jpeg
                    try {
                        for (Uri two : one) {
                            socket = new Socket("162.14.74.164", 9029);
                            outputStream = socket.getOutputStream();
                            byte[] bytes = new byte[1024 * 8];
                            int len;
                            InputStream fd = null;
                            fd = helper.obtainInputStream(two);
                            while ((len = fd.read(bytes)) != -1) {
                                outputStream.write(bytes, 0, len);
                                outputStream.flush();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DataAbilityRemoteException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                            one.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
                    LogUtil.info("title_thing", title.toString());
                    HttpRequestUtil.sendGetRequest(MainAbilitySlice.this, "http://162.14.74.164:8888/release/" + title + "/" + remark + "/" + address + "/" + price + "/" + id);
                });
            }
        });
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


    //    会玩
    private void initCategory(PageSlider pageSlider, Intent intent) {
        DirectionalLayout componentById = findComponentById(ResourceTable.Id_start_playing_id);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.supplements")
                        .withAbilityName("com.example.supplements.Main2Ability")
                        .build();
                intent.setOperation(operation);
                startAbility(intent);
            }
        });
    }


    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }


    //    首页
    private void initIndex(PageSlider pageSlider, Intent intent) {
        //发布跳转
        pageSlider.findComponentById(ResourceTable.Id_index_status).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.supplements")
                        .withAbilityName("com.example.supplements.initShop")
                        .build();
                intent.setOperation(operation);
                intent.setParam("id", (String) intent.getParams().getParam("id"));
                startAbility(intent);
            }
        });
        //发布跳转
        pageSlider.findComponentById(ResourceTable.Id_index_status01).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Operation operation = new Intent.OperationBuilder()
                        .withDeviceId("")
                        .withBundleName("com.example.supplements")
                        .withAbilityName("com.example.supplements.initShop")
                        .build();
                intent.setOperation(operation);
                intent.setParam("id", (String) intent.getParams().getParam("id"));
                startAbility(intent);
            }
        });
        TabList tabList = pageSlider.findComponentById(ResourceTable.Id_thing_tablist);
        Text componentById2 = pageSlider.findComponentById(ResourceTable.Id_backIndex);
        ShapeElement element = new ShapeElement(getContext(), ResourceTable.Graphic_backindex_back);
        element.setCornerRadiiArray(new float[]{
                0f, 0f,
                0f, 0f,
                100f, 100f,
                100f, 100f
        });
        componentById2.setBackground(element);
        String[] tabTexts = {"推荐"};
        tabList.removeAllComponents();
        for (int i = 0; i < tabTexts.length; i++) {
            TabList.Tab tab = tabList.new Tab(this);
            tab.setText(tabTexts[i]);
            tabList.addTab(tab);
            if (i == 0) {
                tab.select(); //默认选择当前tab
            }
        }
        //跳转附近页面
        Text componentById1 = findComponentById(ResourceTable.Id_addressone);
        componentById1.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                present(new addressSlice(), intent);
            }
        });

        Gson gson = new Gson();
        TaskDispatcher globalTaskDispatcher = this.getGlobalTaskDispatcher(TaskPriority.DEFAULT);
//        轮廓图数据
        globalTaskDispatcher.asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/image");
            System.out.println("=========轮廓图===========");
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            if (resultVo.getFlag() == true) {
                String s1 = gson.toJson(resultVo.getData());
                List<IndexImg> list = gson.fromJson(s1, new TypeToken<List<IndexImg>>() {
                }.getType());
//                轮播图数据渲染
                getUITaskDispatcher().asyncDispatch(() -> {
                    IndexImgPageSliderProvider indexImgPageSliderProvider = new IndexImgPageSliderProvider(list, this);
                    PageSlider indexImgPageSlider = findComponentById(ResourceTable.Id_index_image_pageslider);
                    indexImgPageSlider.setProvider(indexImgPageSliderProvider);
                });
            } else {
                showDialog("获取不到有效信息");
            }
        });
//        获取推荐商品数据，显示到ability_main_index.xml布局文件中
        TableLayout productListTable = findComponentById(ResourceTable.Id_index_product_list_table);
        getGlobalTaskDispatcher(TaskPriority.DEFAULT).asyncDispatch(() -> {
            String s = HttpRequestUtil.sendGetRequest(this, "http://162.14.74.164:8888/thingAll");
            ResultVo resultVo = gson.fromJson(s, ResultVo.class);
            System.out.println("------------------------>>>>>>>>>>>>>>>>>>");
            if (resultVo.getFlag() == true) {
                String s1 = gson.toJson(resultVo.getData());
                System.out.println(s1);
                List<Product> o = gson.fromJson(s1, new TypeToken<List<Product>>() {
                }.getType());
//                将获取到的商品列表显示到table布局中
                getUITaskDispatcher().syncDispatch(() -> {
                    productListTable.removeAllComponents();
                    for (Product product : o) {
//                    获取table中显示的每个商品的模板
                        DirectionalLayout parse = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_product_list_item_template, null, false);
//                    将每个商品的信息设置到模板组件
                        Image image = parse.findComponentById(ResourceTable.Id_product_image);
                        image.setCornerRadius(50f);
                        Text title = parse.findComponentById(ResourceTable.Id_product_name_title);
                        Text price = parse.findComponentById(ResourceTable.Id_product_price_text);
                        Text address = parse.findComponentById(ResourceTable.Id_product_address_text);
                        title.setText(product.getTitle());
                        price.setText(product.getPrice());
                        address.setText(product.getAddress());
                        List<photos> photos1 = product.getPhotos();
                        String photos = null;
                        for (photos photos01 : photos1) {
                            photos = "http://162.14.74.164:8888/photo/" + photos01.getId();
                        }
                        LoadImageUtil.loadImg(this, photos, image);
//                    将完成数据渲染的模板添加到table中
                        productListTable.addComponent(parse);
//                    添加商品项的点击事件，跳转并将商品ID传递给详情页面
                        parse.setClickedListener(new Component.ClickedListener() {
                            @Override
                            public void onClick(Component component) {
                                Intent intent = new Intent();
                                intent.setParam("address", product.getAddress());
                                intent.setParam("photoid", String.valueOf(product.getId()));
                                intent.setParam("photo", String.valueOf(product.getPhotos()));
                                intent.setParam("title", String.valueOf(product.getTitle()));
                                intent.setParam("price", String.valueOf(product.getPrice()));
                                intent.setParam("remark", String.valueOf(product.getRemark()));
                                intent.setParam("photoid", String.valueOf(product.getId()));
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
        DirectionalLayout componentById = findComponentById(ResourceTable.Id_Classification);
        componentById.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                Intent intent = new Intent();
                present(new ClassificationSlice(), intent);
            }
        });


        //        监听首页的搜索输入框，点击输入框则跳转到SearchAbilitySlice
        TextField searchTextField = pageSlider.findComponentById(ResourceTable.Id_index_search_textfield);
        searchTextField.setFocusChangedListener((component, b) -> {
//            当搜索输入框获得焦点，导航到SearchAbilitySlice
            if (b) {
                present(new SearchAbilitySlice(), intent);
            }
        });
        //渲染数据：通过接⼝从后台获取数据，并显示到 ability_main_index.xml 布局⽂件的组件中
//        Component productLayout = pageSlider.findComponentById(ResourceTable.Id_layout_product01);
//        productLayout.setClickedListener(component -> {
//            //当index==0时，绑定商品列表的点击事件，点击跳转到详情⻚时，将商品id
////            传递过去
//            Intent intent = new Intent();
////            intent.setParam("productId","10001");
//            this.present(new DetailAbilitySlice(),intent);
//        });
    }


    //    土司弹框
    private void showDialog(String msg) {
        DirectionalLayout layout = (DirectionalLayout) LayoutScatter.getInstance(this).parse(ResourceTable.Layout_layout_toast, null, false);
        Text msgText = (Text) layout.findComponentById(ResourceTable.Id_msg);
        msgText.setText(msg);
        new ToastDialog(this)
                .setDuration(10000)
                .setContentCustomComponent(layout)
                .setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT)
                .show();
    }

    private void selectPic() {
        Intent intent = new Intent();
        Operation opt = new Intent.OperationBuilder().withAction("android.intent.action.GET_CONTENT").build();
        intent.setOperation(opt);
        intent.addFlags(Intent.FLAG_NOT_OHOS_COMPONENT);
        intent.setType("image/*");
        startAbilityForResult(intent, imgRequestCode);
    }

    public void initNotification() {
        NotificationSlot slot = new
                NotificationSlot("slot_1001", "my_slot", NotificationSlot.LEVEL_MIN);//创建
//        notificationSlot对象
        slot.setDescription("发布通知");//通知的描述
        slot.setEnableVibration(true);//设置通知震动提示
        slot.setEnableLight(true);//设置开启呼吸灯提醒
        slot.setLedLightColor(Color.RED.getValue());//设置呼吸灯的提醒颜色
        try {
            NotificationHelper.addNotificationSlot(slot);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int notificationId = 1;
        NotificationRequest request = new NotificationRequest(notificationId);
        request.setSlotId(slot.getId());
        String title = "拾遗";
        String text = "您发布的 小米手环2 物品拾取认领信息，已为您匹配到可能的失主，点击查看详情";
        NotificationRequest.NotificationNormalContent content = new
                NotificationRequest.NotificationNormalContent();
        content.setTitle(title)
                .setText(text);
        NotificationRequest.NotificationContent notificationContent = new
                NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent); // 设置通知的内容
        try {
            NotificationHelper.publishNotification(request);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    public void cancelNotification() {
        int notificationId = 1;
        try {
            NotificationHelper.cancelNotification(notificationId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}