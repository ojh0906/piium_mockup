package com.itso.market.mobile.controller;

import com.itso.market.mobile.model.*;
import com.itso.market.mobile.service.MemberService;
import com.itso.market.mobile.service.NoticeService;
import com.itso.market.mobile.service.StoreService;
import com.itso.market.security.service.SecurityUserDetailsService;
import com.itso.market.util.FileUtils;
import com.itso.market.util.MediaUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file/*")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseOverlays<List<FILE>> upload(MultipartHttpServletRequest mtfRequest) {
        List<FILE> resultLsit = new ArrayList<>();
        try {
            mtfRequest.getMultiFileMap();
            for(String key : mtfRequest.getMultiFileMap().keySet()){
                FILE result = new FILE();
                String uuid = UUID.randomUUID().toString();
                MultipartFile file = mtfRequest.getMultiFileMap().get(key).get(0);
                String oriName = file.getOriginalFilename();
                String fileType = oriName.substring(oriName.indexOf("."));
                String path = FileUtils.upload(uploadDir, uuid+fileType, file.getBytes());
                result.setName(file.getOriginalFilename());
                result.setUuid(uuid);
                result.setPath(path);
                resultLsit.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseOverlays<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"UPLOAD_FILE_FAIL", null);
        }
        return new ResponseOverlays<>(HttpServletResponse.SC_OK,"UPLOAD_FILE_SUCCESS", resultLsit);
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(String fileName, HttpServletRequest request) throws Exception {
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        String header = request.getHeader("User-Agent");

        try {
            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(uploadDir + fileName);

            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }
        return entity;
    }

    @RequestMapping("/filedown")
    public ResponseEntity<byte[]> filedownload(String fileName, HttpServletRequest request) throws Exception {
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        String header = request.getHeader("User-Agent");
        logger.info("FILE NAME : " + fileName);
        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            MediaType mType = MediaUtils.getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            in = new FileInputStream(uploadDir + fileName);

            if (mType != null) {
                //headers.setContentType(mType);
                if (header.contains("MSIE") || header.contains("Trident")) {
                    fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                fileName = fileName.substring(fileName.indexOf("/") + 1);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            } else {
                if (header.contains("MSIE") || header.contains("Trident")) {
                    fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
                } else {
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                fileName = fileName.substring(fileName.indexOf("/") + 1);
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            }
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close();
        }
        return entity;
    }
}