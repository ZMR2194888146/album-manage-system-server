package site.aboy.album.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.aboy.album.utils.Util;
import site.aboy.album.entity.Album;
import site.aboy.album.entity.Image;
import site.aboy.album.repository.AlbumRepository;
import site.aboy.album.repository.ImageRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CommentService commentService;

    @Value("${web.upload-path}")
    private String uploadPath;

    /**
     * 存储一个图片实例
     *
     * @param file 客户端上传的图片实例
     * @param uid  上传图片的客户id
     * @param aid  上传图片的目标相册的id
     */
    public void saveImage(MultipartFile file, Long uid, Long aid) {
        if (file.isEmpty()) return;
        if (!uploadPath.endsWith("/")) {
            uploadPath = uploadPath + "/";
        }
        File path = new File(uploadPath + "/" + uid + "/");
        //抽象路径，用于从数据库中读取图片时，从该抽象路径中取出
        String abstractPath = uid + "/" + file.getOriginalFilename();
        try {
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    logger.warn("文件夹创建失败");
                    return;
                }
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path.getPath() + "/" + file.getOriginalFilename()));
            imageRepository.save(new Image(uid, aid, "", "./" + abstractPath, ""));
            //刷新相册信息
            Album album = albumRepository.findOne(aid);
            album.setAccount(album.getAccount() + 1);
            albumRepository.save(album);
            bos.write(file.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户id查询图片
     *
     * @param uid 查询资源的用户的id
     * @return 用户所有的图片信息列表
     */
    public String getImagesByUid(Long uid) {
        List<Image> list = imageRepository.findByUid(uid);
        return getImageListString(list);
    }

    /**
     * 修改图片信息
     *
     * @param id          需要修改的图片id
     * @param name        新的图片名字
     * @param description 新的图片描述
     * @return 图片信息修改结果
     */
    public String alterImageInfo(Long id, String name, String description) {
        Image image = imageRepository.findOne(id);
        if (image != null) {
            image.setName(name);
            image.setDescription(description);
            imageRepository.save(image);
            return Util.FormatString(200, "update success");
        }
        return Util.FormatString(400, "update failed");
    }

    /**
     * 修改图片为公开
     *
     * @param id 需要修改的图片的id
     * @return 图片修改结果
     */
    public String changeImageToPublic(Long id) {
        Image image = imageRepository.findOne(id);
        if (image != null) {
            image.setShared(true);
            imageRepository.save(image);
            return Util.FormatString(200, "update success");
        }
        return Util.FormatString(400, "this id is not found");
    }

    /**
     * 返回指定相册的照片列表
     *
     * @param aid 需要查询的相册id
     * @return 相册里的照片列表
     */
    public String getImagesByAid(Long aid) {
        List<Image> list = imageRepository.findByAid(aid);
        return getImageListString(list);
    }

    /**
     * 返回指定用户且已分享的照片列表
     * @param id    需要查询的用户的id
     * @return  照片列表
     */
    public String getSharedImageByUid(Long id) {
        List<Image> list = imageRepository.findAllByUidAndSharedIsTrue(id);
        return getImageListString(list);
    }

    private String getImageListString(List<Image> list) {
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            List<Long> cidList = commentService.getPidOfHaveNoReadComments();
            for (Image image : list) {
                JSONObject json = JSONObject.parseObject(JSON.toJSONString(image));
                if (cidList.contains(image.getId())){
                    json.put("note", true);
                }else {
                    json.put("note", false);
                }
                array.add(json);
            }
            return Util.FormatJSONArray(200, array);
        }
        return Util.FormatString(400, "no data");
    }
}
