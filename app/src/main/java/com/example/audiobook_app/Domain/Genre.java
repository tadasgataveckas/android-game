package com.example.audiobook_app.Domain;

/**
 * Enum for book genres
 */
public enum Genre {
    ACTION_ADVENTURE_FICTION("Action/Adventure fiction"),
    CHILDRENS_FICTION("Children’s fiction"),
    CLASSIC_FICTION("Classic fiction"),
    CONTEMPORARY_FICTION("Contemporary fiction"),
    FANTASY("Fantasy"),
    GRAPHIC_NOVEL("Graphic novel"),
    HISTORICAL_FICTION("Historical fiction"),
    HORROR("Horror"),
    LGBTQ("LGBTQ+"),
    LITERARY_FICTION("Literary fiction"),
    MYSTERY("Mystery"),
    NEW_ADULT("New adult"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE_FICTION("Science fiction"),
    SHORT_STORY("Short story"),
    THRILLER("Thriller"),
    WESTERN("Western"),
    WOMENS_FICTION("Women’s fiction"),
    YOUNG_ADULT("Young adult"),
    ART_PHOTOGRAPHY("Art & photography"),
    AUTOBIOGRAPHY_MEMOIR("Autobiography/Memoir"),
    BIOGRAPHY("Biography"),
    ESSAYS("Essays"),
    FOOD_DRINK("Food & drink"),
    HISTORY("History"),
    HOW_TO_GUIDES("How-To/Guides"),
    HUMANITIES_SOCIAL_SCIENCES("Humanities & social sciences"),
    HUMOR("Humor"),
    PARENTING("Parenting"),
    PHILOSOPHY("Philosophy"),
    RELIGION_SPIRITUALITY("Religion & spirituality"),
    SCIENCE_TECHNOLOGY("Science & technology"),
    SELF_HELP("Self-help"),
    TRAVEL("Travel"),
    TRUE_CRIME("True crime");
    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}