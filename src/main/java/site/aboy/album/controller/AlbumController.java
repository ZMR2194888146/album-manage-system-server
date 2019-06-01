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
import site.aboy.album.entity.Album;
import site.aboy.album.service.AlbumService;

@Api(value = "相册管理", description = "相册相关的操作")
@RestController
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    /**
     * 创建一个相册
     *
     * @param album String name(not null), String description, String album_type (not null)
     * @return 相册创建状态信息
     */
    @ApiOperation(value = "创建一个相册", notes = "必须提供相册名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "相册名", required = true, dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "description", value = "相册描述", dataType = "String", paramType = "body"),
            @ApiImplicitParam(name = "album_type", value = "相册类型", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/album")
    public String createAlbum(@RequestBody JSONObject album) {
        String re = albumService.saveAlbum(new Album(album));
        logger.info("reception:" + re);
        return re;
    }

    /**
     * 获取一个人所有的电子相册
     * @return 电子相册列表
     */
    @ApiOperation(value = "通过用户id获取她所创建的相册")
    @ApiImplicitParam(value = "uid",name = "用户id", type = "Long", required = true,paramType = "path")
    @GetMapping("/album/{uid}")
    public String getAlbums(@PathVariable("uid") Long uid){
        return albumService.getList(uid);
    }
}
