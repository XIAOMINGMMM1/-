package cn.wtu.sj.api.controller;

import cn.wtu.sj.api.dto.EssayDTO;
import cn.wtu.sj.entity.Essay;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.service.EssayService;
import cn.wtu.sj.service.UserService;
import cn.wtu.sj.utils.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/6 15:40
 */
@RestController
public class EssayController {

    @Autowired
    private EssayService essayService;

    @Autowired
    private UserService userService;

    @PostMapping("/ordinary/essay/upload")
    public ResponseEntity<Essay> upload(EssayDTO essayDTO) throws FileNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.selectUserByName(userDetails.getUsername());

        //转化文件信息
        ArrayList<MultipartFile> multipartFiles = new ArrayList<>();
        if (!StringUtils.isEmpty(essayDTO.getUpFile()) && essayDTO.getUpFile().split("--").length>0){
            String[] split = essayDTO.getUpFile().split("--");
            for (String s : split) {
                multipartFiles.add(MultipartFileUtils.base64ToMultipart(s));
            }
        }
        essayDTO.setMultipartFiles(multipartFiles);

        Essay essay = essayService.saveImages(essayDTO, user);
        return ResponseEntity.ok(essay);
    }
    @RequestMapping("/ordinary/essay/onclick-like")
    public ResponseEntity<Essay> onclickLikeOne(Integer essayId,String flag){
        Essay essay = essayService.onclickLikeOne(essayId,flag);
        return ResponseEntity.ok(essay);
    }

    @RequestMapping("/ordinary/essay/delete-essay")
    public ResponseEntity<Essay> deleteEssayByEssayId(Integer essayId){
        Essay essay = essayService.deleteByEssayId(essayId);
        return ResponseEntity.ok(essay);
    }

    @GetMapping("/ordinary/essay/report")
    public ResponseEntity<String> report(Integer essayId){
        String res = essayService.report(essayId);
        return ResponseEntity.ok(res);
    }

}
