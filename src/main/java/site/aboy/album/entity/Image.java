package site.aboy.album.entity;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JSONField(name = "id")
    private Long id;

//    隶属用户
    @JSONField(name = "uid")
    private Long uid;

//    隶属相册
    @JSONField(name = "aid")
    private Long aid;

//    被点赞的次数
    @JSONField(name = "fav_num")
    private int favNum;

//    图像标题
    @JSONField(name = "name")
    private String name;

//    图像存储路径
    @JSONField(name = "path")
    private String path;

    private boolean shared;

//    图像描述
    @JSONField(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public Image() {
    }

    public Image(Long uid, Long aid, String name, String path, String description) {
        this.uid = uid;
        this.aid = aid;
        this.name = name;
        this.path = path;
        this.description = description;
        this.shared = false;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
