package cn.wtu.sj.api.controller;

import cn.wtu.sj.entity.Notice;
import cn.wtu.sj.mapper.NoticeMapper;
import cn.wtu.sj.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 20:21
 */
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeMapper noticeMapper;
    @PostMapping("/admin/notice/update-index")
    public ResponseEntity<String> updateIndex(@RequestBody Notice notice){
        String res = noticeService.updateIndex(notice);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/admin/notice/save")
    public ResponseEntity<String> save(@RequestBody Notice notice){
        notice.setRank(0);
        String res = noticeService.save(notice);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/admin/notice/images")
    public ResponseEntity<String> images(MultipartFile file){

        return ResponseEntity.ok("true");
    }

    @GetMapping("/admin/notice/delete")
    public ResponseEntity<String> delete(Integer noticeId){
        noticeMapper.delete(noticeId);
        return ResponseEntity.ok("true");
    }

    @PostMapping("/admin/notice/update")
    public ResponseEntity<String> update(@RequestBody Notice notice){
        String res = noticeService.update(notice);
        return ResponseEntity.ok(res);
    }

}
