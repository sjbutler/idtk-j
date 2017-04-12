/*
    Copyright (C) 2010-2015 The Open University

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
/**
 * idtk provides utility classes for working with Java identifier names.
 * 
 * <h2>Classes</h2>
 * This library provides four classes that support working with Java 
 * identifier names. {@code Modifier} and {@code Species} are enumerations 
 * that codify Java modifiers and name species (such as class, method etc).
 * {@code SimpleNameTokeniser} does what it says on the tin. It is a very 
 * limited class that will tokenise a lot of names, particularly class 
 * names, but will come unstuck at times. {@code TypeName} represents 
 * Java type names and the various components of type names.
 * 
 * <h2>Caveats</h2>
 * This library is research software and is prone to change, it is also 
 * prone to not being maintained. The APIs of 
 * classes are subject to change, as is the behaviour of classes, and the 
 * number of classes in the library.
 * 
 */
package uk.ac.open.crc.idtk;
