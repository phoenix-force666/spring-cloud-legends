package top.legendscloud.zuul.feign;

import top.legendscloud.zuul.core.CoreHeaderInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @Author herion
 * @Description
 * @Date  2019/1/29
 * @Param 
 * @return 
 **/
public class CoreFeignRequestInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CoreFeignRequestInterceptor.class);
    @Override
    public void apply(RequestTemplate template) {
        String header = StringUtils.collectionToDelimitedString(CoreHeaderInterceptor.LABEL.get(), CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
        logger.debug("feign header:{}",header);
        template.header(CoreHeaderInterceptor.HEADER_LABEL, header);
    }

}