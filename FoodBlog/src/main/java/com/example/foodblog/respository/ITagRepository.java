package com.example.foodblog.respository;

import com.example.foodblog.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends JpaRepository<Tag, Integer> {
    Tag findTagByName(String name);
}
