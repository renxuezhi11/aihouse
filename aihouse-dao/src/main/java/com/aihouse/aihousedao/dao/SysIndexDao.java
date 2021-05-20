package com.aihouse.aihousedao.dao;

import java.util.List;
import java.util.Map;

public interface SysIndexDao {

    public Map<String,Object> getTongjiInfo();

    public List<Map<String,Object>> getNewUserOfWeek();

    public List<Map<String,Object>> getActiveUserOfWeek();
}
