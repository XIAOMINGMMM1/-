package cn.wtu.sj.mapper;

import cn.wtu.sj.entity.Notice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 13:00
 */
@Repository
public interface NoticeMapper {
    void save(Notice notice);
    List<Notice> selectAll();
    Notice selectById(Integer noticeId);
    void update(Notice notice);

    void delete(Integer noticeId);

}
