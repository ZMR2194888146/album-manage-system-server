package site.aboy.album.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.aboy.album.entity.Comment;
import site.aboy.album.service.CommentService;

@Api(description = "评论相关接口")
@RestController
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加一条评论")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "pid", name = "需要添加评论的照片",required = true),
            @ApiImplicitParam(value = "uid", name = "留下评论的用户", required = true),
            @ApiImplicitParam(value = "content", name = "评论的内容",required = true)
    })
    @PostMapping(path = "/comment")
    public String commitComment(@RequestBody JSONObject json) {
        logger.info(json.toJSONString());
        return commentService.addComment(new Comment(json));
    }

    @ApiOperation(value = "获取指定id的照片的评论")
    @ApiImplicitParam(value = "pid",name = "需要获取评论的照片的id", required = true,paramType = "path")
    @GetMapping(path = "/comment/{pid}")
    public String getCommentsByPid(@PathVariable("pid") Long pid){
        return commentService.getCommentByPid(pid);
    }
}
