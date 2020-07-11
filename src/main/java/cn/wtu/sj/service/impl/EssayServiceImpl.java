package cn.wtu.sj.service.impl;

import cn.wtu.sj.api.dto.EssayDTO;
import cn.wtu.sj.entity.Essay;
import cn.wtu.sj.entity.Like;
import cn.wtu.sj.entity.User;
import cn.wtu.sj.mapper.CommentMapper;
import cn.wtu.sj.mapper.EssayMapper;
import cn.wtu.sj.mapper.LikeMapper;
import cn.wtu.sj.service.EssayService;
import cn.wtu.sj.service.UserService;
import cn.wtu.sj.utils.MyUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/6 15:51
 */
@Service
public class EssayServiceImpl implements EssayService {

    //用于锁
    private final Object createFileLock = new Object();

    @Autowired
    private EssayMapper essayMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Essay saveImages(EssayDTO essayDTO, User user) throws FileNotFoundException {
//        StringBuilder paths = new StringBuilder();
//            List<MultipartFile> multipartFiles = essayDTO.getMultipartFiles();
//            for (MultipartFile file : multipartFiles) {
//                //文件保存路径
//                Path path;
//                //图片名称
//                String fileName;
//                //图片名称赋值
//                fileName = UUID.randomUUID().toString();
//                //保存路径赋值
//                String[] split = file.getOriginalFilename().split("\\.");
//                File pathStr = ResourceUtils.getFile("classpath:static");
//                String p = "/imgRes/" + user.getId().toString()+"/"+fileName + "." + split[split.length - 1];
//                String str = pathStr + p;
//                path = Paths.get(str );
//
//
//                try {
//                    //创建文件夹
//                    if (!Files.isReadable(path)){
//                        Files.createDirectories(path.getParent());
//                    }
//                    //复制文件到指定文件夹下面
//                    Files.copy(file.getInputStream(), path);
//                    String p1 = "/imgRes/"+user.getId().toString()+"/"+fileName + "." + split[split.length - 1];
//                    paths = paths.append(";").append(p1);
//                } catch (IOException e) {
//                    throw new IllegalArgumentException("文件上传失败");
//                }
//
//
//
//        }
//
//        String images = paths.toString();
//        if (";".equals(images.substring(0, 1))){
//            images = images.replaceFirst(";", "");
//        }
//        //保存信息到数据库
//        Essay essay = new Essay();
//        essay.setCreateTime(new Date());
//        essay.setImages(images);
//        essay.setTitle(essayDTO.getTitle());
//        essay.setInfo(essayDTO.getContent());
//        essay.setUserId(user.getId());
//        essayMapper.insertOne(essay);
//        return essay;

        //保存信息到数据库
        Essay essay = new Essay();
        essay.setCreateTime(new Date());
        essay.setImages(essayDTO.getUpFile());
        essay.setTitle(essayDTO.getTitle());
        essay.setInfo(essayDTO.getContent());
        essay.setUserId(user.getId());
        essayMapper.insertOne(essay);
        return essay;
    }

    @Override
    public PageInfo<EssayDTO> selectByUserName(String userName, Integer pageNum, Integer pageSize) {
        User user = userService.selectUserByName(userName);
        if (user == null){
            return null;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Essay> essays = essayMapper.selectByUserId(user.getId());

        List<EssayDTO> essayDTOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(essays)) {
            for (Essay essay : essays) {
                EssayDTO essayDTO = this.changeEssay(essay);
                essayDTOS.add(essayDTO);
            }
        }
        PageInfo<EssayDTO> essayDTOPageInfo = new PageInfo<>(essayDTOS);
        //设置页数
        essayDTOPageInfo.setTotal(essayMapper.selectByUserId(user.getId()).size());

        return essayDTOPageInfo;
    }

    @Override
    public Essay onclickLikeOne(Integer essayId,String flag) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //查询文章信息
        Essay essay = essayMapper.selectByEssayId(essayId);
        //增加一攒
        if ("add".equals(flag)){
            essay.setLike(essay.getLike()+1);
            essayMapper.updateLikeByEssayId(essay);
            Like like = new Like();
            like.setEssayId(essay.getEssayId());
            like.setUserId(userService.selectUserByName(userDetails.getUsername()).getId());
            likeMapper.insert(like);
        }
        if ("minus".equals(flag)){
            if (essay.getLike() == 0){
                return null;
            }
            essay.setLike(essay.getLike()-1);
            essayMapper.updateLikeByEssayId(essay);
            List<Like> likes = likeMapper.selectByEssayIdAndUserId(essay.getEssayId(),MyUtils.checkLogin().getId());
            Like like = likes.get(0);
            if (like==null){
                return essay;
            }
            likeMapper.deleteByLikeId(like.getLikeId());
        }


