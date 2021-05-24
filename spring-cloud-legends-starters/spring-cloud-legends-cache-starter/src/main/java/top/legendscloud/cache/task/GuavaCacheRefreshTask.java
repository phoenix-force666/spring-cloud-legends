//package top.legendscloud.cache.task;
//
//import CacheDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.Callable;
//
///**
// * Created by zhanghai on 2018/8/1.
// */
//
//@Slf4j
//public class GuavaCacheRefreshTask implements Callable<String> {
//
//    private RestTemplate restTemplate;
//
//    private CacheDTO cacheDTO;
//
//    private String homePageUrl;
//
//    public GuavaCacheRefreshTask(CacheDTO cacheDTO, String homePageUrl,RestTemplate restTemplate){
//        this.cacheDTO=cacheDTO;
//        this.homePageUrl=homePageUrl;
//        this.restTemplate=restTemplate;
//    }
//
//
//    @Override
//    public String call() throws Exception {
//        StringBuffer url=new StringBuffer(homePageUrl);
//
//        url.append("/v1/guavaCache/deleteByCacheName?cacheName=");
//        url.append(cacheDTO.getCacheName());
//        url.append("&cacheKey=");
//        url.append(cacheDTO.getCacheKey());
//
//
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
//        headers.setContentType(type);
//        Map<String, String[]> rquestMap=new HashMap<>();
//        HttpEntity<Map> requestEntity = new HttpEntity<Map>(rquestMap, headers);
//        HttpEntity<String> response = restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity, String.class);
//        log.info(response.getBody());
//        return response.getBody();
//    }
//}
