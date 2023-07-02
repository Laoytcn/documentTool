package utils;

import org.apache.commons.lang3.StringUtils;

public class PathUtils {

    /**
     * 获得输出路径及文件名
     *
     * @param filePath
     * @param suffix
     * @return
     */
    public static String getFilePath(String filePath, String suffix) {
        int startIndex = StringUtils.lastIndexOf(filePath, ".");
        String fileName = StringUtils.substring(filePath, 0, startIndex);
        return fileName + "." + suffix;
    }


    public static String getFilePath(String filePath) {
        int index = StringUtils.lastIndexOf(filePath, "/");
        return StringUtils.substring(filePath, 0, index);
    }
}
