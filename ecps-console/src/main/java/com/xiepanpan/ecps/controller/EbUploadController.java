package com.xiepanpan.ecps.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.xiepanpan.ecps.utils.ECPSUtils;
import com.xiepanpan.ecps.utils.UploadResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;


/**
 * describe:图片上传
 *
 * @author xiepanpan
 * @date 2018/10/31
 */
@Controller
@RequestMapping("/upload")
public class EbUploadController {

    /**
     * 品牌添加上传图片
     * @param request
     * @param printWriter
     * @param lastRealPath
     * @throws IOException
     */
    @RequestMapping("/uploadPic.do")
    public void uploadPic(HttpServletRequest request, PrintWriter printWriter,String lastRealPath) throws IOException {
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        //从表单中获取文件
        Iterator<String> iter = mr.getFileNames();
        String inputName = iter.next();
        // 获取文件
        MultipartFile multipartFile = mr.getFile(inputName);
        byte[] bytes = multipartFile.getBytes();
        //定义上传文件的文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        Random random = new Random();
        //文件名后三位为随机数
        for (int i=0;i<3;i++){
            fileName=fileName+random.nextInt(10);
        }
        //获得后缀名
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //相对路径保存到数据库中 绝对路径显示缩略图
        //要上传的绝对路径
        String relativePath="/upload/" + fileName + suffix;
        String realPath = ECPSUtils.readProp("upload_file_path") + relativePath;

        //创建Jersey客户端
        Client client = Client.create();
        //判断是否为第一次上传 如果不是 删除上一次上传的图片
        WebResource webResource = null;
        if (StringUtils.isNotBlank(lastRealPath)) {
            webResource = client.resource(lastRealPath);
            webResource.delete();
        }
        //指定上传的绝对路径
        webResource=client.resource(realPath);
        webResource.put(bytes);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("realPath",realPath);
        jsonObject.accumulate("relativePath",relativePath);
        String result = jsonObject.toString();
        printWriter.write(result);

    }

    /**
     * Fckeditor 图片上传
     * @param request
     * @param printWriter
     * @throws IOException
     */
    @RequestMapping("/uploadForFck.do")
    public void uploadForFck(HttpServletRequest request, PrintWriter printWriter) throws IOException {
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        //从表单中获取文件
        Iterator<String> iter = mr.getFileNames();
        String inputName = iter.next();
        // 获取文件
        MultipartFile multipartFile = mr.getFile(inputName);
        byte[] bytes = multipartFile.getBytes();
        //定义上传文件的文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        Random random = new Random();
        //文件名后三位为随机数
        for (int i=0;i<3;i++){
            fileName=fileName+random.nextInt(10);
        }
        //获得后缀名
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //相对路径保存到数据库中 绝对路径显示缩略图
        //要上传的绝对路径
        String relativePath="/upload/" + fileName + suffix;
        String realPath = ECPSUtils.readProp("upload_file_path") + relativePath;

        //创建Jersey客户端
        Client client = Client.create();
        WebResource webResource = null;
        //指定上传的绝对路径
        webResource=client.resource(realPath);
        webResource.put(bytes);
        //回调对象的创建 UploadResponse.EN_OK表示成功
        UploadResponse uploadResponse = new UploadResponse(UploadResponse.EN_OK,realPath);
        printWriter.print(uploadResponse);


    }
}
