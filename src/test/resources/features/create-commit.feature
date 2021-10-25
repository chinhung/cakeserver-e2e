Feature: Create Commit

  Scenario: create commit successfully
    Given commit does not exist: New Message
    When create commit with message: New Message and note: New Note
    Then commit exists: New Message