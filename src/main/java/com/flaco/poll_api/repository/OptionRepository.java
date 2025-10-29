package com.flaco.poll_api.repository;

import com.flaco.poll_api.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
