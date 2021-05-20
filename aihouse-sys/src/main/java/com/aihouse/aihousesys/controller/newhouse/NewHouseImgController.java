package com.aihouse.aihousesys.controller.newhouse;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.bean.NewHouseImg;
import com.aihouse.aihouseservice.newhouse.NewHouseImgService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class NewHouseImgController {
    @Resource
    private NewHouseImgService newHouseImgService;

    @Value("${auto.code.filePath}")
    private String filePath;

    /**
     * 跳转到楼盘图片页面
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newghouseimg/gotoList")
    @RequiresPermissions("newhouse/selectAll")
    public String gotohouseImg(Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){//获取所有的图片
            NewHouseImg newHouseImg=new NewHouseImg();
            newHouseImg.setNewHouseId(id);
            newHouseImg.setOrderByString("order by img_type asc");
            try {
                request.setAttribute("list", newHouseImgService.selectAll(newHouseImg));
                request.setAttribute("houseId",id);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("list",null);
            request.setAttribute("houseId",id);
        }
        return "newhouse/newhouseimg_list";
    }

    /**
     * 删除图片
     * @param newHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhouseimg/deleteImg")
    @ResponseBody
    public DataRes deleteImg(NewHouseImg newHouseImg,HttpServletRequest request,HttpServletResponse response){
        if(newHouseImg.getId()!=null){
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            NewHouseImg newHouseImg1=newHouseImgService.selectByPrimaryKey(newHouseImg);
            ossClientUtil.deleteFile(newHouseImg1.getImgUrl());
            return DataRes.success(newHouseImgService.deleteByPrimaryKey(newHouseImg));
        }
        return DataRes.success(null);
    }

    /**
     * 保存图片
     * @param imgUrls
     * @param newHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("newhouseimg/save")
    @RequiresPermissions("newHouseImg/save")
    @ResponseBody
    public Object saveImg(@RequestParam(value = "imgUrls[]",required=false)List<String> imgUrls, NewHouseImg newHouseImg, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
           int i= newHouseImgService.saveImg(newHouseImg, imgUrls);
            return DataRes.success(i);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DataRes.success(0);
    }

      /**
     * 多图片上传
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("newhouseimg/upload")
    @ResponseBody
    public DataRes upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        if(files!=null&&files.size()>0){
            List<String> list=new ArrayList<>();
            for(MultipartFile file:files){
                /*// 获得文件：
                // 获得文件名：
                String filename = UUID.randomUUID().toString().replaceAll("-", "")+"."+file.getOriginalFilename().split("\\.")[1];
                // 获得输入流：
                InputStream input = file.getInputStream();
                String fileDris= DateUtils.formatDateByPattern(new Date(), "/yyyy/MM/dd/");
                File p = new File(filePath + fileDris);
                if (!p.exists()) {
                    p.mkdirs();
                }
                String filepath=DateUtils.formatDateByPattern(new Date(), "yyyyMMddHHmmss") + filename;
                File file1 = new File(p, filepath);
                try {
                    file.transferTo(file1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                list.add(fileDris+filepath);*/
                OSSClientUtil ossClientUtil=new OSSClientUtil();
                try {
                    String name=ossClientUtil.uploadImg2Oss(file);
                    list.add(name);
                }catch (Exception e){
                    System.out.println("上传失败");
                }
            }
            return DataRes.success(list);
        }else{
            return DataRes.success(null);
        }
    }
}
