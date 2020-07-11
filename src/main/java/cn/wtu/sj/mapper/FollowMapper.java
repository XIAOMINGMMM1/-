package cn.wtu.sj.mapper;

import cn.wtu.sj.api.dto.FollowDTO;
import cn.wtu.sj.entity.Follow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 18:47
 */
@Repository
public interface FollowMapper {
  void save(Follow follow);
  void deleteByUser(Follow follow);
  void deleteById(Integer followId);
  Integer selectFollowCount(Integer userId);
  Integer selectFollowerCount(Integer followUserId);
  List<FollowDTO> selectAllFollow(Integer userId);
  List<FollowDTO> selectAllFollower(Integer followUserId);
  Integer selectByUser(Follow follow);

}
