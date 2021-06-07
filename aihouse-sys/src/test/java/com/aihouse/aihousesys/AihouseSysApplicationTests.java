package com.aihouse.aihousesys;

import com.aihouse.aihousecore.utils.HttpClient;
import com.aihouse.aihousedao.bean.Area;
import com.aihouse.aihousedao.bean.SecondHandHouse;
import com.aihouse.aihouseservice.AreaService;
import com.aihouse.aihouseservice.secondHandHouse.SecondHandHouseService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AihouseSysApplicationTests {

	@Autowired
	private SecondHandHouseService secondHandHouseService;

	@Value("${amap.key}")
	private String ak;

	private AreaService areaService;

	private static final String mapUrl="https://restapi.amap.com/v3/geocode/regeo";

	@Test
	public void contextLoads() {
		SecondHandHouse secondHandHouse =new SecondHandHouse();
		secondHandHouse.setFlag(1);
		secondHandHouse.setIsDel(0);
		List<SecondHandHouse> list= secondHandHouseService.selectAll(secondHandHouse);
		for(SecondHandHouse secondHandHouse1:list){
			StringBuilder str=new StringBuilder();
			str.append(mapUrl).append("?").append("location=").append(secondHandHouse1.getLng()).append(",");
			str.append(secondHandHouse1.getLat()).append("&radius=1000&extensions=all&key=");
			str.append(ak);
			String result= HttpClient.doGet(str.toString());
			System.out.println(result);
			Map resil= JSON.parseObject(result, HashMap.class);
			Map map=(Map)((Map) resil.get("regeocode")).get("addressComponent");
			Area area=new Area();
			area.setAreaname(map.get("township").toString());
			area.setPosition("%"+secondHandHouse.getCityid()+"%");
			List<Area> list1=areaService.queryByCondition(area);
			if(list1.size()>0){
				secondHandHouse.setStreesid(list1.get(0).getId());
				secondHandHouse.setAreaid(list1.get(0).getParentId());
			}
		}
	}

}
