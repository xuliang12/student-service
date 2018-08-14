package com.tengyue360.service.impl;

import com.tengyue360.bean.SsAttachFilePath;
import com.tengyue360.common.ReturnCode;
import com.tengyue360.dao.SsAttachFilePathMapper;
import com.tengyue360.service.SsAttachFilePathService;
import com.tengyue360.utils.OssFileUtil;
import com.tengyue360.web.requestModel.UploadFileRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


/**
 * 附件服务
 *
 * @author xuliang
 * @date 2018/8/10 10:03
 */
@Service
public class SsAttachFilePathServiceImpl implements SsAttachFilePathService {


    @Autowired
    SsAttachFilePathMapper attachFilePathMapper;


    /**
     * 保存附件信息
     *
     * @return
     * @throws Exception
     */

    @Override
    public ResponseResult saveAttachaFile(UploadFileRequestModel model) {

        ResponseResult responseResult = new ResponseResult();
        try {
            //oss返回url 路径
            String url = OssFileUtil.getFileUrl(model.getFile().getBytes(), model.getFile().getOriginalFilename());

            //插入附件信息
            int num = attachFilePathMapper.insert(neWattachFilePath(url, model.getStudentId(),
                    model.getFile().getOriginalFilename(), model.getUploadType()));
            if (num > 0) {
                responseResult.setCode(ReturnCode.ACTIVE_SUCCESS.code());
                responseResult.setMsg(ReturnCode.ACTIVE_SUCCESS.msg());
                responseResult.setData(null);
                return responseResult;
            }
            responseResult.setCode(ReturnCode.ACTIVE_FAILURE.code());
            responseResult.setMsg(ReturnCode.ACTIVE_FAILURE.msg());
            responseResult.setData(null);
            return responseResult;
        } catch (Exception ex) {
            responseResult.setCode(ReturnCode.ACTIVE_EXCEPTION.code());
            responseResult.setMsg(ReturnCode.ACTIVE_EXCEPTION.msg());
            responseResult.setData(null);
        }
        return responseResult;
    }

    /**
     * 封装attachFilePath 对象
     *
     * @return
     * @throws Exception
     */

    public SsAttachFilePath neWattachFilePath(String url, String relationId, String fileName, String uploadType) {
        SsAttachFilePath ssAttachFilePath = new SsAttachFilePath();
        ssAttachFilePath.setId(UUID.randomUUID().toString());
        ssAttachFilePath.setAttachName(fileName);
        ssAttachFilePath.setAttachPath(url);
        ssAttachFilePath.setAttachType(uploadType);
        ssAttachFilePath.setRelationId(relationId);//关联id
        ssAttachFilePath.setCreateTime(new Date());
        return ssAttachFilePath;
    }


}