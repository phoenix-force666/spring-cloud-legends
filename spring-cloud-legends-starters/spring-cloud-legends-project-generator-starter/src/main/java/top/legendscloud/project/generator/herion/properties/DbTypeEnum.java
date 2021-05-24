package top.legendscloud.project.generator.herion.properties;


/**
 * Created by zhanghai on 2019/5/15.
 */
public enum DbTypeEnum {
    ORACLE("ojdbc", "oracle"),
    ORACLE6("ojdbc6", "oracle"),
    ORACLE7("ojdbc6", "oracle"),
    MYSQL("mysql-connector-java", "mysql");

    /**
     * 代码
     */
    private final String key;

    /**
     * 说明
     */
    private final String dbType;

    public String getKey() {
        return key;
    }

    public String getDbType() {
        return dbType;
    }

    DbTypeEnum(String key, String dbType) {
        this.key = key;
        this.dbType = dbType;
    }


    /**
     * @param key
     * @return ErrorEnum 返回类型
     * @Title: getEnumByCode
     */
    public static DbTypeEnum getEnumByCode(String key) {

        for (DbTypeEnum item : DbTypeEnum.values()) {
            if (item.getKey().equals(key)) {
                return item;
            }
        }

        return DbTypeEnum.ORACLE6;
    }


    public static String getDbTypeByCode(String key) {

        for (DbTypeEnum item : DbTypeEnum.values()) {
            if (item.getKey().equals(key)) {
                return item.getDbType();
            }
        }

        return DbTypeEnum.ORACLE6.getDbType();
    }

}
