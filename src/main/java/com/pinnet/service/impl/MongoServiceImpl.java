package com.pinnet.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.pinnet.service.IMongoService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class MongoServiceImpl implements IMongoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 上传文件
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public String uploadFile(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename());
        return objectId.toHexString();
    }

    /**
     * 下载文件
     * @param map
     * @param response
     * @throws IOException
     */
    @Override
    public void downloadFile(Map<String, Object> map, HttpServletResponse response) throws IOException {
        gridFSBucket.downloadToStream(new ObjectId(String.valueOf(map.get("mongoId"))), response.getOutputStream());
    }

    /**
     * 删除文件
     * @param map
     */
    @Override
    public void deleteFile(Map<String, Object> map) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(map.get("mongoId"))));
    }
}
