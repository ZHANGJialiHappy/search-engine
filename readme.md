## version control
In order to show my knowledge in distributed version control, I use git to control version.
1. Create a new branch, naming convention
    1. features/name-of-your-feature
    2. refactoring/name-of-the-refactoring-task
2. Create a pull request
3. merge
4. switch to Main, git pull, create new branch.

## useful tools & command
1. ./gradlew run
2. gradle test jacocoTestReport     build/reports/jacoco/test/html/index.html
3. gradle javadoc     build/docs/javadoc/index.html
4. gradle test

## task
1. Class design and feature set
2. Improve the data structures
    1. Change data structure, improve performance. Make the service fast with large size of data.
3. Add query functionality 
    1. faster Queries using an InvertedIndex.
    2. support OR query.
    3. support multiple words query.
6. Ranking Algorithms with 2 ways
    1. term frequency score.
    2. term frequency-inverse document score.
    3. make the upper 2 methods switch easily.

