package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.RedisUtil;
import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.RedisConstants;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Appointment;
import com.aihouse.aihouseservice.AppointmentService;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预约看房controller
 */
@CrossOrigin
@RestController
public class AppointmentController {

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private NewHouseSearchService newHouseSearchService;


    /**
     * 预约看房
     * @param appointment
     * @param request
     * @return
     */
    @RequestMapping(value = "app/publishAppointment",method = RequestMethod.POST)
    public DataRes publishAppointment(Appointment appointment, HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        SessionUser sessionUser = (SessionUser) redisUtil.get(RedisConstants.USER_TOKEN + userId);
        appointment.setUserId(Integer.parseInt(userId));
        Integer houseId=appointment.getHouseId();
        //appointment.setHouseId(null);
        List<Appointment> list=appointmentService.selectAll(appointment);
        int time=Integer.parseInt(appointment.getAppointmentTime().split("-")[0]);
        for(Appointment appointment1:list){
            if(appointment1.getStatus()!=1) {
                String[] str = appointment1.getAppointmentTime().split("-");
                if (Integer.parseInt(str[0]) <= time && Integer.parseInt(str[1]) >= time) {
                    return DataRes.error(ResponseCode.USER_APPOINTMENT_ERROR);
                }
            }
        }
        appointment.setUserTelephone(sessionUser.getUsers().getTelephone());
        appointment.setHouseId(houseId);
        int i=appointmentService.insert(appointment);
        if(i>0){
            return DataRes.success(true);
        }else{
            return DataRes.success(false);
        }
    }


    /**
     * 获取用户预约记录
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getUserAppointments",method = RequestMethod.POST)
    public DataRes getUserAppointments(Appointment appointment,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        appointment.setOrderByString(" order by createtime desc");
        appointment.setUserId(Integer.parseInt(userId));
        appointment=appointmentService.selectAllByPaging(appointment);
        for(Object s:appointment.getRows()){
            s=(Appointment)s;
            ((Appointment) s).setMap(newHouseSearchService.queryDetail(((Appointment) s).getHouseId()));
        }
        return DataRes.success(appointment);
    }

    /**
     * 获取经纪人的预约记录
     * @param request
     * @return
     */
    @RequestMapping(value = "app/getBrokerAppointments",method = RequestMethod.POST)
    public DataRes getBrokerAppointments(Appointment appointment,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        appointment.setOrderByString(" order by createtime desc");
        appointment.setBorkerId(Integer.parseInt(userId));
        appointment=appointmentService.selectAllByPaging(appointment);
        for(Object s:appointment.getRows()){
            s=(Appointment)s;
            ((Appointment) s).setMap(newHouseSearchService.queryDetail(((Appointment) s).getHouseId()));
        }
        return DataRes.success(appointment);
    }
    /**
     * 用户取消预约
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/userCancleAppointments",method = RequestMethod.POST)
    public DataRes userCancleAppointments(Integer id,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Appointment appointment=new Appointment();
        appointment.setId(id);
        appointment.setUserId(Integer.parseInt(userId));
        appointment.setStatus(1);
        Appointment appointment1=appointmentService.selectByPrimaryKey(appointment);
        if(appointment.getUserId()==appointment1.getUserId()){
            return DataRes.success(appointmentService.update(appointment));
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }

    /**
     * 经纪人标记已看房预约
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "app/brokerStatusAppointments",method = RequestMethod.POST)
    public DataRes brokerStatusAppointments(Integer id,HttpServletRequest request){
        String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
        Appointment appointment=new Appointment();
        appointment.setId(id);
        appointment.setStatus(3);
        appointment.setBorkerId(Integer.parseInt(userId));
        Appointment appointment1=appointmentService.selectByPrimaryKey(appointment);
        if(appointment.getBorkerId()==appointment1.getBorkerId()){
            return DataRes.success(appointmentService.update(appointment));
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


}
