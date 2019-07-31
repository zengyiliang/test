package com.xhg.ops.common;

import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.system.vo.image.ImageVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ComService {

    RsBody<ImageVO> uploadImg(MultipartFile multipartFile)throws Exception;
}
