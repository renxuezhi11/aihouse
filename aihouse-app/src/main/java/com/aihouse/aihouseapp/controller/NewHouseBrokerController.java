package com.aihouse.aihouseapp.controller;


import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.HouseLimit;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.NewHouseBroker;
import com.aihouse.aihouseservice.HouseLimitService;
import com.aihouse.aihouseservice.newhouse.NewHouseBrokerService;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class NewHouseBrokerController {
    @Resource
    private NewHouseBrokerService newHouseBrokerService;

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private HouseLimitService houseLimitService;

    /**
     * 用户入驻楼盘
     * @param houseId
     * @param request
     * @return
     */
    @RequestMapping(value = "app/addNewHouseBroker",method = RequestMethod.POST)
    public DataRes addNewHouseBroker(Integer houseId, HttpServletRequest request){
        if(houseId!=null) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            if(newHouseSearchService.queryInfo(houseId)!=null){
                NewHouseBroker newHouseBroker=new NewHouseBroker();
                newHouseBroker.setNewHouseId(houseId);
                newHouseBroker.setBrokerId(Integer.parseInt(userId));
                newHouseBroker.setStatus(0);
                List<NewHouseBroker> list=newHouseBrokerService.selectAll(newHouseBroker);
                if(list.size()>0){
                    return DataRes.success("已入驻");
                }else{
                    HouseLimit houseLimit=new HouseLimit();
                    List<HouseLimit> limitList=houseLimitService.selectAll(houseLimit);
                    if(limitList.size()>0){
                        //查询楼盘已经入驻的人数
                        NewHouseBroker newHouseBroker1=new NewHouseBroker();
                        newHouseBroker1.setNewHouseId(houseId);
                        newHouseBroker1.setStatus(0);
                        List<NewHouseBroker> list1 =newHouseBrokerService.selectAll(newHouseBroker1);
                        //查询合伙人已经入驻的楼盘数量

                        NewHouseBroker newHouseBroker2=new NewHouseBroker();
                        newHouseBroker2.setBrokerId(Integer.parseInt(userId));
                        newHouseBroker2.setStatus(0);
                        List<NewHouseBroker> list2 =newHouseBrokerService.selectAll(newHouseBroker2);
                        if(list1.size()<limitList.get(0).getLimitNewHouse()){
                            if(list2.size()<limitList.get(0).getLimitBroker()){
                                newHouseBrokerService.insert(newHouseBroker);
                                return DataRes.success("入驻成功");
                            }else{
                                return DataRes.error("1","入驻楼盘已达到上限");
                            }
                        }else{
                            return DataRes.error("1","入驻楼盘人数已满");
                        }
                    }else{
                        newHouseBrokerService.insert(newHouseBroker);
                        return DataRes.success("入驻成功");
                    }

                }
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 获取经纪人入驻的楼盘
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getBrokerNewHouse",method = RequestMethod.POST)
    public DataRes getBrokerNewHouse(HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        NewHouseBroker newHouseBroker=new NewHouseBroker();
        newHouseBroker.setBrokerId(Integer.parseInt(userId));
        newHouseBroker.setStatus(0);
        newHouseBroker.setOrderByString(" order by createtime desc");
        List<NewHouseBroker> list=newHouseBrokerService.selectAll(newHouseBroker);
        for(NewHouseBroker s:list){
            s.setMap(newHouseSearchService.queryDetail(s.getNewHouseId()));
        }
        return DataRes.success(list);
    }

    /**
     * 经纪人取消入驻楼盘
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/cancleNewHouseBroker",method = RequestMethod.POST)
    public DataRes cancleNewHouseBroker(Integer id,HttpServletRequest request){
        if(id!=null) {
            String userId = request.getHeader("token").split(SessionUser.FLAG)[1];
            NewHouseBroker newHouseBroker=new NewHouseBroker();
            newHouseBroker.setNewHouseId(id);
            newHouseBroker.setBrokerId(Integer.parseInt(userId));
            newHouseBroker.setStatus(0);
            List<NewHouseBroker> list=newHouseBrokerService.selectAll(newHouseBroker);
            if(list.size()>0){
                newHouseBroker.setStatus(1);
                newHouseBroker.setId(list.get(0).getId());
                newHouseBrokerService.update(newHouseBroker);
                return DataRes.success("取消成功");
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }
}
