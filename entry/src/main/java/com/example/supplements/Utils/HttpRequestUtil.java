package com.example.supplements.Utils;

import ohos.app.Context;
import ohos.net.NetHandle;
import ohos.net.NetManager;
import ohos.net.NetStatusCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestUtil {
    /**
     *
     * @param context Ability上下⽂
     * @param urlString 请求的接⼝路径
     * @param method 请求⽅式：GET POST DELETE PUT
     * @param token 访问受限资源携带token，⾮受限资源传递null即可
     * @param data 当请求⽅式为PUT/POST时，通过请求体传递的数据
    （JSON格式字符串）
     * @return json格式字符串
     */
    private static String sendRequest(Context context, String urlString, String method, String token, String data){
        String result = null;
        NetManager netManager = NetManager.getInstance(context);
        if (!netManager.hasDefaultNet()) {
            return null;
        }
        NetHandle netHandle = netManager.getDefaultNet();
        // 可以获取⽹络状态的变化
        NetStatusCallback callback = new NetStatusCallback() {
            // 重写需要获取的⽹络状态变化的override函数
        };
        netManager.addDefaultNetStatusCallback(callback);
        // 通过openConnection来获取URLConnection
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) netHandle.openConnection(url, java.net.Proxy.NO_PROXY);
            //设置请求⽅式
            connection.setRequestMethod(method);
            //如果通过请求体传递参数到服务端接⼝，需要对connection进⾏额外的设置
            if(data != null ){
                //允许通过此⽹络连接向服务端写数据
                connection.setDoOutput(true);
                connection.setRequestProperty("ContentType","application/json;charset=utf-8");
            }
            //如果参数token！=null,则需要将token设置到请求头
            if(token!=null){
                connection.setRequestProperty("token",token);
            }
            //发送请求建⽴连接
            connection.connect();
            //向服务端传递data中的数据
            if(data != null){
                byte[] bytes = data.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(bytes);
                os.flush();
            }
            //从连接中获取输⼊流，读取api接⼝返回的数据
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();
                StringBuilder builder = new StringBuilder();
                byte[] bs = new byte[1024];
                int len = -1;
                while((len = is.read(bs))!=-1){
                    builder.append( new String(bs,0,len));
                }
                result = builder.toString();
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return result;
    }
    public static String sendGetRequest(Context context,String urlString){
        return sendRequest(context,urlString,"GET",null,null);
    }
    public static String sendGetRequestWithToken(Context context,String urlString,String token){
        return sendRequest(context,urlString,"GET",token,null);
    }
    public static String sendDeleteRequest(Context context,String urlString){
        return sendRequest(context,urlString,"DELETE",null,null);
    }
    public static String sendDeleteRequestWithToken(Context context,String urlString,String token){
        return sendRequest(context,urlString,"DELETE",token,null);
    }
    public static String sendPostRequest(Context context,String urlString){
        return sendRequest(context,urlString,"POST",null,null);
    }
    public static String sendPostRequest(Context context,String urlString,String data){
        return sendRequest(context,urlString,"POST",null,data);
    }
    public static String sendPostRequestWithToken(Context context,String urlString,String token){
        return sendRequest(context,urlString,"POST",token,null);
    }
    public static String sendPostRequestWithToken(Context context,String urlString,String token,String data){
        return sendRequest(context,urlString,"POST",token,data);
    }
    public static String sendPutRequest(Context context,String urlString){
        return sendRequest(context,urlString,"PUT",null,null);
    }
    public static String sendPutRequest(Context context,String urlString,String data){
        return sendRequest(context,urlString,"PUT",null,data);
    }
    public static String sendPutRequestWithToken(Context context,String urlString,String token){
        return sendRequest(context,urlString,"PUT",token,null);
    }
    public static String sendPutRequestWithToken(Context context,String urlString,String token,String data){
        return sendRequest(context,urlString,"PUT",token,data);
    }
}
