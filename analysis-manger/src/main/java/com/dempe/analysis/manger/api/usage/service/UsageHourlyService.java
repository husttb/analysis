package com.dempe.analysis.manger.api.usage.service;

import com.alibaba.fastjson.JSONArray;
import com.dempe.analysis.manger.api.usage.dao.UsageHourlyDao;
import com.dempe.analysis.manger.api.usage.model.UsageHourly;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/3.
 */
@Service
public class UsageHourlyService {

    @Resource
    private UsageHourlyDao usageHourlyDao;

    private final static String APPKEY = "ca2bbd6a539ae3a33c5f2832f8baa4ac";
    private final static String PLATFORM = "1";


    public Object[] getRunNumHourly(String create_date) {
        Map<String, Integer> usageHourlyMap = new HashMap<String, Integer>();
        List<UsageHourly> usageHourlies = usageHourlyDao.findByAppkeyAndPlatformAndCreateDate(APPKEY, PLATFORM, create_date);
        for (UsageHourly usageHourly : usageHourlies) {
            Integer count = usageHourlyMap.get(usageHourly.getCreate_hour());
            if (count == null) {
                count = 0;
            }
            usageHourlyMap.put(usageHourly.getCreate_hour(), count + usageHourly.getRunNum());
        }
        return usageHourlyMap.values().toArray();
    }


    public JSONArray getNewNumHourly(String create_date) {
        Map<String, Integer> usageHourlyMap = new HashMap<String, Integer>();
        List<UsageHourly> usageHourlies = usageHourlyDao.findByAppkeyAndPlatformAndCreateDate(APPKEY, PLATFORM, create_date);
        for (UsageHourly usageHourly : usageHourlies) {
            Integer count = usageHourlyMap.get(usageHourly.getCreate_hour());
            if (count == null) {
                count = 0;
            }
            usageHourlyMap.put(usageHourly.getCreate_hour(), count + usageHourly.getNewNum());
        }
        Iterator<String> keys = usageHourlyMap.keySet().iterator();

        JSONArray result = new JSONArray();
        while (keys.hasNext()) {
            String hourKey = keys.next();
            JSONArray array = new JSONArray();
            array.add(hourKey);
            array.add(usageHourlyMap.get(hourKey));
            result.add(array);
        }

        return result;
    }

    public Object[] getActiveHourly(String create_date) {
        Map<String, Integer> usageHourlyMap = new HashMap<String, Integer>();
        List<UsageHourly> usageHourlies = usageHourlyDao.findByAppkeyAndPlatformAndCreateDate(APPKEY, PLATFORM, create_date);
        for (UsageHourly usageHourly : usageHourlies) {
            Integer count = usageHourlyMap.get(usageHourly.getCreate_hour());
            if (count == null) {
                count = 0;
            }
            usageHourlyMap.put(usageHourly.getCreate_hour(), count + usageHourly.getActiveNum());
        }
        return usageHourlyMap.values().toArray();
    }


}
