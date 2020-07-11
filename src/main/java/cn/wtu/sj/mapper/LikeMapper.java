package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.Like;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/14 15:44
 */
@Repository
public interface LikeMapper {
    void insert(Like like);
    List<Like> selectByEssayId(Integer essayId);
    void deleteByLikeId(Integer likeId);
    void deleteByEssayId(Integer essayId);
    List<Like> selectByEssayIdAndUserId(@Param("essayId") Integer essayId, @Param("userId") Integer userId);
}
