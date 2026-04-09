package com.civicconnect.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import com.civicconnect.model.User;
import com.civicconnect.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.civicconnect.model.Issue;
import com.civicconnect.service.IssueService;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin
public class IssueController {

	@Autowired
	private IssueService service;

	@Autowired
	private UserService userService;  // ✅ ADD THIS
    @GetMapping
    public List<Issue> getAll() {
        return service.getAllIssues();
    }

    @GetMapping("/{id}")
    public Issue getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Issue> getUserIssues(@PathVariable Long userId) {
        return service.getUserIssues(userId);
    }

    @PostMapping
    public Issue create(@RequestBody Issue issue) {

        // get logged-in user from JWT
        String email = SecurityContextHolder.getContext()
                        .getAuthentication().getName();

        User user = userService.findByEmail(email);

        issue.setUser(user);

        return service.createIssue(issue);
    }

    @PutMapping("/{id}")
    public Issue update(@PathVariable Long id, @RequestBody Issue issue) {
        return service.updateStatus(id, issue.getStatus());
    }
}