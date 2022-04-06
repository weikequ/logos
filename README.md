# Logical statement calculator

## Background
Logical expressions and logical equivalency is something that's easy to test, but hard to prove.
Working through the CPSC 121 material necessitates a lot of trial and error, and generating
truth tables (*especially* for 4+ variables) is excruciating. Fundamentally, this calculator will 
take 2 input strings, parse them into logical statements, and then generate a truth table to 
compare their equivalency. For anyone taking CPSC 121 or any discrete mathematics course, this
calculator would be extremely helpful.

## User Stories

### Phase 1
**Functionality**
- I want to be able to enter a simple logical statement, eg: a&b or a|b or a&b
- I want to be able to use variables to represent booleans in the logical statement
- I want to be able to see the truth table of a logical statement
- I want to be able to add logical statements to a statement collection to be retrieved 
and used to generate truth tables or be evaluated later

**UI**
- I want to see a basic console UI
- I want to see basic displays of boolean results and truth tables
- I want to see a basic representation of my collection

### Phase 2
**Functionality**
- I want to be able to save my collection of statements onto the disk
- I want to be able to read my collection of statements from the disk

### Phase 3
**UI**
- I want to see a modern looking GUI

### Phase 4

## Phase 4: Task 2

>Logos Application Log:
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: a&b
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T|F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:19 PST 2021  
>Col - Added statement: T&F
>
>Mon Nov 22 15:50:22 PST 2021  
>Col - Removed statement at index 4: T&F
>
>Mon Nov 22 15:50:23 PST 2021  
>Col - Removed statement at index 0: a&b
>
>Mon Nov 22 15:50:27 PST 2021  
>Eval - Evaluated statement T|F = true
>
>Mon Nov 22 15:50:34 PST 2021  
>Col - Added statement: a&b
>
>Mon Nov 22 15:50:37 PST 2021  
>Eval - Evaluated statement F&F = false
>
>Mon Nov 22 15:50:37 PST 2021  
>Eval - Evaluated statement F&T = false
>
>Mon Nov 22 15:50:37 PST 2021  
>Eval - Evaluated statement T&F = false
>
>Mon Nov 22 15:50:37 PST 2021  
>Eval - Evaluated statement T&T = true
>
>Mon Nov 22 15:50:37 PST 2021  
>LS - Generated truth table for a&b: FFFT

## Phase 4: Task 3
Overall, there are a lot of things that can be refactored to work better
and adhere better to object oriented design principles. The GUI class MainWindow
is where I would focus most of my refactoring efforts. It is currently an
amalgamation of different fields and methods that can be better represented by
additional classes with respect to the single responsibility principle. It may
also be possible to improve coupling behaviour by abstracting some duplicate code
by creating more abstract/generalized methods. 

In addition, the current MainWindow class lacks maintainability and readability,
the code is long, cumbersome, and interwoven with different dependencies that
are not immediately clear. This can definitely be improved further to become
more logically ordered/encapsulated.