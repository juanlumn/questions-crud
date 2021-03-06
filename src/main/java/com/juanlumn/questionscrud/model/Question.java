package com.juanlumn.questionscrud.model;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
    @Column(columnDefinition="LONGTEXT NOT NULL")
    @Lob
    String question;

    /**
     * {@link List<String>} with the Answers
     */
    @ElementCollection
    @Column(columnDefinition="LONGTEXT")
    @Lob
    List<String> answers;

    /**
     * {@link String} with the correct answer
     */
    @Column(columnDefinition="LONGTEXT NOT NULL")
    @Lob
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
