package cn.wtu.sj.service;

import cn.wtu.sj.api.dto.FollowDTO;
import cn.wtu.sj.entity.Follow;
import com.github.pagehelper.PageInfo;

public interface FollowService {

    PageInfo<FollowDTO> selectAllFollow(Integer pageNum, Integer pageSize,Integer userId);
    PageInfo<FollowDTO> selectAllFollower(Integer pageNum, Integer pageSize,Integer followUserId);
    String save(Follow follow);
}
