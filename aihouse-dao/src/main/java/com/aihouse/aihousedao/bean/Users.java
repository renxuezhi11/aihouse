package com.aihouse.aihousedao.bean;

import java.util.List;

import com.aihouse.aihousecore.utils.DateUtils;
import com.aihouse.aihousedao.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 *用户表 bean
 */
public class Users   extends Page {

	/**
	 * 性别：男
	 * **/
	private static  final  Integer SEX_MAN = 1;
	/**
	 * 性别：女
	 * **/
	private static  final  Integer SEX_WOMAN = 2;

	/***
	 * 是否实名认证:未认证
	 * **/
	public static  final  Integer ISCERTIFICATION_NO = 0;
	/***
	 * 是否实名认证:已认证
	 * **/
	public static  final  Integer ISCERTIFICATION_YES = 2;

	/**
	 * 认证中
	 */
	public static  final Integer ISCERTIFICATION_ING=1;

	/**
	 * 认证失败
	 */
	public static  final Integer ISCERTIFICATION_FAIL=3;

	//角色（1业主，2租客，3购房者4.职业房东5，置业顾问6，合伙人）
	/**
	 * 角色（业主)
	 * ***/
	private static final Integer ROLE_OWNER = 1;
	/**
	 * 角色（租客)
	 * ***/
	private static final Integer ROLE_TENANT = 2;
	/**
	 * 角色（购房者)
	 * ***/
	private static final Integer ROLE_SHOPER = 3;
	/**
	 * 角色（职业房东)
	 * ***/
	private static final Integer ROLE_LANDLORD= 4;
	/**
	 * 角色（置业顾问)
	 * ***/
	private static final Integer ROLE_PROPERTY = 5;
	/**
	 * 角色（合伙人)
	 * ***/
	private static final Integer ROLE_PARTNER = 6;
	/**
	 * 用户状态(正常)
	 * ***/
	private static final Integer STATUS_NORMAL = 0;
	/**
	 * 用户状态(禁用)
	 * ***/
	private static final Integer STATUS_BANNER = 1;


	/**
	 * 用户搜索记录
	 */
	private List<UserSearch> userSearchList;


	/**
	 * 用户浏览记录表
	 */
	private List<UserHistory> userHistoryList;


	/**
	 * 二手房图片
	 */
	private List<UserFocus> userFocusList;


	/**
	 * 二手房图片
	 */
	private List<UserCollect> userCollectList;


	/**
	 * 用户认证信息
	 */
	private UserCertification userCertification;



	/**
	 * id
	 */
	private Integer id;
	/**
	 * 手机号
	 */
	private String telephone;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 真实姓名
	 */
	private String truename;

	private String username;
	/**
	 * 性别(1男，2女)
	 */
	private Integer sex;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 头像
	 */
	private String userphoto;
	/**
	 userphoto	 * 绑定邮箱
	 */
	private String email;
	/**
	 * 绑定微信账号
	 */
	private String wxaccount;
	/**
	 * 绑定支付宝账号
	 */
	private String aliaccount;
	/**
	 * 是否实名认证0未认证1认证
	 */
	private Integer isCertification;
	/**
	 * 及时通讯账号
	 */
	private String imAccount;
	/**
	 * 及时通讯账号密码
	 */
	private String imPassword;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 角色（1业主，2租客，3购房者4.职业房东5，置业顾问6，合伙人）
	 */
	private Integer role;
	/**
	 * 注册时间
	 */
	private Date registerTime;
	/**
	 * 用户状态0正常1禁用
	 */
	private Integer status;

	private String signname;

	private String cityname;

	private String areaname;

	//推广权限
	private Integer isSpread;

	//推广码
	private String spreadCode;

	//推广人
	private Integer parentId;


	public Integer getIsSpread() {
		return isSpread;
	}

	public void setIsSpread(Integer isSpread) {
		this.isSpread = isSpread;
	}

	public String getIsSpread_(){
		if(isSpread!=null){
			if(isSpread==0){
				return "无";
			}else {
				return "有";
			}
		}
		return "";
	}
	public String getSpreadCode() {
		return spreadCode;
	}

