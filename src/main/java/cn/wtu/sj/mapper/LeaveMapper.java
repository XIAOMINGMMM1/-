package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.Leave;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 13:00
 */
@Repository
public interface LeaveMapper {

    List<Leave> selectByUserId(Integer userId);

    void deleteByLeaveId(Integer leaveId);

    void save(Leave leave);

    Leave selectByLeaveId(Integer leaveId);

}
