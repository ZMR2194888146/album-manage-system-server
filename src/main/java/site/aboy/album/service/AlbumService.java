package site.aboy.album.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.aboy.album.utils.Util;
import site.aboy.album.entity.Album;
import site.aboy.album.entity.Image;
import site.aboy.album.repository.AlbumRepository;
import site.aboy.album.repository.ImageRepository;

import java.util.List;

@Service
public class AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ImageRepository imageRepository;

    /**
     * 保存一个电子相册实例
     *
     * @param album 需要保存的电子相册实体
     * @return 保存状态信息
     */
    public String saveAlbum(Album album) {
        if (albumRepository.save(album) != null) {
            return Util.FormatString(200, "update success");
        }
        return Util.FormatString(400, "update failed");
    }

    /**
     * 通过用户id获取电子相册列表
     *
     * @param uid 需要查询的用户id
     * @return 该用户的电子相册列表
     */
    public String getList(Long uid) {
        List<Album> list = albumRepository.findAlbumsByUid(uid);
        if (list.size() > 0) {
            JSONArray array = new JSONArray();
            for (Album album : list) {
                JSONObject json = JSONObject.parseObject(JSON.toJSONString(album));
                if (album.getAccount() > 0) {
                    Image image = imageRepository.findTopByAid(album.getId());
                    json.put("cover", image.getPath());
                }else {
                    json.put("cover", "/favicon.ico");
                }
                array.add(json);
            }
            return Util.FormatJSONArray(200, array);
        } else {
            logger.error("相册为空");
            return Util.FormatString(400, "no date");
        }
    }
}
