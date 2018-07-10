package com.juanlumn.questionscrud.model;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Model bean that define a statistic
 */
@Entity
@Table(name = "generalStatistics")
@Setter
@Getter
public class GeneralStatistics {
    /**
     * {@link Long} that contains the questionID for each question (primary key on database)
     */
    @Id
    @GeneratedValue(strategy = AUTO)
    Long statisticID;

    /**
     * {@link String} with the category
     */
    @Column
    String category;

    /**
     * {@link Boolean} true if has been answered correct or false if not
     */
    @Column
    boolean answered;

    /**
     * {@link String} with the date in format dd-mm-yyyy
     */
    @Column
    String date;

}
