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

/**
 * Identifies program entity species and provides a number of utility methods 
 * for grouping species.
 */
public enum Species {

    /** A member of an annotation. Has the description "annotation member". */
    ANNOTATION_MEMBER( "annotation member" ),
    /** An annotation. Has the description "annotation". */
    ANNOTATION( "annotation" ),
    /** A class. Hass the description "class". */
    CLASS( "class" ),
    /** A constructor. Has the description "constructor". */
    CONSTRUCTOR( "constructor" ),
    /** An enumeration constant. Has the description "enumeration constant". */
    ENUMERATION_CONSTANT( "enum constant" ),
    /** An enumeration. Has the description "enum". */
    ENUMERATION( "enum" ),
    /** A field. Has the description "field". */
    FIELD( "field" ),
    /** A formal argument. Has the description "formal argument". */
    FORMAL_ARGUMENT( "formal argument" ),
    /** An initialiser block. Has the description "initialiser". */
    INITIALISER( "initialiser" ), // oddity: initialisers are always anonymous
    /** An interface. Has the description "interface". */
    INTERFACE( "interface" ),
    /** A label. Has the description "label name". */
    LABEL( "label name" ),
    /** A local variable. Has the description "local". */
    LOCAL_VARIABLE( "local" ),
    /** A local class. Has the description "local class". */
    LOCAL_CLASS( "local class" ),
    /** A member class. Has the description "member class". */
    MEMBER_CLASS( "member class" ),
    /** A method. Has the description "method". */
    METHOD( "method" ),
    /** A nested interface. Has the description "nested interface". */
    NESTED_INTERFACE( "nested interface" );

    private final String description;

    private Species( String description ) {
        this.description = description;
    }

    /**
     * Indicates whether the species is a container that can hold 
     * name declarations. 
     * @return true if the species is a container
     */
    public boolean isContainer() {
        return (this == METHOD
                || this == CLASS
                || this == CONSTRUCTOR
                || this == INITIALISER
                || this == INTERFACE
                || this == LOCAL_CLASS
                || this == MEMBER_CLASS
                || this == ENUMERATION
                || this == NESTED_INTERFACE);
    }

    /**
     * Indicates whether the species is a class, including member 
     * and local classes.
     * @return true if the species is a class
     */
    public boolean isClass() {
        return (this == CLASS
                || this == LOCAL_CLASS
                || this == MEMBER_CLASS);
    }

    /**
     * Indicates if the species is an interface.
     * @return true if the species is an interface
     */
    public boolean isInterface() {
        return (this == INTERFACE
                || this == NESTED_INTERFACE);
    }

    /**
     * Indicates if the species is a class or interface.
     * @return true if the species is a class or interface
     */
    public boolean isClassOrInterface() {
        return (this.isClass() || this.isInterface());
    }

    /** 
     * Indicates if the species is a method.
     * @return true if the species is a method
     */
    public boolean isMethod() {
        return (this == METHOD);
    }

    /** 
     * Indicates if the species is a constructor.
     * @return true if the species is a constructor
     */
    public boolean isConstructor() {
        return (this == CONSTRUCTOR);
    }

    /** 
     * Indicates if the species is a reference .
     * @return true if the species is a reference.
     */
    public boolean isReference() {
        return (this == LOCAL_VARIABLE
                || this == FORMAL_ARGUMENT
                || this == FIELD);
    }

    /**
     * Indicates whether the species is a reference that isn't a field.
     * @return true if the species is a formal argument or local variable
     */
    public boolean isNonFieldReference() {
        return (this == LOCAL_VARIABLE || this == FORMAL_ARGUMENT);
    }

    /**
     * Returns a lower case {@code String} describing the species name. This
     * method should be used in preference to toString().
     *
     * @return A lower case {code String} that represents the species.
     */
    public String description() {
        return this.description;
    }

    /**
     * Recovers the enumeration member for the given textual description. 
     * The textual descriptions are given in documentation for each member 
     * of the enumeration.
     * @param description a species description
     * @return an enumeration member
     * @throws IllegalArgumentException if a null, empty or unrecognised 
     * description is passed to the method
     */
    public static Species getSpeciesFor( String description ) {
        if ( description == null ) {
            throw new IllegalArgumentException( 
                    "null value passed to getSpeciesFor()" );
        }

        for ( Species species : Species.values() ) {
            if ( species.description.equals( description ) ) {
                return species;
            }
        }

        throw new IllegalArgumentException( String.format( 
                "No Species found with description: \"%s\"", 
                description)); 
    }
}
