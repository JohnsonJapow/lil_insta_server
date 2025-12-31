package com.lil.insta.repository;

import com.lil.insta.entity.UserProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<UserProfile, String> {

    @Query("""
        SELECT u FROM UserProfile u
        WHERE LOWER(u.fullname) LIKE LOWER(CONCAT('%', :fullname, '%'))
    """)
    List<UserProfile> searchByFullname(@Param("fullname") String keyword);
}