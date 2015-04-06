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
package uk.ac.open.crc.idtk;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Provides a simple representation of a Java type. 
 *
 * @author Simon Butler (simon@facetus.org.uk)
 */
public class TypeName {
    public static final String NO_TYPE = "#no type#";
    
    private String fqn;
    private String packageName;
    private String identifierName;
    private String localTypeName; // the name the type is addressed by within 
                                  // the package. So records nested types
    private ArrayList<TypeName> parameterisedTypes;
    
    private String fullName;  // base name for the type, sans generics
    
//    private boolean isResolved;  // flags whether the type is fully resolved
    private final String typeAcronym;
    
    private int arrayDimensions;
    
    /**
     * Create an instance given a type name as a string.
     * @param typeNameString a single string of the type name, 
     * e.g. java.util.HashMap<String,Object>
     */
    public TypeName(String typeNameString) {
        // what does a null string mean and how should the class behave.
        if ( NO_TYPE.equals( typeNameString ) ) {
            // this is for the crude no type representation
            this.identifierName = typeNameString;
            this.fqn = typeNameString;
            this.typeAcronym = null;
        }
        else {
            this.parameterisedTypes = new ArrayList<>();
//            this.isResolved = false;  // check the semantics for this
            // parse the string
            this.parse(typeNameString);
            this.typeAcronym = makeTypeAcronym( this.identifierName );
        }
    }
    
    /**
     * Principally designed for creating type names where the package 
     * name is known. 
     * @param packageName
     * @param typeNameString
     */
    public TypeName(String packageName, String typeNameString) {
        this.parameterisedTypes = new ArrayList<>();
        
        this.packageName = packageName;
       
        this.parse(typeNameString);
       
        this.typeAcronym = makeTypeAcronym( this.identifierName );
    }
    
    
    public String identifierName() {
        return this.identifierName;
    }
    
    /**
     * The package name.
     * @return a package name, or an empty string if it is not defined.
     */
    public String packageName() {
        return this.packageName;
    }
    
    /**
     * The fully qualified name of the type if present.
     * @return a fully qualified name, or {@code null} if it is not available.
     */
    public String fqn() {
        return this.fqn;
    }

    public List<TypeName> parameterisedTypes() {
        return new ArrayList<>(this.parameterisedTypes);
    }
    
    
    public boolean isArrayDeclaration() {
        return this.arrayDimensions > 0;
    }
    
    public int arrayDimensions() {
        return this.arrayDimensions;
    }
    
    public String typeAcronym() {
        return this.typeAcronym;
    }
    
    /**
     * Identifies the elements of the type name passed in and populates
     * the attributes.
     * 
     * <p>The intention is to take a type name, which at worst is 
     * an inner class with parameterised types and extract all the 
     * elements. So, package.name.Class.InnerClass<ParameterisedType, ...>[]
     * 
     * </p>
     * 
     * @param rawTypeName 
     */
    private void parse(String rawTypeName) {
        // take away any array dimensions (counting them of course)
        while ( rawTypeName.contains("[") ) {
            rawTypeName = rawTypeName.substring(0, rawTypeName.lastIndexOf("["));
            this.arrayDimensions++;
        }
        
        // strip away any parameterised types
        String typeParameters = "";
        if ( rawTypeName.contains("<") ) {
            int firstLeftAngleBracketIndex = rawTypeName.indexOf("<");
            typeParameters = rawTypeName.substring(firstLeftAngleBracketIndex);
            rawTypeName = rawTypeName.substring(0, firstLeftAngleBracketIndex);
            // and process them for the list
            extractParameterisedTypes(typeParameters);
        }
        
        // should have 
        //   - packageName?.OuterClass.InnerClass
        //   - packageName?.ClassName
        //   
        // or a nested class e.g. OuterClass.InnerClass
        if ( rawTypeName.matches(".*\\.[A-Z].*") ) {
            
            // if raw type name is a nested type it will start with a
            // capital letter
            // if it starts with a lower case letter then raw typename
            // must be an FQN
            if ( rawTypeName.matches("^[a-z].*") ) {
                this.fqn = rawTypeName;
//                this.isResolved = true;
            }
            
            int lastDotIndex = rawTypeName.lastIndexOf(".");
            this.identifierName = rawTypeName.substring(lastDotIndex + 1);
            // now we need to be sure that we get this right. The check 
            // is for any outer classes so that we can remove them to 
            // extract the package name
            
            do {
                lastDotIndex = rawTypeName.lastIndexOf(".");
                rawTypeName = rawTypeName.substring(0, lastDotIndex);
            }
            while ( rawTypeName.matches(".*\\.[A-Z].*") );
            
            // make sure we start with a lower case letter
            if ( rawTypeName.matches("^[a-z].*") ) {
                this.packageName = rawTypeName;
            }
        }
        else {
            // this should be a single word, but it could be 
            // 
            this.identifierName = rawTypeName;
            if ( this.packageName == null ) {
                this.packageName = "";
            }
        }
    }

