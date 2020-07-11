package cn.wtu.sj.service;

import cn.wtu.sj.entity.Suggest;
import com.github.pagehelper.PageInfo;

public interface SuggestService {
    String saveSuggest(Suggest suggest);

    String updateReply(Suggest suggest);

    String updateReplyTwo(Suggest suggest);

    PageInfo<Suggest> selectAll(Integer pageNum, Integer pageSize, Suggest suggest);

    PageInfo<Suggest> select(Integer pageNum, Integer pageSize, Integer userId);


}
