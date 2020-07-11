package cn.wtu.sj.service.impl;

import cn.wtu.sj.entity.Suggest;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.mapper.SuggestMapper;
import cn.wtu.sj.mapper.UserMapper;
import cn.wtu.sj.service.SuggestService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SuggestServiceImpl implements SuggestService {

    @Autowired
    private SuggestMapper suggestMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public String saveSuggest(Suggest suggest) {
        if (suggest == null || StringUtils.isEmpty(suggest.getContent())){
            return "false";
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userMapper.selectByUserName(userDetails.getUsername());
        Suggest sug = new Suggest();
        sug.setCreateTime(new Date());
        sug.setContent(suggest.getContent());
        sug.setUserId(user.getId());
        suggestMapper.save(sug);
        return "true";
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String updateReply(Suggest suggest) {
        Suggest sug = new Suggest();
        sug.setStatus("1");
        sug.setReply(suggest.getReply());
        sug.setSuggestId(suggest.getSuggestId());
        suggestMapper.update(sug);
        return "true";
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public String updateReplyTwo(Suggest suggest) {
        Suggest sug = suggestMapper.selectById(suggest.getSuggestId());
        sug.setStatus("2");
        suggestMapper.update(sug);
        return "true";
    }

    @Override
    public PageInfo<Suggest> selectAll(Integer pageNum, Integer pageSize, Suggest suggest) {
        PageHelper.startPage(pageNum,pageSize);
        List<Suggest> suggests = suggestMapper.selectByContentAndStatus(suggest);
        return new PageInfo<Suggest>(suggests);

    }

    @Override
    public PageInfo<Suggest> select(Integer pageNum, Integer pageSize, Integer userId) {
        Suggest suggest = new Suggest();
        suggest.setUserId(userId);
        PageHelper.startPage(pageNum,pageSize);
        List<Suggest> list = suggestMapper.select(suggest);
        PageInfo<Suggest> suggestPageInfo = new PageInfo<>(list);

        return suggestPageInfo;
    }

}
