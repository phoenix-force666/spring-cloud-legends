/**
 * 
 */
package com.legends.cloud.web.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhaopeng
 *
 */
@ConfigurationProperties(prefix = "legends")
public class LegendsBaseProperties {

    /**
     * The properties of swagger.
     */
    private ArrayList<String> swBasePacks = new ArrayList<String>(Arrays.asList("com.legends.cloud.web.controller"));
    private String swBasePack = "com.legends.cloud.we";
    private String swName = "legends";
    private String swUrl = "http://localhost:8080/doc.html";
    private String swEmail = "";
    private String swTitle = "Yi APIs";
    private String swDesc = "Sample Yi APIs, 请修改配置文件变为您自己的API描述";
    private String swVers = "0.1.0";
    /**
     * The white list of security, the uris in this list don't need auth. 
     */
    private ArrayList<String> secWhiteList = new ArrayList<String>(Arrays.asList(("/v2/api-docs")));
    
    
    /**
     * @return the swBasePack
     */
    public String getSwBasePack() {
        return swBasePack;
    }
    /**
     * @param swBasePack the swBasePack to set
     */
    public void setSwBasePack(String swBasePack) {
        this.swBasePack = swBasePack;
    }

    /**
     * @return the secWhiteList
     */
    public ArrayList<String> getSecWhiteList() {
        return secWhiteList;
    }
    /**
     * @param secWhiteList the secWhiteList to set
     */
    public void setSecWhiteList(ArrayList<String> secWhiteList) {
        this.secWhiteList = secWhiteList;
    }
    public ArrayList<String> getSwBasePacks() {
        return swBasePacks;
    }
    public void setSwBasePacks(ArrayList<String> swBasePacks) {
        this.swBasePacks = swBasePacks;
    }
    public String getSwName() {
        return swName;
    }
    public void setSwName(String swName) {
        this.swName = swName;
    }
    public String getSwUrl() {
        return swUrl;
    }
    public void setSwUrl(String swUrl) {
        this.swUrl = swUrl;
    }
    public String getSwEmail() {
        return swEmail;
    }
    public void setSwEmail(String swEmail) {
        this.swEmail = swEmail;
    }
    public String getSwTitle() {
        return swTitle;
    }
    public void setSwTitle(String swTitle) {
        this.swTitle = swTitle;
    }
    public String getSwDesc() {
        return swDesc;
    }
    public void setSwDesc(String swDesc) {
        this.swDesc = swDesc;
    }
    public String getSwVers() {
        return swVers;
    }
    public void setSwVers(String swVers) {
        this.swVers = swVers;
    }
    
}
