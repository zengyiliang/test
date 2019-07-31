package com.xhg.ops.common;

import com.xhg.core.util.OSSClientUtils;
import com.xhg.core.web.vo.ResponseBean;
import com.xhg.core.web.vo.RsBody;
import com.xhg.ops.system.vo.image.ImageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ComServiceImpl implements ComService {

    @Override
    public RsBody<ImageVO> uploadImg(MultipartFile multipartFile) throws Exception {

        RsBody<ImageVO> rsBody=new RsBody<>();
        ImageVO imageVO=new ImageVO();
        String name=multipartFile.getOriginalFilename();
        //设置允许上传文件类型
        String suffixList = "jpg,png";
        String suffix = name.substring(name.lastIndexOf(".") + 1, name.length());
        if (!suffixList.contains(suffix.trim().toLowerCase())) {
            rsBody.setCode("-1");
            rsBody.setMessage("图片格式不正确，当前支持的格式为："+suffixList);
        }else {
            String url = OSSClientUtils.uploadImg2Oss(multipartFile);
            imageVO.setUrl(url);
            imageVO.setImgName(name);
            rsBody.setData(imageVO);
        }
        return rsBody;
    }
}
