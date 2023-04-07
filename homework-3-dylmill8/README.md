# CS193HW3: Git
 
## What is this assignment
In this assignment, you will be working on the basics of git. Git, as you heard from lecture, is amazing for keeping track of source code. The website you're currently reading this on, and for all the past homeworks as well, is GitHub. There **is** a difference between Git and GitHub! Think of Git as the local repository and GitHub as the central repository. The local repository is what you have on your computer. So when you transfer something from your local repository, you are transferring it to GitHub. In this homework, you will be practicing source control using Git and managing your local and central repository. Afterward, you will be answering questions and turning those answers in on GitHub. 
 
## When is this due?
This homework will be due on Friday, 10/21 at 11:59 PM Eastern Time.
 
## When will I know I'm done?
Advice: read the steps!!
 
You are done when you can see your answers to the tasks in `answers.txt` and `worksheet.txt` on **GitHub** and not just the terminal.
 
Keep in mind: **For us to see your work it has to be on GitHub, aka the central repository. We can't grade your work if it is just in your local repository.**
 
## Step 1: Clone the repository. TODOs exist here
### TODO 1 
As you've done before, everything git related usually starts with a clone. Once you have created your copy of the Homework 3 repository (`homework-3-<your GitHub username>`), **run** the command `git clone https://github.com/Purdue-CS193/homework-3-<your GitHub username>.git`. As you know, all this command does is to download a copy of the *remote* repository to your *local* machine. 
 
Now if you run `ls`, you should see a directory called `homework-3-<your GitHub username>`. `cd` into this directory and run `ls` again and you should see all the same files that GitHub shows in the browser for this repository. 
 
## Step 2: Status
One of the most frequently used git commands is `git status`. Any time you are inside a git repository (i.e. your current directory or one of its parents is an initialized git repository), you can run `git status` to see precisely what state your repository is in. 
 
Make sure you are in the Homework 4 directory and try running `git status`. The output of this command tells you many things. 
 
The first line `On branch master` tells you that you are currently working from the `master` branch of the repository (more on branches later).
 
The second line `Your branch is up to date with 'origin/master'` tells you that your local repository (the files stored on your computer) are in the same state as the files stored remotely on the GitHub repository.
 
The third line `nothing to commit, working tree clean` tells you that you have not made any changes to the files in the repository at this point.
 
## Step 3: Sync Local with Remote. TODOs exist in this step!
It is important to understand how useful git and GitHub can be for collaboration. For a moment, let's pretend that you and someone else are both working on solving Homework 3 together. We will **simulate** someone else pushing changes to Homework 4 while you are working on it locally. To do this, make sure you have already cloned the Homework 3 repo onto your local machine. Next, in your web browser, navigate to your Homework 3 repository on GitHub. 
 
### TODO 2
Open the `answers.txt` file in GitHub from the web browser click the pencil icon to edit the file. Under `Part 1`, write a message to yourself. This can be anything you want. Scroll to the bottom of the page where it says "Commit changes". Press `Commit changes` to commit this message to the `master` branch. Again, make sure this change is made from GitHub in your web browser!! 
 
Now, locally in your terminal, run `git status` again in the Homework 3 directory. Notice the output this time! Wait... it's still the same. Why does `git status` tell us  `Your branch is up to date with 'origin/master'` if we just made a change to the `master` branch on GitHub?
 
Even though we made a change to a file in the remote server (GitHub), we need to tell git that it needs to `fetch` new information from the remote server before it will know that we made a change. Run `git fetch` in the Homework 3 directory and then `git status` again. 
 
This time notice how git tells you
 
`Your branch is behind 'origin/master' by 1 commit and can be fast-forwarded. (use "git pull" to update your local branch)` 
 
This means that there is one commit (the message you wrote to yourself) on the branch `origin/master` that is not in your current branch. Let's get this commit into your current branch! Just like git tells you, run `git pull` to `pull` the new changes from the remote branch into your local branch. If you run `git status`, you should see that everything is up to date again (also look at `answers.txt` and verify that your message is now there!).
 
### TODO 3
Open `answers.txt` on your local computer (NOT FROM GITHUB) with a text editor and fill out the remaining question for Task 1 in answers.txt
 
## Step 4: Making a commit. TODOs are here too
Now that we've made a change to `answers.txt` we need to commit this change. Run `git status` in the Homework 3 directory. Notice how git lists the `Changes not staged for commit`. As expected, `answers.txt` is listed as a file that we have changed. 
 
To `commit` the change, we need to first `stage` `answers.txt` so it is ready to be committed. To do this, run `git add answers.txt` and then run `git status` again. git now tells us that `Changes to be committed:` includes `answers.txt`. To stage all changed files you can run `git add .`
 
Perfect! We are now ready to make our commit. Run `git commit` and in the text editor that pops up, write a detailed commit message such as "Added description for 'git fetch' in answers.txt". After you save your commit message and exit the text editor, your commit is complete! If you would like to do it all in one line, you can use the `-m` flag. Let's say I want my message to be *Implemented smoother control flow*. I would run `git commit -m "Implemented smoother control flow"`. 
 
Now if we go back to GitHub, we should see this change right???? WRONG! Remember from Task 1 that syncing your local git repository with the remote server (GitHub) is not automatic! To `push` your local changes to the remote server, run `git push`. Now check the GitHub Homework 3 repository again. BAM! There are your fancy new changes. *Note it might take a couple of seconds*. 
 
*IMPORTANT NOTE: In a git repository, NONE of your work will be saved without making a commit. A commit is the only way for you to tell git that it needs to care about the changes you made. Because of this, a common and extremely important git mantra is COMMIT EARLY AND COMMIT OFTEN. Keeping this in mind as you work on more involved projects with git will save you hours of headache about losing files and undoing yesterday's stupid, dumb changes you thought were genius at 3 in the morning (I speak from experience).*
 
![alt text](https://image.slidesharecdn.com/git-mume12-121022042023-phpapp02/95/an-introduction-to-git-9-638.jpg?cb=1350879713)
 
### TODO 4
Open `answers.txt` on your local computer (NOT FROM GITHUB) with a text editor and answer the question for Task 2, then repeat the steps we just did to commit this change and push it to GitHub.
 
## Step 4: Worksheet. Another TODO!
Congratulations! You've successfully used a source control tool, Git! Now picture how it would look in a big project setting. Multiple people will be editing various files within a source folder. Instead of having to share computers or use google docs, GitHub provides easy access to source control. Commit messages help coworkers and teammates understand the flow of your program, and fetching and pulling allow you to easily add in teammates' code. Other features of Git such as branching allows for even more efficient source control. 
 
### TODO 5
Complete the worksheet on GitHub. You can just open it in the browser and add your answers. Remember to hit the big green `Commit changes` button on the bottom when you're finished or else we can't grade it!!
 
## Rubric
|        Task         | Points |
| ------------------- | ------ |
| Answers.txt: Part 1 |    1   |
| Answers.txt: Task 1 |    2   |
| Answers.txt: Task 2 |    3   |
| Worksheet.txt: Q1   |    2   |
| Worksheet.txt: Q2   |    2   |
| Worksheet.txt: Q3   |    2   |
| Worksheet.txt: Q4   |    2   |
| Worksheet.txt: Q5   |    2   |
| Worksheet.txt: Q6   |    2   |
| Worksheet.txt: Q7   |    2   |
|       Total         |   20   |
 
## Reminder
Please ask questions if you need them.  Reach out on Ed Discussion, office hours, or PSOs. For grading issues on homeworks, please email one of your PSO TAs. If you email us, we will happily inform you to email your PSO TAs. Good luck :)
