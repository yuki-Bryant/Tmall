package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 19:28
 */
public interface PictureService {
    Map uploadPicture(MultipartFile uploadFile);
}
