package com.ken.material.common.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 输入流可重复读的request
 * @author ken
 * @version 1.0
 * @date 2020-09-14
 */
public class BufferRequest extends HttpServletRequestWrapper {
    private static final Logger logger = LoggerFactory.getLogger(BufferRequest.class);
    private byte[] buffer;

    public BufferRequest(HttpServletRequest request) {
        super(request);
        request.getParameterMap();
        try {
            buffer = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            logger.error("stream copy to array error", e);
        }
    }


    @Override
    public String getCharacterEncoding() {
        String enc = super.getCharacterEncoding();
        return (enc != null ? enc : WebUtils.DEFAULT_CHARACTER_ENCODING);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (buffer != null) {
            return new ServletInputStream() {
                ByteArrayInputStream bis = new ByteArrayInputStream(buffer);

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }

                @Override
                public int read() {
                    return bis.read();
                }
            };
        }
        return super.getInputStream();
    }

    public String getRequestBody() throws IOException {
        BufferedReader reader = getReader();
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        do {
            line = reader.readLine();
            if (null != line) {
                inputBuffer.append(line.trim());
            }
        } while (line != null);
        reader.close();
        return inputBuffer.toString().trim();
    }

}
