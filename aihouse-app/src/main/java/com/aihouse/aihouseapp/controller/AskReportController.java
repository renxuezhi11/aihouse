package com.aihouse.aihouseapp.controller;

import com.aihouse.aihouseapp.utils.SessionUser;
import com.aihouse.aihousecore.utils.DataRes;
import com.aihouse.aihousecore.utils.ResponseCode;
import com.aihouse.aihousedao.bean.AskReport;
import com.aihouse.aihousedao.bean.CommunityReport;
import com.aihouse.aihouseservice.AskReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 问答举报controller
 */
@CrossOrigin
@RestController
public class AskReportController {

    @Resource
    private AskReportService askReportService;

    /**
     * 举报问答
     * @return
     */
    @RequestMapping(value = "app/ask/reportAsk",method = RequestMethod.POST)
    public DataRes reportAsk(HttpServletRequest request, Integer askId, Integer type, String reason){
        if(askId!=null&&type!=null&&reason!=null){
            if(type==0||type==1||type==2){
                String userId=request.getHeader("token").split(SessionUser.FLAG)[1];
                AskReport askReport=new AskReport();
                askReport.setAskId(askId);
                askReport.setType(type);
                askReport.setReason(reason);
                askReport.setUserId(Integer.parseInt(userId));
                List<AskReport> list=askReportService.selectAll(askReport);
                if(list!=null&&list.size()>0){//已举报
                    return DataRes.error(ResponseCode.COMMUNITY_REPORT_ERROR);
                }else{
                    askReportService.insert(askReport);
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
