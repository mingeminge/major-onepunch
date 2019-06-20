package com.yzm.majorserver.common.utils;

import com.yzm.majorserver.common.enums.ResultEnum;
import com.yzm.majorserver.core.exception.MajorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 20:11 2019/6/14
 * ===========================
 */
@Slf4j
public class FileUploadUtil {

    private static final String FILE_TYPE = "^\\.(jpeg|jpg|png|gif)$";

    /**
     * 文件上传
     *
     * @param multipartFile 文件
     * @param filePath      文件上传路径
     * @return
     */
    public static String uploadImg(MultipartFile multipartFile, String filePath) {
        if (multipartFile.isEmpty()) {
            return null;
        }
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            if (!fileType.matches(FILE_TYPE)) {
                log.error("******************该文件不是一个图片!");
                throw new MajorException(ResultEnum.IS_NOT_IMAGES);
            }
            String fileName = System.currentTimeMillis() + fileType;
            File file = new File(filePath + fileName);
            multipartFile.transferTo(file);
            log.info("文件上传成功");
            return fileName;
        } catch (Exception e) {
            log.error("文件上传失败,原因:", e.getMessage());
        }
        return null;
    }
}
