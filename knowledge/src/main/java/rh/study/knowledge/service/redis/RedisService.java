package rh.study.knowledge.service.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import rh.study.knowledge.common.result.Column;
import rh.study.knowledge.common.result.PageResult;
import rh.study.knowledge.dao.redis.MonitorMapper;
import rh.study.knowledge.dao.redis.RedisGroupMapper;
import rh.study.knowledge.dao.redis.RedisIpMapper;
import rh.study.knowledge.entity.redis.Monitor;
import rh.study.knowledge.util.DataUtil;
import rh.study.knowledge.util.DateUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


@Service
public class RedisService {


    private static List<Map<String, Object>> ipList = new ArrayList<>();

    @Autowired
    private RedisGroupMapper redisGroupMapper;

    @Autowired
    private RedisIpMapper redisIpMapper;

    @Autowired
    private MonitorMapper monitorMapper;

    private Map<String, Object> get(String url) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "Basic " + "cHJvbWV0aGV1czpuanlocHJvbWV0aGV1cw==");
        //                                                          "cHJvbWV0aGV1czpuanlocHJvbWV0aGV1cw==
        //Basic                                                      cHJvbWV0aGV1czpuanlocHJvbWV0aGV1cw==
        httpGet.setHeader("Referer", "http://promes.cloudytrace.com/d/IgYjVi-mk/redisjian-kong-gai-lan-xi-tong-wei-du-wu-tipban-ben?refresh=1m&panelId=42&fullscreen&orgId=1&var-interval=1m&var-center=%E4%BE%9B%E5%BA%94%E5%95%86%E5%8F%8A%E5%95%86%E6%88%B7%E5%B9%B3%E5%8F%B0%E7%A0%94%E5%8F%91%E4%B8%AD%E5%BF%83&var-appId=MRTDS&var-sysChName=%E5%95%86%E5%AE%B6%E5%AE%9E%E6%97%B6%E6%95%B0%E6%8D%AE%E6%9C%8D%E5%8A%A1%E7%B3%BB%E7%BB%9F&var-ldc=NJYH&var-env=PRD&var-promesIp=All&var-up_ip=All");
        httpGet.setHeader("Accept", "application/json, text/plain, */*");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        httpGet.setHeader("x-PROMES-center", "%E4%BE%9B%E5%BA%94%E5%95%86%E5%8F%8A%E5%95%86%E6%88%B7%E5%B9%B3%E5%8F%B0%E7%A0%94%E5%8F%91%E4%B8%AD%E5%BF%83");
        httpGet.setHeader("x-PROMES-env", "PRD");
        httpGet.setHeader("x-PROMES-ldc", "NJYH");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                String str = EntityUtils.toString(responseEntity);
