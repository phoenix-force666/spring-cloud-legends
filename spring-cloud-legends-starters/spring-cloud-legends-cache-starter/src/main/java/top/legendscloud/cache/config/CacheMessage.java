package top.legendscloud.cache.config;
import java.io.Serializable;

/**
 * @Author herion
 * @Description //TODO
 * @Date  2019/5/22
 * @Param
 * @return
 **/
public class CacheMessage implements Serializable {
    private String cacheName;

    private Object key;

    public CacheMessage(String cacheName, Object key) {
        super();
        this.cacheName = cacheName;
        this.key = key;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

}
