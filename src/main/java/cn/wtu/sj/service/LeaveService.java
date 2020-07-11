package cn.wtu.sj.service;

import cn.wtu.sj.entity.Leave;
import com.github.pagehelper.PageInfo;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/13 10:53
 */
public interface LeaveService {
    PageInfo<Leave> selectByUserId(Integer userId, Integer pageNum, Integer pageSzie);

    Leave save(Leave leave);

    Leave deleteOne(Integer leaveId);
}
