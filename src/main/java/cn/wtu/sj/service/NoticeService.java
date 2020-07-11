package cn.wtu.sj.service;

import cn.wtu.sj.entity.Notice;

public interface NoticeService {
    String save(Notice notice);
    String updateIndex(Notice notice);
    String update(Notice notice);
}
