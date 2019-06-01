package site.aboy.album.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.aboy.album.entity.User;
import site.aboy.album.repository.UserRepository;
import site.aboy.album.utils.Store;
import site.aboy.album.utils.Util;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取所有的用户列表
     *
     * @return 包含用户列表的json
     */
    public String getAllUserList() {
        List<User> list = userRepository.findAll();
        return getUserList(list);
    }

    /**
     * 获取所有激活的用户列表
     * @return 包含已激活的用户列表
     */
    public String getAllActiveUser(){
        List<User> list = userRepository.findUsersByActiveIsTrue();
        return getUserList(list);
    }

    public String setUserMotto(Long uid, String motto){
        User student = userRepository.findOne(uid);
        if (student != null){
            student.setMotto(motto);
            userRepository.save(student);
            return Util.FormatString(200, "update success");
        }
        return Util.FormatString(400, "this user was not found");
    }

    /**
     * 绑定用户信息
     * @param json      用户提交的认证信息
     * @return  认证结果
     */
    public String bindUserInfo(JSONObject json) {
        String sid = json.getString("sid");
        User student = userRepository.findUserBySid(sid);
        if (student != null && json.getString("rcode").equals(student.getRcode())) {
            if (json.getString("name").equals(student.getName())) {
                String aliUserId = json.getString("aliUserId");
                JSONObject userInfo = (JSONObject) Store.get(aliUserId);
                student.setAliUserId(aliUserId);
                student.setNickName(json.getString("nick_name"));
                student.setAvatar(userInfo.getString("avatar"));
                student.setSid(json.getString("sid"));
                student.setName(json.getString("name"));
                student.setActive(true);
                userRepository.save(student);
                JSONObject info = new JSONObject();
                info.put("avatar", userInfo.getString("avatar"));
                info.put("uid", student.getId());
                return Util.FormJSONObject(200, info);
            }
            return Util.FormatString(400, "misinformation");
        }
        return Util.FormatString(400, "this sid was not found or authentication code was wrong!");
    }

    private String getUserList(List<User> list) {
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (User u : list) {
                array.add(JSONObject.parseObject(JSON.toJSONString(u)));
            }
            return Util.FormatJSONArray(200, array);
        }
        return Util.FormatString(400, "no data");
    }
}
