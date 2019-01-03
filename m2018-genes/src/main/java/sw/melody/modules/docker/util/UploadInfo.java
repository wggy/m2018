package sw.melody.modules.docker.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadInfo {
    private String md5;
    private String chunks;
    private String chunk;
    private String path;
    private String fileName;
    private String ext;
    private String guid;

    public UploadInfo(String md5, String chunks, String chunk, String path, String fileName, String ext, String guid) {
        this.md5 = md5;
        this.chunks = chunks;
        this.chunk = chunk;
        this.path = path;
        this.fileName = fileName;
        this.ext = ext;
        this.guid = guid;
    }

    public UploadInfo(String md5, String chunks, String chunk, String path, String fileName, String ext) {
        this.md5 = md5;
        this.chunks = chunks;
        this.chunk = chunk;
        this.path = path;
        this.fileName = fileName;
        this.ext = ext;
    }
}
