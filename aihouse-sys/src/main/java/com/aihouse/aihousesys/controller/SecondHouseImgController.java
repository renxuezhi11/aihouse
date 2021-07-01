package com.aihouse.aihousesys.controller;

import OSS.OSSClientUtil;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousedao.bean.SecondHandHouseImg;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseImgService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SecondHouseImgController {
    @Resource
    private SecondHandHouseImgService secondHandHouseImgService;

    /**
     * 跳转到楼盘图片页面
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("secondHandHouseImg/gotoList")
    @RequiresPermissions("secondHandHouse/save")
    public String gotoHouseImg(Integer id, HttpServletRequest request, HttpServletResponse response){
        if(id!=null){//获取所有的图片
            SecondHandHouseImg secondHandHouseImg=new SecondHandHouseImg();
            secondHandHouseImg.setSecondHouse(id);
            secondHandHouseImg.setIsDel(0);
            secondHandHouseImg.setImgType(1);
            try {
                request.setAttribute("list", secondHandHouseImgService.selectAll(secondHandHouseImg));
                request.setAttribute("houseId",id);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            request.setAttribute("list",null);
            request.setAttribute("houseId",id);
        }
        return "sys/sys_secondhand_house_img_list";
    }

    /**
     * 删除图片
     * @param secondHandHouseImg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("secondHandHouseImg/deleteImg")
    @ResponseBody
    public DataRes deleteImg(SecondHandHouseImg secondHandHouseImg,HttpServletRequest request,HttpServletResponse response){
        if(secondHandHouseImg.getId()!=null){
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            SecondHandHouseImg secondHandHouseImg1=secondHandHouseImgService.selectByPrimaryKey(secondHandHouseImg);
            ossClientUtil.deleteFile(secondHandHouseImg1.getImgUrl());
            return DataRes.success(secondHandHouseImgService.deleteByPrimaryKey(secondHandHouseImg));
        }
        return DataRes.success(null);
    }

    /**
     * 保存图片
     * @param imgUrls
     * @param secondHandHouseImg
     * @return
     */
    @RequestMapping("secondHandHouseImg/save")
    @RequiresPermissions("secondHandHouse/save")
    @ResponseBody
    public Object saveImg(@RequestParam(value = "imgUrls[]",required=false)List<String> imgUrls, SecondHandHouseImg secondHandHouseImg) {
        try {
           int i= secondHandHouseImgService.saveImg(secondHandHouseImg, imgUrls);
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
    @RequestMapping("secondHandHouseImg/upload")
    @ResponseBody
    public DataRes upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        if(files!=null&&files.size()>0){
            List<String> list=new ArrayList<>();
            OSSClientUtil ossClientUtil=new OSSClientUtil();
            for(MultipartFile file:files){
                try {
                    String name=ossClientUtil.uploadImg2Oss(file);
                    list.add(name);
                }catch (Exception e){
                    System.out.println("上传失败");
                }
            }
            ossClientUtil.destory();
            return DataRes.success(list);
        }else{
            return DataRes.success(null);
        }
    }
}
