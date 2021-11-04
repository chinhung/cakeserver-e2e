Feature: Copy Commit Message

    Background:
        Given create commit with message: Given Message and note: Given Note

    Scenario: copy commit message
        When select commit with message: Given Message and note: Given Note
        * copy commit message
        * enter commit note from clipboard
        * save commit
        Then commit with message: Given Message and note: Given Message exists