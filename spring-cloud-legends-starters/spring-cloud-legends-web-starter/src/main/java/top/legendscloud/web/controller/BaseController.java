/**
 *
 */
package top.legendscloud.web.controller;

import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.utils.DateUtil;
import top.legendscloud.web.dto.DemoDTO;
import top.legendscloud.web.vo.DemoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhaopeng
 *
 */
@Api("示例API")
@RestController
@Configuration
public class BaseController {

    @GetMapping("/base/time")
    @ApiOperation(value = "获取系统时间", notes = "获取系统时间", httpMethod = "GET", response = String.class)
    public String getTime() {
        LocalDateTime now  = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("MM dd yyyy  hh:mm:ss"));
    }


    @PostMapping("/time/get")
    @ApiOperation(value = "获取时区时间及系统时间", notes = "获取时区时间及系统时间", httpMethod = "POST")
    public ComResp<DemoVO> getTime(@Validated @RequestBody ComReq<DemoDTO> comReq) {
        LocalDateTime now  = LocalDateTime.now();
        DemoVO demoVO=new DemoVO();
        demoVO.setTimeZoneOffsetDate(DateUtil.getFormatedDateString(comReq.getData().getTimeZoneOffset()));
        demoVO.setLocalDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return new ComResp.Builder().fromReq(comReq).data(demoVO).success().build();
    }
}
