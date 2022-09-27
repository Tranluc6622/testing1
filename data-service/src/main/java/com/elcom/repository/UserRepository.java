package com.elcom.repository;


import com.elcom.model.User;
import com.elcom.project.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByUserName(String usernameName);
}
