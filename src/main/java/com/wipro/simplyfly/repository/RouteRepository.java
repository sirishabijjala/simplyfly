package com.wipro.simplyfly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.simplyfly.entity.Route;


@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

}
