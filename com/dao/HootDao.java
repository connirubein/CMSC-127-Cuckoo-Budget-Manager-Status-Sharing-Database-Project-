package com.dao;

import java.util.List;

import com.entities.Hoot;
import com.entities.Person;

public interface HootDao {
    void createHootTable();
    int insertHoot(Hoot hoot);
    int countHoot(int id);
    boolean isListEmpty(int id);
    List<Hoot> selectAll();
    List<Hoot> selectById(int id);
    void deleteHoot(int id);
    
}
