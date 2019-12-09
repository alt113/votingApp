package com.example.apple.votingapp.classes;

import java.io.Serializable;

/**
 * This class serves as the data structure for all
 * policy objects stored in the Firebase Realtime
 * Database. The structure defined here adheres to
 * that in the remote database and can be outlined
 * as the following:
 * <ol>
 *     <li>
 *         title = Policy title
 *     </li>
 *     <li>
 *         description = Policy description
 *     </li>
 *     <li>
 *         count1 = Number of votes for the 1st option
 *     </li>
 *     <li>
 *         count2 = Number of votes for the 2nd option
 *     </li>
 *     <li>
 *         count3 = Number of votes for the 3rd option
 *     </li>
 *     <li>
 *         count4 = Number of votes for the 4th option
 *     </li>
 *     <li>
 *         option1 = Description of 1st option
 *     </li>
 *     <li>
 *         option2 = Description of 2nd option
 *     </li>
 *     <li>
 *         option3 = Description of 3rd option
 *     </li>
 *     <li>
 *         option4 = Description of 4th option
 *     </li>
 * </ol>
 *
 * @author      Rayyan Nasr
 * @author      Jihad Eddine Al Khrufan
 * @version     %I%, %G%
 * @since       1.0
 */
public class Policy implements Serializable {

    private String title;
    private String description;
    private Long count1;
    private Long count2;
    private Long count3;
    private Long count4;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    /**
     * Instantiates a new Policy.
     */
    public Policy() {
    }

    /**
     * Instantiates a new Policy.
     *
     * @param title       the title
     * @param description the description
     * @param count1      the count 1
     * @param count2      the count 2
     * @param count3      the count 3
     * @param count4      the count 4
     * @param option1     the option 1
     * @param option2     the option 2
     * @param option3     the option 3
     * @param option4     the option 4
     */
    public Policy(String title, String description, Long count1, Long count2, Long count3, Long count4, String option1, String option2, String option3, String option4) {
        this.title = title;
        this.description = description;
        this.count1 = count1;
        this.count2 = count2;
        this.count3 = count3;
        this.count4 = count4;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets count 1.
     *
     * @return the count 1
     */
    public Long getCount1() {
        return count1;
    }

    /**
     * Sets count 1.
     *
     * @param count1 the count 1
     */
    public void setCount1(Long count1) {
        this.count1 = count1;
    }

    /**
     * Gets count 2.
     *
     * @return the count 2
     */
    public Long getCount2() {
        return count2;
    }

    /**
     * Sets count 2.
     *
     * @param count2 the count 2
     */
    public void setCount2(Long count2) {
        this.count2 = count2;
    }

    /**
     * Gets count 3.
     *
     * @return the count 3
     */
    public Long getCount3() {
        return count3;
    }

    /**
     * Sets count 3.
     *
     * @param count3 the count 3
     */
    public void setCount3(Long count3) {
        this.count3 = count3;
    }

    /**
     * Gets count 4.
     *
     * @return the count 4
     */
    public Long getCount4() {
        return count4;
    }

    /**
     * Sets count 4.
     *
     * @param count4 the count 4
     */
    public void setCount4(Long count4) {
        this.count4 = count4;
    }

    /**
     * Gets option 1.
     *
     * @return the option 1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Sets option 1.
     *
     * @param option1 the option 1
     */
    public void setOption1(String option1) {
        this.option1 = option1;
    }

    /**
     * Gets option 2.
     *
     * @return the option 2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Sets option 2.
     *
     * @param option2 the option 2
     */
    public void setOption2(String option2) {
        this.option2 = option2;
    }

    /**
     * Gets option 3.
     *
     * @return the option 3
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Sets option 3.
     *
     * @param option3 the option 3
     */
    public void setOption3(String option3) {
        this.option3 = option3;
    }

    /**
     * Gets option 4.
     *
     * @return the option 4
     */
    public String getOption4() {
        return option4;
    }

    /**
     * Sets option 4.
     *
     * @param option4 the option 4
     */
    public void setOption4(String option4) {
        this.option4 = option4;
    }
}
