package com.hardware.hardwareStore.Repository;

import com.hardware.hardwareStore.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>{
}
