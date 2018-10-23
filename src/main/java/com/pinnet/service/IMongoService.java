package com.pinnet.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Funtion: TODO.
 * <p>
 * Author: lWX559685
 * Date: 2018/10/23 13:36
 */
public interface IMongoService {

    public String uploadFile(HttpServletRequest request) throws IOException;
    public void downloadFile(Map<String, Object> map, HttpServletResponse response) throws IOException;
    public void deleteFile(Map<String, Object> map);
}
