package com.juanlumn.questionscrud.repository;

import static com.juanlumn.questionscrud.constants.Constants.QUESTIONS;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.juanlumn.questionscrud.model.GeneralStatistics;

import io.swagger.annotations.Api;

/**
 * CRUD interface repository to define methods that will be used to manage general statistics
 */
@Api
@RepositoryRestResource(collectionResourceRel = QUESTIONS, path = QUESTIONS)
public interface StatisticsRepository extends CrudRepository<GeneralStatistics, Long> {

}
