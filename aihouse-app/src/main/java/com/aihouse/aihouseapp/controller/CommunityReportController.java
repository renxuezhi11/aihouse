package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.CommunityReport;
import com.aihouse.aihouseservice.CommunityReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class CommunityReportController {

    @Resource
    private CommunityReportService communityReportService;

    /**
     * 举报圈子
     * @return
     */
    @RequestMapping(value = "app/housecircle/reportCommunity",method = RequestMethod.POST)
    public DataRes  reportCommunity(HttpServletRequest request,Integer communityId,Integer type,String reason){
        if(communityId!=null&&type!=null&&reason!=null){
            if(type==0||type==1){
                String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
                CommunityReport communityReport=new CommunityReport();
                communityReport.setCommunityId(communityId);
                communityReport.setType(type);
                communityReport.setReason(reason);
                communityReport.setUserId(Integer.parseInt(userId));
                List<CommunityReport> list=communityReportService.selectAll(communityReport);
                if(list!=null&&list.size()>0){//已举报
                    return DataRes.error(ResponseCode.COMMUNITY_REPORT_ERROR);
                }else{
                    communityReportService.insert(communityReport);
                    return DataRes.success("举报提交成功");
                }
            }else{
                return DataRes.error(ResponseCode.DATA_ERROR);
            }
        }else{
            return DataRes.error(ResponseCode.DATA_ERROR);
        }
    }


}
