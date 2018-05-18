package com.taotao.dao;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by IntelliJ IDEA.
 * User: LHL
 * Date: 2018/5/4
 * Time: 17:17
 */
public class FtpTest {

    @Test
    public void testFtpClient() throws Exception{
        //创建ftpClient对象
        FTPClient ftpClient = new FTPClient();
        //创建连接
        ftpClient.connect("192.168.149.128",21);
        //使用用户名和密码登陆ftp服务器
        ftpClient.login("ftpuser","ftpuser");
        //上传文件,第一个参数，服务器文件名，第二个参数，上传文件的inputStream流
        //修改上传文件格式，图片二进制格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //读取本地文件
        //设置上传路径
        ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
        FileInputStream fileInputStream = new FileInputStream(new File("F:\\电脑图片\\隔扣.jpg"));
        ftpClient.storeFile("hello1.jpg",fileInputStream);
        //关闭连接
        ftpClient.logout();
    }
}
