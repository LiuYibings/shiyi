package com.example.supplements.slice;

import com.example.supplements.ResourceTable;
import com.example.supplements.provider.TabPageSliderProvider;
import com.google.gson.Gson;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;

import java.util.ArrayList;
import java.util.List;

public class ClassificationSlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        Gson gson = new Gson();
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_classification);
        TabList tabList = findComponentById(ResourceTable.Id_class_tablist);
        tabList.removeAllComponents();
        String[] tabTexts = {"手机数码", "生活", "家用电器", "美妆", "运动户外", "儿童玩具", "箱包", "卡片"};
        for (int i = 0; i < tabTexts.length; i++) {
            TabList.Tab tab = tabList.new Tab(this);
            tab.setText(tabTexts[i]);
            tabList.addTab(tab);
            if (i == 0) {
                tab.select(); //默认选择当前tab
            }
        }
        //2.初始化PageSlider
        List<Integer> layoutFileIds = new ArrayList<>();
        layoutFileIds.add(ResourceTable.Layout_class01);
        layoutFileIds.add(ResourceTable.Layout_class02);
        layoutFileIds.add(ResourceTable.Layout_class04);
        layoutFileIds.add(ResourceTable.Layout_class05);
        layoutFileIds.add(ResourceTable.Layout_class06);
        layoutFileIds.add(ResourceTable.Layout_class08);
        layoutFileIds.add(ResourceTable.Layout_class10);
        layoutFileIds.add(ResourceTable.Layout_class11);
        PageSlider pageSlider = (PageSlider) findComponentById(ResourceTable.Id_class_pageSlider);
        pageSlider.setProvider(new TabPageSliderProvider(layoutFileIds, this));
        //3.TabList与PageSlider联动
        tabList.addTabSelectedListener(new TabList.TabSelectedListener() {
            public void onSelected(TabList.Tab tab) {
                //获取点击的菜单的索引
                int index = tab.getPosition();
                //设置pageSlider的索引与菜单索引⼀只
                pageSlider.setCurrentPage(index);
                if (index == 0) {
                    class01(pageSlider);
                } else if (index == 1) {
                    class02(pageSlider);
                } else if (index == 2) {
                    class03(pageSlider);
                } else if (index == 3) {
                    class04(pageSlider);
                } else if (index == 4) {
                    class05(pageSlider);
                } else if (index == 5) {
                    class06(pageSlider);
                } else if (index == 6) {
                    class07(pageSlider);
                } else if (index == 7) {
                    class08(pageSlider);
                }
            }

            public void onUnselected(TabList.Tab tab) {
            }

            public void onReselected(TabList.Tab tab) {
            }

            private void class02(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class19).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("华为手机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class20).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("手机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class21).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("苹果手机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class22).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("打印机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class23).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("笔");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class24).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("激光笔");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class25).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("投影机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class26).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("提示牌");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class27).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("钥匙");
                    }
                });
            }

            private void class03(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class28).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("吸尘器");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class29).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("风扇");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class30).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("洗衣机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class31).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("扫地机器人");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class32).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("音响");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class33).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("吉他");
                    }
                });

            }

            private void class04(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class34).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("乳液乳霜");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class35).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("洁面乳");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class36).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("眼霜");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class37).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("卸妆油");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class38).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("男士洁面");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class39).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("润肤乳");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class40).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("粉底液");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class41).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("粉底");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class42).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("bb霜");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class43).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("香水");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class44).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("指甲油");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class45).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("防晒");
                    }
                });
            }

            private void class05(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class46).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("运动外套");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class47).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("运动短裤");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class48).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("运动上衣");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class49).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("篮球鞋");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class50).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("休闲鞋");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class51).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("跑步鞋");
                    }
                });
            }

            private void class06(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class52).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("自行车");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class53).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("童车");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class54).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("积木");
                    }
                });
            }

            private void class07(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class55).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("钱包");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class56).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("背包");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class57).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("斜挎包");
                    }
                });
            }

            private void class08(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class58).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("银行卡");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class59).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("饭卡");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class60).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("身份证");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class61).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("学生证");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class62).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("火车票学生优惠卡");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class63).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("其他证件");
                    }
                });
            }


            private void class01(PageSlider pageSlider) {
                pageSlider.findComponentById(ResourceTable.Id_thing_class01).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("数据线");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class02).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("智能手表");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class03).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("手环");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class04).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("电瓶车电池");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class05).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("移动充电宝");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class06).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("耳机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class07).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("键盘");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class08).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("固态硬盘");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class09).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("机械硬盘");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class10).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("显示器");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class11).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("主板");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class12).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("内存条");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class13).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("笔记本电脑");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class14).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("平板电脑");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class15).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("台式机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class16).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("照相机");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class17).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("镜头");
                    }
                });
                pageSlider.findComponentById(ResourceTable.Id_thing_class18).setClickedListener(new Component.ClickedListener() {
                    @Override
                    public void onClick(Component component) {
                        httpPhx("数码相机");
                    }
                });
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

    private void httpPhx(String name) {
        Intent intent = new Intent();
        intent.setParam("name",name);
        present(new ClassChatSlice(),intent);
    }
}
