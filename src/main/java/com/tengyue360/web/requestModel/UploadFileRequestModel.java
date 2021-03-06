package com.tengyue360.web.requestModel;


import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 * 封装上传图片请求参数1
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class UploadFileRequestModel extends BaseRequestModel {


    private MultipartFile file;

    private String relationId;

    private String uploadType;

    private String attachaFileId;//附件id


    public String getAttachaFileId() {
        return attachaFileId;
    }

    public void setAttachaFileId(String attachaFileId) {
        this.attachaFileId = attachaFileId;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }


    @Override
    public String toString() {
        return "UploadFileRequestModel{" +
                "file=" + file +
                ", relationId='" + relationId + '\'' +
                ", uploadType='" + uploadType + '\'' +
                ", attachaFileId='" + attachaFileId + '\'' +
                '}';
    }
}
