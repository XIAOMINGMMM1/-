package cn.wtu.sj.api.controller;

import cn.wtu.sj.api.dto.EssayDTO;
import cn.wtu.sj.api.dto.FollowDTO;
import cn.wtu.sj.entity.*;
import cn.wtu.sj.mapper.FollowMapper;
import cn.wtu.sj.mapper.NoticeMapper;
import cn.wtu.sj.mapper.SuggestMapper;
import cn.wtu.sj.mapper.UserMapper;
import cn.wtu.sj.service.*;
import cn.wtu.sj.utils.MyUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/2 16:28
 */
@Controller
public class CommonController {

    @Autowired
    private EssayService essayService;

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SuggestService suggestService;

    @Autowired
    private SuggestMapper suggestMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowService followService;
    @RequestMapping(value = {"/{folder}/{name}.html"})
    public String folder1RenderView(@PathVariable String folder, @PathVariable String name, Model model,
                                    @RequestParam(required = false) String visitUserName,
                                    @RequestParam(required = false) Integer essayId,
                                    HttpSession httpSession, HttpServletRequest request,
                                    @RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                    @RequestParam(required = false) String userNameLike,
                                    @RequestParam(required = false) String contentLike,
                                    @RequestParam(required = false) String statusLike,
                                    @RequestParam(required = false) Integer suggestId,
                                    @RequestParam(required = false) Integer noticeId,
                                    @RequestParam(defaultValue = "0", required = false) Integer sortFlag
    ) {

        //当前登录用户信息放入
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            //放入用户姓名
            model.addAttribute("userName", userDetails.getUsername());
            //放入用户信息
            User user = userService.selectUserByName(userDetails.getUsername());
            model.addAttribute("user", user);

            //放入意见条数，
            Suggest suggest = new Suggest();
            suggest.setUserId(user.getId());
            Integer suggestCount = suggestMapper.selectStatusOne(suggest);
            model.addAttribute("suggestCount",suggestCount);
        } else {
            model.addAttribute("user", new User());
        }


        //visitUserName纯在 放入visitUser信息
        if (!StringUtils.isEmpty(visitUserName)) {
            User visitUser = userService.selectUserByName(visitUserName);
            model.addAttribute("visitUser", visitUser);
        }

        if ("index".equals(name)){
            List<Notice> noticeList = noticeMapper.selectAll();
            model.addAttribute("noticeList",noticeList);
        }

        //访问home必须加 visitUserName=
        if ("homePage".equals(name)) {
            //根据用户名查询文章信息
            User user = userService.selectUserByName(visitUserName);
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectByUserName(user.getUserName(), pageNum, pageSize);
            model.addAttribute("essayDTOPageInfo", essayDTOPageInfo);

        }

        //访问all_home 访问home必须加 visitUserName=
        if ("all_home".equals(name)) {
            model.addAttribute("sortFlag",sortFlag);
            if (sortFlag == 0){
                EssayDTO essayDTO = new EssayDTO();
                essayDTO.setContent(contentLike);
                PageInfo<EssayDTO> essayDTOPageInfo = essayService.select(pageNum, pageSize, essayDTO);
                essayDTOPageInfo.setPageNum(pageNum);
                model.addAttribute("essayDTOPageInfo", essayDTOPageInfo);
                model.addAttribute("contentLike",contentLike);
            }
            if (sortFlag ==1){
                EssayDTO essayDTO = new EssayDTO();
                essayDTO.setContent(contentLike);
                PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectSort(pageNum, pageSize, essayDTO);
                essayDTOPageInfo.setPageNum(pageNum);
                model.addAttribute("essayDTOPageInfo", essayDTOPageInfo);
                model.addAttribute("contentLike",contentLike);
            }
        }


        //访问about页面
        if ("about".equals(name)) {
            User user = userService.selectUserByName(visitUserName);
            PageInfo<Leave> leavePageInfo = leaveService.selectByUserId(user.getId(), pageNum, pageSize);
            model.addAttribute("leavePageInfo", leavePageInfo);

            Integer followCount = followMapper.selectFollowCount(user.getId());
            Integer followerCount = followMapper.selectFollowerCount(user.getId());
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectByUserName(user.getUserName(), pageNum, pageSize);
            Integer essayCount = essayDTOPageInfo.getSize();
            model.addAttribute("followCount",followCount);
            model.addAttribute("followerCount",followerCount);
            model.addAttribute("essayCount",essayCount);

            User self = MyUtils.checkLogin();

            if (self != null){
                //查看浏览的用户自己是否关注
                Follow follow = new Follow();
                follow.setUserId(self.getId());
                follow.setFollowUserId(user.getId());
                Integer count = followMapper.selectByUser(follow);
                if (count==0){
                    model.addAttribute("followFlag",false);
                }else {
                    model.addAttribute("followFlag",true);
                }
            }else{
                model.addAttribute("followFlag",false);
            }
        }


        //访问essay_details
        if ("essay_details".equals(name)) {
            EssayDTO essayDTO = essayService.selectByEssayId(essayId);
            model.addAttribute("essay", essayDTO);
            PageInfo<Comment> commentPageInfo = commentService.selectByEssayId(essayId, pageNum, pageSize);
            model.addAttribute("commentPageInfo", commentPageInfo);

        }

