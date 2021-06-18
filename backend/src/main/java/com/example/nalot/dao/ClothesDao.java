package com.example.nalot.dao;

import com.example.nalot.model.clothes.ClothesDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;

@Repository
public class ClothesDao {
    private final SqlSession sqlSession;
    public ClothesDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<ClothesDto> selectClothesListByCategory(String category) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("category",category);
        return sqlSession.selectList("com.example.nalot.dao.ClothesDao.selectClothesListByCategory",param);
    }

    public ClothesDto selectClothesInfo(int clothesId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("clothesId",clothesId);
        return sqlSession.selectOne("com.example.nalot.dao.ClothesDao.selectClothesInfo",clothesId);
    }
}
