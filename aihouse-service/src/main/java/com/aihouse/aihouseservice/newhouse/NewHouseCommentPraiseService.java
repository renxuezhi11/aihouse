package com.aihouse.aihouseservice.newhouse;

import com.aihouse.aihousedao.bean.NewHouseCommentPraise;
import com.aihouse.aihousedao.dao.newhouse.NewHouseCommentPraiseDao;
import com.aihouse.aihouseservice.BaseService;

public interface NewHouseCommentPraiseService extends BaseService<NewHouseCommentPraise, NewHouseCommentPraiseDao> {
    public void deleteNewHouseCommentPraise(NewHouseCommentPraise newHouseCommentPraise);
}
