package com.juanlumn.questionscrud.repository;

import static com.juanlumn.questionscrud.constants.Constants.CATEGORY;
import static com.juanlumn.questionscrud.constants.Constants.QUESTIONS;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.juanlumn.questionscrud.model.Question;

import io.swagger.annotations.Api;

/**
 * CRUD interface repository to define methods that will be used to manage questions
 */
@Api
@RepositoryRestResource(collectionResourceRel = QUESTIONS, path = QUESTIONS)
public interface QuestionsRepository extends CrudRepository<Question, Long> {

    /**
     * Method to find a list of questions by category
     *
     * @param category the category to search for
     * @return List<Question> with the questions or empty list if none found
     */
    List<Question> findByCategory(
        @Param(CATEGORY)
            String category);

    /**
     * Method to find all unanswered questions
     *
     * @return List<Question> with the questions or empty list if none found
     */
    List<Question> findByAnsweredFalse();

    /**
     * Method to find a list of non answered questions by category
     *
     * @param category the category to search for
     * @return List<Question> with the questions or empty list if none found
     */
    List<Question> findByCategoryAndAnsweredFalse(
        @Param(CATEGORY)
            String category);
}
