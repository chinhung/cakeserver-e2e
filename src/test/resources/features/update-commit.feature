Feature: Update Commit

  Background:
    Given commit with message: Given Message does not exist
    When create commit with message: Given Message and note: Given Note
    Then commit with message: Given Message and note: Given Note exists

  Scenario: update commit message
    When select commit with message: Given Message and note: Given Note
    * enter commit message: Updated
    * save commit
    Then commit with message: Updated and note: Given Note exists

  Scenario: update commit note
    When select commit with message: Given Message and note: Given Note
    * enter commit note: Updated
    * save commit
    Then commit with message: Given Message and note: Updated exists