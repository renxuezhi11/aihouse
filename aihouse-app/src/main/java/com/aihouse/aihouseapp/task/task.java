package com.aihouse.aihouseapp.task;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.*;
import com.aihouse.aihouseservice.PriceLogService;
import com.aihouse.aihouseservice.PriceNoticeService;
import com.aihouse.aihouseservice.newhouse.NewHouseService;
import com.aihouse.aihouseservice.officehouse.OfficeHouseService;
import com.aihouse.aihouseservice.renthouse.SysRentHouseService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.aihouse.aihouseservice.shophouse.ShopHouseService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class task {
//    private static final Logger logger = LoggerFactory.getLogger(task.class);

    @Resource
    private PriceLogService priceLogService;

    @Resource
    private PriceNoticeService priceNoticeService;

    @Resource
    private NewHouseService newHouseService;

    @Resource
    private SecondHandHouseService secondHandHouseService;

    @Resource
    private SysRentHouseService sysRentHouseService;

    @Resource
    private ShopHouseService shopHouseService;

    @Resource
    private OfficeHouseService officeHouseService;
    /**
     * 定时任务执行降价通知，半个小时执行一次
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void runPriceNotice(){
        //获取还没通知的降价变动记录
        List<PriceLog> logList=priceLogService.selectAllLow();
        if(logList!=null&&logList.size()>0){
            List<Integer> ids=new ArrayList<>();
            for(PriceLog p:logList){
                PriceNotice priceNotice=new PriceNotice();
                priceNotice.setHouseType(p.getType());
                priceNotice.setHouseId(p.getHouseId());
                List<PriceNotice> priceNoticeList=priceNoticeService.selectAll(priceNotice);
                if(priceNoticeList!=null&&priceNoticeList.size()>0){
                    String name="";
                    if(p.getType()==1){
                        NewHouse newHouse=new NewHouse();
                        newHouse.setId(p.getHouseId());
                        newHouse=newHouseService.selectByPrimaryKey(newHouse);
                        if(newHouse!=null){
                            name=newHouse.getName();
                        }
                    }else if(p.getType()==2){
                        SecondHandHouse secondHandHouse=new SecondHandHouse();
                        secondHandHouse.setId(p.getHouseId());
                        secondHandHouse=secondHandHouseService.selectByPrimaryKey(secondHandHouse);
                        if(secondHandHouse!=null){
                            name=secondHandHouse.getTitle();
                        }
                    }else if(p.getType()==3){
                        RentHouse rentHouse=new RentHouse();
                        rentHouse.setId(p.getHouseId());
                        rentHouse=sysRentHouseService.selectByPrimaryKey(rentHouse);
                        if(rentHouse!=null){
                            name=rentHouse.getTitle();
                        }
                    }else if(p.getType()==4){
                        ShopHouse shopHouse=new ShopHouse();
                        shopHouse.setId(p.getHouseId());
                        shopHouse=shopHouseService.selectByPrimaryKey(shopHouse);
                        if(shopHouse!=null){
                            name=shopHouse.getTitle();
                        }
                    }else if(p.getType()==5){
                        OfficeHouse officeHouse=new OfficeHouse();
                        officeHouse.setId(p.getHouseId());
                        officeHouse=officeHouseService.selectByPrimaryKey(officeHouse);
                        if(officeHouse!=null){
                            name=officeHouse.getName();
                        }
                    }
                   for(PriceNotice n:priceNoticeList){
                       HashMap<String, String> hashMap = new HashMap<String, String>();
                       hashMap.put("phoneName", n.getTelephone());
                       hashMap.put("SignName", SmsConstant.SIGN_NAME);
                       hashMap.put("TemplateCode", SmsConstant.LOWER_NOTICE);
                       hashMap.put("TemplateParam", SmsConstant.HOUSE + "\"" + name + "\"}");
                       try {
                           SendSmsResponse res = SmsDemo.sendSms(hashMap);
                       } catch (ClientException e) {
                           e.printStackTrace();
                       }
                   }
                }
                ids.add(p.getId());
            }
            priceLogService.updateIds(ids);
        }

    }
}
