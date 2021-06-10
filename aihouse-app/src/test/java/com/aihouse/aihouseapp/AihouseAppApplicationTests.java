package com.aihouse.aihouseapp;

import com.aihouse.aihousecore.utils.HttpClient;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihousedao.bean.UserSpreadLog;
import com.aihouse.aihousedao.vo.UserSpreadLogVO;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import com.aihouse.aihouseservice.users.UserSpreadLogService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AihouseAppApplicationTests {

	@Resource
	private SecondHandHouseService secondHandHouseService;

	@Resource
	private SecondHouseSearchService secondHouseSearchService;

	@Value("${amap.key}")
	private String ak;

	@Resource
	private AreaService areaService;

	@Resource
	private UserSpreadLogService userSpreadLogService;

	private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";


	@Test
	public void contextLoads() {
		SecondHandHouse secondHandHouse =new SecondHandHouse();
		secondHandHouse.setFlag(1);
		secondHandHouse.setIsDel(0);
		List<SecondHandHouse> secondHandHouses = secondHandHouseService.selectAll(secondHandHouse);
		for(SecondHandHouse secondHandHouse1:secondHandHouses) {
			StringBuilder str = new StringBuilder();
			str.append(mapUrl).append("?").append("location=").append(secondHandHouse1.getLng()).append(",");
			str.append(secondHandHouse1.getLat()).append("&radius=1000&extensions=all&key=");
			str.append(ak);
			String result = HttpClient.doGet(str.toString());
			System.out.println(result);
			Map resil = JSON.parseObject(result, HashMap.class);
			Map map = (Map) ((Map) resil.get("regeocode")).get("addressComponent");
			Area area = new Area();

			secondHandHouse1.setCityid(441300);
			if(!ObjectUtils.isEmpty(map.get("district"))) {
				area.setAreaname(map.get("district").toString());
				area.setParentId(secondHandHouse1.getCityid());
				List<Area> list1 = areaService.queryByCondition(area);
				if (list1.size() > 0) {
					secondHandHouse1.setAreaid(list1.get(0).getId());
				}
				area.setAreaname(map.get("township").toString());
				area.setParentId(secondHandHouse1.getAreaid());
				list1 = areaService.queryByCondition(area);
				if (list1.size() > 0) {
					secondHandHouse1.setStreesid(list1.get(0).getId());
				}

			}else{
				secondHandHouse1.setCityid(441300);
			}
			secondHandHouseService.updateSecondHouse(secondHandHouse1);
			secondHouseSearchService.deleteSecondHouseIndex(secondHandHouse1.getId());
			secondHouseSearchService.addSecondHouseIndex(secondHandHouse1.getId());
		}
	}


	@Test
	public void  testSpreadLog(){
		List<UserSpreadLogVO> list = userSpreadLogService.getPersonSpreadLog("12");
		System.out.println(list);
	}
}
