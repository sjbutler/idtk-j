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
 * Provides a simple enumeration to represent Java modifiers.
 */
public enum Modifier {
    /** Represents the Java {@code abstract} modifier.  */
    ABSTRACT( "abstract" ),
    /** Represents the Java {@code final} modifier. */
    FINAL( "final" ),
    /** Represents the Java {@code native} modifier. */
    NATIVE( "native" ),
    /** Represents the Java {@code private} modifier. */ 
    PRIVATE( "private" ),
    /** Represents the Java {@code protected} modifier. */ 
    PROTECTED( "protected" ),
    /** Represents the Java {@code public} modifier. */
    PUBLIC( "public" ),
    /** Represents the Java {@code static} modifier. */
    STATIC( "static" ),
    /** Represents the Java {@code strictfp} modifier. */
    STRICTFP( "strictfp" ),
    /** Represents the Java {@code synchronized} modifier.  */
    SYNCHRONIZED( "synchronized" ),
    /** Represents the Java {@code transient} modifier. */
    TRANSIENT( "transient" ),
    /** Represents the Java {@code volatiel} modifier. */
    VOLATILE( "volatile" );

    private final String description;

    private Modifier( String description ) {
        this.description = description;
    }

    /**
     * Retrieves a lower case description of the modifier consisting of a 
     * single word identical to the modifier text found in a source code 
     * file.
     * @return the modifier as a lower case string
     */
    public String description() {
        return this.description;
    }

    /**
     * Indicates whether this is an instance of the abstract modifier.
     * @return {@code true} if the modifier is abstract
     */
    public boolean isAbstract() {
        return this == ABSTRACT;
    }
    
    /**
     * Indicates whether this is an instance of the final modifier.
     * @return {@code true} if the modifier is final
     */
    public boolean isFinal() {
        return this == FINAL;
    }
    
    /**
     * Indicates whether this is an instance of the native modifier.
     * @return {@code true} if the modifier is native
     */
    public boolean isNative() {
        return this == NATIVE;
    }
    
    /**
     * Indicates whether this is an instance of the private modifier.
     * @return {@code true} if the modifier is private
     */
    public boolean isPrivate() {
        return this == PRIVATE;
    }
    
    /**
     * Indicates whether this is an instance of the protected modifier.
     * @return {@code true} if the modifier is protected
     */
    public boolean isProtected() {
        return this == PROTECTED;
    }
    
    /**
     * Indicates whether this is an instance of the public modifier.
     * @return {@code true} if the modifier is public
     */
    public boolean isPublic() {
        return this == PUBLIC;
    }

    /**
     * Indicates whether this is an instance of the static modifier.
     * @return {@code true} if the modifier is static
     */
    public boolean isStatic() {
        return this == STATIC;
    }
            
    /**
     * Indicates whether this is an instance of the strictfp modifier.
     * @return {@code true} if the modifier is strictfp
     */
    public boolean isStrictfp() {
        return this == STRICTFP;
    }
            
    /**
     * Indicates whether this is an instance of the synchronized modifier.
     * @return {@code true} if the modifier is synchronized
     */
    public boolean isSynchronized() {
        return this == SYNCHRONIZED;
    }
            
    /**
     * Indicates whether this is an instance of the transient modifier.
     * @return {@code true} if the modifier is transient
     */
    public boolean isTransient() {
        return this == TRANSIENT;
    }
            
    /**
     * Indicates whether this is an instance of the volatile modifier.
     * @return {@code true} if the modifier is volatile
     */
    public boolean isVolatile() {
        return this == VOLATILE;
    }

    /**
     * Retrieves a modifier matching the given description. Convenience method 
     * for obtaining modifiers when parsing source code.
     * @param description a modifier in lower case
     * @return a {@code Modifier}
     * @throws IllegalArgumentException if {@code null}, or an 
     * empty or unrecognised description is passed to the method
     */
    public static Modifier getModifierFor( String description ) {
        if ( description == null ) {
            throw new IllegalArgumentException( 
                    "null description passed to getModifierFor()" );
        }
        for ( Modifier modifier : Modifier.values() ) {
            if ( modifier.description.equals( description ) ) {
                return modifier;
            }
        }

        throw new IllegalArgumentException( 
            String.format( 
                    "Unrecognised modifier description passed to "
                            + "getModifierFor(): \"%s\"", 
                    description ) );
    }
    
}
