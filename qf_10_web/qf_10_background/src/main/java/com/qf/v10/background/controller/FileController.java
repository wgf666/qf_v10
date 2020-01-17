package com.qf.v10.background.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.v10.pojo.MultiResultBean;
import com.qf.v10.pojo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author chenzetao
 * @Date 2020/1/7
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Value("${image.server}")
    private String IMAGE_SERVER;

    @Autowired
    private FastFileStorageClient client;

    @RequestMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file) {
        System.out.println(file + "!!!!!");
        //得到文件名称
        String originalFilename = file.getOriginalFilename();
        //截取
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            //通过把文件保存到fastDFSf中
            StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
            //得到文件的存储的路径
            String path = storePath.getFullPath();
            //把路径返回给前端
            String pathStr = new StringBuilder(IMAGE_SERVER).append(path).toString();
            return ResultBean.success(pathStr);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultBean.error("操作失败，请稍后再试!!");
        }
    }

    @RequestMapping("multiUpload")
    @ResponseBody
    public MultiResultBean multiUpload(MultipartFile[] files) {
        MultiResultBean resultBean = new MultiResultBean();
        //装文件的存储地址
        String[] data = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            //得到文件名称
            String originalFilename = files[i].getOriginalFilename();
            //截取
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                //通过把文件保存到fastDFSf中
                StorePath storePath = client.uploadFile(files[i].getInputStream(), files[i].getSize(), suffix, null);
                //得到文件的存储的路径
                String path = storePath.getFullPath();
                //把路径返回给前端
                String pathStr = new StringBuilder(IMAGE_SERVER).append(path).toString();
                data[i] = pathStr;
            } catch (IOException e) {
                e.printStackTrace();
                resultBean.setErrno("-1");
                return resultBean;
            }
        }
        //成功的情况
        resultBean.setErrno("0");
        resultBean.setData(data);
        return resultBean;
    }
}
