package service.impl;

import com.aspose.slides.Presentation;
import org.apache.commons.io.FilenameUtils;
import service.DocumentService;
import utils.PathUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PPT  操作类
 */
public class PptServiceImpl implements DocumentService {

    /**
     * PPT 转其他文件
     *
     * @param filePath
     * @param type
     * @return
     */
    @Override
    public String convertFile(String filePath, String type) {
        // 获取文件后缀名
        String checkType = FilenameUtils.getExtension(filePath);
        if (!"ppt".equals(checkType) && !"pptx".equals(checkType)) {
            throw new RuntimeException("输入文件不是PPT文件！");
        }
        try {
            switch (type.toUpperCase()) {
                case "HTML": {
                    return SwitchFile(filePath, com.aspose.slides.SaveFormat.Html, "html");
                }
                case "HTML5": {
                    return SwitchFile(filePath, com.aspose.slides.SaveFormat.Html5, "html");
                }
                case "PDF": {
                    return SwitchFile(filePath, com.aspose.slides.SaveFormat.Pdf, "pdf");
                }
                default: {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String SwitchFile(String filePath, int saveFormat, String suffix) {
        String url = "";
        try {
            String fileName = PathUtils.getFilePath(filePath, suffix);
            FileOutputStream os = new FileOutputStream(fileName);
            //加载源文件数据
            FileInputStream is = new FileInputStream(filePath);
            Presentation ppt = new Presentation(is);
            //设置转换文件类型并转换
            ppt.save(os, saveFormat);
            os.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }


}
