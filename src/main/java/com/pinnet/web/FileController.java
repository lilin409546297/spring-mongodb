package com.pinnet.web;

import com.pinnet.service.IMongoService;
import com.pinnet.util.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IMongoService mongoService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResultInfo uploadFile(MultipartFile multipartFile) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            resultInfo.setData(mongoService.uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename()));
        } catch (Exception e) {
            logger.error("upload file error:", e);
            resultInfo.setStatus(false);
            resultInfo.setFailCode(e.getMessage());
        }
        return resultInfo;
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    public ResultInfo downloadFile(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=test.mp3");
            mongoService.downloadFile(map.get("mongoId").toString(), response.getOutputStream());
        } catch (Exception e) {
            logger.error("download file error:", e);
            resultInfo.setStatus(false);
            resultInfo.setFailCode(e.getMessage());
        }
        return resultInfo;
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResultInfo deleteFile(@RequestBody Map<String, Object> map) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            mongoService.deleteFile(map.get("mongoId").toString());
        } catch (Exception e) {
            logger.error("delete file error:", e);
            resultInfo.setStatus(false);
            resultInfo.setFailCode(e.getMessage());
        }
        return resultInfo;
    }
}
