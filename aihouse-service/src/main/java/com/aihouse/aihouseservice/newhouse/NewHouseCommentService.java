package com.aihouse.aihouseservice.newhouse;

import com.aihouse.aihousedao.bean.NewHouseComment;
import com.aihouse.aihousedao.dao.newhouse.NewHouseCommentDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface NewHouseCommentService extends BaseService<NewHouseComment,NewHouseCommentDao> {
    List<Map<String,Object>> selectAllComment(Integer houseId,Integer userId, Integer page, Integer pageSize);

    public void updateNewHouseCommentCnt(Integer houseId);

    public int insertComment(NewHouseComment newHouseComment);
}
