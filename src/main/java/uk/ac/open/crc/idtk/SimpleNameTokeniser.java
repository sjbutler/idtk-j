/*
    Copyright (C) 2010-2015 The Open University
    Copyright (C) 2019-2020 Simon Butler

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A very simple, conservative name splitter. Only splits on underscores and 
 * lower case to upper case boundaries.
 */
public class SimpleNameTokeniser {
    private static final Pattern separatorPattern;
    private static final Pattern lcUcPattern;

    static {
        separatorPattern = Pattern.compile( "[\\_\\$]+" );
	lcUcPattern = Pattern.compile("\\p{Ll}\\p{Lu}");
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
    
    /**
     * Provides a naive camel case splitter to work on character only string.
     *
     * @param name a character only string
     * @return an Array list of components of the input string that result from
     * splitting on LCUC boundaries.
     */
    private static List<String> tokeniseOnLowercaseToUppercase( String name ) {
	ArrayList<String> splits = new ArrayList<>();
        Matcher matcher = lcUcPattern.matcher(name);
	int start = 0;
	while ( matcher.find() ) {
	    splits.add(name.substring(start, matcher.end() - 1));
	    start = matcher.end() - 1;
	}
	splits.add(name.substring(start, name.length()));

        return splits;
    }

    private static List<String> tokeniseOnLowercaseToUppercase( List<String> tokens ) {
        List<String> splits = new ArrayList<>();

        tokens.stream().forEach( (token) -> {
            splits.addAll( tokeniseOnLowercaseToUppercase( token ) );
        } );

        return splits;
    }

    private static List<String> tokeniseOnSeparators( String name ) {
        List<String> splits = separatorPattern.splitAsStream(name)
	    .filter(s -> s.length() > 0)
	    .collect(Collectors.toList());

        return splits;
    }

    // prevent instantiation as this class only provides static services
    private SimpleNameTokeniser() {}
}
