package com.civicconnect.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.civicconnect.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByUserId(Long userId);
}