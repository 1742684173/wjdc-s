package com.aloogn.wjdc.filter;

import com.mysql.cj.util.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Enumeration;

/**
 * 修改或者添加参数
 *
 * @Author: Mr_li
 * @CreateDate: 2019-04-20 11:56
 * @Version: 1.0
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储请求数据
     */
    private String body;

    public RequestWrapper(HttpServletRequest request) {
        super(request);

        this.renewBody(request);
    }

    /**
     * 重写getInputStream方法
     *
     * @return
     */
    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        ServletInputStream servletInputStream = new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
        return servletInputStream;

    }

    /**
     * 重写getReader方法
     *
     * @return
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * 读取body的值
     *
     * @param request
     */
    private void renewBody(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        String contentType = request.getContentType();

        if (!StringUtils.isNullOrEmpty(contentType) &&
                (contentType.contains("multipart/form-data") ||
                        contentType.contains("x-www-form-urlencoded"))){

            Enumeration<String> pars=request.getParameterNames();

            while(pars.hasMoreElements()){
                String key=pars.nextElement();
                String value=request.getParameter(key);

                stringBuilder.append(key).append("=").append(value).append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }else{
            try {
                InputStream inputStream = request.getInputStream();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        body = stringBuilder.toString();
    }

    public String getBody() {
        return body;
    }
}
