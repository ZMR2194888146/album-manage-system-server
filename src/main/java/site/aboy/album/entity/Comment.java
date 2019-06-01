package site.aboy.album.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

//用户评论实例
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JSONField(name = "cid")
    private Long comId;

    //照片id
    @JSONField(name = "pid")
    private Long pid;

    //留下评论的人
    @JSONField(name = "uid")
    private Long uid;

//    是否已读
    @JSONField(name = "haveRead")
    @Column(name = "haveRead", columnDefinition = "bool default false")
    private boolean haveRead = false;

    //评论的内容
    @JSONField(name = "comment_content")
    private String commentContent;

    public Comment() {
    }

    public Comment(Long pid, Long uid, String commentContent) {
        this.pid = pid;
        this.uid = uid;
        this.haveRead = false;
        this.commentContent = commentContent;
    }

    public Comment(JSONObject json){
        this.pid = Long.parseLong(json.getString("pid"));
        this.uid = Long.parseLong(json.getString("uid"));
        this.commentContent = json.getString("content");
        this.setHaveRead(false);
    }

    public boolean isHaveRead() {
        return haveRead;
    }

    public void setHaveRead(boolean haveRead) {
        this.haveRead = haveRead;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
