package top.legendscloud.db.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import top.legendscloud.db.config.properties.LegendsGeneratorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 */

@Configuration
@EnableConfigurationProperties(LegendsGeneratorProperties.class)//开启属性注入,通过@autowired注入
public class LegendsCodeGenerator {

    @Autowired
    private LegendsGeneratorProperties legendsGeneratorProperties;


    @Autowired
    private DataSourceProperties dataSourceProperties;




    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public void run(){
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        mpg.setGlobalConfig(globalConfig());

        //数据源配置
        mpg.setDataSource(dataSourceConfig());

        // 包配置
        PackageConfig pc =packageConfig();
        mpg.setPackageInfo(pc);

        // 自定义配置
        mpg.setCfg(injectionConfig(pc.getModuleName()));

        //配置模板
        mpg.setTemplate(templateConfig());
        //策略配置
        mpg.setStrategy(strategy(pc.getModuleName()));
        //模板方式
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.setTemplateEngine(new LegendsFreemarkerTemplateEngine());
        //执行
        mpg.execute();
    }


    private String projectModulePath(){
        if(StringUtils.isNotBlank(legendsGeneratorProperties.getProjectModule())){
            return legendsGeneratorProperties.getProjectPath() + legendsGeneratorProperties.getProjectModule();
        }else{
            return legendsGeneratorProperties.getProjectPath();
        }
    }

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig() {

        return new GlobalConfig()
                .setOutputDir(projectModulePath() +"/src/main/java")
                // 作者
                .setAuthor("herion")
                // 打开文件
                .setOpen(false)
                // swagger注解; 须添加swagger依赖
                .setSwagger2(true)
                // 文件覆盖
                .setFileOverride(true)
                // 开启activeRecord模式
                .setActiveRecord(true)
                // XML ResultMap: mapper.xml生成查询映射结果
                .setBaseResultMap(true)
                .setDateType(DateType.ONLY_DATE)
                // XML ColumnList: mapper.xml生成查询结果列
                .setBaseColumnList(true);
                // 设置实体类名称
//                .setEntityName("%sDao");
    }


    /**
     * 数据源配置
     */
    private DataSourceConfig dataSourceConfig() {
       DbType dbType=DbType.MYSQL;;
        //数据源配置
      if(legendsGeneratorProperties.getDbType().toLowerCase().equals(DbType.ORACLE.getDb())){
            // oracle
            dbType=DbType.ORACLE;
        }
        return new DataSourceConfig()
                // 数据库类型
                .setDbType(dbType)
                // 连接驱动
                .setDriverName(dataSourceProperties.getDriverClassName())
                // 地址
                .setUrl(dataSourceProperties.getUrl())
                // 用户名
                .setUsername(dataSourceProperties.getUsername())
                // 密码
                .setPassword(dataSourceProperties.getPassword());
    }



    /**
     * 包配置
     * 设置包路径用于导包时使用，路径示例：com.path
     */
    private PackageConfig packageConfig() {
        return new PackageConfig()
        //模块名
//        .setModuleName(scanner("模块名"))
        .setModuleName(legendsGeneratorProperties.getPackageModule())
        // 父包名
        .setParent(legendsGeneratorProperties.getParentPackagePath());
    }


    /**
     * 自定义配置
     * @param moduleName
     * @return
     */
    private InjectionConfig injectionConfig(String moduleName) {
        return new InjectionConfig() {
                @Override
                public void initMap() {
                    // 注入配置
                }
            }
                // 判断是否创建文件
            .setFileCreate(new IFileCreate() {
                @Override
                public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                    // 检查文件目录，不存在自动递归创建
                    checkDir(filePath);

                    // 指定需要覆盖的文件
                    // 文件结尾名字参照 全局配置 中对各层文件的命名,未修改为默认值
                    if (isExists(filePath) && (!filePath.endsWith("Mapper.xml") && !filePath.endsWith("Dao.java") && !filePath.endsWith("Mapper.java"))) {
                        return false;
                    }

                    return true;
                }
            })
           // 自定义输出文件
         .setFileOutConfigList(fileOutConfigList(legendsGeneratorProperties.getProjectPath(),moduleName));
    }

    /**
     * 自定义模板
     * @param projectPath
     * @param moduleName
     * @return
     */
    private List fileOutConfigList(String projectPath,String moduleName){
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String dtoTemplatePath = "/templates/entityDTO.java.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectModulePath() +"/src/main/resources/mappers/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        return focList;
    }


    /**
     * 模板配置
     * 配置自定义输出模板
     * 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
     * @return
     */
    private static TemplateConfig templateConfig() {
        return new TemplateConfig()
                // 置空后方便使用自定义输出位置
        .setService("templates/service.java")
        .setServiceImpl("templates/serviceImpl.java")
        .setController("templates/controller.java")
        .setEntityDto("templates/entityDto.java")
        .setXml(null);
    }


    /**
     * 策略配置
     * @param moduleName
     * @return
     */
    private StrategyConfig strategy(String moduleName){
        System.out.println("moduleName:"+moduleName);
        return new StrategyConfig()
          // 表名生成策略：下划线连转驼峰
         .setNaming(NamingStrategy.underline_to_camel)
          // 表字段生成策略：下划线连转驼峰
         .setColumnNaming(NamingStrategy.underline_to_camel)
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 是否为lombok模型; 需要lombok依赖
        .setEntityLombokModel(true)
        // 生成controller
        .setRestControllerStyle(true)
        // 公共父类
//      .setSuperControllerClass("你自己的父类控制器,没有就不用设置!")
        // 写于父类中的公共字段
        .setSuperEntityColumns("id")
//        .setInclude(scanner("表名，多个英文逗号分割").split(","))
        .setInclude(legendsGeneratorProperties.getTables().split(","))
        // controller映射地址：驼峰转连字符
        .setControllerMappingHyphenStyle(true)
        // 去除表前缀
        .setTablePrefix(moduleName + "_")
        // 生成实体类字段注解
        .setEntityTableFieldAnnotationEnable(true);
    }


    /**
     * 判断文件是否存在
     * @param path 路径
     * @return
     */
    private static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

}