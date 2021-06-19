package top.legendscloud.zuul.core;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author herion
 * @Description
 * @Date  2017/12/29
 * @Param 
 * @return 
 **/
public class LabelAndWeightMetadataRule extends ZoneAvoidanceRule {

    private static final Logger logger = LoggerFactory.getLogger(LabelAndWeightMetadataRule.class);

    public static final String META_DATA_KEY_LABEL_AND = "labelAnd";
    public static final String META_DATA_KEY_LABEL_OR = "labelOr";

    public static final String META_DATA_KEY_WEIGHT = "weight";

    private Random random = new Random();

    @Override
    public Server choose(Object key) {
        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        /*计算总值并剔除0权重节点*/
        int totalWeight = 0;
        Map<Server, Integer> serverWeightMap = new HashMap<>();
        for (Server server : serverList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();

            /* 优先匹配label*/
            String labelOr = metadata.get(META_DATA_KEY_LABEL_OR);
            if(!StringUtils.isEmpty(labelOr)){
                List<String> metadataLabel = Arrays.asList(labelOr.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT));
                for (String label : metadataLabel) {
                    if(CoreHeaderInterceptor.LABEL.get().contains(label)){
                        logger.info("host:{},hostPort:{},",server.getHost(),server.getHostPort());
                        return server;
                    }
                }
            }

            String labelAnd = metadata.get(META_DATA_KEY_LABEL_AND);
            if(!StringUtils.isEmpty(labelAnd)){
                List<String> metadataLabel = Arrays.asList(labelAnd.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT));
                if(CoreHeaderInterceptor.LABEL.get().containsAll(metadataLabel)){
                    logger.info("host:{},hostPort:{},",server.getHost(),server.getHostPort());
                    return server;
                }
            }

            String strWeight = metadata.get(META_DATA_KEY_WEIGHT);

            int weight = 100;
            try {
                weight = Integer.parseInt(strWeight);
            } catch (Exception e) {
                // 无需处理
            }

            if (weight <= 0) {
                continue;
            }

            serverWeightMap.put(server, weight);
            totalWeight += weight;
        }

        // 权重随机
        int randomWight = this.random.nextInt(totalWeight);
        int current = 0;
        for (Map.Entry<Server, Integer> entry : serverWeightMap.entrySet()) {
            current += entry.getValue();
            if (randomWight <= current) {
                logger.info("host:{},hostPort:{},",entry.getKey().getHost(),entry.getKey().getHostPort());
                return entry.getKey();
            }
        }

        return null;
    }
}
