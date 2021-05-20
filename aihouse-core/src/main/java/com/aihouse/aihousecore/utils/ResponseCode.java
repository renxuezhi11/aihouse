package com.aihouse.aihousecore.utils;

public enum ResponseCode {
    SUCCESS("0","成功"),
    FAIL("1","失败"),
    LOGIN_UNPASSWORD("10002","密码不正确"),
    NO_BANG_PHONE("10003","请绑定手机号"),
    DATA_ERROR("10004","数据格式错误"),

    PHONE_ERROR("10005", "手机号不正确"),

    SMS_SEND_ERROR("10006","验证码发送失败"),

    SMS_SEND_REPEAT("10007","验证码重复请求"),

    PHONE_VERIFICATION_CODE_EXPIRED("10008", "验证码已失效, 请重新获取!"),

    PHONE_VERIFICATION_CODE_ERROR("10009","验证码错误"),

    NO_ACCOUNT("10010","用户不存在或密码错误"),

    LOGIN_TOKEN("10011","TOKEN登录成功"),
    LOGIN_TOKEN_ERRROR("10012","TOKEN登录失败"),
    LOGIN_TOKEN_DEVICEID_ERRROR("10013","TOKEN和DEVICEID不符"),
    DELETE_IMG_SUCCESS("10014","图片删除成功"),
    LOGIN_TOKEN_EXPIRED("10015","TOKEN失效"),
    PUBLISH_NEW_HOUSE_COMMENT_ERROR("10016","点评失败"),

    USER_FOCUS_ERROR("10017","用户关注失败"),
    USER_FOCUS_REPEAT("10018","用户已关注"),
    USER_PHONE_REPEAT("10019","手机号已绑定"),

    USER_PASSWORD_ERROR("10020","旧密码错误"),
    HOUSE_ERROR("10021","房源已下架"),
    HOUSE_CHECK_ING("10022","房源审核中"),
    HOUSE_CHECK_ERROR("10022","房源审核失败"),
    HOUSE_GROUNDING_ERROR("10027","房源上架中"),
    USER_LOGIN_ERROR("10023","用户账号已禁用"),
    USER_APPOINTMENT_ERROR("10024","用户预约失败"),
    USER_EDIT_PHOTO_ERROR("10025","用户修改头像失败"),
    USER_EDIT_INFO_ERROR("10026","用户修改信息失败"),
    NEW_HOUSE_COMMENT_PRAISE_ERROR("10026","用户已点赞"),
    SPREAD_CODE_ERROR("10027","推广码不存在"),
    USER_SCREEN_REPART("10028","用户已屏蔽"),
    USER_CREEN_ERROR("10029","用户拉黑失败"),
    COMMUNITY_REPORT_ERROR("10030","举报已提交，无需重复提交"),
    UPDATE_ERROR("10031","已是最新版本"),
    NO_REGISTER_ERROR("10032","用户不存在"),
    ;

    private String code;
    private String desc;
    ResponseCode(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public String code(){
        return this.code;
    }

    public String desc(){
        return this.desc;
    }

}