        return essay;
    }

    @Override
    public Essay deleteByEssayId(Integer essayId) {
        User user = MyUtils.checkLogin();
        if (user == null){
            throw new RuntimeException("必须要登入");
        }
        Essay essay = essayMapper.selectByEssayId(essayId);
        if (!essay.getUserId().equals(user.getId())){
            throw new RuntimeException("操作权限不够");
        }
        //删除文章所对应得评论
        commentMapper.deleteByEssayId(essayId);
        //删除文章所对应得赞信息
        likeMapper.deleteByEssayId(essay.getEssayId());
        //删除文章
        essayMapper.deleteByEssayId(essay.getEssayId());
        return essay;

    }

    @Override
    public EssayDTO selectByEssayId(Integer essayId) {
        Essay essay = essayMapper.selectByEssayId(essayId);
        EssayDTO essayDTO = this.changeEssay(essay);
        return essayDTO;
    }

    @Override
    public PageInfo<EssayDTO> selectAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Essay> essays = essayMapper.selectAll();
        ArrayList<EssayDTO> essayDTOS = new ArrayList<>();
        for (Essay essay : essays) {
            EssayDTO essayDTO = this.changeEssay(essay);
            essayDTOS.add(essayDTO);
        }
        PageInfo<EssayDTO> essayDTOPageInfo = new PageInfo<>(essayDTOS);
        //设置页数
        essayDTOPageInfo.setTotal(essayMapper.selectAll().size());

        return essayDTOPageInfo;
    }

    @Override
    public PageInfo<EssayDTO> select(Integer pageNum, Integer pageSize, EssayDTO essayDTO) {
        PageHelper.startPage(pageNum, pageSize);
        List<Essay> essays = essayMapper.select(essayDTO);
        ArrayList<EssayDTO> essayDTOS = new ArrayList<>();
        for (Essay essay : essays) {
            EssayDTO essayDTO1 = this.changeEssay(essay);
            essayDTOS.add(essayDTO1);
        }
        PageInfo<EssayDTO> essayDTOPageInfo = new PageInfo<>(essayDTOS);
        //设置页数
       // essayDTOPageInfo.setTotal(essays.size());
        essayDTOPageInfo.setTotal(essayMapper.select(essayDTO).size());

        return essayDTOPageInfo;

    }

    @Override
    public String report(Integer essayId) {
        Essay e = essayMapper.selectByEssayId(essayId);
        Essay essay = new Essay();
        essay.setEssayId(e.getEssayId());
        essay.setReportNum(e.getReportNum()+1);
        essayMapper.updateReportByEssayId(essay);
        return "true";
    }

    @Override
    public PageInfo<EssayDTO> selectSort(Integer pageNum, Integer pageSize, EssayDTO essayDTO) {
        PageHelper.startPage(pageNum, pageSize);
        List<Essay> essays = essayMapper.selectSort(essayDTO);
        ArrayList<EssayDTO> essayDTOS = new ArrayList<>();
        for (Essay essay : essays) {
            EssayDTO essayDTO1 = this.changeEssay(essay);
            essayDTOS.add(essayDTO1);
        }
        PageInfo<EssayDTO> essayDTOPageInfo = new PageInfo<>(essayDTOS);
        //设置页数
        // essayDTOPageInfo.setTotal(essays.size());
        essayDTOPageInfo.setTotal(essayMapper.select(essayDTO).size());

        return essayDTOPageInfo;
    }


    private EssayDTO changeEssay(Essay essay){
        //根据文章id查询点赞数 和 评论数
        int likeCount = likeMapper.selectByEssayId(essay.getEssayId()).size();
        int commentCount = commentMapper.selectByEssayId(essay.getEssayId()).size();

        EssayDTO essayDTO = new EssayDTO();
        //放入点赞和评论数
        essayDTO.setLike(likeCount);
        essayDTO.setCommentNum(commentCount);

        essayDTO.setContent(essay.getInfo());
        essayDTO.setTitle(essay.getTitle());
        essayDTO.setEssayId(essay.getEssayId());
        essayDTO.setImages(Arrays.asList(essay.getImages().split("--")));
        //只显示出时分秒
        DateFormat df = DateFormat.getDateTimeInstance();
        essayDTO.setCreateDate(df.format(essay.getCreateTime()));
        essayDTO.setUserName(essay.getUserName());
        essayDTO.setUserId(essay.getUserId());
        essayDTO.setReportNum(essay.getReportNum());

        //判断当前用户是否点赞过
        User u = MyUtils.checkLogin();
        if (u==null){
            essayDTO.setLikeFlag("false");
        }else {
            List<Like> like = likeMapper.selectByEssayIdAndUserId(essay.getEssayId(),MyUtils.checkLogin().getId());
            if (CollectionUtils.isEmpty(like)){
                essayDTO.setLikeFlag("false");
            }else {
                essayDTO.setLikeFlag("true");
            }
        }

        return essayDTO;
    }
}
