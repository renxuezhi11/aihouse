package com.aihouse.aihousesys.controller;

import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ExcelUtils;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.Appointment;
import com.aihouse.aihousedao.bean.NewHouse;
import com.aihouse.aihousedao.bean.Users;
import com.aihouse.aihouseservice.AppointmentService;
import com.aihouse.aihouseservice.newhouse.NewHouseSearchService;
import com.aihouse.aihouseservice.newhouse.NewHouseService;
import com.aihouse.aihouseservice.users.UsersService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sms.SmsConstant;
import sms.SmsDemo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统预约管理
 */
@Controller
public class SysAppointmentController {

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private NewHouseSearchService newHouseSearchService;

    @Resource
    private UsersService usersService;

    /**
     * 跳转到预约页面
     * @return
     */
    @RequestMapping("appointment/gotoList")
    @RequiresPermissions("appointment/gotoList")
    public String gotoList(){
        return "sys/sys_appointment_list";
    }

    /**
     * 分页查询预约
     * @param appointment
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("appointment/selectAll")
    @ResponseBody
    public DataRes selectAll(Appointment appointment, HttpServletRequest request, HttpServletResponse response){
        appointment.setOrderByString(" order by appointment_date desc ");
        if(appointment.getUsername()!=null &&!appointment.getUsername().equals("")){
            Users users=new Users();
            users.setUsername(appointment.getUsername());
            List<Users> list=usersService.selectAll(users);
            if(list.size()>0){
                appointment.setUserId(list.get(0).getId());
            }
        }
        if(appointment.getBrokername()!=null&&!appointment.getBrokername().equals("")){
            Users users=new Users();
            users.setUsername(appointment.getBrokername());
            List<Users> list=usersService.selectAll(users);
            if(list.size()>0){
                appointment.setBorkerId(list.get(0).getId());
            }
        }
        return DataRes.success(appointmentService.selectAllByPaging(appointment));
    }

    /**
     * 导出
     * @param appointment
     * @param response
     * @param request
     */
    @RequestMapping("appointment/export")
    public void export(Appointment appointment,HttpServletResponse response,HttpServletRequest request){
        List<Appointment> list=appointmentService.selectAll(appointment);
        Map<String, String> header = new LinkedHashMap<>();
        header.put("id","预约id");
        header.put("userId","用户id");
        header.put("houseType_","预约类型");
        header.put("houseId","房源id");
        header.put("appointmentDate_","预约提交时间");
        header.put("appointmentTime","预约时间段");
        header.put("borkerId","经纪人id");
        header.put("status_","预约状态");
        header.put("userTelephone","手机号");
        header.put("name","房源名称");
        ExcelUtils.exportExcel("预约列表",header,list,response,request);
    }

    @RequestMapping("appointment/gotoDetail")
    @RequiresPermissions("appointment/save")
    public String gotoDetail(Appointment appointment,HttpServletRequest request,HttpServletResponse response){
        if(appointment.getId()!=null){
            appointment=appointmentService.selectByPrimaryKey(appointment);
            Map newHouse=newHouseSearchService.queryDetail(appointment.getHouseId());
            if(newHouse!=null) {
                appointment.setMap(newHouse);
            }else{
                newHouse=new HashMap();
                newHouse.put("name","房源已下架");
                appointment.setMap(newHouse);
            }
            request.setAttribute("appoint",appointment);
        }else{
            request.setAttribute("appoint",appointment);
        }
        return "sys/sys_appointment_detail";
    }

    /**
     * 编辑保存
     * @param appointment
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("appointment/save")
    @ResponseBody
    public DataRes save(Appointment appointment,HttpServletResponse response,HttpServletRequest request){
        Appointment appointment1=appointmentService.selectByPrimaryKey(appointment);
        if(appointment1.getBorkerId()!=appointment.getBorkerId() &&appointment.getBorkerId()!=null&&appointment.getStatus()==2){
            //通知用户预约成功
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("phoneName", appointment1.getUserTelephone());
            hashMap.put("SignName", SmsConstant.SIGN_NAME);
            hashMap.put("TemplateCode", SmsConstant.BOOK_SUCCESS);
            String time=appointment1.getAppointmentDate()+" "+appointment1.getAppointmentTime()+"时";
            Users users=new Users();
            users.setId(appointment.getBorkerId());
            users=usersService.selectByPrimaryKey(users);
            String telephone=users.getTelephone();
            hashMap.put("TemplateParam", SmsConstant.HOUSE + "\"" + appointment1.getName()+"\","+SmsConstant.TIME + "\"" +time+"\","+SmsConstant.NAME+ "\""+appointment.getBrokername()+ "\","+SmsConstant.PHONE+"\""+telephone+"\"}");
            try {
                SendSmsResponse res = SmsDemo.sendSms(hashMap);
                System.out.println(res);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return DataRes.success(appointmentService.update(appointment));
    }

    /**
     * 房源带看人列表
     * @param id
     * @return
     */
    @RequestMapping("appointment/gotoHouseBrokerList")
    public String gotoHouseBrokerList(Integer id,HttpServletRequest request){
        request.setAttribute("id",id);
        return "sys/housebroker_list";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("appointment/selectAllBroker")
    @ResponseBody
    public DataRes selectAllBroker(Integer id){
        return DataRes.success(newHouseSearchService.getNewHouseAllBroker(id));
    }
}
