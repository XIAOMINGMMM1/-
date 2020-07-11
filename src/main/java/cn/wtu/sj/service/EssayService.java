package cn.wtu.sj.service;

import cn.wtu.sj.api.dto.EssayDTO;
import cn.wtu.sj.entity.Essay;
import cn.wtu.sj.entity.User;
import com.github.pagehelper.PageInfo;

import java.io.FileNotFoundException;

/**
 * @author huiming.wang@hand-china.com
 * @date 2020/2/6 15:51
 */
public interface EssayService {
    /**
     *
     * @param essayDTO
     * @param user
     * @return
     */
    Essay saveImages(EssayDTO essayDTO, User user) throws FileNotFoundException;

    PageInfo<EssayDTO> selectByUserName(String userName,Integer pageNum,Integer pageSize);

    Essay onclickLikeOne(Integer essayId,String flag);

    Essay deleteByEssayId(Integer essayId);

    EssayDTO selectByEssayId(Integer essayId);

    PageInfo<EssayDTO> selectAll(Integer pageNum,Integer pageSize);

    PageInfo<EssayDTO> select(Integer pageNum,Integer pageSize,EssayDTO essayDTO);

    String report(Integer essayId);

    PageInfo<EssayDTO> selectSort(Integer pageNum,Integer pageSize,EssayDTO essayDTO);
}
