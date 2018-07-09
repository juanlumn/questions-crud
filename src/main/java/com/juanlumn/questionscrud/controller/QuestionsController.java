package com.juanlumn.questionscrud.controller;

import static java.util.Collections.shuffle;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static com.juanlumn.questionscrud.constants.Constants.ANSWERED;
import static com.juanlumn.questionscrud.constants.Constants.ANSWER_QUESTION_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.CATEGORIES;
import static com.juanlumn.questionscrud.constants.Constants.DELETE_QUESTION_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.EDIT_QUESTION_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.GET_ALL_QUESTIONS_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.GET_FAILED_RANDOM_QUESTIONS_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.GET_RANDOM_QUESTIONS_BY_CATEGORY_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.GET_RANDOM_QUESTIONS_ENDPOINT;
import static com.juanlumn.questionscrud.constants.Constants.QUESTION_ID;
import static com.juanlumn.questionscrud.constants.Constants.SAVE_QUESTION_ENDPOINT;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.juanlumn.questionscrud.model.GeneralStatistics;
import com.juanlumn.questionscrud.model.Question;
import com.juanlumn.questionscrud.repository.QuestionsRepository;

/**
 * This Controller provides the end points needed to create, read, update and delete Questions
 **/
@RestController
public class QuestionsController {

    private static final Logger LOGGER = getLogger(QuestionsController.class);

    @Autowired
    private QuestionsRepository questionsRepository;

    @RequestMapping(value = SAVE_QUESTION_ENDPOINT, method = POST)
    ResponseEntity<?> saveQuestion(
        @RequestBody
            Question question) {
        Gson gson = new Gson();
        Question save = questionsRepository.save(question);
        return new ResponseEntity<>(gson.toJson(save), OK);
    }

    /**
     * Endpoint to get random questions
     *
     * @return List<Question> with the result
     */
    @RequestMapping(value = GET_RANDOM_QUESTIONS_ENDPOINT, method = GET, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Question> getRandomQuestions() {
        Iterable<Question> questions = questionsRepository.findAll();
        List<Question> questionList = new ArrayList<>();

        //Makes random order for answers and questions
        for (Question question : questions) {
            shuffle(question.getAnswers());
            questionList.add(question);
        }
        shuffle(questionList);
        return questionList;
    }

    /**
     * Endpoint to get random questions
     *
     * @return List<Question> with the result
     */
    @RequestMapping(value = GET_ALL_QUESTIONS_ENDPOINT, method = GET, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Question> getAllQuestions() {
        Iterable<Question> questions = questionsRepository.findAll();
        List<Question> questionList = new ArrayList<>();

        for (Question question : questions) {
            questionList.add(question);
        }
        return questionList;
    }

    /**
     * Endpoint to get random questions by category and if they are answered
     *
     * @param categories List<String> with the categories to search for
     * @param answered   boolean if the questions are answered or not
     * @return List<Question> with the result
     */
    @RequestMapping(value = GET_RANDOM_QUESTIONS_BY_CATEGORY_ENDPOINT, method = GET,
        produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Question> getRandomQuestionsByCategory(
        @RequestParam(value = CATEGORIES)
            List<String> categories,
        @RequestParam(value = ANSWERED)
            boolean answered) {

        List<Question> questionList = new ArrayList<>();

        //Gets all the questions by category
        if (answered) {
            for (String category : categories) {
                questionList.addAll(questionsRepository.findByCategory(category));
            }
        } else {
            for (String category : categories) {
                questionList.addAll(questionsRepository.findByCategoryAndAnsweredFalse(category));
            }
        }

        //Makes random order for answers and questions
        for (Question question : questionList) {
            shuffle(question.getAnswers());
        }

        shuffle(questionList);
        return questionList;
    }

    /**
     * Endpoint to delete a question
     *
     * @param questionId int with the questionId to delete
     * @return OK if succeed or BAD REQUEST if error
     */
    @RequestMapping(value = DELETE_QUESTION_ENDPOINT, method = GET, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public HttpStatus deleteQuestion(
        @RequestParam(value = QUESTION_ID)
            Long questionId) {
        try {
            questionsRepository.delete(questionId);
            return OK;
        } catch (IllegalArgumentException e) {
            return BAD_REQUEST;
        }
    }

    /**
     * Endpoint to edit a question
     *
     * @param question Question with the question to edit
     * @return OK if succeed or BAD REQUEST if error
     */
    @RequestMapping(value = EDIT_QUESTION_ENDPOINT, method = POST, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public HttpStatus editQuestion(
        @RequestBody
            Question question) {
        try {
            questionsRepository.delete(question.getQuestionID());
            questionsRepository.save(question);
            return OK;
        } catch (IllegalArgumentException e) {
            return BAD_REQUEST;
        }
    }

    /**
     * Endpoint to edit a question after being answered
     *
     * @param question Question with the question to edit
     * @return OK if succeed or BAD REQUEST if error
     */
    @RequestMapping(value = ANSWER_QUESTION_ENDPOINT, method = POST, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public HttpStatus answerQuestion(
        @RequestBody
            Question question) {
        try {
            GeneralStatistics generalStatistics = new GeneralStatistics();
            generalStatistics.setAnswered(question.isAnswered());
            generalStatistics.setCategory(question.getCategory());

            questionsRepository.delete(question.getQuestionID());
            questionsRepository.save(question);

            questionsRepository.save(generalStatistics);

            return OK;
        } catch (IllegalArgumentException e) {
            return BAD_REQUEST;
        }
    }

    /**
     * Endpoint to get random questions which are failed
     *
     * @return List<Question> with the result
     */
    @RequestMapping(value = GET_FAILED_RANDOM_QUESTIONS_ENDPOINT, method = GET, produces = {APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<Question> getFailedRandomQuestions() {

        List<Question> questionList = questionsRepository.findByAnsweredFalse();

        //Makes random order for answers and questions
        for (Question question : questionList) {
            shuffle(question.getAnswers());
        }

        shuffle(questionList);
        return questionList;
    }
}
