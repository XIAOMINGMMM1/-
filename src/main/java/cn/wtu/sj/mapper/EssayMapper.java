package cn.wtu.sj.mapper;

import cn.wtu.sj.api.dto.EssayDTO;
import cn.wtu.sj.entity.Essay;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 13:00
 */
@Repository
public interface EssayMapper {
    /**
     * 插入文章信息
     * @param essay
     * @return
     */
    void insertOne(Essay essay);

    List<Essay> selectByUserId(Integer userId);

    void deleteByEssayId(Integer essayId);

    void updateLikeByEssayId(Essay essay);

    Essay selectByEssayId(Integer essayId);

    List<Essay> selectAll();

    List<Essay> select(EssayDTO essayDTO);

    void updateReportByEssayId(Essay essay);

    List<Essay> selectSort(EssayDTO essayDTO);
}
