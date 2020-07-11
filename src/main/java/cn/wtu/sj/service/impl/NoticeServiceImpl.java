package cn.wtu.sj.service.impl;

import cn.wtu.sj.entity.Notice;
import cn.wtu.sj.mapper.NoticeMapper;
import cn.wtu.sj.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(Notice notice) {
        noticeMapper.save(notice);
        return "true";
    }

    @Override
    public String updateIndex(Notice notice) {
        Notice selectNotice = noticeMapper.selectById(notice.getNoticeId());
        selectNotice.setRank(notice.getRank());
        noticeMapper.update(selectNotice);
        return "true";
    }

    @Override
    public String update(Notice notice) {
        Notice selectNotice = noticeMapper.selectById(notice.getNoticeId());
        notice.setRank(selectNotice.getRank());
        noticeMapper.update(notice);
        return "true";
    }
}
