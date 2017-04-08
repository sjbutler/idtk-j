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
 */package uk.ac.open.crc.idtk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A very simple, conservative name splitter. Only splits on underscores and 
 * lower case to upper case boundaries.
 */
public class SimpleNameTokeniser {

    private static final Logger LOGGER
            = LoggerFactory.getLogger( SimpleNameTokeniser.class );
    private static final Set<Integer> javaSeparators;

    static {
        javaSeparators = new HashSet<>();
        javaSeparators.add( "_".codePointAt( 0 ) );
        javaSeparators.add( "$".codePointAt( 0 ) );
    }

    /**
     * Tokenises the given name.
     * @param name a name
     * @return a list of tokens found in the name
     */
    public static List<String> split( String name ) {
        List<String> firstPassTokens = tokeniseOnSeparators( name );

        List<String> tokens = tokeniseOnLowercaseToUppercase( firstPassTokens );

        return tokens;
    }

    private static boolean isSeparator( Integer codePoint ) {
        return javaSeparators.contains( codePoint );
    }

    private static List<String> tokeniseOnSeparators( String name ) {
        ArrayList<String> splits = new ArrayList<>();

        ArrayList<Integer> candidateBoundaries = new ArrayList<>();

        Integer currentChar;

        // now search for boundaries
        for ( Integer index = 0; index < name.length(); index++ ) {
            currentChar = name.codePointAt( index );

            if ( isSeparator( currentChar ) ) {
                // we have a character that
                //  (a) can be tossed; and
                //  (b) may have a boundary both before and after
                // so, boundary before
                if ( index > 0 && !isSeparator( name.codePointAt( index - 1 ) ) ) {
                    candidateBoundaries.add( index - 1 );
                    // NB: this also records the final char of a name
                    // with trailing underscores
                }

                // and boundary after
                // now to mark the separator to any character transition
                // using lookahead
                if ( index < (name.length() - 1)
                        && !isSeparator( name.codePointAt( index + 1 ) ) ) {
                    candidateBoundaries.add( index + 1 );
                }
            }
            else {
                if ( index == 0 ) {
                    candidateBoundaries.add( index );
                }

                // now check whether this is the terminal character.
                // and record it to give us the final boundary
                if ( index == name.length() - 1 ) {
                    candidateBoundaries.add( index );
                }
            }
        }

        if ( candidateBoundaries.size() % 2 == 1 ) {
            LOGGER.warn(
                    "Odd number of boundaries found for: \"{}\"",
                    name );
        }

        for ( int i = 0; i < candidateBoundaries.size(); i += 2 ) {
            splits.add( name.substring( candidateBoundaries.get( i ), candidateBoundaries.get( i + 1 ) + 1 ) );
        }

        return splits;
    }

    /**
     * Provides a naive camel case splitter to work on character only string.
     *
     * @param name a character only string
     * @return an Array list of components of the input string that result from
     * splitting on LCUC boundaries.
     */
    private static List<String> tokeniseOnLowercaseToUppercase( String name ) {
        List<String> splits = new ArrayList<>();
        // the following stores data in pairs (start, finish, start, ...)
        ArrayList<Integer> candidateBoundaries = new ArrayList<>();

        // now process the array looking for boundaries
        for ( Integer index = 0; index < name.length(); index++ ) {

            if ( index == 0 ) {
                // the first character is always a boundary
                candidateBoundaries.add( index );
            }
            else {
                if ( Character.isUpperCase( name.codePointAt( index ) )
                        && Character.isLowerCase( name.codePointAt( index - 1 ) ) ) {
                    candidateBoundaries.add( index - 1 );
                    candidateBoundaries.add( index );
                }
            }

            // now check whether this is the terminal character.
            // and record it to give us the final boundary
            if ( index == name.length() - 1 ) {
                candidateBoundaries.add( index );
            }
        }

        if ( candidateBoundaries.size() % 2 == 1 ) {
            LOGGER.warn(
                    "Odd number of boundaries found for: \"{}\"",
                    name );
        }

        for ( int i = 0; i < candidateBoundaries.size(); i += 2 ) {
            splits.add( name.substring( candidateBoundaries.get( i ), candidateBoundaries.get( i + 1 ) + 1 ) );
        }

        return splits;
    }

    private static List<String> tokeniseOnLowercaseToUppercase( List<String> tokens ) {
        List<String> splits = new ArrayList<>();

        tokens.stream().forEach( (token) -> {
            splits.addAll( tokeniseOnLowercaseToUppercase( token ) );
        } );

        return splits;
    }

    // hide the constructor as this class only provides static services
    private SimpleNameTokeniser() {}
}
