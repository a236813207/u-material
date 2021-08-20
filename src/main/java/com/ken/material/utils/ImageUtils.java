package com.ken.material.utils;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author ken
 * @version 1.0
 * @date 2021-08-15
 */
public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static void svg2Jpg(File svg, File jpg) throws IOException, TranscoderException {
        try (InputStream in =new FileInputStream(svg);
             OutputStream out = new BufferedOutputStream(new FileOutputStream(jpg))) {
                Transcoder tr = new JPEGTranscoder();
                TranscoderInput input = new TranscoderInput(in);
                TranscoderOutput output = new TranscoderOutput(out);
                tr.transcode(input, output);
        }catch (Exception e) {
            logger.error("ImageUtils svg2Jpg error:[{}]", e.getMessage(), e);
            throw e;
        }
    }

    /**
     *
     * @param urlString 需要下载图片的路径
     * @param filename  下载后图片的命名
     * @param savePath  下载到那个文件夹下
     * @throws Exception
     */
    public static void download(String urlString, String filename, String savePath) throws Exception {
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf = new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            OutputStream os = new FileOutputStream(sf.getPath()+ File.separator + filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
        } catch (Exception e) {
            logger.error("ImageUtils download error:[{}]", e.getMessage(), e);
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            download("http://w1chat.oss-cn-shenzhen.aliyuncs.com/material/fc6a4f91f45a4d0102f4097afa3dcb8a.png", "3.jpg", "H:\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
