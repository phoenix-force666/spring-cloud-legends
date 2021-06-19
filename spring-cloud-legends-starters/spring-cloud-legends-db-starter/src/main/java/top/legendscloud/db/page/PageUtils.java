package top.legendscloud.db.page;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页参数获取
 */
@Component
public class PageUtils {

    /**
     * 获取分页数据
     */
    public Page page(long page,long size,boolean sort,String sortName){
        Page resultPage = new Page();
        resultPage.setCurrent(page);
        resultPage.setSize(size);
        List<OrderItem> orderItems = getOrders(sort,sortName);
        if(orderItems != null && orderItems.size() > 0){
            resultPage.setOrders(orderItems);
        }

        return resultPage;
    }

    /**
     * 排序
     */
    private List<OrderItem> getOrders(boolean sort,String sortName){
        // 排序字段
        if(StringUtils.isBlank(sortName)){
            return null;
        }

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(sortName);
        orderItem.setAsc(sort);

        orderItems.add(orderItem);

        return orderItems;
    }



}