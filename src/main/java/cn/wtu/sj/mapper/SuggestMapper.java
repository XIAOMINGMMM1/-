package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.Suggest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestMapper {
    /**
     * 保存
     * @param suggest
     */
    void save(Suggest suggest);

    /**
     * 修改
     * @param suggest
     * @return
     */
    void update(Suggest suggest);

    /**
     * 根据id查询
     */
    Suggest selectById(Integer suggestId);

    List<Suggest> selectByContentAndStatus(Suggest suggest);

    List<Suggest> select(Suggest suggest);
    Integer selectStatusOne(Suggest suggest);

}
