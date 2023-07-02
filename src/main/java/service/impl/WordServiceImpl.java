package service.impl;

import com.aspose.words.SaveFormat;
import org.apache.commons.io.FilenameUtils;
import service.DocumentService;
import utils.PathUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Word操作类
 */
public class WordServiceImpl implements DocumentService {

    /**
     * Word 转其他文件
     *
     * @param filePath
     * @param type
     * @return
     */
    @Override
    public String convertFile(String filePath, String type) {
        String checkType = FilenameUtils.getExtension(filePath);
        if (!"doc".equals(checkType) && !"docx".equals(checkType)) {
            throw new RuntimeException("输入文件不是Word文件！");
        }
        try {
            switch (type.toUpperCase()) {
                case "TEXT": {
                    return switchFile(filePath, SaveFormat.TEXT, "txt");
                }
                case "PDF": {
                    return switchFile(filePath, com.aspose.words.SaveFormat.PDF, "pdf");
                }
                /*************** 需要操作每一页Word文件，一般Word类的直接电脑操作，应该用不上************/
//                case "PNG" : {
//                    return switchFile(file, com.aspose.words.SaveFormat.PNG, "png");
//                }
//                case "JPG" : {
//                    return switchFile(file, com.aspose.words.SaveFormat.JPEG, "jpg");
//                }
                default: {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String switchFile(String filePath, int saveFormat, String suffix) {
        String url = "";
        try {
            // 输出路径
            String fileName = PathUtils.getFilePath(filePath, suffix);
            FileOutputStream os = new FileOutputStream(fileName);
            FileInputStream is = new FileInputStream(filePath);
            com.aspose.words.Document doc = new com.aspose.words.Document(is);
            doc.save(os, saveFormat);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
