package cn.wtu.sj.service.impl;

import cn.wtu.sj.entity.Leave;
import cn.wtu.sj.mapper.LeaveMapper;
import cn.wtu.sj.service.LeaveService;
import cn.wtu.sj.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/13 10:53
 */
@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveMapper leaveMapper;
    @Override
    public PageInfo<Leave> selectByUserId(Integer userId,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Leave> leaves = leaveMapper.selectByUserId(userId);
        PageInfo<Leave> leavePageInfo = new PageInfo<>(leaves);
        List<Leave> list = leavePageInfo.getList();
        list = MyUtils.addDateString(leaves);
        leavePageInfo.setList(list);
        return leavePageInfo;
    }

    @Override
    public Leave save(Leave leave) {
        leaveMapper.save(leave);
        return leave;
    }

    @Override
    public Leave deleteOne(Integer leaveId) {
        Leave leave = leaveMapper.selectByLeaveId(leaveId);
        leaveMapper.deleteByLeaveId(leave.getLeaveId());
        return leave;
    }
}
