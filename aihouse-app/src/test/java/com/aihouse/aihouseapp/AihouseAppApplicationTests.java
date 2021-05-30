package com.aihouse.aihouseapp;

import com.aihouse.aihouseservice.secondHandHouse.SecondHouseSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AihouseAppApplicationTests {

	@Resource
	private SecondHouseSearchService secondHouseSearchService;
	@Test
	public void contextLoads() {
		Map<String,Object> map= secondHouseSearchService.queryDetail(27);
		System.out.println(map);
	}

}
