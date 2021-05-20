package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.PriceNotice;
import com.aihouse.aihouseservice.PriceNoticeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.util.List;

/**
 * 降价通知controller
 */
@CrossOrigin
@RestController
public class PriceNoticeController {
    @Resource
    private PriceNoticeService priceNoticeService;

    @Resource
    private RedisUtil redisUtil;
    /**
     * 添加降价通知
     * @param houseId
     * @param houseType
     * @param request
     * @return
     */
    @RequestMapping(value = "app/addPriceNotice",method = RequestMethod.POST)
    public DataRes addPriceNotice(Integer houseId, Integer houseType, HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
        PriceNotice priceNotice=new PriceNotice();
        priceNotice.setHouseType(houseType);
        priceNotice.setHouseId(houseId);
        priceNotice.setUserId(Integer.parseInt(userId));
        priceNotice.setTelephone(sessionUser.getUsers().getTelephone());
        List<PriceNotice> list=priceNoticeService.selectAll(priceNotice);
        if(list.size()>0){
            return DataRes.success("已添加");
        }else{
            priceNoticeService.insert(priceNotice);
            return DataRes.success("添加成功");
        }
    }

    /**
     * 取消降价通知
     * @param houseId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/canclePriceNotice",method = RequestMethod.POST)
    public DataRes canclePriceNotice(Integer houseId,Integer houseType,HttpServletRequest request){
        if(houseId!=null){
            String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
            PriceNotice priceNotice=new PriceNotice();
            priceNotice.setHouseId(houseId);
            priceNotice.setHouseType(houseType);
            priceNotice.setUserId(Integer.parseInt(userId));
            List<PriceNotice> list=priceNoticeService.selectAll(priceNotice);
            if(list.size()>0){
                priceNoticeService.deleteByPrimaryKey(list.get(0));
            }
            return DataRes.success("取消成功");
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
