package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Transactional(readOnly = true)
    Optional<Topic> findByLinkName(String linkName);


}