
# Customer Statement Processor

Command line application to process and validate monthly statement records delivered from customers. This information is delivered in two formats, CSV and XML. These records need to be validated against the rules:

-  all transaction references should be unique 
-   the end balance needs to be validated

  

## Application Architecture


This application uses hexagonal separation of concerns.

## How to run

As a command line application you will generate a fat jar first and then can run the application.

### Packing

```
./gradlew jar
```

### Running CLI

```
java -jar build/libs/customer-statement-validator-1.0.jar "filePath=/dirFiles" 
```

### Arguments

 - filePath - represents the directory where files to read either CSV or XML are located.

### Output report

A `/report` directory will be created withing files in it containing the validations of each statement proccessed, following the same file extension.

## Application decisions


#### Type
- Command line application as per requirement

### Architechure
**Hexagonal** is in place to keep interfaces and implementations segregated to decouple the app, and facilitate new implementations.

**No external dependency** to keep the application as smaller as possible, using only Java Core.

### Design patterns
#### > Chain of responsability
Used to apply rules along a chain of handlers with only one call.

#### > Decorator
Used to apply the rule for duplicate statements after applying rules for each/singular statement.

*Point of attention*: In case of many other rules that would need to traverse all statements again, it must be more appropriate to apply Chain of Responsibility as well, with an abstract method being called while iterating through statements preventing exponential time processing.

#### Parallelism
Each file will be read, processed, and written  by a separate thread, using default thread pool.

#### File Reading Memory Efficient 

For CSV the application reads all lines from a file as a Stream. Unlike reading all lines, this method does not read all lines into a List but instead populates lazily as the stream is consumed, avoiding doubling the lines versus Statement objects.

For XML, an improvement is preferable as a better maintainable approach. Now the application uses DOM reading, which does load the entire XML file into memory.


## Improvements

- Add robust logging system
- Prepare for native image, reducing application size
- Memory efficiency for XML Reading
- Time efficiency to process aggregated rules 
- Add cleaner fluent API that abastract tecnical details and focus more on  business
