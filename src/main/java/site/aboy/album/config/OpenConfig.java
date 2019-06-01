package site.aboy.album.config;

import java.util.ResourceBundle;

public class OpenConfig {

    public static String getReturnUrl() {
        ResourceBundle bundle = ResourceBundle.getBundle("alipay_openapi_sanbox");

        // 商户APP_ID
        String APP_ID = bundle.getString("APP_ID");
        //回调地址
        String RETURN_URL = bundle.getString("RETURN_URL");
        //认证连接
        String AUTH_URL = bundle.getString("AUTH_URL");

        return AUTH_URL + "?" + "app_id=" + APP_ID + "&scope=auth_user&redirect_uri=" + RETURN_URL;
    }
}
