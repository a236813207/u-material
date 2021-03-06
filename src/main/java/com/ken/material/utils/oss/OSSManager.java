package com.ken.material.utils.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

/**
 * @author Ken
 * @date 2020/3/28
 * @description
 */
public class OSSManager {
    private static final String DIR = "material/svg/";
    private OSSClient client;
    private Map<String, String> buckets;

    public OSSManager(OSSClient ossClient, Map<String, String> buckets) {
        this.client = ossClient;
        this.buckets = buckets;
    }

    public String putObject(String dir, String bucketName, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(dir)) {
            dir = DIR;
        }
        //先将流写入临时文件，做为上传使用，和计算md5的值
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        File dest = new File(tmpdir, UUID.randomUUID().toString());
        String fileName = file.getOriginalFilename();
        String suffix = ".jpg";
        if (StringUtils.isNotBlank(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        DigestInputStream digestInput = new DigestInputStream(file.getInputStream(), md);
        OutputStream output = new FileOutputStream(dest);
        IOUtils.copy(digestInput, output);
        IOUtils.closeQuietly(output);
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(md.digest());
        String name = dir + md5 + suffix;
        IOUtils.closeQuietly(digestInput);
        InputStream localInputStream = new BufferedInputStream(new FileInputStream(dest));
        PutObjectResult result = upload(bucketName, name, localInputStream, file.getContentType());
        IOUtils.closeQuietly(localInputStream);
        if (dest.exists()) {
            dest.delete();
        }
        if (StringUtils.isEmpty(result.getETag())) {
            throw new IOException("can not upload file at this moment.");
        }/* else if (!result.getETag().equalsIgnoreCase(md5)) {
            throw new IOException("the etag difference form before upload,may be some one had change it on uploading!");
        }*/
        return this.buckets.get(bucketName) + "/" + name;
    }

    public String putObject(String dir, String bucketName, File file) throws IOException, NoSuchAlgorithmException {
        if (StringUtils.isBlank(dir)) {
            dir = DIR;
        }
        //先将流写入临时文件，做为上传使用，和计算md5的值
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        File dest = new File(tmpdir, UUID.randomUUID().toString());
        String fileName = file.getName();
        String suffix = ".jpg";
        if (StringUtils.isNotBlank(fileName)) {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        DigestInputStream digestInput = new DigestInputStream(new FileInputStream(file), md);
        OutputStream output = new FileOutputStream(dest);
        IOUtils.copy(digestInput, output);
        IOUtils.closeQuietly(output);
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(md.digest());
        String name = dir + md5 + suffix;
        IOUtils.closeQuietly(digestInput);
        InputStream localInputStream = new BufferedInputStream(new FileInputStream(dest));
        PutObjectResult result = upload(bucketName, name, localInputStream, Files.probeContentType(file.toPath()));
        IOUtils.closeQuietly(localInputStream);
        if (dest.exists()) {
            dest.delete();
        }
        if (StringUtils.isEmpty(result.getETag())) {
            throw new IOException("can not upload file at this moment.");
        }/* else if (!result.getETag().equalsIgnoreCase(md5)) {
            throw new IOException("the etag difference form before upload,may be some one had change it on uploading!");
        }*/
        return this.buckets.get(bucketName) + "/" + name;
    }

    /**
     * 上传一个文件
     *
     * @param bucketName
     * @return 文件路径
     * @throws FileNotFoundException
     */
    public String putObject(String bucketName, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return putObject(DIR, bucketName, file);
    }

    private PutObjectResult upload(String bucketName, String fileName,
                                   InputStream content, String contentType) throws IOException {
        if (!client.doesBucketExist(bucketName)) {
            throw new IllegalArgumentException(
                    "your project does not exit on this oss.");
        }
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(content.available());
        if (StringUtils.isNotEmpty(contentType)) {
            meta.setContentType(contentType);
        }

        return client.putObject(bucketName, fileName, content, meta);
    }

    public String putObject(String bucketName, String imageData) throws IOException {
        return putObject(DIR, bucketName, imageData);
    }

    public String putObject(String dir, String bucketName, String imageData) throws IOException {
        if (StringUtils.isBlank(dir)) {
            dir = DIR;
        }
        String imageDataCopy = imageData;
        String suffix = ".jpg";
        String contentType = "image/jpeg";
        if (imageData.contains("data:image/png;base64,")) {
            imageDataCopy = imageData.substring("data:image/png;base64,".length()).replace(" ", "+");
            contentType = "image/png";
            suffix = ".png";
        } else if (imageData.contains("data:image/jpeg;base64,")) {
            imageDataCopy = imageData.substring("data:image/jpeg;base64,".length()).replace(" ", "+");
            contentType = "image/jpg";
            suffix = ".jpg";
        }
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        File dest = new File(tmpdir, UUID.randomUUID().toString());
        generateImage(imageDataCopy, dest.getAbsolutePath());
        InputStream localInputStream = new BufferedInputStream(new FileInputStream(dest));
        String md5 = DigestUtils.md5DigestAsHex(new FileInputStream(dest));
        String name = dir + md5 + suffix;
        PutObjectResult result = upload(bucketName, name, localInputStream, contentType);
        IOUtils.closeQuietly(localInputStream);
        if (dest.exists()) {
            dest.delete();
        }
        if (StringUtils.isEmpty(result.getETag())) {
            throw new IOException("can not upload file at this moment.");
        } /*else if (!result.getETag().equalsIgnoreCase(md5)) {
            throw new IOException("the etag difference form before upload,may be some one had change it on uploading!");
        }*/
        return this.buckets.get(bucketName) + "/" + name;
    }


    public boolean generateImage(String imgStr, String path) {
        if (imgStr == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
