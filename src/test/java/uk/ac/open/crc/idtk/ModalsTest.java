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
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Simple tests for the {@code Modals} helper class. 
 * 
 */
public class ModalsTest {

    @Test
    public void isModalTest() {
	boolean response = Modals.isModal( "cant" );
	assertThat("Unable to expand 'cant'", response, is(true));
    }
    
    @Test 
    public void singleTokenExpansionTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "wont" );
	List<String> expanded = Modals.expand( tokens );
	
	assertThat("Expansion of 'wont' contains wrong number of tokens", 
		expanded.size(), 
		is(2));
	assertThat("Wont expanded incorrectly", 
		expanded, 
		contains("will","not"));
    }
    
    
    @Test
    public void multipleTokenExpansionTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "they" );
	tokens.add( "cant" );
	tokens.add( "sing" );
	List<String> expanded = Modals.expand( tokens );
	
	assertThat("Expansion of 'they, cant, sing' contains wrong number of tokens", 
		expanded.size(), 
		is(4));
	assertThat("'they, cant, sing' expanded incorrectly", 
		expanded, 
		contains("they", "can","not", "sing"));
    }
    
    
    @Test
    public void multipleModalTokenExpansionTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "cant" );
	tokens.add( "shouldnt" );
	tokens.add( "didnt" );
	List<String> expanded = Modals.expand( tokens );
	
	assertThat("Expansion of 'cant, shouldnt, didnt' contains wrong number of tokens", 
		expanded.size(), 
		is(6));
	assertThat("'cant, shouldnt, didnt' expanded incorrectly", 
		expanded, 
		contains("can","not", "should", "not", "did","not"));
    }
}
