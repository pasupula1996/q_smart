package qualtech_q_smart_home_automation_2018.q_smart.SQLITE;

public class LightDbConstants {

    static final String DB_NAME = "CUSTOMIZED_CARDS_DB";
    static final int DB_VERSION = 1;

    protected static final String TB_NAME = "CARDS_DB";


    public static final String ROW_ID = "ID";
    public static final String ROW_TITLE = "TITLE";
    public static final String ROW_THUMBNAIL = "THUMBNAIL";
    public static final String ROW_CLICKED = "CLICKED";

    static final String CREATE_TB = "CREATE TABLE CARDS_DB (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "TITLE TEXT NOT NULL,"+ "THUMBNAIL INTEGER,"+ "CLICKED TEXT NOT NULL);";
    static final String DROP_TB = "DROP TABLE IF EXISTS" + TB_NAME;

    //entertainment database values
    protected static final String ENTERTAINMENT_TB_NAME = "ENTERTAINMENT_DB";
    public static final String E_ROW_ID = "EID";
    public static final String E_ROW_TITLE = "ETITLE";
    public static final String E_ROW_THUMBNAIL = "ETHUMBNAIL";
    public static final String E_ROW_THUMBNAIL_NAME ="ICONNAME";
    public static final String E_CLICKED = "ECLICKED";

    static final String CREATE_E_TB = "CREATE TABLE ENTERTAINMENT_DB (EID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ETITLE TEXT NOT NULL," + "ETHUMBNAIL INTEGER,"+ "ICONNAME TEXT NOT NULL," + "ECLICKED TEXT NOT NULL);";
    static final String DROP_E_TABLE = "DROP TABLE IF EXISTS" + ENTERTAINMENT_TB_NAME;


    //UTILITIES DATABASE VARIABLES
    protected static final String UTILITIES_TB_NAME = "UTILITIES_DB";
    public static final String U_ROW_ID = "UID";
    public static final String U_ROW_TITLE = "UTITLE";
    public static final String U_ROW_THUMBNAIL = "UTHUMBNAIL";
    public static final String U_CLICKED = "UCLICKED";

    static final String CREATE_U_TB = "CREATE TABLE UTILITIES_DB ( UID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "UTITLE TEXT NOT NULL," + "UTHUMBNAIL INTEGER,"  + "UCLICKED TEXT NOT NULL);";
    static final String DROP_U_TB= "DROP TABLE IF EXISTS" + UTILITIES_TB_NAME;

    //SERVICES DATABASE VARIABLES
    protected static final String SERVICES_TB_NAME = "SERVICES_DB";
    public static final String S_ROW_ID = "SID";
    public static final String S_ROW_TITLE = "STITLE";
    public static final String S_ROW_THUMBNAIL = "STHUMBNAIL";
    public static final String S_CLICKED = "SCLICKED";

    static final String CREATE_S_TB = "CREATE TABLE SERVICES_DB ( SID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "STITLE TEXT NOT NULL," + "STHUMBNAIL INTEGER," + "SCLICKED TEXT NOT NULL);";
    static final String DROP_S_TABLE = "DROP TABLE IF EXISTS" + SERVICES_TB_NAME;
}
