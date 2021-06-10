package com.aihouse.aihousedao.dao.users;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.vo.UserSpreadLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserSpreadLogDao extends BaseDao<UserSpreadLog> {

   public Map<String,Object> selectCnt(UserSpreadLog userSpreadLog);

    List<UserSpreadLogVO> getPersonSpreadLog(@Param("userId") String userId);
}
