package com.bdqn.controller.admin;

import com.alibaba.fastjson.JSON;
import com.bdqn.utils.SystemConstant;
import com.bdqn.utils.UUIDUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/file")
public class FileController {
    /**
     * 文件上传
     * @param attach
     * @return
     */
    @RequestMapping("/uploadFile")
    public String uploadFile(@RequestParam(value = "file") MultipartFile attach){  //把attach重命名为file
        //创建map集合保存返回的JSON数据
        Map<String,Object> map =new HashMap<String,Object>();
        //判断是否有选中文件
        if(!attach.isEmpty()){
            //获取源文件的名称
            String oldFileName =attach.getOriginalFilename();
            //获取源文件后缀名
            String extension = FilenameUtils.getExtension(oldFileName);
            //重命名旧文件
            String newFileName = UUIDUtils.randomUUID()+"."+extension;
            //为了解决同一个文件夹下文件过多的问题，使用日期作为文件夹管理
            String dataPath = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //组装最终的文件名
            String finalName =dataPath+"/"+newFileName;
            //创建文件对象
            //参数一：文件上传的地址   参数二：文件名称
            File dest = new File(SystemConstant.IMAGE_UPLOAD_PATH,finalName);
            //判断该文件夹是否存在，不存在则创建文件夹
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();//创建文件夹
            }

            try {
                //进行文件上传
                attach.transferTo(dest);
                map.put("code",0);//状态码
                map.put("msg","上传成功");
                Map<String,Object> dataMap = new HashMap<String,Object>();
                dataMap.put("src","/hotel/show/"+finalName);
                map.put("data",dataMap);//文件数据
                map.put("imagePath",finalName);//隐藏域的值，只保留日期文件夹+重命名后的文件名
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSON.toJSONString(map);
    }
}
