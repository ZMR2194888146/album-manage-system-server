package site.aboy.album.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.aboy.album.entity.User;
import site.aboy.album.repository.UserRepository;
import site.aboy.album.utils.Store;
import site.aboy.album.utils.WebSocketServer;

import java.util.Objects;
import java.util.ResourceBundle;

@Service
public class ShareService {
    private static final Logger logger = LoggerFactory.getLogger(ShareService.class);

    @Autowired
    private UserRepository userRepository;

    public String alipayUserInfoShareService(String code, String sid) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        String access_token = Objects.requireNonNull(getAccessTokenMethod(code)).getAccessToken();
        if (access_token != null) {
            JSONObject userInfo = JSONObject.parseObject(Objects.requireNonNull(getInfoBody(access_token)).getBody());
            if (checkLogin((JSONObject) userInfo.get("alipay_user_info_share_response"), sid)) {
                return "<h2>登录成功，你可以关闭此页面</h2>";
            }
        }
        return "<h3>出现错误</h3>";
    }


    private boolean checkLogin(JSONObject userInfo, String sid) {
        logger.info("userInfo：" + userInfo);
        if (!userInfo.isEmpty()) {
            String loginUid = userInfo.getString("user_id");
            User u = userRepository.findUserByAliUserId(loginUid);
            logger.info("user====>" + JSON.toJSONString(u));
            if (u == null) {
                JSONObject json = new JSONObject();
                json.put("code", 200);
                json.put("bind", true);
                json.put("login", false);
                json.put("aliUserId", loginUid);
                json.put("message", "need bind your info");
                Store.save(loginUid, userInfo);
                WebSocketServer.sendMessage(sid, json.toJSONString());
            } else {
                JSONObject mes = new JSONObject();
                mes.put("code", 200);
                mes.put("uid", u.getId());
                mes.put("bind", false);
                mes.put("login", true);
                mes.put("avatar", u.getAvatar());
                mes.put("message", "login success");
                WebSocketServer.sendMessage(sid, mes.toJSONString());
            }

            return true;
        }
        return false;
    }

    //    获取access_token
    private AlipaySystemOauthTokenResponse getAccessTokenMethod(String authCode) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = getAlipayClient().execute(request);
            logger.info(oauthTokenResponse.getAccessToken());
            return oauthTokenResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
        }
        return null;
    }


    //    获取用户信息
    private AlipayUserInfoShareResponse getInfoBody(String accessCode) {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        try {
            AlipayUserInfoShareResponse response = getAlipayClient().execute(request, accessCode);
            logger.info(response.getBody());
            return response;
        } catch (AlipayApiException e) {
            logger.info(e.getErrMsg());
            return null;
        }
    }

    //获取支付宝客户端
    private static AlipayClient getAlipayClient() {
        ResourceBundle bundle = ResourceBundle.getBundle("alipay_openapi_sanbox");
        // 网关
        String URL = bundle.getString("ALIPAY_GATEWAY_URL");
        // 商户APP_ID
        String APP_ID = bundle.getString("APP_ID");
        // 商户RSA 私钥
        String APP_PRIVATE_KEY = bundle.getString("RSA2_PRIVATE_KEY");
        // 请求方式 json
        String FORMAT = bundle.getString("FORMAT");
        // 编码格式，目前只支持UTF-8
        String CHARSET = bundle.getString("CHARSET");
        // 支付宝公钥
        String ALIPAY_PUBLIC_KEY = bundle.getString("ALIPAY_RSA2_PUBLIC_KEY");
        // 签名方式
        String SIGN_TYPE = bundle.getString("SIGN_TYPE");
        return new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }
}