package com.flaco.poll_api.repository;

import com.flaco.poll_api.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
   
}
