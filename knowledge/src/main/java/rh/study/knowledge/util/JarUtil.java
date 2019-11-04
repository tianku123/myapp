package rh.study.knowledge.util;

/**
 * Created by 18121254 on 2019/9/27.
 */
public class JarUtil {

    /**
     * 获取jar包所在目录
     *
     * @return
     */
    public static String getJarPath() {
        String path = JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (path.toUpperCase().indexOf(".JAR") != -1) {
            try {
                //截取".JAR第一次出现前的字符串"
                String StrPath = path.substring(0, path.toUpperCase().indexOf(".jar".toUpperCase()));
                //获取“.jar”包的上一层文件夹
                path = StrPath.substring(0, StrPath.lastIndexOf("/") + 1);
            } catch (Exception e) {
                return "出错了:" + e.toString();
            }
        }
        return path;
    }
}