        if("follow".equals(name)){
            User user = userMapper.selectByUserName(visitUserName);
            Integer followCount = followMapper.selectFollowCount(user.getId());
            Integer followerCount = followMapper.selectFollowerCount(user.getId());
            PageInfo<FollowDTO> followDTOPageInfo = followService.selectAllFollow(pageNum, pageSize, user.getId());
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectByUserName(user.getUserName(), pageNum, pageSize);
            Integer essayCount = essayDTOPageInfo.getSize();
            model.addAttribute("followCount",followCount);
            model.addAttribute("followerCount",followerCount);
            model.addAttribute("essayCount",essayCount);
            model.addAttribute("followDTOPageInfo",followDTOPageInfo);



        }

        if("follower".equals(name)){
            User user = userMapper.selectByUserName(visitUserName);
            Integer followCount = followMapper.selectFollowCount(user.getId());
            Integer followerCount = followMapper.selectFollowerCount(user.getId());
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectByUserName(user.getUserName(), pageNum, pageSize);
            Integer essayCount = essayDTOPageInfo.getSize();
            model.addAttribute("followCount",followCount);
            model.addAttribute("followerCount",followerCount);
            model.addAttribute("essayCount",essayCount);
            PageInfo<FollowDTO> followerDTOPageInfo = followService.selectAllFollower(pageNum, pageSize, user.getId());
            model.addAttribute("followerDTOPageInfo",followerDTOPageInfo);
        }

        if("essay".equals(name)){
            User user = userMapper.selectByUserName(visitUserName);
            Integer followCount = followMapper.selectFollowCount(user.getId());
            Integer followerCount = followMapper.selectFollowerCount(user.getId());
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.selectByUserName(user.getUserName(), pageNum, pageSize);
            Integer essayCount = essayDTOPageInfo.getSize();
            model.addAttribute("followCount",followCount);
            model.addAttribute("followerCount",followerCount);
            model.addAttribute("essayCount",essayCount);
            model.addAttribute("essayDTOPageInfo",essayDTOPageInfo);
        }

        if ("suggest".equals(name)){
            PageInfo<Suggest> suggestPageInfo = suggestService.select(pageNum, pageSize, MyUtils.checkLogin().getId());
            model.addAttribute("suggestPageInfo",suggestPageInfo);
        }

        //admian页面操作
        if ("account_list".equals(name)){
            PageInfo<User> userPageInfo = userService.selectAll(pageNum, pageSize,userNameLike);
            model.addAttribute("userPageInfo", userPageInfo);
            model.addAttribute("userNameLike",userNameLike);
        }

        if ("suggest_list".equals(name)){
            Suggest suggest = new Suggest();
            if(!"null".equals(statusLike)){
                suggest.setStatus(statusLike);
            }
            suggest.setContent(contentLike);
            PageInfo<Suggest> suggestPageInfo = suggestService.selectAll(pageNum, pageSize, suggest);
            model.addAttribute("suggestPageInfo", suggestPageInfo);
            model.addAttribute("contentLike",contentLike);
            model.addAttribute("statusLike",statusLike);
        }

        if ("suggest_detail".equals(name) && suggestId!=null){
            Suggest suggest = new Suggest();
            suggest.setSuggestId(suggestId);
            List<Suggest> suggests = suggestMapper.selectByContentAndStatus(suggest);
            if (!CollectionUtils.isEmpty(suggests)){
                model.addAttribute("suggest",suggests.get(0));
            }

        }

        if ("essay_list".equals(name)){
            EssayDTO essayDTO = new EssayDTO();
            essayDTO.setContent(contentLike);
            essayDTO.setUserName(userNameLike);
            PageInfo<EssayDTO> essayDTOPageInfo = essayService.select(pageNum, pageSize,essayDTO);
            model.addAttribute("essayDTOPageInfo", essayDTOPageInfo);

        }

        if ("essay_detail".equals(name)) {
            EssayDTO essayDTO = essayService.selectByEssayId(essayId);
            model.addAttribute("essay", essayDTO);

        }

        if ("notice_list".equals(name)) {
            List<Notice> noticeList = noticeMapper.selectAll();
            model.addAttribute("noticeList",noticeList);

        }

        if ("visit_notice".equals(name)) {
            Notice notice = noticeMapper.selectById(noticeId);
            model.addAttribute("notice",notice);

        }


        if ("update_notice".equals(name)) {
            Notice notice = noticeMapper.selectById(noticeId);
            model.addAttribute("notice",notice);

        }
        return new StringBuilder(folder).append("/").append(name).toString();
    }


    @RequestMapping("/")
    public String login(Model model,@RequestParam(required = false) String visitUserName) {
        //当前登录用户信息放入
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            //放入用户姓名
            model.addAttribute("userName", userDetails.getUsername());
            //放入用户信息
            User user = userService.selectUserByName(userDetails.getUsername());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }


        //visitUserName纯在 放入visitUser信息
        if (!StringUtils.isEmpty(visitUserName)) {
            User visitUser = userService.selectUserByName(visitUserName);
            model.addAttribute("visitUser", visitUser);
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "/public/index";
        }
        return "/public/login";
    }


}
