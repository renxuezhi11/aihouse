package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.bean.NewHouseComment;
import com.aihouse.aihousedao.dao.newhouse.NewHouseCommentDao;
import com.aihouse.aihouseservice.newhouse.NewHouseCommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 楼盘点评serice
 */
@Service
public class NewHouseCommentServiceImpl implements NewHouseCommentService {

    @Resource
    private NewHouseCommentDao newHouseCommentDao;

    @Override
    public NewHouseCommentDao initDao() {
        return this.newHouseCommentDao;
    }

    @Override
    public List<Map<String, Object>> selectAllComment(Integer houseId,Integer userId, Integer page, Integer pageSize) {
        try {
            PageHelper.startPage(page, pageSize);
            List<Map<String, Object>> lists = newHouseCommentDao.selectAllComment(houseId,userId);
            PageInfo pageInfo = new PageInfo(lists);
            return  lists;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void updateNewHouseCommentCnt(Integer houseId) {
        newHouseCommentDao.updateNewHouseCommentCnt(houseId);
    }

    @Override
    public int insertComment(NewHouseComment newHouseComment) {
        return this.insert(newHouseComment);
    }
}
