package com.example.supplements.Utils;

import com.example.supplements.ResourceTable;
import com.example.supplements.slice.MainAbilitySlice;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.agp.window.dialog.CommonDialog;
import ohos.app.Context;

public class MyDialog {
    public static void showDialog(Context context, String msg, String name, String userid) {
//把弹框展示出来
//创建一个弹框对象
        CommonDialog cd = new CommonDialog(context);
//大小是默认包裹内容的。
//弹框默认是居中放置
//弹框默认是透明的
//弹框默认是直角，可以把直角设置为圆角
        cd.setCornerRadius(15);
//把messagedislog的xml文件加载到内存当中。交给弹框并展示出来。
//加载xml文件并获得一个布局对象
//parse方法：加载一个xml文件，返回一个布局对象。
//参数一：要加载的xml文件
//参数二：该xml文件是否跟其他xml文件有关。如果无关是独立的，就写null就可以了
//参数三：如果文件是独立的，那么直接写false
        DirectionalLayout dl = (DirectionalLayout)
                LayoutScatter.getInstance(context).parse(ResourceTable.Layout_messagedialog, null, false);
//要给布局里面的文本和按钮设置事件或者修改内容
//此时需要用dl去调用，表示获取的是dl这个布局里面的组件。
        Text name1 = (Text) dl.findComponentById(ResourceTable.Id_messagelog_name);
        Text password1 = dl.findComponentById(ResourceTable.Id_messagelog_password);
        Text cancel = dl.findComponentById(ResourceTable.Id_cancel);
        Text title = dl.findComponentById(ResourceTable.Id_messagelog_title);
        Text sure = dl.findComponentById(ResourceTable.Id_message_Sure);
//title给标题设置内容
        title.setText(msg);
        name1.setHint(name);
        //取消按钮也要添加点击事件
        cancel.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
//当点击了取消按钮之后，把弹框给关闭
                cd.destroy();
            }
        });
        sure.setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                if (name1.getText() != null && password1.getText() != null) {
                    Text componentById = dl.findComponentById(ResourceTable.Id_messagelog_name);
                    Text componentById1 = dl.findComponentById(ResourceTable.Id_messagelog_password);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpRequestUtil.sendGetRequest(new MainAbilitySlice(), "http://162.14.74.164:8888/user_nameandpassword/" + userid + "/" + componentById.getText() + "/" + componentById1.getText());
                        }
                    }).start();
                    cd.destroy();
                } else {
                    cd.destroy();
                }
            }
        });
//此时布局对象跟弹框还没有任何关系
//我还需要把布局对象交给弹框才可以
        cd.setContentCustomComponent(dl);
//让弹框展示出来
        cd.show();
    }
}
