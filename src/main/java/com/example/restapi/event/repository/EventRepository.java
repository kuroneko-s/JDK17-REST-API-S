package com.example.restapi.event.repository;

import com.example.restapi.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
