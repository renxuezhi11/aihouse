package sms;

/**
 * 发送短信常用的key常量定义，固定key或者key前缀等。（每个可以必须标明注释、且绝对不可重复）
 *
 * @Filename: SmsConstant.java
 * @Version: 1.0
 * @Author: 钟威
 * @Email: 372909111@qq.com
 *
 */
public class SmsConstant {

    /** 固定签名 */
    public static final String SIGN_NAME = "房小麦";

//    public static final String SIGN_NAME = "爱好室";

    /** 短信模板---用户注册验证码 */
    public static final String USER_REGISTER = "SMS_216745737";

    /** 短信模板---用户身份验证码 */
    public static final String USER_CONFIRM = "SMS_216745740";

    /** 短信模板---登录确认验证码 */
    public static final String LOGIN_CONFIRM = "SMS_216745739";

    /** 短信模板---登录异常验证码 */
    public static final String LOGIN_DIFFERENT = "SMS_216745738";

    /** 短信模板---修改密码验证码 */
    public static final String UPDATE_PASSWORD = "SMS_216745736";

    /** 短信模板---信息变更验证码 */
    public static final String UPDATE_USER = "SMS_216745735";

    /** 短信模板---绑定手机验证码 */
    public static final String BIND_PHONE = "SMS_165677664";

    /** 短信模板---预约成功通知短信 */
    public static final String BOOK_SUCCESS = "SMS_165677679";

    /** 短信模板---降价通知通知短信 */
    public static final String LOWER_NOTICE = "SMS_165692418";

    /** 短信模板--实名认证未通过提醒**/
    public static final String USER_CERTIFICA_NO="SMS_170555484";

    /** 短信模板==实名认证通过提醒**/
    public static final String USER_CERTIFICA_OK="SMS_170555482";

    /** 短信模板---用户禁用提醒**/
    public static final String USER_FORBIDDEN="SMS_170555499";

    /**短信模板--事业合伙人提醒**/
    public static final String USER_PARTNER="SMS_170555493";

    /**房源下架提醒**/
    public static  final String HOUSE_UNDER="SMS_170560469";

    /**房源审核未通过**/
    public static final String HOUSE_NO_PASS="SMS_170560462";

    public static final String HOUSE_SUCCESS="SMS_170600106";

    /** 常用变量Code----${code}*/
    public static final String CODE = "{\"code\":";

    /** 常用变量House----${house}----楼盘名*/
    public static final String HOUSE = "{\"house\":";

    /** 常用变量House----${time}----期间*/
    public static final String TIME = "\"time\":";

    /** 常用变量House----${name}----姓名*/
    public static final String NAME = "\"name\":";

    /** 常用变量House----${phone}----手机号*/
    public static final String PHONE = "\"phone\":";

    public static final String REASON="\"reason\":";

}