    // the test on the incoming string is for '<' only
    // so the string "<" occasionally gets passed in. 
    // I have no idea why this is the case,but it does.
    // so treat it as a special case.
    /**
     * Strips one character from each end of a string.
     * 
     * @param s a string, hopefully with < and > at the ends.
     * @return the string without the terminal characters
     */
    private String removeOuterAngleBrackets(String s) {
        if ( s.startsWith( "<" ) ) {
            s = s.substring( 1 );
        }
        
        if ( s.endsWith( ">" ) ) {
            s = s.substring( 0, s.length() - 1 );
        }
        
        return s;
    }
    
    private final Character COMMA = ',';
    private final Character LEFT_ANGLE_BRACKET = '<';
    private final Character RIGHT_ANGLE_BRACKET = '>';
    
    
    private void extractParameterisedTypes(String typeParameters) {
        // suppose we start with <String,HashMap<String,ArrayList>>
        String parameterisedTypeString = this.removeOuterAngleBrackets(typeParameters);
        // ok - so any angle brackets make generics used for the type parameters
        // i.e. we should now have String,HashMap<String,ArrayList>
        // so now we split on commas ignoring anything between angle brackets
        int nestingLevel = 0;  // a bracket counter
        ArrayList<Integer> commaLocations = new ArrayList<>();
        
        int currentChar;
        for ( int index = 0; index < parameterisedTypeString.length(); index++ ) {
            currentChar = parameterisedTypeString.codePointAt(index);
            if ( currentChar == COMMA && nestingLevel == 0 ) {
                commaLocations.add(index);
            }
            else if ( currentChar == LEFT_ANGLE_BRACKET ) {
                nestingLevel++;
            }
            else if ( currentChar == RIGHT_ANGLE_BRACKET ) {
                nestingLevel--;
            }
        }
        
        // so now we have trapped the boundaries
        // we need to go to work
        // but first, the low hanging fruit
        if ( commaLocations.isEmpty() ) {
            // we have only one type
            this.parameterisedTypes.add(new TypeName(parameterisedTypeString));
        }
        else {
            int firstCharacterLocation = 0;
            for ( int commaLocationIndex = 0 ; commaLocationIndex < commaLocations.size(); commaLocationIndex++) {
                // we are processing a string of the form a,b(,c)*
                // so substring is first character to comma
                // 
                
                int commaLocation = commaLocations.get(commaLocationIndex);
                this.parameterisedTypes.add(new TypeName(parameterisedTypeString.substring(firstCharacterLocation, commaLocation).trim()));
                
                // update the record of the first character, so that it refs the
                // following type name
                firstCharacterLocation = commaLocation + 1;

                // for last comma we also need to extract the type name beyond the comma
                if ( commaLocationIndex == (commaLocations.size() - 1) ) {
                    this.parameterisedTypes.add(new TypeName(parameterisedTypeString.substring(firstCharacterLocation).trim()));
                }
            }
        }
    }
    
    private String makeTypeAcronym( String typeName ) {
        List<String> tokens = SimpleNameTokeniser.split( typeName );
        StringBuilder acronym = new StringBuilder();
        tokens.stream()
                .forEach( ( word ) -> { acronym.append( word.charAt( 0 ) ); } );
        
        return acronym.toString().toLowerCase();
    }
    
}
