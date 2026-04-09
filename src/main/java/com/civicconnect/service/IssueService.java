package com.civicconnect.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.civicconnect.model.Issue;
import com.civicconnect.repository.IssueRepository;

@Service
public class IssueService {

    @Autowired
    private IssueRepository repo;

    public List<Issue> getAllIssues() {
        return repo.findAll();
    }

    public Issue getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public List<Issue> getUserIssues(Long userId) {
        return repo.findByUserId(userId);
    }

    public Issue createIssue(Issue issue) {
        return repo.save(issue);
    }

    public Issue updateStatus(Long id, String status) {
        Issue issue = repo.findById(id).orElseThrow();
        issue.setStatus(status);
        return repo.save(issue);
    }
}