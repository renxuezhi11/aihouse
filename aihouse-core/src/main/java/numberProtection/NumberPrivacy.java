package numberProtection;

import com.aihouse.aihousecore.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 隐私保护工具类
 */
public class NumberPrivacy {
    private static final String accessKeyId = "LTAIfRpMHFSepgef";
    private static final String accessKeySecret = "uTbqD9GVU2Cz6ITiXr4BtPaVnbm16z";
    private static  final  String PoolKey="FC100000069032013";
    private static  final String domain="dyplsapi.aliyuncs.com";
    private static  final String version="2017-05-25";
    //绑定过期时间5分钟
    private static final int expiration=2;
    /**
     * 绑定a和b
     * @param phoneA
     * @param phoneB
     * @return
     */
    public static Map BindAxb(String phoneA, String phoneB){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("BindAxb");
        Date date=null;
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MINUTE,expiration);
        date=c1.getTime();
        String expiration= DateUtils.formatDateTime(date);
        request.putQueryParameter("PhoneNoA", phoneA);
        request.putQueryParameter("Expiration",expiration);
        request.putQueryParameter("PhoneNoB", phoneB);
        request.putQueryParameter("PoolKey", PoolKey);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map resil= JSON.parseObject(response.getData(),HashMap.class);
            return resil;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用接口QueryCallStatus查询呼叫状态。
     * @param subsId 绑定关系id
     */
    public static Map QueryCallStatus(String subsId){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("QueryCallStatus");
        request.putQueryParameter("SubsId", subsId);
        request.putQueryParameter("PoolKey", PoolKey);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map resil= JSON.parseObject(response.getData(),HashMap.class);
            return resil;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *解除绑定关系
     * @param subsId 关系id
     * @param SecretNo 隐私号码
     * @return
     */
    public static Map UnbindSubscription(String subsId,String SecretNo){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("UnbindSubscription");
        request.putQueryParameter("SubsId", subsId);
        request.putQueryParameter("SecretNo", SecretNo);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map resil= JSON.parseObject(response.getData(),HashMap.class);
            return resil;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用接口QuerySubscriptionDetail查询号码的绑定关系。
     * @param subsId 关系id
     * @param phoneNoX 隐私号
     * @return
     */

    public static Map QuerySubscriptionDetail(String subsId,String phoneNoX){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("QuerySubscriptionDetail");
        request.putQueryParameter("SubsId", subsId);
        request.putQueryParameter("PhoneNoX", phoneNoX);
        request.putQueryParameter("PoolKey", PoolKey);
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map resil= JSON.parseObject(response.getData(),HashMap.class);
            return resil;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用接口UpdateSubscription修改绑定关系。
     * @param subsId
     * @param phoneNoX
     * @param OperateType
     * @param content
     */
    public static Map UpdateSubscription(String subsId,String phoneNoX,String OperateType,String content){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction("UpdateSubscription");
        request.putQueryParameter("SubsId", "1000002355533251");
        request.putQueryParameter("PhoneNoX", "17168895718");
        request.putQueryParameter("OperateType", OperateType);
        request.putQueryParameter("PoolKey", "1");
        if(OperateType.equals("updateNoA")) {
            request.putQueryParameter("PhoneNoA", content);
        }else if(OperateType.equals("updateNoB")) {
            request.putQueryParameter("PhoneNoB", content);
        }else if(OperateType.equals("updateExpire")) {
            request.putQueryParameter("Expiration", content);
        }
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map resil= JSON.parseObject(response.getData(),HashMap.class);
            return resil;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