	public void setSpreadCode(String spreadCode) {
		this.spreadCode = spreadCode;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getSignname() {
		return signname;
	}

	public void setSignname(String signname) {
		this.signname = signname;
	}

	private String registerTime_;

	private String  status_;

	private String sex_;

	private String isCertification_;

	private String role_;

	private String isSpread_;

	public Integer getId(){
		return id;
	}


	public void setId(Integer id){
		this.id=id;
	}


	public String getTelephone(){
		return telephone;
	}


	public void setTelephone(String telephone){
		this.telephone=telephone;
	}


	public String getNickname(){
		return nickname;
	}


	public void setNickname(String nickname){
		this.nickname=nickname;
	}

	public String getTruename(){
		return truename;
	}


	public void setTruename(String truename){
		this.truename=truename;
	}


	public Integer getSex(){
		return sex;
	}


	public void setSex(Integer sex){
		this.sex=sex;
	}


	public String getPassword(){
		return password;
	}


	public void setPassword(String password){
		this.password=password;
	}


	public String getUserphoto(){
		return userphoto;
	}


	public void setUserphoto(String userphoto){
		this.userphoto=userphoto;
	}


	public String getEmail(){
		return email;
	}


	public void setEmail(String email){
		this.email=email;
	}


	public String getWxaccount(){
		return wxaccount;
	}


	public void setWxaccount(String wxaccount){
		this.wxaccount=wxaccount;
	}


	public String getAliaccount(){
		return aliaccount;
	}


	public void setAliaccount(String aliaccount){
		this.aliaccount=aliaccount;
	}


	public Integer getIsCertification(){
		return isCertification;
	}


	public void setIsCertification(Integer isCertification){
		this.isCertification=isCertification;
	}


	public String getImAccount(){
		return imAccount;
	}


	public void setImAccount(String imAccount){
		this.imAccount=imAccount;
	}


	public String getImPassword(){
		return imPassword;
	}


	public void setImPassword(String imPassword){
		this.imPassword=imPassword;
	}


	public String getIdCard(){
		return idCard;
	}


	public void setIdCard(String idCard){
		this.idCard=idCard;
	}


	public Integer getRole(){
		return role;
	}


	public void setRole(Integer role){
		this.role=role;
	}


	public String getregisterTime_(){
		return DateUtils.formatDateTime(registerTime);
	}


	@JsonIgnore
	public Date getRegisterTime(){
		return registerTime;
	}


	public void setRegisterTime(Date registerTime){
		this.registerTime=registerTime;
	}


	public Integer getStatus(){
		return status;
	}


	public void setStatus(Integer status){
		this.status=status;
	}


	/**
	 * 用户认证信息
	 */
	public UserCertification getUserCertification(){
		return userCertification;
	}


	/**
	 * 用户认证信息
	 */
	public void setUserCertification(UserCertification userCertification){
		this.userCertification = userCertification;
	}



	/**
	 * 获取用户收藏列表
	 */
	public List<UserCollect> getUserCollectList(){
		return userCollectList;
	}


	/**
	 * 设置用户收藏列表
	 */
	public void setUserCollectList(List<UserCollect> userCollectList){
		this.userCollectList = userCollectList;
	}



	/**
	 * 获取用户关注
	 */
	public List<UserFocus> getUserFocusList(){
		return userFocusList;
	}


	/**
	 * 设置用户关注
	 */
	public void setUserFocusList(List<UserFocus> userFocusList){
		this.userFocusList = userFocusList;
	}



	/**
	 * 获取用户浏览记录
	 */
	public List<UserHistory> getUserHistoryList(){
		return userHistoryList;
	}


	/**
	 * 设置用户浏览记录
	 */
	public void setUserHistoryList(List<UserHistory> userHistoryList){
		this.userHistoryList = userHistoryList;
	}



	/**
	 * 获取用户搜索记录
	 */
	public List<UserSearch> getUserSearchList(){
		return userSearchList;
	}


	/**
	 * 设置用户搜索记录
	 */
	public void setUserSearchList(List<UserSearch> userSearchList){
		this.userSearchList = userSearchList;
	}


	public String getsex_(){
		if(StringUtils.isEmpty(sex)){
			return "";
		}else if(sex.equals(SEX_MAN)){
			return "男";
		}else if(sex.equals(SEX_WOMAN)){
			return "女";
		}
		return "";
	}

	public String getisCertification_(){
		if(StringUtils.isEmpty(isCertification)){
			return "";
		}else if(isCertification.equals(ISCERTIFICATION_NO)){
			return "未认证";
		}else if(isCertification.equals(ISCERTIFICATION_YES)){
			return "已认证";
		}else if(isCertification.equals(ISCERTIFICATION_ING)){
			return "认证中";
		}else if(isCertification.equals(ISCERTIFICATION_FAIL)){
			return "认证失败";
		}
		return "";
	}

	public String getrole_(){
		if(StringUtils.isEmpty(role)){
			return "";
		}else if(role.equals(ROLE_OWNER)){
			return "业主";
		}else if(role.equals(ROLE_TENANT)){
			return "租客";
		}else if(role.equals(ROLE_SHOPER)){
			return "购房者";
		}else if(role.equals(ROLE_LANDLORD)){
			return "职业房东";
		}else if(role.equals(ROLE_PROPERTY)){
			return "置业顾问";
		}else if(role.equals(ROLE_PARTNER)){
			return "合伙人";
		}
		return "";
	}

	public String getstatus_(){
		if(StringUtils.isEmpty(status)){
			return "";
		}else if(status.equals(STATUS_NORMAL)){
			return "正常";
		}else if(status.equals(STATUS_BANNER)){
			return "禁用";
		}
		return "";
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
