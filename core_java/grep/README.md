# Introduction
This project is mainly about simulating the grep command functionality using Java. It takes three arguments: the parameters, root directory path, a regular expression to be matched, and the output file path. It recursively walks through the root directory, then finds the files containing the given regular expression then puts those lines to the output file. Its first version is using standard Java IO operation to perform the functionality. The second version is using the lambda function and stream API from Java to optimize the performance.

# Quick Start
It takes three arguments.

**rootDirectory**: the root directory path to do the recursive operations.
**regex pattern**: the regular expression to be matched in files.
**output file path**: the result will be redirected to this file.

JavaGrep regex rootPath outFile
`JavaGrep .*Romeo.*Juliet.* /path/to/file/shakespeare.txt ./`

#Implementation
## Pseudocode
```
    List<File> fileList = listFiles(this.rootPath);
    List<String> matchedLines = new ArrayList<>();
    for(File f : fileList) {
      List<String> readLines = readLines(f);
      for(String s : readLines){
        if(containsPattern(s))
          matchedLines.add(s);
      }
    }
    writeToFile(matchedLines);
```

## Performance Issue
When dealing with large files, our program will not have a good performance, and we may encounter out-of-memory problems because we keep all the files in the memory.
To avoid these problems, we can use the stream and lambda.

# Test
1. Prepare the sample data
2. Run some test cases manually
3. Compare the results

# Deployment
1. Register Docker hub account
2. Create dockerfile
3. Clean maven package
4. Build a new docker image locally
5. Verify the image
6. Run docker container
7. Push the image to Docker Hub

# Improvement
* If users are dealing the large files, choose the stream and lambda version. Otherwise, they can use the standard Java I/O version for the files.
* We can modify the interface to allow some methods to return the stream rather than a list to improve the performance.
* Adding more flag options for users to achieve the furthermore grep functionalities.