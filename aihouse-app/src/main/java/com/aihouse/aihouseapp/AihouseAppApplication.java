package com.aihouse.aihouseapp;

import IM.IMUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

@SpringBootApplication
@EnableAsync
@ComponentScan("com.aihouse.*")
@MapperScan("com.*.*.dao")
@EnableScheduling
@EnableCaching
public class AihouseAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AihouseAppApplication.class, args);
	}

}
