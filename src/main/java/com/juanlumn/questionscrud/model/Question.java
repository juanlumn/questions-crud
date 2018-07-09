package com.juanlumn.questionscrud.model;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Model bean that define a question
 */
@Entity
@Table(name = "question")
@Setter
@Getter
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * {@link Long} that contains the questionID for each question (primary key on database)
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    Long questionID;

    /**
     * {@link String} with the question itself
     */
    @Column
    String question;

    /**
     * {@link List<String>} with the Answers
     */
    @Column
    @ElementCollection
    List<String> answers;

    /**
     * {@link String} with the correct answer
     */
    @Column
    String correctAnswer;

    /**
     * {@link String} with the category
     */
    @Column
    String category;

    /**
     * {@link Boolean} true if has been answered or false if not
     */
    @Column
    boolean answered;
}
