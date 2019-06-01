package site.aboy.album.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Album {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @JSONField(name = "id")
    private Long id;

    @JSONField(name = "uid")
    private Long uid;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "album_type")
    private String albumType;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "create_time")
    private String createTime;

    @JSONField(name = "account")
    private int account;

    public Album() {
    }

    public Album(Long uid, String name,String description, String albumType ) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.albumType = albumType;
        this.createTime = getNowDate();
        this.account = 0;
    }

    public Album(JSONObject json){
        this.uid = json.getLong("uid");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.albumType = json.getString("album_type");
        this.createTime = getNowDate();
        this.account = 0;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    private static String getNowDate(){
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
        return smf.format(Calendar.getInstance().getTime());
    }
}
