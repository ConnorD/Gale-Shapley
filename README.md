Gale-Shapley
============

Gale-Shapley stable matching algorithm implemented in Java.

Input: a text file that defines the preference list of each man and woman, where there are n number of men and n number of women. Each person lists n people in their pref list.

On each line of the input file, the first word is the person that is listing their preferences, and the following words on that line are their preferences in order of importance.

Output: Each line represents a couple. Each couple, obviously, consists of one man and one woman. There are n number of couples (or lines) in the output, and they are paired such that they are a stable matching.