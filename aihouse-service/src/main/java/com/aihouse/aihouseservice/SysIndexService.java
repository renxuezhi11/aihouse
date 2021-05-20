package com.aihouse.aihouseservice;

import java.util.List;
import java.util.Map;

public interface SysIndexService {
    public Map<String,Object> getTongjiInfo();

    public List<Map<String,Object>> getNewUserOfWeek();

    public List<Map<String,Object>> getActiveUserOfWeek();
}
