package top.legendscloud.file.enums;

/**
 * 文件类型枚取
 * @Author herion
 * @Description
 * @Date 14:02 2019/8/31
 * @Param
 * @return
 **/
public enum FileGroupTypeEnum {
    /**
     * JEPG.
     */
    JPEG("FFD8FF","JEPG",1,"图片"),
    /**
     * PNG.
     */
    PNG("89504E47","PNG",1,"图片"),

    /**
     * GIF.
     */
    GIF("47494638","GIF",1,"图片"),

    /**
     * TIFF.
     */
    TIFF("49492A00","TIFF",1,"图片"),

    /**
     * Windows Bitmap.
     */
    BMP("424D","BMP",1,"图片"),

    /**
     * CAD.
     */
    DWG("41433130","DWG",1,"图片"),

    /**
     * Adobe Photoshop.
     */
    PSD("38425053","PSD",1,"图片"),

    /**
     * Rich Text Format.
     */
    RTF("7B5C727466","RTF",2,"文档"),

    /**
     * XML.
     */
    XML("3C3F786D6C","XML",2,"文档"),

    /**
     * HTML.
     */
    HTML("68746D6C3E","HTML",2,"文档"),
    /**
     * CSS.
     */
    CSS("48544D4C207B0D0A0942","CSS",2,"文档"),
    /**
     * JS.
     */
    JS("696B2E71623D696B2E71","JS",2,"文档"),
    /**
     * Email [thorough only].
     */
    EML("44656C69766572792D646174653A","EML",2,"文档"),

    /**
     * Outlook Express.
     */
    DBX("CFAD12FEC5FD746F","DBX",2,"文档"),

    /**
     * Outlook (pst).
     */
    PST("2142444E","PST",2,"文档"),

    /**
     * MS Word/Excel.
     */
    XLS_DOC("D0CF11E0","XLS_DOC",2,"文档"), XLSX_DOCX("504B030414000600080000002100","XLSX_DOCX",2,"文档"),
    /**
     * Visio
     */
    VSD("d0cf11e0a1b11ae10000","VSD",2,"文档"),
    /**
     * MS Access.
     */
    MDB("5374616E64617264204A","MDB",2,"文档"),
    /**
     * WPS文字wps、表格et、演示dps都是一样的
     */
    WPS("d0cf11e0a1b11ae10000","WPS",2,"文档"),
    /**
     * torrent
     */
    TORRENT("6431303A637265617465","TORRENT",4,"种子"),
    /**
     * WordPerfect.
     */
    WPD("FF575043","WPD",2,"文档"),

    /**
     * Postscript.
     */
    EPS("252150532D41646F6265","EPS",2,"文档"),

    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E","PDF",2,"文档"),

    /**
     * Quicken.
     */
    QDF("AC9EBD8F","QDF",2,"文档"),

    /**
     * Windows Password.
     */
    PWL("E3828596","PWL",2,"文档"),

    /**
     * ZIP Archive.
     */
    ZIP("504B0304","ZIP",2,"文档"),

    /**
     * RAR Archive.
     */
    RAR("52617221","RAR",2,"文档"),
    /**
     * JSP Archive.
     */
    JSP("3C2540207061676520","JSP",2,"文档"),
    /**
     * JAVA Archive.
     */
    JAVA("7061636B61676520","JAVA",2,"文档"),
    /**
     * CLASS Archive.
     */
    CLASS("CAFEBABE0000002E00","CLASS",2,"文档"),
    /**
     * JAR Archive.
     */
    JAR("504B03040A000000","JAR",2,"文档"),
    /**
     * MF Archive.
     */
    MF("4D616E69666573742D56","MF",2,"文档"),
    /**
     *EXE Archive.
     */
    EXE("4D5A9000030000000400","EXE",2,"文档"),
    /**
     *CHM Archive.
     */
    CHM("49545346030000006000","CHM",2,"文档"),
    /*
     * INI("235468697320636F6E66"), SQL("494E5345525420494E54"), BAT(
     * "406563686F206f66660D"), GZ("1F8B0800000000000000"), PROPERTIES(
     * "6C6F67346A2E726F6F74"), MXP(
     * "04000000010000001300"),
     */


    /**
     * AVI.
     */
    AVI("41564920","AVI",3,"视频"),

    /**
     * Real Audio.
     */
    RAM("2E7261FD","RAM",3,"视频"),

    /**
     * Real Media.
     */
    RM("2E524D46","RM",3,"视频"),

    /**
     * MPEG (mpg).
     */
    MPG("000001BA","MPG",3,"视频"),

    /**
     * Quicktime.
     */
    MOV("6D6F6F76","MOV",3,"视频"),

    /**
     * Windows Media.
     */
    ASF("3026B2758E66CF11","ASF",3,"视频"),

    /**
     * MIDI.
     */
    MID("4D546864","MID",3,"视频"),
    /**
     * MP4.
     */
    MP4("00000020667479706d70","MP4",3,"视频"),

    /**
     * FLV.
     */
    FLV("464C5601050000000900","FLV",3,"视频"),

    /**
     * MP3.
     */
    MP3("49443303000000002176","MP3",5,"音乐"),

    /**
     * Wave.
     */
    WAV("57415645","WAV",5,"音乐");


    private String value;
    private String type;
    private int group;
    private String desc;


    private FileGroupTypeEnum(String value, String type, int group, String desc) {
        this.value = value;
        this.type=type;
        this.group=group;
        this.desc=desc;;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}