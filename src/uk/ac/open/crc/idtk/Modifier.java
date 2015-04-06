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
 *
 * Provides a simple enumeration for representing Java modifiers.
 *
 * @author Simon Butler (simon@facetus.org.uk)
 */
public enum Modifier {

    PUBLIC( "public" ),
    PROTECTED( "protected" ),
    PRIVATE( "private" ),
    ABSTRACT( "abstract" ),
    STATIC( "static" ),
    FINAL( "final" ),
    SYNCHRONIZED( "synchronized" ),
    NATIVE( "native" ),
    TRANSIENT( "transient" ),
    VOLATILE( "volatile" ),
    STRICTFP( "strictfp" );

    private final String description;

    private Modifier( String description ) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public boolean isPublic() {
        return this == PUBLIC;
    }

    public boolean isFinal() {
        return this == FINAL;
    }

    public boolean isPrivate() {
        return this == PRIVATE;
    }

    public boolean isAbstract() {
        return this == ABSTRACT;
    }

    private boolean hasDescription( String description ) {
        return this.description.equals( description );
    }

    public static Modifier getModifierFor( String description ) {
        if ( description == null ) {
            throw new IllegalArgumentException( "null description passed to getModifierFor()" );
        }
        for ( Modifier modifier : Modifier.values() ) {
            if ( modifier.hasDescription( description ) == true ) {
                return modifier;
            }
        }

        return null;
    }
}
