package com.taotao.service.impl;

import com.taotao.common.util.FtpUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片至ftp服务器
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 19:33
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;

    @Value("${FTP_PORT}")
    private Integer FTP_PORT;

    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;

    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;

    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;

    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    @Override
    public Map uploadPicture(MultipartFile uploadFile){
        Map resultMap = new HashMap();
        //获取源文件名
        String oldName = uploadFile.getOriginalFilename();
        //生产的新文件名
        String newName = IDUtils.genImageName();
        //合成新的文件,获取后缀拼接起来
        newName = newName+oldName.substring(oldName.lastIndexOf("."));
        //图片上传
        try {
            String imagePath = new DateTime().toString("/yyyy/MM/dd");
            boolean flag = FtpUtil.uploadFile(FTP_ADDRESS,FTP_PORT,FTP_USERNAME,FTP_PASSWORD,FTP_BASE_PATH,
                    imagePath,newName,uploadFile.getInputStream());
            //上传失败
            if (!flag){
                resultMap.put("error",1);
                resultMap.put("message","上传文件失败");
                return resultMap;
            }
            String url = IMAGE_BASE_URL+ imagePath+"/"+newName;
            resultMap.put("error",0);
            resultMap.put("url",url);
            return resultMap;
        } catch (IOException e) {
            resultMap.put("error",1);
            resultMap.put("message","上传文件异常");
            return resultMap;
        }
    }
}
