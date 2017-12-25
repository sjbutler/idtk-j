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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a service that expands contracted modal verbs. 
 * 
 */
public class Modals {

    private static final Logger LOGGER = LoggerFactory.getLogger( Modals.class );
    
    private static final HashMap<String,ArrayList<String>> contractionsMap;
    
    static {
        contractionsMap = new HashMap<>();
        InputStream inStream = 
                Modals.class.getResourceAsStream( "/contractions.txt" );
        try ( BufferedReader in = new BufferedReader(new InputStreamReader(inStream)) ) {
            String line;

            // read file
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(",");
                // sanity check
                if ( tokens.length == 2 ) {
                    String[] modalTokens = tokens[1].split( " " );
                    ArrayList<String> modalPhrase = new ArrayList<>();
                    modalPhrase.add( modalTokens[0] );
                    modalPhrase.add( modalTokens[1] );
                    contractionsMap.put( tokens[0], modalPhrase );
                }
            }
        }
        catch( IOException e ) {
            LOGGER.error(
                    "problem instantiating Modal Expansion component:{}", 
                    e.getMessage() );
            throw new IllegalStateException( 
                    "Could not instantiate Modal Expansion Component. "
                            + "Refer error to developer." );
        }
    }
    
    public static boolean isModal(String token) {
	return contractionsMap.containsKey(token);
    } 
    
    public static List<String> expand(List<String> tokens) {
	List<String> expanded = new ArrayList<>();
	
	tokens.forEach( (token) -> {
	    if ( isModal( token ) ) {
		expanded.addAll( contractionsMap.get( token ) );
	    }
	    else {
		expanded.add( token );
	    }
	} );
	return expanded;
    }
    
}
