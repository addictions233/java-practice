package com.one.es.v7.mapper;

import com.one.es.v7.entity.NBAPlayer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NBAPlayerMapper {

    @Select("select * from nba_player")
    public List<NBAPlayer> selectAll();
}
