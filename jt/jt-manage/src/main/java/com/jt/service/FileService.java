package com.jt.service;

import com.jt.vo.EasyUI_Image;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    EasyUI_Image fileUpload(MultipartFile uploadFile);
}
