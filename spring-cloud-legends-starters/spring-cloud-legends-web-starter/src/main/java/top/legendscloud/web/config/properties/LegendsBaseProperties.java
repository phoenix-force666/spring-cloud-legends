/**
 * 
 */
package top.legendscloud.web.config.properties;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhaopeng
 *
 */
@ConfigurationProperties(prefix = "legends")
@Data
@Builder
public class LegendsBaseProperties implements Serializable {

    /**
     * The properties of swagger.
     */
    private ArrayList<String> swBasePacks = new ArrayList<String>(Arrays.asList("top.legendscloud.web.controller"));
    private String swBasePack = "top.legendscloud.web";
    private String swName = "legends";
    private String swUrl = "http://localhost:8080/doc.html";
    private String swEmail = "";
    private String swTitle = "Spring Cloud Legends APIs";
    private String swDesc = "Spring Cloud Legends APIs, 请修改配置文件变为您自己的API描述";
    private String swVers = "0.1.0";
    /**
     * The white list of security, the uris in this list don't need auth.
     */
    private ArrayList<String> secWhiteList = new ArrayList<String>(Arrays.asList(("/v2/api-docs")));

}
