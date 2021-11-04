Feature: Delete Commit

    Background:
        Given create commit with message: Given Message and note: Given Note

    Scenario: delete commit
        When select commit with message: Given Message and note: Given Note
        * delete commit
        Then commit with message: Given Message and note: Given Message does not exist