package cn.windf.core.util;

public class SystemUtil {
    /**
     * 当前是否是windows系统
     * @return
     */
    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
    }
}
