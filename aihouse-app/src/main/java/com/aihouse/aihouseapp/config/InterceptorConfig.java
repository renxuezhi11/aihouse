package com.aihouse.aihouseapp.config;

import com.aihouse.aihouseapp.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/**")

                .excludePathPatterns( "/app/getNewHouseDetail/**","/app/getNewHouseImg/**","/app/login","/app/sendCode","/app/sendChangePhoneCode","/app/thirdPartyLogin","/app/bangPhone","/image/**","/app/uploadImg","/app/checkUpdate"
                ,"/app/getRecommendSecondHouse","/app/searchSecondHouse","/app/secondhouse/getKeywordComplet","/app/getSecondHouseHotSearch","/app/getSecondHousedetail/**"
                ,"/app/getSecondHouseImg/**","/app/getMarket","/app/getRecommendNewHouse","/app/newhouse/getKeywordComplet","/app/searchNewHouse","/app/getNewHouseHotSearch"
               ,"/app/getRecommendRentHouse","/app/renthouse/getKeywordComplet","/app/searchRentHouse","/app/getRentHouseHotSearch"
                ,"/app/getRentHouseDetail/**","/app/getRentHouseImg/**","/app/newhouse/conditionList","/app/secondhandhouse/conditionList","/app/jointrent/conditionList","/app/officepremises/conditionList"
                ,"/app/store/conditionList","/app/searchOfficeHouse","/app/getOfficeHouseHotSearch","/app/officehouse/getKeywordComplet","/app/getOfficeHouseDetail/**","/app/getOfficeHouseImg/**"
                ,"/app/searchShopHouse","/app/shophouse/getKeywordComplet","/app/getShopHouseHotSearch","/app/getShopHouseDetail/**","/app/getShopHouseImg/**","/app/getAllBroker","/app/searchVillage","/app/getAllSearchHistory"
                ,"/app/getAllKeywordComplet","/app/getNewHouseAllBroker/**","/app/getNewHouseTypeDetail/**","/app/getNewHouseMoreDetail/**","/app/getSettingInfo");//排除请求

        super.addInterceptors(registry);
    }
}
