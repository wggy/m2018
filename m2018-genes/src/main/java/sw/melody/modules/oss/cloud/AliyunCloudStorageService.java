package sw.melody.modules.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sw.melody.common.exception.RRException;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 阿里云存储
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 16:22
 */
@Slf4j
@Setter
@Getter
public class AliyunCloudStorageService extends CloudStorageService implements Closeable {
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e) {
            throw new RRException("上传文件失败，请检查配置信息", e);
        }

        return config.getAliyunDomain() + "/" + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public void download(String location, String objectName) throws Throwable {
        log.info("文件：{} >>>>>>>>>>>>开始下载", location);
        // 下载请求，5个任务并发下载，启动断点续传。
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(config.getAliyunBucketName(), objectName);
        downloadFileRequest.setDownloadFile(location);
        downloadFileRequest.setPartSize(5 * 1024 * 1024);
        downloadFileRequest.setTaskNum(5);
        downloadFileRequest.setEnableCheckpoint(true);
        DownloadFileResult downloadRes = client.downloadFile(downloadFileRequest);
        // 下载成功时，会返回文件元信息。
        downloadRes.getObjectMetadata();
        log.info("文件：{} >>>>>>>>>>>>下载完成", location);
    }

    @Override
    public void close() throws IOException {
        client.shutdown();
    }
}
