package com.aloogn.webapp.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.rmi.runtime.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class ConvertUtil {

    /**
     * 字符转时间
     * @param dateStr
     * @param formatStrs 格式
     * @return
     */
    public static Date getDateByStr(String dateStr, String... formatStrs) {
        SimpleDateFormat sDateFormat=new SimpleDateFormat(formatStrs.length > 0?formatStrs[0]:"yyyy-MM-dd HH:mm:ss");
        try {
            return sDateFormat.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 	字符转map
     */
    public static TreeMap<String,Object> getTreeMapByJsonstr(String jsonStr){
        TreeMap<String,Object> treeMap = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            String strMap = objectMapper.writeValueAsString(rootNode);
            treeMap = objectMapper.readValue(strMap, TreeMap.class);
        }catch (Exception e){

        }
        return treeMap;
    }

    //字符转List
    public static List getListByJsonstr(String jsonStr){
        List list = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonStr);
            String logJson = objectMapper.writeValueAsString(rootNode);
            JavaType logType = objectMapper.getTypeFactory().constructParametricType(List.class, Log.class);
            list = objectMapper.readValue(logJson, logType);
        }catch (Exception e){

        }
        return list;
    }
}
