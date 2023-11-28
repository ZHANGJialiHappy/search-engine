## version control
1. Create a new branch, naming convention
features/name-of-your-feature
refactoring/name-of-the-refactoring-task
2. Create a pull request
3. merge

## useful command
1. ./gradlew run
2. gradle test jacocoTestReport     build/reports/jacoco/test/html/index.html
3. gradle javadoc     build/docs/javadoc/index.html
4. gradle test

## task
1. Improve class design and feature set
2. Set up the repository on your machine (task 0)
3.  Cleaning up the prototype (tasks 1-2) 
    1. Improve test
    2. Divide into multiple file, It is not good write all the code in one file
    3. Make the code object orientation. 
    4. Write documentation(Javadoc???), choices
4. Improve the data structures (tasks 3) 
    1. Change data structure, improve performance. Make the service fast with large size of data.
5. Discuss! Add query functionality (task 4)
    1. Check the query, fx: if the query less than 3 characters, won’t call search
    2. Make query logic, fx: query only match the whole word
    3. Rank logic, fx: rank base on count number.
6. Discuss! Ranking Algorithms (task 5)

## question
1. readme?
2. git tree
3. difference between ./gradlew run and run in backend.
4. refined queries
getMatchingWebPages(String query).

