package site.aboy.album.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JSONField(name = "id")
    private Long id;

    private String aliUserId;

    private String sid;

    private String name;

    private String rcode;

    @JSONField(name = "motto")
    private String motto;

    @JSONField(name = "avatar")
    private String avatar;

    @JSONField(name = "isStudent")
    private boolean student;

    @JSONField(name = "nick_name")
    private String nickName;

    @JSONField(name = "active")
    private boolean active;

    public User() {
    }

    public User(String aliUserId, String avatar, boolean student, String nickName) {
        this.aliUserId = aliUserId;
        this.avatar = avatar;
        this.student = student;
        this.nickName = nickName;
    }

    public User(String sid, String name, String rcode) {
        this.sid = sid;
        this.name = name;
        this.rcode = rcode;
        this.student = false;
    }

    public User(JSONObject json){
        this.aliUserId = json.getString("user_id");
        this.avatar = json.getString("avatar");
        this.student = json.getString("is_student_certified").equals("T");
        this.nickName = json.getString("nick_name");
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliUserId() {
        return aliUserId;
    }

    public void setAliUserId(String aliUserId) {
        this.aliUserId = aliUserId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRcode() {
        return rcode;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
