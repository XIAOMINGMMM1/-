package cn.wtu.sj.service.impl;

import cn.wtu.sj.api.dto.FollowDTO;
import cn.wtu.sj.entity.Follow;
import cn.wtu.sj.mapper.FollowMapper;
import cn.wtu.sj.service.FollowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowMapper followMapper;
    @Override
    public PageInfo<FollowDTO> selectAllFollow(Integer pageNum, Integer pageSize,Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<FollowDTO> followDTOS = followMapper.selectAllFollow(userId);
        PageInfo<FollowDTO> followDTOPageInfo = new PageInfo<>(followDTOS);
        return followDTOPageInfo;
    }

    @Override
    public PageInfo<FollowDTO> selectAllFollower(Integer pageNum, Integer pageSize,Integer followUserId) {
        PageHelper.startPage(pageNum,pageSize);
        List<FollowDTO> followerDTOS = followMapper.selectAllFollower(followUserId);
        PageInfo<FollowDTO> followerDTOPageInfo = new PageInfo<>(followerDTOS);
        return followerDTOPageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(Follow follow) {
        Integer integer = followMapper.selectByUser(follow);
        if (integer==0){
            followMapper.save(follow);
        }
        return "true";
    }
}
