# GriddyBot User Guide

GriddyBot is a CLI Interface chatbot that is able to store your tasks and check them off.

## Adding To Dos

To Dos are tasks with only descriptions.

Syntax: `todo [description]`

The output will give you a summary of the todo added along with the total number of tasks.

```
Got it. I've added this task:
[T][ ] task1
Now you have 1 task(s) in the list.
```

## Adding Deadlines

Deadlines are tasks with a description and due date.

Syntax: `deadline [description] /by [due date]`

The output will give you a summary of the deadline added along with the total number of tasks.

```
Got it. I've added this task:
[D][ ] task1 (by: monday)
Now you have 10 task(s) in the list.
```

## Adding Events

Events are tasks with a description, a start and end date.

Syntax: `event [description] /from [start date] /to [end date]`

The output will give you a summary of the event added along with the total number of tasks.

```
Got it. I've added this task:
[E][ ] task1 (from: monday to: friday)
Now you have 11 task(s) in the list.
```

## Deleting Tasks

All tasks can be deleted.

Syntax: `delete [task number]`

The output will give you a summary of the event deleted along with the remaining number of tasks.

```
I've deleted this task:
[E][ ] task1 (from: monday to: friday)
Now you have 10 task(s) in the list.
```

## Marking / Unmarking Tasks

All tasks can be marked as done or undone.

Syntax: `mark [task number]`
Syntax: `unmark [task number]`

The output will display the task marked/unmarked along with its new status.

```
I've marked this task as done:
[E][X] task1 (from: monday to: friday)
```

```
I've marked this task as not done:
[E][ ] task1 (from: monday to: friday)
```

## View Task List

A list of all tasks can be viewed at any time.

Syntax: `list`

The output will display the list of tasks, along with their marked status.

```
1.[T][X] tasktodo
2.[D][ ] deadline (by: monday)
3.[E][ ] event1 (from: monday to: friday)
```

## Searching for Tasks

Input a keyword to search for matching tasks.

Syntax: `find [keyword]`

The output will show you a list of tasks matching the keyword.

```
Here are the matching tasks in your list:
1.[D][ ] return book (by: Dec 02 2019, 6:00pm)
2.[D][ ] return book (by: 2/12/2019 2800)
3.[D][ ] return book (by: 2/12/2019 2800)
```

## Exit

Exit the chatbot.

Syntax: `bye`
