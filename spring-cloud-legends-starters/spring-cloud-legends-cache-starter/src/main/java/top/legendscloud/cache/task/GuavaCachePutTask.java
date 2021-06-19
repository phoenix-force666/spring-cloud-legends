//package top.legendscloud.cache.task;
//
//import CachePutDTO;
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
//public class GuavaCachePutTask implements Callable<String> {
//
//    private RestTemplate restTemplate;
//
//    private CachePutDTO cachePutDTO;
//
//    private String homePageUrl;
//
//    public GuavaCachePutTask(CachePutDTO cachePutDTO, String homePageUrl,RestTemplate restTemplate){
//        this.cachePutDTO=cachePutDTO;
//        this.homePageUrl=homePageUrl;
//        this.restTemplate=restTemplate;
//    }
//
//
//
//
//    @Override
//    public String call() throws Exception {
//        StringBuffer url=new StringBuffer(homePageUrl);
//
//        url.append("/v1/guavaCache/put?cacheName=");
//        url.append(cachePutDTO.getCacheName());
//        url.append("&cacheKey=");
//        url.append(cachePutDTO.getCacheKey());
//        url.append("&cacheValue=");
//        url.append(cachePutDTO.getCacheValue());
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
