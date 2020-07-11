package cn.wtu.sj.api.controller;

import cn.wtu.sj.entity.Leave;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.mapper.LeaveMapper;
import cn.wtu.sj.service.LeaveService;
import cn.wtu.sj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/13 12:23
 */
@RestController
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveMapper leaveMapper;

    @PostMapping("/ordinary/leave/publish-leave")
    public ResponseEntity<Integer> publishLeave(@RequestBody Leave  leave){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.selectUserByName(userDetails.getUsername());
        //评论者id 当前登入的用户
        leave.setLeaveUserId(user.getId());
        leave.setCreateTime(LocalDateTime.now());

        Leave save = leaveService.save(leave);

        return  ResponseEntity.ok(leave.getUserId());
    }
    @RequestMapping("/ordinary/leave/delete-leave")
    public ResponseEntity<String> deleteLeave(Integer leaveId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Leave leave = leaveMapper.selectByLeaveId(leaveId);
        if (leave == null || !leave.getUserId().equals(userService.selectUserByName(userDetails.getUsername()).getId())){
            return  ResponseEntity.ok("failed");
        }
        leaveService.deleteOne(leaveId);

        return  ResponseEntity.ok("success");
    }

}
