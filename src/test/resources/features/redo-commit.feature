Feature: Redo Commit

    Background:
        Given create commit with message: Given Message and note: Given Note

    Scenario: redo commit message
        When select commit with message: Given Message and note: Given Note
        * enter commit message: Updated
        * redo commit
        Then current commit message is Given Message

    Scenario: redo commit note
        When select commit with message: Given Message and note: Given Note
        * enter commit note: Updated
        * redo commit
        Then current commit note is Given Note