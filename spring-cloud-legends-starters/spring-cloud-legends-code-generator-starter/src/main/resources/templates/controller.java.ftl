package ${package.Controller};
import com.google.common.util.concurrent.Callables;
import org.springframework.validation.annotation.Validated;
import com.baomidou.mybatisplus.core.metadata.IPage;

import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.base.dto.BaseDelDTO;
import top.legendscloud.common.base.dto.BaseDelsDTO;

import ${package.Entity}.${entity};
import ${package.Dto}.${entityDto};
import ${package.UpdDto}.${entityUpdDto};
import ${package.AddDto}.${entityAddDto};
import ${package.Vo}.${vo};
import ${package.Service}.${table.serviceName};
import ${package.ConvertMapper}.${entityMapper};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import java.util.List;

/**
* <p>
* ${table.comment} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
    public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
    public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    @Autowired
    private ${entityMapper} ${entityMapper?uncap_first};

    @ApiOperation(value = "${table.comment}分页列表",httpMethod = "POST")
    @PostMapping(value = "/page")
    public   ComResp<IPage<${entity}>> page (@Validated @RequestBody ComReq<ReqPage<${entity}>> comReq) {
        return  new ComResp.Builder()
                .fromReq(comReq).data(${table.serviceName?uncap_first}.page(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "${table.comment}列表",httpMethod = "POST")
    @PostMapping(value = "/list")
    public   ComResp<List<${entity}>> listByEntity (@Validated @RequestBody ComReq<${entityDto}> comReq) {
        return  new ComResp.Builder()
                .fromReq(comReq).data(${table.serviceName?uncap_first}.listByEntity(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "${table.comment}详情",httpMethod = "POST")
    @PostMapping(value = "loadById")
    public  ComResp<${vo}> loadById(@Validated @RequestBody ComReq<BaseDelDTO> comReq) throws Exception {
        return Callables.returning(
            new ComResp.Builder()
            .fromReq(comReq)
            .data(${entityMapper?uncap_first}.${entity?uncap_first}EntityToVo(${table.serviceName?uncap_first}.loadById(comReq.getData().getId())))
            .success()
            .build()
        ).call();
    }


    @ApiOperation(value = "${table.comment}新增",httpMethod = "POST")
    @PostMapping(value = "/add")
    public  ComResp add(@Validated @RequestBody ComReq<${entityAddDto}> comReq) {
        ${table.serviceName?uncap_first}.add(comReq.getData());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }


    @ApiOperation(value = "${table.comment}修改" ,httpMethod = "POST")
    @PostMapping(value = "/modifyById")
    public  ComResp modifyById(@Validated @RequestBody ComReq<${entityUpdDto}> comReq) {
        ${table.serviceName?uncap_first}.modifyById(comReq.getData());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "${table.comment}删除(单个条目)",httpMethod = "POST")
    @PostMapping(value = "/deleteById")
    public  ComResp remove(@Validated @RequestBody ComReq<BaseDelDTO> comReq) {
        ${table.serviceName?uncap_first}.deleteById(comReq.getData().getId());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "${table.comment}删除(多个条目)",httpMethod = "POST")
    @PostMapping(value = "/deleteByIds")
    public  ComResp deleteByIds(@Validated @RequestBody ComReq<BaseDelsDTO> comReq) {
        ${table.serviceName?uncap_first}.deleteByIds(comReq.getData().getIds());
        return  new ComResp.Builder().fromReq(comReq).success().build();
    }


    @ApiOperation(value = "${table.comment}列表",httpMethod = "GET")
    @GetMapping(value = "/list")
    public   ComResp<List<${entity}>> findListByEntity (@Validated ${entityDto} ${entityDto?uncap_first}) {
        return  new ComResp.Builder()
                .data(${table.serviceName?uncap_first}.listByEntity(${entityDto?uncap_first}))
                .success().build();
    }

    @ApiOperation(value = "${table.comment}新增",httpMethod = "POST")
    @PostMapping(value = "/save")
    public  ComResp save(@Validated @RequestBody ${entityAddDto} ${entityAddDto?uncap_first}) {
        ${table.serviceName?uncap_first}.add(${entityAddDto?uncap_first});
        return  new ComResp.Builder().success().build();
    }

    @ApiOperation(value = "${table.comment}详情",httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public  ComResp<${vo}> loadById(@PathVariable Long id) {
        return new ComResp.Builder()
        .data(${entityMapper?uncap_first}.${entity?uncap_first}EntityToVo(${table.serviceName?uncap_first}.loadById(id)))
        .success().build();
    }

    @ApiOperation(value = "${table.comment}修改" ,httpMethod = "PUT")
    @PutMapping(value = "/modifyById")
    public  ComResp updateById(@Validated @RequestBody ${entityUpdDto} ${entityUpdDto?uncap_first}) {
        ${table.serviceName?uncap_first}.modifyById(${entityUpdDto?uncap_first});
        return  new ComResp.Builder().success().build();
    }

    @ApiOperation(value = "${table.comment}删除(单个条目)",httpMethod = "DELETE")
    @DeleteMapping(value = "/deleteById/{id}")
    public  ComResp delete(@Validated @PathVariable  @Min(value = 1,message = "id不能为空") @NotNull long id) {
        ${table.serviceName?uncap_first}.deleteById(id);
        return  new ComResp.Builder().success().build();
    }

    @ApiOperation(value = "${table.comment}删除(多个条目)",httpMethod = "DELETE")
    @DeleteMapping(value = "/deleteByIds/{ids}")
    public  ComResp deleteByIds(@Validated @PathVariable  @NotNull() @Size(min = 1,message = "至少删除一条数据") List<Long> ids) {
        ${table.serviceName?uncap_first}.deleteByIds(ids);
        return  new ComResp.Builder().success().build();
    }

}
</#if>