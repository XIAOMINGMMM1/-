package cn.wtu.sj.api.controller;

import cn.wtu.sj.entity.Suggest;
import cn.wtu.sj.service.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuggestController {

    @Autowired
    private SuggestService suggestService;


    /**
     * 保存
     * @param suggest
     * @return
     */
    @PostMapping("/ordinary/suggest/save")
    public ResponseEntity<String> save(@RequestBody Suggest suggest){
        String res = suggestService.saveSuggest(suggest);
        return ResponseEntity.ok(res);
    }

    /**
     * 回复
     * @return
     */
    @PostMapping("/admin/suggest/reply")
    public ResponseEntity<String> updateReply(@RequestBody Suggest suggest){
        String res = suggestService.updateReply(suggest);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/ordinary/suggest/reply-two")
    public ResponseEntity<String> updateReply(Integer suggestId){
        Suggest suggest = new Suggest();
       suggest.setSuggestId(suggestId);
        String res = suggestService.updateReplyTwo(suggest);

        return ResponseEntity.ok(res);
    }
}
