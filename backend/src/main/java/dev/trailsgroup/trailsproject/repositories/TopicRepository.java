package dev.trailsgroup.trailsproject.repositories;

import dev.trailsgroup.trailsproject.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}