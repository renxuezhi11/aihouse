package com.aihouse.aihousedao.dao.newhouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.NewHouseComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 楼盘点评dao
 */
public interface NewHouseCommentDao extends BaseDao<NewHouseComment>{

    public List<Map<String,Object>> selectAllComment(@Param("houseId") Integer houseId, @Param("userId") Integer userId);

    public void updateNewHouseCommentCnt(Integer houseId);
}
