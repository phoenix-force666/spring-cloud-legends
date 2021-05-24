package top.legendscloud.file.config;

import top.legendscloud.file.aspect.FileTypeAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author herion
 * @version V1.0
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}(用一句话描述该文件做什么)
 * @date ${date}
 */
@Configuration
@EnableConfigurationProperties({MultipartProperties.class})
public class FileTypeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(FileTypeAspect.class)
    public FileTypeAspect fileTypeAspect(){
        return new FileTypeAspect();
    }
}