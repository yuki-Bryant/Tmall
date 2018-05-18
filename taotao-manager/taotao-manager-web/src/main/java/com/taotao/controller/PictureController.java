package com.taotao.controller;

import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传图片处理
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 20:16
 */
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map pictureUpload(MultipartFile uploadFile){
        Map map = pictureService.uploadPicture(uploadFile);
        return map;
    }
}
