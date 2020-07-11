package cn.wtu.sj.api.dto;

import cn.wtu.sj.utils.MultipartFileUtils;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/11 18:47
 */
@Data
public class EssayDTO implements Serializable {
    private String upFile;
    private String title;
    private String content;
    private List<MultipartFile> multipartFiles;
    private List<String> images;
    private String createDate;
    private Integer essayId;
    private Integer like;
    private String likeFlag;
    private Integer commentNum;
    private String userName;
    private Integer userId;
    private Integer reportNum;

    public EssayDTO(String upFile, String title, String content) {
        this.upFile = upFile;
        this.title = title;
        this.content = content;
        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        if (!StringUtils.isEmpty(upFile) && upFile.split("--").length>0){
            String[] split = upFile.split("--");
            for (String s : split) {
                multipartFiles.add(MultipartFileUtils.base64ToMultipart(s));
            }
        }
        this.multipartFiles = multipartFiles;
    }

    public EssayDTO() {
    }
}
