package com.aihouse.aihousedao.dao.newhouse;

import com.aihouse.aihousedao.BaseDao;
import com.aihouse.aihousedao.bean.NewHouseImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NewHouseImgDao extends BaseDao<NewHouseImg>{

    public void deleteByNewHouseId(NewHouseImg newHouseImg);

    public int insertBatch(@Param("imgType")int imgType,@Param("newHouseId")int newHouseId, @Param("list")List<String> list);

    public List<Map<String,Object>> queryImgType(Integer newHouseId);

    public List<Map<String,Object>> queryImgByTypeAndHouseId(NewHouseImg newHouseImg);
}
