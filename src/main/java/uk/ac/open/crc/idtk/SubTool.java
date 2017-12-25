/*
    Copyright (C) 2017 Simon Butler

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
 * Experimental functionality to support consistent tokenisation of the particle 
 * 'sub'. Developers can create identifier names where 'sub' is, typographically, 
 * a separate token, e.g. 'topicSubMenu', and where it is concatenated with 
 * the following word. e.g. 'topicSubmenu'. The idea is that consistent 
 * use of 'sub' may support better analysis. However, there is no evidence to 
 * support this idea, so use the functionality wisely. Also not that the 
 * transformations are currently applied blindly, so the caller needs to 
 * determine that they are not oversplitting words such as 'subject' and 
 * 'submarine' - this functionality is likely to move to another library 
 * at some point, and the obvious improvement may be made then.
 * 
 */
public class SubTool {
    public enum Policy {
	CONCATENATE,
	EXPAND;
    } 
    
    /**
     * A case insensitive test of whether the supplied token 
     * begins with 'sub'.
     * @param token a string
     * @return {@code true} if the token begins with 'sub' regardless of case
     */
    public static boolean hasSubPrefix(String token) {
	return token.toLowerCase().startsWith( "sub" );        
    }
    
    
    /**
     * A case insensitive test of whether any of the supplied tokens 
     * begins with 'sub'.
     * @param tokens a list of strings to test
     * @return {@code true} if any token begins with 'sub' regardless of case
     */
    public static boolean hasSubPrefix(List<String> tokens) {
	return tokens.stream().anyMatch( token -> token.toLowerCase().startsWith( "sub" ));        
    }
    
    /**
     * A case insensitive test of whether any of the supplied tokens 
     * is 'sub'.
     * @param tokens a list of string
     * @return {@code true} if any token is 'sub' regardless of case
     */
    public static boolean containsSub(List<String> tokens) {
	return tokens.stream().anyMatch( token -> isSub( token));        
    }
    
    /**
     * A case insensitive test of whether the supplied tokens 
     * is 'sub'.
     * @param token a string
     * @return {@code true} if the token is 'sub' regardless of case
     */
    public static boolean isSub(String token) {
	return "sub".equalsIgnoreCase(token);
    }
    
    /**
     * Process a list of tokens to apply the stated policy to the tokens.
     * @param tokens a list of tokens to process
     * @param policy either to expand or concatenate tokens in the list
     * @return a processed list of tokens
     */
    public static List<String> process(List<String> tokens, Policy policy) {
	if (policy == Policy.CONCATENATE) {
	    return concatenate(tokens);
	}
	else {
	    return expand(tokens);
	}
    }
    
    private static List<String> concatenate(List<String> tokens) {
	if (! containsSub(tokens)) {
	    return tokens;
	}
	
	List<String> concatenatedTokens = new ArrayList<>();
	
	for (int i = 0; i < tokens.size(); i++) {
	    String token = tokens.get( i );
	    if ( isSub(token) && i < tokens.size() - 1 ) {
		concatenatedTokens.add(token + tokens.get( i + 1 ));
		i++;
	    }
	    else {
		concatenatedTokens.add(token);
	    }
	}
	
	return concatenatedTokens;
    }
    
    private static List<String> expand(List<String> tokens) {
	if (! hasSubPrefix(tokens) ) {
	    return tokens;
	}
	
	List<String> expandedTokens = new ArrayList<>();
	
	tokens.forEach( token -> { 
	    if (hasSubPrefix(token)) {
		expandedTokens.add( token.substring( 0, 3) );
		expandedTokens.add( token.substring( 3) );
	    }
	    else {
		expandedTokens.add(token);
	    }
	});
	
	return expandedTokens;
    }
}
