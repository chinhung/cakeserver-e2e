Feature: Create Commit

  Scenario: create commit successfully
    Given commit with message: New Message does not exist
    When create commit with message: New Message and note: New Note
    Then commit with message: New Message and note: New Note exists