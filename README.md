# HTML-toInMemory-XLS-Parser

## Summary
This assignment was given to me in my XML class in the Faculty of Mathematics and Informatics.

The assignment is to create program, which can accept HTML (or XLS) file,then parsed it to
in memory representation of XLS file. You can write XLS formulas and the program can 
execute them.

Currently the program accepts XLS formulas in the form:
```
A1=1;
A2=2;
A3=IF(A2>A1,0,1);
```
## Programming
The assignment is written in Java 1.7, Java EE 7, Primefaces 5.3, JSONP HTML Parser.
The project is deployed with Glassfish 4.1 Application Server.

## Future development
The programs can developed to: 
  * Accept and parse XLS files
  * Use regular expressions(currently it uses substrings)
  * Calculating more and more advanced XLS formulas
  * Exporting the in memory XLS to real XLS(or HTML) file
