Feature: API Testing
Scenario: To verify and generate bearer token
Given user has base uri
And user generates bearer token
And user get valid toolid
Then user create order for valid tool id