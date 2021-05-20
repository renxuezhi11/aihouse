package com.aihouse.aihouseservice.newhouse.impl;

import com.aihouse.aihousedao.bean.NewHouseImg;
import com.aihouse.aihousedao.dao.newhouse.NewHouseDao;
import com.aihouse.aihousedao.dao.newhouse.NewHouseImgDao;
import com.aihouse.aihouseservice.newhouse.NewHouseImgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NewHouseImgServiceImpl implements NewHouseImgService {

    @Resource
    private NewHouseImgDao newHouseImgDao;

    @Override
    public NewHouseImgDao initDao() {
        return this.newHouseImgDao;
    }

    /**
     * 保存图片
     * @param newHouseImg
     * @param list
     * @return
     */
    @Override
    public int saveImg(NewHouseImg newHouseImg, List<String> list) {
        newHouseImgDao.deleteByNewHouseId(newHouseImg);
        if(list.size()>0){
            try{
                 newHouseImgDao.insertBatch(newHouseImg.getImgType(),newHouseImg.getNewHouseId(),list);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return 0;
    }

    /**
     * 获取楼盘的图片分组
     * @param newHouseId
     * @return
     */
    @Override
    public List<Map<String, Object>> queryImgType(Integer newHouseId) {
        return newHouseImgDao.queryImgType(newHouseId);
    }

    @Override
    public List<Map<String, Object>> queryImgByTypeAndHouseId(NewHouseImg newHouseImg) {
        return newHouseImgDao.queryImgByTypeAndHouseId(newHouseImg);
    }
}
