package com.shell.mvppro.uitls;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import android.view.WindowManager;


import com.shell.mvppro.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备系统工具类
 * 作者： JairusTse
 * 日期： 17/12/19
 */

public class OSUtil {

    //MIUI标识
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //EMUI标识
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    //Flyme标识
    private static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    private static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";

    /**
     * 是否是Flyme系统
     * @return
     */
    public static boolean isFlyme() {
        if(isPropertiesExist(KEY_FLYME_ICON_FALG, KEY_FLYME_SETUP_FALG, KEY_FLYME_PUBLISH_FALG)) {
            return true;
        }
        try {
            BuildProperties buildProperties = BuildProperties.newInstance();
            if(buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
                String romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY);
                if (!TextUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是EMUI系统
     * @return
     */
    public static boolean isEMUI() {
        return isPropertiesExist(KEY_EMUI_VERSION_CODE, KEY_EMUI_API_LEVEL,
                KEY_EMUI_CONFIG_HW_SYS_VERSION);
    }

    /**
     * 是否是MIUI系统
     * @return
     */
    public static boolean isMIUI() {
        return isPropertiesExist(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME,
                KEY_MIUI_INTERNAL_STORAGE);
    }

    private static boolean isPropertiesExist(String... keys) {
        if (keys == null || keys.length == 0) {
            return false;
        }
        try {
            BuildProperties properties = BuildProperties.newInstance();
            for (String key : keys) {
                String value = properties.getProperty(key);
                if (value != null)
                    return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static final class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            // 读取系统配置信息build.prop类
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build" +
                    ".prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_VIVO = "VIVO";
    public static final String ROM_QIKU = "QIKU";
    private static String sName;
    private static String sVersion;
    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";



    public static boolean isVivo() {
        return check(ROM_VIVO);
    }

    public static boolean isOppo() {
        return check(ROM_OPPO);
    }
    public static boolean check(String rom) {
        if (sName != null) {
            return sName.equals(rom);
        }

        if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_MIUI))) {
            sName = ROM_MIUI;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_EMUI))) {
            sName = ROM_EMUI;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_OPPO))) {
            sName = ROM_OPPO;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_VIVO))) {
            sName = ROM_VIVO;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_SMARTISAN))) {
            sName = ROM_SMARTISAN;
        } else {
            sVersion = Build.DISPLAY;
            if (sVersion.toUpperCase().contains(ROM_FLYME)) {
                sName = ROM_FLYME;
            } else {
                sVersion = Build.UNKNOWN;
                sName = Build.MANUFACTURER.toUpperCase();
            }
        }
        return sName.equals(rom);
    }

    public static String getProp(String name) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
    //获取虚拟按键的高度
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;
    public static int getScreenWidth() {
        if (SCREEN_WIDTH == 0) {
            readScreenSize();
        }
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == 0) {
            readScreenSize();
        }
        return SCREEN_HEIGHT;
    }
    private static void readScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        boolean isFlag = (BaseApplication.getInstance().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (SCREEN_HEIGHT < SCREEN_WIDTH && !isFlag) {
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
        }
    }

    /**
     * ROM 类型
     */
    private static final ROM ROM_TYPE = initRomType();

    private static final String KEY_DISPLAY_ID = "ro.build.display.id";
    private static final String KEY_BASE_OS_VERSION = "ro.build.version.base_os";
    private static final String KEY_CLIENT_ID_BASE = "ro.com.google.clientidbase";

    // 小米 : MIUI
    private static final String KEY_MIUI_VERSION = "ro.build.version.incremental"; // "7.6.15"
    private static final String KEY_MIUI_VERSION_NANE = "ro.miui.ui.version.name"; // "V8"

    private static final String VALUE_MIUI_CLIENT_ID_BASE = "android-xiaomi";
    // 华为 : EMUI
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui"; // "EmotionUI_3.0"
    private static final String KEY_EMUI_SYSTEM_VERSION = "ro.confg.hw_systemversion"; // "T1-A21wV100R001C233B008_SYSIMG"
    // 魅族 : Flyme
    private static final String KEY_FLYME_PUBLISHED = "ro.flyme.published"; // "true"
    private static final String KEY_FLYME_SETUP = "ro.meizu.setupwizard.flyme"; // "true"

    private static final String VALUE_FLYME_DISPLAY_ID_CONTAIN = "Flyme"; // "Flyme OS 4.5.4.2U"
    // OPPO : ColorOS
    private static final String KEY_COLOROS_VERSION = "ro.oppo.theme.version"; // "703"
    private static final String KEY_COLOROS_THEME_VERSION = "ro.oppo.version"; // ""
    private static final String KEY_COLOROS_ROM_VERSION = "ro.rom.different.version"; // "ColorOS2.1"

    private static final String VALUE_COLOROS_BASE_OS_VERSION_CONTAIN = "OPPO"; // "OPPO/R7sm/R7sm:5.1.1/LMY47V/1440928800:user/release-keys"
    private static final String VALUE_COLOROS_CLIENT_ID_BASE = "android-oppo";
    // vivo : FuntouchOS
    private static final String KEY_FUNTOUCHOS_BOARD_VERSION = "ro.vivo.board.version"; // "MD"
    private static final String KEY_FUNTOUCHOS_OS_NAME = "ro.vivo.os.name"; // "Funtouch"
    private static final String KEY_FUNTOUCHOS_OS_VERSION = "ro.vivo.os.version"; // "3.0"
    private static final String KEY_FUNTOUCHOS_DISPLAY_ID = "ro.vivo.os.build.display.id"; // "FuntouchOS_3.0"
    private static final String KEY_FUNTOUCHOS_ROM_VERSION = "ro.vivo.rom.version"; // "rom_3.1"

    private static final String VALUE_FUNTOUCHOS_CLIENT_ID_BASE = "android-vivo";
    // Samsung
    private static final String VALUE_SAMSUNG_BASE_OS_VERSION_CONTAIN = "samsung"; // "samsung/zeroltezc/zeroltechn:6.0.1/MMB29K/G9250ZCU2DQD1:user/release-keys"
    private static final String VALUE_SAMSUNG_CLIENT_ID_BASE = "android-samsung";
    // Sony
    private static final String KEY_SONY_PROTOCOL_TYPE = "ro.sony.irremote.protocol_type"; // "2"
    private static final String KEY_SONY_ENCRYPTED_DATA = "ro.sony.fota.encrypteddata"; // "supported"

    private static final String VALUE_SONY_CLIENT_ID_BASE = "android-sonyericsson";
    // 乐视 : eui
    private static final String KEY_EUI_VERSION = "ro.letv.release.version"; // "5.9.023S"
    private static final String KEY_EUI_VERSION_DATE = "ro.letv.release.version_date"; // "5.9.023S_03111"
    private static final String KEY_EUI_NAME = "ro.product.letv_name"; // "乐1s"
    private static final String KEY_EUI_MODEL = "ro.product.letv_model"; // "Letv X500"
    // 金立 : amigo
    private static final String KEY_AMIGO_ROM_VERSION = "ro.gn.gnromvernumber"; // "GIONEE ROM5.0.16"
    private static final String KEY_AMIGO_SYSTEM_UI_SUPPORT = "ro.gn.amigo.systemui.support"; // "yes"

    private static final String VALUE_AMIGO_DISPLAY_ID_CONTAIN = "amigo"; // "amigo3.5.1"
    private static final String VALUE_AMIGO_CLIENT_ID_BASE = "android-gionee";
    // 酷派 : yulong
    private static final String KEY_YULONG_VERSION_RELEASE = "ro.yulong.version.release"; // "5.1.046.P1.150921.8676_M01"
    private static final String KEY_YULONG_VERSION_TAG = "ro.yulong.version.tag"; // "LC"

    private static final String VALUE_YULONG_CLIENT_ID_BASE = "android-coolpad";
    // HTC : Sense
    private static final String KEY_SENSE_BUILD_STAGE = "htc.build.stage"; // "2"
    private static final String KEY_SENSE_BLUETOOTH_SAP = "ro.htc.bluetooth.sap"; // "true"

    private static final String VALUE_SENSE_CLIENT_ID_BASE = "android-htc-rev";
    // LG : LG
    private static final String KEY_LG_SW_VERSION = "ro.lge.swversion"; // "D85720b"
    private static final String KEY_LG_SW_VERSION_SHORT = "ro.lge.swversion_short"; // "V20b"
    private static final String KEY_LG_FACTORY_VERSION = "ro.lge.factoryversion"; // "LGD857AT-00-V20b-CUO-CN-FEB-17-2015+0"
    // 联想
    private static final String KEY_LENOVO_DEVICE = "ro.lenovo.device"; // "phone"
    private static final String KEY_LENOVO_PLATFORM = "ro.lenovo.platform"; // "qualcomm"
    private static final String KEY_LENOVO_ADB = "ro.lenovo.adb"; // "apkctl,speedup"

    private static final String VALUE_LENOVO_CLIENT_ID_BASE = "android-lenovo";

    /**
     * 获取 ROM 类型
     *
     * @return ROM
     */
    public static ROM getRomType() {
        return ROM_TYPE;
    }

    /**
     * 初始化 ROM 类型
     */
    private static ROM initRomType() {
        ROM rom = ROM.Other;
        if (containsKey(KEY_MIUI_VERSION_NANE) || containsKey(KEY_MIUI_VERSION_CODE)) {
            // MIUI
            rom = ROM.MIUI;
            if (containsKey(KEY_MIUI_VERSION_NANE)) {
                String versionName = getProperty(KEY_MIUI_VERSION_NANE);
                if (!TextUtils.isEmpty(versionName) && versionName.matches("[Vv]\\d+")) { // V8
                    try {
                        rom.setBaseVersion(Integer.parseInt(versionName.split("[Vv]")[1]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (containsKey(KEY_MIUI_VERSION)) {
                String versionStr = getProperty(KEY_MIUI_VERSION);
                if (!TextUtils.isEmpty(versionStr) && versionStr.matches("[\\d.]+")) {
                    rom.setVersion(versionStr);
                }
            }
        } else if (containsKey(KEY_EMUI_VERSION) || containsKey(KEY_EMUI_API_LEVEL)
                || containsKey(KEY_EMUI_SYSTEM_VERSION)) {
            // EMUI
            rom = ROM.EMUI;
            if (containsKey(KEY_EMUI_VERSION)) {
                String versionStr = getProperty(KEY_EMUI_VERSION);
                Matcher matcher = Pattern.compile("EmotionUI_([\\d.]+)").matcher(versionStr); // EmotionUI_3.0
                if (!TextUtils.isEmpty(versionStr) && matcher.find()) {
                    try {
                        String version = matcher.group(1);
                        rom.setVersion(version);
                        rom.setBaseVersion(Integer.parseInt(version.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_FLYME_SETUP) || containsKey(KEY_FLYME_PUBLISHED)) {
            // Flyme
            rom = ROM.Flyme;
            if (containsKey(KEY_DISPLAY_ID)) {
                String versionStr = getProperty(KEY_DISPLAY_ID);
                Matcher matcher = Pattern.compile("Flyme[^\\d]*([\\d.]+)[^\\d]*").matcher(versionStr); // Flyme OS 4.5.4.2U
                if (!TextUtils.isEmpty(versionStr) && matcher.find()) {
                    try {
                        String version = matcher.group(1);
                        rom.setVersion(version);
                        rom.setBaseVersion(Integer.parseInt(version.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_COLOROS_VERSION) || containsKey(KEY_COLOROS_THEME_VERSION)
                || containsKey(KEY_COLOROS_ROM_VERSION)) {
            // ColorOS
            rom = ROM.ColorOS;
            if (containsKey(KEY_COLOROS_ROM_VERSION)) {
                String versionStr = getProperty(KEY_COLOROS_ROM_VERSION);
                Matcher matcher = Pattern.compile("ColorOS([\\d.]+)").matcher(versionStr); // ColorOS2.1
                if (!TextUtils.isEmpty(versionStr) && matcher.find()) {
                    try {
                        String version = matcher.group(1);
                        rom.setVersion(version);
                        rom.setBaseVersion(Integer.parseInt(version.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_FUNTOUCHOS_OS_NAME) || containsKey(KEY_FUNTOUCHOS_OS_VERSION)
                || containsKey(KEY_FUNTOUCHOS_DISPLAY_ID)) {
            // FuntouchOS
            rom = ROM.FuntouchOS;
            if (containsKey(KEY_FUNTOUCHOS_OS_VERSION)) {
                String versionStr = getProperty(KEY_FUNTOUCHOS_OS_VERSION);
                if (!TextUtils.isEmpty(versionStr) && versionStr.matches("[\\d.]+")) { // 3.0
                    try {
                        rom.setVersion(versionStr);
                        rom.setBaseVersion(Integer.parseInt(versionStr.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_EUI_VERSION) || containsKey(KEY_EUI_NAME)
                || containsKey(KEY_EUI_MODEL)) {
            // EUI
            rom = ROM.EUI;
            if (containsKey(KEY_EUI_VERSION)) {
                String versionStr = getProperty(KEY_EUI_VERSION);
                Matcher matcher = Pattern.compile("([\\d.]+)[^\\d]*").matcher(versionStr); // 5.9.023S
                if (!TextUtils.isEmpty(versionStr) && matcher.find()) {
                    try {
                        String version = matcher.group(1);
                        rom.setVersion(version);
                        rom.setBaseVersion(Integer.parseInt(version.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_AMIGO_ROM_VERSION) || containsKey(KEY_AMIGO_SYSTEM_UI_SUPPORT)) {
            // amigo
            rom = ROM.AmigoOS;
            if (containsKey(KEY_DISPLAY_ID)) {
                String versionStr = getProperty(KEY_DISPLAY_ID);
                Matcher matcher = Pattern.compile("amigo([\\d.]+)[a-zA-Z]*").matcher(versionStr); // "amigo3.5.1"
                if (!TextUtils.isEmpty(versionStr) && matcher.find()) {
                    try {
                        String version = matcher.group(1);
                        rom.setVersion(version);
                        rom.setBaseVersion(Integer.parseInt(version.split("\\.")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (containsKey(KEY_SONY_PROTOCOL_TYPE) || containsKey(KEY_SONY_ENCRYPTED_DATA)) {
            // Sony
            rom = ROM.Sony;
        } else if (containsKey(KEY_YULONG_VERSION_RELEASE) || containsKey(KEY_YULONG_VERSION_TAG)) {
            // YuLong
            rom = ROM.YuLong;
        } else if (containsKey(KEY_SENSE_BUILD_STAGE) || containsKey(KEY_SENSE_BLUETOOTH_SAP)) {
            // Sense
            rom = ROM.Sense;
        } else if (containsKey(KEY_LG_SW_VERSION) || containsKey(KEY_LG_SW_VERSION_SHORT)
                || containsKey(KEY_LG_FACTORY_VERSION)) {
            // LG
            rom = ROM.LG;
        } else if (containsKey(KEY_LENOVO_DEVICE) || containsKey(KEY_LENOVO_PLATFORM)
                || containsKey(KEY_LENOVO_ADB)) {
            // Lenovo
            rom = ROM.Lenovo;
        } else if (containsKey(KEY_DISPLAY_ID)) {
            String displayId = getProperty(KEY_DISPLAY_ID);
            if (!TextUtils.isEmpty(displayId)) {
                if (displayId.contains(VALUE_FLYME_DISPLAY_ID_CONTAIN)) {
                    return ROM.Flyme;
                } else if (displayId.contains(VALUE_AMIGO_DISPLAY_ID_CONTAIN)) {
                    return ROM.AmigoOS;
                }
            }
        } else if (containsKey(KEY_BASE_OS_VERSION)) {
            String baseOsVersion = getProperty(KEY_BASE_OS_VERSION);
            if (!TextUtils.isEmpty(baseOsVersion)) {
                if (baseOsVersion.contains(VALUE_COLOROS_BASE_OS_VERSION_CONTAIN)) {
                    return ROM.ColorOS;
                } else if (baseOsVersion.contains(VALUE_SAMSUNG_BASE_OS_VERSION_CONTAIN)) {
                    return ROM.SamSung;
                }
            }
        } else if (containsKey(KEY_CLIENT_ID_BASE)) {
            String clientIdBase = getProperty(KEY_CLIENT_ID_BASE);
            switch (clientIdBase) {
                case VALUE_MIUI_CLIENT_ID_BASE:
                    return ROM.MIUI;
                case VALUE_COLOROS_CLIENT_ID_BASE:
                    return ROM.ColorOS;
                case VALUE_FUNTOUCHOS_CLIENT_ID_BASE:
                    return ROM.FuntouchOS;
                case VALUE_SAMSUNG_CLIENT_ID_BASE:
                    return ROM.SamSung;
                case VALUE_SONY_CLIENT_ID_BASE:
                    return ROM.Sony;
                case VALUE_YULONG_CLIENT_ID_BASE:
                    return ROM.YuLong;
                case VALUE_SENSE_CLIENT_ID_BASE:
                    return ROM.Sense;
                case VALUE_LENOVO_CLIENT_ID_BASE:
                    return ROM.Lenovo;
                case VALUE_AMIGO_CLIENT_ID_BASE:
                    return ROM.AmigoOS;
                default:
                    break;
            }
        }
        return rom;
    }

    /**
     * @param propName
     * @return
     */
    private static boolean containsKey(String propName) {
        if (TextUtils.isEmpty(getProperty(propName))) {
            return false;
        }
        return true;
    }

    private static String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) value = (String) roSecureObj;
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }


    public enum ROM {
        MIUI, // 小米
        Flyme, // 魅族
        EMUI, // 华为
        ColorOS, // OPPO
        FuntouchOS, // vivo
        SmartisanOS, // 锤子
        EUI, // 乐视
        Sense, // HTC
        AmigoOS, // 金立
        _360OS, // 奇酷360
        NubiaUI, // 努比亚
        H2OS, // 一加
        YunOS, // 阿里巴巴
        YuLong, // 酷派

        SamSung, // 三星
        Sony, // 索尼
        Lenovo, // 联想
        LG, // LG

        Google, // 原生

        Other; // CyanogenMod, Lewa OS, 百度云OS, Tencent OS, 深度OS, IUNI OS, Tapas OS, Mokee

        private int baseVersion = -1;
        private String version;

        void setVersion(String version) {
            this.version = version;
        }

        void setBaseVersion(int baseVersion) {
            this.baseVersion = baseVersion;
        }

        public int getBaseVersion() {
            return baseVersion;
        }

        public String getVersion() {
            return version;
        }
    }
}
