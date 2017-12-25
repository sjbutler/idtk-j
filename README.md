# idtk-j

idtk-j is a small library of utility classes for working with Java
identifier names. The classes represent Java modifiers and Java name
species (class, method etc), as well as Java type names. There is also
a class that provides a (very) simple identifier name splitter.

## Copyright and License

idtk-j is Copyright (C) 2010-2015 The Open University and is open
source software licensed under version 2.0 of the Apache License.
A copy of the licence can be found in the file LICENCE and the
text is available at https://www.apache.org/licenses/LICENSE-2.0

## Requirements

idtk-j requires Java v8 to run. It is straightforward to revise
idtk-j to run using Java 7 if necessary, but becoming more difficult.

## Build
A build script is available for Gradle which will download dependencies, 
and a pom is provided, but not tested with maven.

The current version is 0.5.1 and can be accessed by adding  
`compile 'uk.org.facetus:idtk-j:0.5.1'` to `build.gradle`.

## Dependencies

idtk-j uses SLF4J for logging. Incorporating SLF4J in (existing) code
is straightforward. A copy of slf4j-api.x.x.x.jar must be on the
classpath when compiling and running nominal. Another slf4j jar
will also be required to support logging through your chosen logging
system -- see http://slf4j.org/ for more information. idtk-j was
coded against slf4j v1.7.24, so v1.7.24 or greater should work.
