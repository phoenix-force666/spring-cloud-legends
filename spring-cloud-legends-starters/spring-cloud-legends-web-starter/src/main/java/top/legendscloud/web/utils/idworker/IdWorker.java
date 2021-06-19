package top.legendscloud.web.utils.idworker;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 类IdWorker.java的实现描述：主机和进程的机器码
 * @author xiongpan 2019年1月8日 下午5:17:05
 */
@Slf4j
public class IdWorker {
    
    /**
     * 主机和进程的机器码
     */
    private static Sequence worker = new Sequence();

    public static long getId() {
        try {
            return worker.nextId();
        } catch (Exception e) {
            log.error("序列生成失败：{}",e);
        }
        return 0L;
    }

    public static String getStrId() {
        try {
            return worker.nextId()+"";
        } catch (Exception e) {
            log.error("序列生成失败：{}",e);
        }
        return "";
    }

}
