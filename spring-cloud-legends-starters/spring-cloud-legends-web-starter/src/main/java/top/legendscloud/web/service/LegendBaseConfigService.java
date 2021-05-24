package top.legendscloud.web.service;

import top.legendscloud.web.config.properties.LegendsBaseProperties;

import java.util.ArrayList;
import java.util.Arrays;

public class LegendBaseConfigService {

    private LegendsBaseProperties lbp;
    
    private ArrayList<String> swBasePacks = new ArrayList<String>(Arrays.asList("top.legendscloud.web.controller"));
    private String swBasePack = "top.legendscloud.web.controller";
    private String swName = "legends";
    private String swUrl = "http://localhost:8080";
    private String swEmail = "";
    private String swTitle = "Yi APIs";
    private String swDesc = "Sample Yi APIs, 请修改配置文件变为您自己的API描述";
    private String swVers = "0.1.0";
    /**
     * The white list of security, the uris in this list don't need auth. 
     */
    private ArrayList<String> secWhiteList = new ArrayList<String>(Arrays.asList(("/v2/api-docs")));
    
    /**
     * @return the lbp
     */
    public LegendsBaseProperties getLbp() {
        return lbp;
    }

    /**
     * @param lbp the lbp to set
     */
    public void setLbp(LegendsBaseProperties lbp) {
        this.lbp = lbp;
    }
    /**
     * @return the swBasePacks
     */
    public ArrayList<String> getSwBasePacks() {
        return swBasePacks;
    }
    /**
     * @param swBasePacks the swBasePacks to set
     */
    public void setSwBasePacks(ArrayList<String> swBasePacks) {
        this.swBasePacks = swBasePacks;
    }
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
     * @return the swName
     */
    public String getSwName() {
        return swName;
    }
    /**
     * @param swName the swName to set
     */
    public void setSwName(String swName) {
        this.swName = swName;
    }
    /**
     * @return the swUrl
     */
    public String getSwUrl() {
        return swUrl;
    }
    /**
     * @param swUrl the swUrl to set
     */
    public void setSwUrl(String swUrl) {
        this.swUrl = swUrl;
    }
    /**
     * @return the swEmail
     */
    public String getSwEmail() {
        return swEmail;
    }
    /**
     * @param swEmail the swEmail to set
     */
    public void setSwEmail(String swEmail) {
        this.swEmail = swEmail;
    }
    /**
     * @return the swTitle
     */
    public String getSwTitle() {
        return swTitle;
    }
    /**
     * @param swTitle the swTitle to set
     */
    public void setSwTitle(String swTitle) {
        this.swTitle = swTitle;
    }
    /**
     * @return the swDesc
     */
    public String getSwDesc() {
        return swDesc;
    }
    /**
     * @param swDesc the swDesc to set
     */
    public void setSwDesc(String swDesc) {
        this.swDesc = swDesc;
    }
    /**
     * @return the swVers
     */
    public String getSwVers() {
        return swVers;
    }
    /**
     * @param swVers the swVers to set
     */
    public void setSwVers(String swVers) {
        this.swVers = swVers;
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
    
}