//                System.out.println("响应内容为:" + str);
                Map<String,Object> list = parseResponse(str);
                return list;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Map<String,Object> parseResponse(String str) throws IOException {
        Map<String,Object> resMap = new HashMap<>();
        if (!StringUtils.isEmpty(str)) {
            JSONObject jsonObject = JSONObject.parseObject(str);
            String status= jsonObject.getString("status");
            if ("success".equals(status)) {
                JSONArray result = jsonObject.getJSONObject("data").getJSONArray("result");
                JSONObject metricJsonObj;
                JSONArray valueJsonArr;
                String ip;
                String value = null;
                for (int i = 0; i < result.size(); i++) {
                    //metric
                    jsonObject = result.getJSONObject(i);
                    metricJsonObj= jsonObject.getJSONObject("metric");
                    ip = metricJsonObj.getString("ip");
                    valueJsonArr= jsonObject.getJSONArray("value");
                    if (valueJsonArr.size() > 1) {
                        value = valueJsonArr.getString(1);
                    }
//                    System.out.println(ip + ":" + value);

                    resMap.put(ip, value);
                }
            }
        }
        return resMap;
    }

    /**
     * 数据云上的文件和分组IP文件模板对比生成有序的监控文件
     * @return
     * @throws IOException
     */
    public XSSFWorkbook getRedisExcel() throws IOException {
        List<Map<String, Object>> ipLst = redisIpMapper.list(null, null, 0);
        // 分组IP文件模板地址

        long time = new Date().getTime() / 1000;
        // 连接客户端
        String redis_connected_clients_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=sum(redis_connected_clients%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2CsoftType%3D%22Redis%22%7D)%20by(ip)&time=" + time;
        Map<String,Object> redis_connected_clients = get(redis_connected_clients_url);
        // keys数量
        String redis_db_keys_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=sum%20(redis_db_keys%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2C%20softType%3D%22Redis%22%7D)%20by%20(ip)&time=" + time;
        Map<String,Object> redis_db_keys = get(redis_db_keys_url);
        // 已用内存
        String redis_memory_used_bytes_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=max(redis_memory_used_bytes%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2C%20softType%3D%22Redis%22%7D)by%20(ip)&time=" + time;
        Map<String,Object> redis_memory_used_bytes = get(redis_memory_used_bytes_url);
        // 启动时间
        String redis_uptime_in_seconds_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=min(redis_uptime_in_seconds%7BexporterName%3D%22redis_exporter%22%2CappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%7D)%20by%20(ip)&time=" + time;
        Map<String,Object> redis_uptime_in_seconds = get(redis_uptime_in_seconds_url);
        // 每分钟命令数
        String redis_commands_processed_total_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=max(increase(redis_commands_processed_total%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2CsoftType%3D%22Redis%22%7D%5B70s%5D))%20by%20(ip)&time=" + time;
        Map<String,Object> redis_commands_processed_total = get(redis_commands_processed_total_url);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        List<Map<String, Object>> orderList = new ArrayList<>();
        for (Map<String, Object> ipMap : ipLst) {
            String ip = ipMap.get("ip").toString().trim();
            Map<String, Object> map = new HashMap<>();
            map.put("分组", ipMap.get("name"));
            map.put("IP", ip);
            map.put("连接客户端", redis_connected_clients.get(ip));
            map.put("keys数量", redis_db_keys.get(ip));
            if (redis_memory_used_bytes.get(ip) != null) {
                StringBuilder sb = new StringBuilder();
                double nc = Double.parseDouble(redis_memory_used_bytes.get(ip).toString());
                if (nc / 1024 / 1024 >= 1024 ) {
                    nc = nc / 1024 / 1024 / 1024;
                    sb.append(decimalFormat.format(nc)).append(" GB");
                } else {
                    nc = nc / 1024 / 1024;
                    sb.append(decimalFormat.format(nc)).append(" MB");
                }
                map.put("已用内存", sb.toString());
            }
            if (redis_commands_processed_total.get(ip) != null) {
                Double num = Double.parseDouble(redis_commands_processed_total.get(ip).toString());
                StringBuilder sb = new StringBuilder();
                if (num >=1000) {
                    num /= 1000;
                    sb.append(num.intValue()).append(" k");
                } else {
                    sb.append(num.intValue());
                }

                map.put("每分钟命令数", sb.toString());
            }
            orderList.add(map);
        }

        XSSFWorkbook workbook1 = exportE(orderList, Arrays.asList("分组", "IP", "连接客户端","每分钟命令数","keys数量","已用内存"), null);
        return workbook1;
    }

    private XSSFWorkbook exportE(List<Map<String, Object>> orderList, List<String> keyList, List<String> titleList) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Redis分组监控数据");
        XSSFRow row;
        XSSFCell cell;
        int rowNum = 0;
        row = sheet.createRow(rowNum++);
        int i = 0;
        int j = 0;
        for (String str : keyList) {
            cell = row.createCell(i++);
            cell.setCellValue(str);
            if (titleList != null) {
                cell.setCellValue(titleList.get(j++));
            }
        }
        for (Map<String, Object> map : orderList) {
            row = sheet.createRow(rowNum++);
            i = 0;
            for (String str : keyList) {
                cell = row.createCell(i++);
                cell.setCellValue(map.get(str) != null ? map.get(str).toString() : "");
            }
        }
        return workbook;
    }


    public int saveRedisMonitor() {
        List<Map<String, Object>> ipLst = redisIpMapper.list(null, null, 0);
        // 分组IP文件模板地址

        long time = new Date().getTime() / 1000;
        // 连接客户端
        String redis_connected_clients_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=sum(redis_connected_clients%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2CsoftType%3D%22Redis%22%7D)%20by(ip)&time=" + time;
        Map<String,Object> redis_connected_clients = get(redis_connected_clients_url);
        // keys数量
        String redis_db_keys_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=sum%20(redis_db_keys%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2C%20softType%3D%22Redis%22%7D)%20by%20(ip)&time=" + time;
        Map<String,Object> redis_db_keys = get(redis_db_keys_url);
        // 已用内存
        String redis_memory_used_bytes_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=max(redis_memory_used_bytes%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2C%20softType%3D%22Redis%22%7D)by%20(ip)&time=" + time;
        Map<String,Object> redis_memory_used_bytes = get(redis_memory_used_bytes_url);
        // 启动时间
        String redis_uptime_in_seconds_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=min(redis_uptime_in_seconds%7BexporterName%3D%22redis_exporter%22%2CappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%7D)%20by%20(ip)&time=" + time;
        Map<String,Object> redis_uptime_in_seconds = get(redis_uptime_in_seconds_url);
        // 每分钟命令数
        String redis_commands_processed_total_url = "http://njyh.data.promes.cloudytrace.com:9801/api/v1/query?query=max(increase(redis_commands_processed_total%7BappId%3D%22MRTDS%22%2CldcId%3D%22NJYH%22%2CsoftType%3D%22Redis%22%7D%5B70s%5D))%20by%20(ip)&time=" + time;
        Map<String,Object> redis_commands_processed_total = get(redis_commands_processed_total_url);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        List<Monitor> monitorList = new ArrayList<>();
        int i = 0;
        Date now = new Date();
        for (Map<String, Object> ipMap : ipLst) {
            String ip = ipMap.get("ip").toString().trim();
            Monitor monitor = new Monitor();
            monitor.setIpId(MapUtils.getInteger(ipMap, "id"));
            // 连接客户端
            monitor.setClientNum(MapUtils.getInteger(redis_connected_clients, ip));
            // keys数量
            monitor.setRedisKeys(MapUtils.getInteger(redis_db_keys, ip));
            if (redis_memory_used_bytes.get(ip) != null) {
                StringBuilder sb = new StringBuilder();
                double nc = Double.parseDouble(redis_memory_used_bytes.get(ip).toString());
                monitor.setMemoryUsed(nc);
                if (nc / 1024 / 1024 >= 1024 ) {
                    nc = nc / 1024 / 1024 / 1024;
                    sb.append(decimalFormat.format(nc)).append(" GB");
                } else {
                    nc = nc / 1024 / 1024;
                    sb.append(decimalFormat.format(nc)).append(" MB");
                }
            }
            if (redis_commands_processed_total.get(ip) != null) {
                Double num = Double.parseDouble(redis_commands_processed_total.get(ip).toString());
                monitor.setCommands(num);
                StringBuilder sb = new StringBuilder();
                if (num >=1000) {
                    num /= 1000;
                    sb.append(num.intValue()).append(" k");
                } else {
                    sb.append(num.intValue());
                }

            }
            monitor.setCreateTime(now);
            monitorList.add(monitor);
//            i += monitorMapper.insertSelective(monitor);
        }
        monitorMapper.batchInsert(monitorList);
        return i;
    }

    /**
     * 纵向
     * @param current
     * @param pageSize
     * @param ipId
     * @return
     */
    public PageResult listPageable(int current, int pageSize, Integer ipId) {//分页查询，包括分页和总数查询，第三个参数是控制是否计算总数，默认是true,true为查询总数，分页效果只对其后的第一个查询有效。
        Page page = PageHelper.startPage(current, pageSize);
//        List<RedisIp> list = redisIpMapper.selectAll();
        Map<String, Object> params = new HashMap<>();
        params.put("ipId", ipId);
        List<Map<String, Object>> list = monitorMapper.list(params);
        return new PageResult(page.getTotal(), page.getPages(), list, page.getPageNum(), page.getPageSize());

    }

    /**
     * 横向
     * @param current
     * @param pageSize
     * @param startDate
     * @param endDate
     * @return
     */
    public PageResult listGroupPageable(int current, int pageSize, String startDate, String endDate) {
        if (StringUtils.isEmpty(startDate)) {
            Date now = new Date();
            startDate = DateUtil.format(now, DateUtil.yyyyMM) + "01";
            endDate = DateUtil.format(now, DateUtil.yyyyMMdd);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("startDate", !StringUtils.isEmpty(startDate) ? DateUtil.parseDate(startDate.replaceAll("-", "") + " 00:00:00", DateUtil.yyyyMMddHHmmss) : null);
        params.put("endDate", !StringUtils.isEmpty(endDate) ? DateUtil.parseDate(endDate.replaceAll("-", "") + " 23:59:59", DateUtil.yyyyMMddHHmmss) : null);
        List<Map<String, Object>> list = monitorMapper.list(params);
        Map<String, Map<String, Map<String, Object>>> ipGroupMap = new LinkedHashMap<>();
        Map<String, Map<String, Object>> timeMap;
        String ip;
        String group_name;
        String create_time;
        Integer ip_id;
        Integer group_id;
        StringBuilder key;
        Set<String> time = new TreeSet<>();
        for (Map<String, Object> map : list) {
            ip_id = MapUtils.getInteger(map, "ip_id");
            group_id = MapUtils.getInteger(map, "group_id");
            create_time = DateUtil.format(map.get("create_time"), DateUtil.yyyyMMddHH_Zh);
            time.add(create_time);
            key = new StringBuilder();
            key.append(ip_id).append(group_id);
            if (ipGroupMap.containsKey(key.toString())) {
                timeMap = ipGroupMap.get(key.toString());
            } else {
                timeMap = new HashMap<>();
            }
            timeMap.put(create_time, map);
            ipGroupMap.put(key.toString(), timeMap);
        }
        // keys
        List<String> keysList = Arrays.asList("client_num", "commands", "memory_used", "redis_keys");
        List<String> titleList = Arrays.asList("连接客户端数", "每分钟命令数", "已用内存", "keys数量");
        List<String> commonKeyList = Arrays.asList("group_id", "group_name", "ip_id"
                , "ip");
        List<Column> columns = new ArrayList<>();
        Column column = new Column();
        column.setTitle("分组");
        column.setDataIndex("group_name");
        column.setFixed("left");
        column.setWidth(230);
        columns.add(column);
        column = new Column();
        column.setTitle("IP");
        column.setDataIndex("ip");
        column.setFixed("left");
        column.setWidth(125);
        columns.add(column);
        int i = 0;
        for (String k : keysList) {
            String title = titleList.get(i++);
            for (String t : time) {
                column = new Column();
                column.setDataIndex(k + t);
                column.setTitle(title + t);
                column.setWidth(120);
                columns.add(column);
            }
        }
        List<Map<String, Object>> resList = new ArrayList<>();
        Map<String, Object> resMap;
        Map<String, Object> map;
        for (String s : ipGroupMap.keySet()) {
            timeMap = ipGroupMap.get(s);
            resMap = new HashMap<>();
            for (String k : timeMap.keySet()) {
                map = timeMap.get(k);
                for (String f : keysList) {
                    if ("memory_used".equals(f)) {
                        StringBuilder sb = new StringBuilder();
                        Double nc = MapUtils.getDouble(map, f, 0d);
                        if (nc / 1024 / 1024 >= 1024 ) {
                            nc = nc / 1024 / 1024 / 1024;
                            sb.append(DataUtil.decimalFormat.format(nc)).append(" GB");
                        } else {
                            nc = nc / 1024 / 1024;
                            sb.append(DataUtil.decimalFormat.format(nc)).append(" MB");
                        }
                        resMap.put(f+k, sb.toString());
                    } else if ("commands".equals(f)) {
                        Double num = MapUtils.getDouble(map, f, 0d);
                        StringBuilder sb = new StringBuilder();
                        if (num >=1000) {
                            num /= 1000;
                            sb.append(DataUtil.decimalFormat.format(num)).append(" k");
                        } else {
                            sb.append(num.intValue());
                        }
                        resMap.put(f+k, sb.toString());
                    } else {
                        resMap.put(f + k, map.get(f));
                    }
                }
                for (String f : commonKeyList) {
                    resMap.put(f, map.get(f));
                }
            }
            resList.add(resMap);
        }
        return new PageResult((long) resList.size(), 1, resList, 1, resList.size(), columns);
    }

    public XSSFWorkbook getRedisExcel2(String startDate, String endDate) {
        PageResult pageResult = listGroupPageable(0,0, startDate, endDate);
        List<Column> list = pageResult.getColumns();
        List<String> keyList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (Column c : list) {
            keyList.add(c.getDataIndex());
            titleList.add(c.getTitle());
        }
        XSSFWorkbook workbook1 = exportE((List<Map<String, Object>>) pageResult.getRows(), keyList, titleList);
        return workbook1;
    }
}