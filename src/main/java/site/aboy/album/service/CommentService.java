package site.aboy.album.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.aboy.album.entity.Comment;
import site.aboy.album.repository.CommentRepository;
import site.aboy.album.utils.Util;

import java.util.List;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    /**
     * 添加一条评论
     *
     * @param comment 用户评论实例
     * @return 实例保存状态
     */
    public String addComment(Comment comment) {
        if (commentRepository.save(comment) != null) {
            return Util.FormatString(200, "update success");
        }
        return Util.FormatString(400, "have error in save");
    }

    /**
     * 获取指定照片的全部评论
     * @param pid   需要获取评论的照片id
     * @return  评论列表
     */
    public String getCommentByPid(Long pid) {
        List<Comment> list = commentRepository.findCommentsByPid(pid);
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (Comment comment : list) {
                array.add(JSONObject.parseObject(JSON.toJSONString(comment)));
                comment.setHaveRead(true);
                commentRepository.save(comment);
            }
            return Util.FormatJSONArray(200, array);
        }
        return Util.FormatString(400, "no data");
    }


    List<Long> getPidOfHaveNoReadComments(){
       return  commentRepository.findPidByHaveReadIsFalse();
    }
}
