package com.pinnet.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import com.pinnet.service.IMongoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.List;

@Service
@Transactional
public class MongoServiceImpl implements IMongoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public String uploadFile(File file) throws FileNotFoundException {
        ObjectId objectId = gridFsTemplate.store(new FileInputStream(file), file.getName());
        return objectId.toHexString();
    }

    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @return
     */
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        ObjectId objectId = gridFsTemplate.store(inputStream, fileName);
        return objectId.toHexString();
    }

    /**
     * 下载文件
     * @param mongoId
     * @param outputStream
     */
    @Override
    public void downloadFile(String mongoId, OutputStream outputStream) throws IOException {
//        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(mongoId)));
//        GridFsResource gridFsResource = new GridFsResource(gridFSFile);
//        IOUtils.copy(gridFsResource.getInputStream(), outputStream);
        //上面这种方式也可以实现下载
        gridFSBucket.downloadToStream(new ObjectId(mongoId), outputStream);
    }

    /**
     * 删除文件
     * @param mongoId
     */
    @Override
    public void deleteFile(String mongoId) {
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is(mongoId)));
    }

    /**
     * 保存对象
     * @param t
     * @param <T>
     */
    @Override
    public <T> void save(T t) {
        mongoTemplate.save(t);
    }

    /**
     * 查询对象
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> List<T> find(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    /**
     * 查询单个对象
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T findOne(Query query, Class<T> clazz) {
        return mongoTemplate.findOne(query, clazz);
    }

    /**
     * 删除对象
     * @param query
     * @param clazz
     * @param <T>
     */
    @Override
    public <T> void delete(Query query, Class<T> clazz) {
        mongoTemplate.findAndRemove(query, clazz);
    }

}
