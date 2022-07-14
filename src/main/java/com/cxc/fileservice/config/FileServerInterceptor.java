package com.cxc.fileservice.config;

import com.cxc.fileservice.constant.ReqParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author chenxiangcai
 */
@Component
public class FileServerInterceptor implements HandlerInterceptor {

    @Value("${fileServer.resource-location}")
    private String resourceLocation;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String matchUri = request.getRequestURI();
        String fsPath = resourceLocation.replace("file:", "")+matchUri;
        File file = new File(fsPath);
        if(file.isDirectory()){
            request.setAttribute(ReqParam.REQ_URI, request.getRequestURI());
            request.setAttribute(ReqParam.FILE_SERVER_RES_PATH,fsPath);
            request.getRequestDispatcher("/index").forward(request,response);
            return false;
        }
        return true;
    }
}
