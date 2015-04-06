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
 * Identifies program entity species and provides a number of useful methods.
 *
 * @author Simon Butler (simon@facetus.org.uk)
 */
public enum Species {

    ANNOTATION_MEMBER( "annotation member" ),
    ANNOTATION( "annotation" ),
    CLASS( "class" ),
    CONSTRUCTOR( "constructor" ),
    ENUMERATION_CONSTANT( "enum constant" ),
    ENUMERATION( "enum" ),
    FIELD( "field" ),
    FORMAL_ARGUMENT( "formal argument" ),
    INITIALISER( "initialiser" ), // oddity: initialisers are always anonymous
    INTERFACE( "interface" ),
    LABEL( "label name" ),
    LOCAL_VARIABLE( "local" ),
    LOCAL_CLASS( "local class" ),
    MEMBER_CLASS( "member class" ),
    METHOD( "method" ),
    NESTED_INTERFACE( "nested interface" );

    private final String description;

    private Species( String description ) {
        this.description = description;
    }

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

    public boolean isClass() {
        return (this == CLASS
                || this == LOCAL_CLASS
                || this == MEMBER_CLASS);
    }

    public boolean isInterface() {
        return (this == INTERFACE
                || this == NESTED_INTERFACE);
    }

    public boolean isClassOrInterface() {
        return (this.isClass() || this.isInterface());
    }

    public boolean isMethod() {
        return (this == METHOD);
    }

    public boolean isConstructor() {
        return (this == CONSTRUCTOR);
    }

    public boolean isReference() {
        return (this == LOCAL_VARIABLE
                || this == FORMAL_ARGUMENT
                || this == FIELD);
    }

    public boolean isNonFieldReference() {
        return (this == LOCAL_VARIABLE || this == FORMAL_ARGUMENT);
    }

    private boolean hasDescription( String description ) {
        return this.description.equals( description );
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
     * Recovers an enumeration member for a textual description.
     * @param description One of the species descriptions.
     * @return an enumeration member.
     */
    public static Species getSpeciesFor( String description ) {
        if ( description == null ) {
            throw new IllegalArgumentException( "null value passed to getSpeciesFor()" );
        }

        for ( Species species : Species.values() ) {
            if ( species.hasDescription( description ) ) {
                return species;
            }
        }

        throw new IllegalArgumentException( 
                "No IdentifierSpecies with description: \"" 
                        + description + "\" found" );
    }
}
