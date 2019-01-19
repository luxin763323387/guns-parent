package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.film.FilmServiceAsyncAPI;
import com.stylefeng.guns.film.vo.ActorsVO;
import com.stylefeng.guns.film.vo.FilmDescVO;
import com.stylefeng.guns.film.vo.ImgsVO;
import com.stylefeng.guns.rest.common.persistence.dao.MoocActorTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocActorT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = FilmServiceAsyncAPI.class)
public class DefaultFilmAsyncServiceImpl implements FilmServiceAsyncAPI {


    @Autowired
    private MoocActorTMapper moocActorTMapper;
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;


    //为了下面方法  提出这个方法通过id补全moocFilmInfoT的其他数据
    private  MoocFilmInfoT getFilmId(String filmId){
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);
        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);

        return moocFilmInfoT;
    }

    /**
     * 获取电影描述
     * @param filmId
     * @return
     */
    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmId(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);
        return filmDescVO;
    }

    @Override
    public ActorsVO getDirector(String filmId) {
        ActorsVO actorsVO = new ActorsVO();
        MoocFilmInfoT moocFilmInfoT = getFilmId(filmId);
        Integer directorId = moocFilmInfoT.getDirectorId();
        MoocActorT moocActorT = moocActorTMapper.selectById(directorId);
        actorsVO.setImgAddress(moocActorT.getActorImg());
        actorsVO.setDirectorName(moocActorT.getActorName());
        return actorsVO;
    }

    @Override
    public List<ActorsVO> getActors(String filmId) {
        List<ActorsVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }

    /**
     * 获取电影图片
     * @param filmId
     * @return
     */
    @Override
    public ImgsVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfoT = getFilmId(filmId);
        ImgsVO  imgsVO = new ImgsVO();
        String imgStr = moocFilmInfoT.getFilmImgs(); //获取的有“，”分隔的字符串 films/3065271341357040f5f5dd988550951e586199.jpg,films/6b2b3fd6260ac37e5ad44d00ea474ea3651419.jpg,films/4633dd44c51ff15fc7e939679d7cdb67561602.jpg,films/df2d30b1a3bd58fb1d38b978662ae844648169.jpg,films/c845f6b04aa49059951fd55e6b0eddac454036.jpg
        String [] img = imgStr.split(",");//去除","
        imgsVO.setMainImg(img [0]);
        imgsVO.setImg01(img [1]);
        imgsVO.setImg02(img [2]);
        imgsVO.setImg03(img [3]);
        imgsVO.setImg04(img [4]);
        return imgsVO;
    }
}
