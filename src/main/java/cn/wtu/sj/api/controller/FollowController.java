package cn.wtu.sj.api.controller;

import cn.wtu.sj.entity.Follow;
import cn.wtu.sj.mapper.FollowMapper;
import cn.wtu.sj.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 20:21
 */
@RestController
public class FollowController {
   @Autowired
    private FollowService followService;

   @Autowired
    private FollowMapper followMapper;

   @PostMapping("/ordinary/follow/delete")
    public ResponseEntity<String> delete(@RequestBody Follow follow){
       followMapper.deleteByUser(follow);
       return ResponseEntity.ok("true");
   }
   @PostMapping("/ordinary/follow/save")
    public ResponseEntity<String> save(@RequestBody Follow follow){
       String res = followService.save(follow);
       return ResponseEntity.ok(res);
   }

}
