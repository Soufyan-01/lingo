Feature: Start a game
  As a Player,
  I want to start a game so that i can practice my Lingo skills

Feature: Guess a word
  As a Player,
  I want to get an opportunity to get a 5, 6 or 7 acronym,
  In order to guess a word and improve my Lingo skills

Feature: Getting feedback
  As a Player,
  I want to get feedback after every attempt,
  In order to be send in the right direction

Feature: Request game status
  As a Player,
  I want to get my game status with score of the game and progress of the current round,
  In order to know how I am doing

Scenario: Start a new game
  When I want to begin a new game
  Then I should see one letter of an five acronym

Scenario: Show existing game
  Given The game that I want to see needs to exists
  When I select a game
  Then I should see the game information

Scenario: Start a new round
  Given I want to continue a game that exists
  When I want to start a new round
  Then I should be able to continue an game