package com.aihouse.aihouseservice.newhouse;

import com.aihouse.aihousedao.bean.NewHouseImg;
import com.aihouse.aihousedao.dao.newhouse.NewHouseImgDao;
import com.aihouse.aihouseservice.BaseService;

import java.util.List;
import java.util.Map;

public interface NewHouseImgService extends BaseService<NewHouseImg,NewHouseImgDao>{

    public int saveImg(NewHouseImg newHouseImg,List<String> list);

    public List<Map<String,Object>> queryImgType(Integer newHouseId);

    public List<Map<String,Object>> queryImgByTypeAndHouseId(NewHouseImg newHouseImg);
}
