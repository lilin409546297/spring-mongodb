package com.pinnet.service;

import org.springframework.data.mongodb.core.query.Query;

import java.io.*;
import java.util.List;

public interface IMongoService {

    /**
     * 上传文件
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    String uploadFile(File file) throws FileNotFoundException;


    /**
     * 上传文件
     * @param inputStream
     * @param fileName
     * @return
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 下载文件
     * @param mongoId
     * @param outputStream
     */
    void downloadFile(String mongoId, OutputStream outputStream) throws IOException;

    /**
     * 删除文件
     * @param mongoId
     */
    void deleteFile(String mongoId);

    /**
     * 保存对象
     * @param t
     * @param <T>
     */
    <T> void save(T t);

    /**
     * 查询对象
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> find(Query query, Class<T> clazz);

    /**
     * 查询单个对象
     * @param query
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T findOne(Query query, Class<T> clazz);

    /**
     * 删除对象
     * @param query
     * @param clazz
     * @param <T>
     */
    <T> void delete(Query query, Class<T> clazz);
}
