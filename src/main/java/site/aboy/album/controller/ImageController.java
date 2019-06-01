package site.aboy.album.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.aboy.album.service.ImageService;

@Api(value = "图片控制器", description = "图片相关操作")
@RestController
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    /**
     * 上传照片到服务器
     *
     * @param file 上传的图片文件
     * @param uid  上传照片的用户的用户id
     * @param aid  上传到的电子相册id
     */
    @ApiOperation(value = "上传照片到指定的相册中")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uid", name = "上传图片的用户的id", type = "Long", required = true),
            @ApiImplicitParam(value = "aid", name = "上传图片的目标相册", type = "Long", required = true)
    })
    @PostMapping(path = "/image/{uid}/{aid}")
    public void receiveImage(@RequestParam("file") MultipartFile file,
                             @PathVariable("uid") Long uid, @PathVariable("aid") Long aid) {
        imageService.saveImage(file, uid, aid);
    }

    /**
     * 通过用户id获取她名下所有的图片
     *
     * @param uid 查询照片的用户的id
     * @return 照片的列表
     */
    @ApiOperation(value = "获取用户照片")
    @ApiParam(value = "uid", name = "用户id", required = true, type = "Long")
    @GetMapping(path = "/image/{uid}")
    public String getImageByUid(@PathVariable("uid") Long uid) {
        String re = imageService.getImagesByUid(uid);
        logger.info(re);
        return re;
    }

    /**
     * 修改图片描述信息
     *
     * @param id   需要修改的图片的id
     * @param json 包含修改信息的json对象
     * @return 修改的结果状态
     */
    @ApiOperation(value = "修改图片描述信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "name", name = "设置的图片名称", type = "String"),
            @ApiImplicitParam(value = "description", name = "图片描述", type = "String"),
            @ApiImplicitParam(value = "id", name = "需要修改的照片的id", required = true, type = "Long")
    })
    @PutMapping(path = "/image/{id}")
    public String alterImage(@PathVariable("id") Long id, @RequestBody JSONObject json) {
        logger.info(json.toJSONString());
        return imageService.alterImageInfo(id, json.getString("name"), json.getString("description"));
    }

    /**
     * 修改指定id的图片的权限为开放
     * @param id    需要公开的图片id
     * @return  图片权限修改情况
     */
    @ApiOperation(value = "修改图片的权限信息")
    @ApiImplicitParam(value = "图片id", name = "id", type = "Long", paramType = "path")
    @PutMapping("/image/public/{id}")
    public String alterImageToPublic(@PathVariable("id") Long id){
        logger.info("change image to public where id = " + id);
        return imageService.changeImageToPublic(id);
    }

    /**
     * 通过相册的id获取相册里的照片
     * @param aid   需要查询的相册id
     * @return  相册里的图片列表
     */
    @ApiOperation(value = "通过相册id获取相册里面的照片")
    @ApiParam(value = "aid", name = "相册id", required = true, type = "Long")
    @GetMapping("/image/album/{aid}")
    public String getImageByAid(@PathVariable("aid")Long aid){
        return imageService.getImagesByAid(aid);
    }

    /**
     * 通过用户id查询已分享的照片
     * @param id    需要查询目标的用户的id
     * @return  该用户已分享的照片列表
     */
    @ApiOperation(value = "获取指定用户的已分享的照片")
    @ApiParam(value = "id", name = "需要查询的目标用户的id", type = "Long", required = true)
    @GetMapping("/image/share/{id}")
    public String getSharedImageByUid(@PathVariable("id")Long id){
        return imageService.getSharedImageByUid(id);
    }
}
