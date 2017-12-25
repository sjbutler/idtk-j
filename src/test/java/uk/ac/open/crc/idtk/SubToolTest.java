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
 * Simple tests for {@code SubTool}.
 * 
 */
public class SubToolTest {
    @Test
    public void isSubTestLowerCase() {
	boolean outcome = SubTool.isSub( "sub" );

	assertThat("Lower case 'sub' not recognised", outcome, is(true));
    }

    @Test
    public void isSubTestMixedCase() {
	boolean outcome = SubTool.isSub( "SUb" );
	
	assertThat("Mixed case 'sub' not recognised", outcome, is(true));
    }
    
    @Test
    public void containsSubTestLowerCase() {
	List<String> tokens = new ArrayList<>();
	
	tokens.add("contains");
	tokens.add("sub");
	tokens.add("token");
	
	boolean outcome = SubTool.containsSub( tokens );
	assertThat("'sub' not recognised", outcome, is(true));
    }
    
    @Test
    public void containsSubTestLowerCaseNotPresent() {
	List<String> tokens = new ArrayList<>();
	
	tokens.add("contains");
	tokens.add("somethings");
	tokens.add("token");
	
	boolean outcome = SubTool.containsSub( tokens );
	assertThat("'sub' found in list of tokens", outcome, is(false));
	
    }
    
    @Test
    public void containsSubTestMixedCase() {
	List<String> tokens = new ArrayList<>();
	
	tokens.add("contains");
	tokens.add("SUb");
	tokens.add("token");
	
	boolean outcome = SubTool.containsSub( tokens );
	assertThat("'sub' not recognised", outcome, is(true));
    }
	    
    @Test 
    public void hasSubPrefixInListTestLowerCase() {
	List<String> tokens = new ArrayList<>();
	
	tokens.add("contains");
	tokens.add("subtoken");
	
	boolean outcome = SubTool.hasSubPrefix( tokens );
	assertThat("'sub' prefix not recognised", outcome, is(true));
    }
    
    @Test 
    public void hasSubPrefixInListTestMixedCase() {
	List<String> tokens = new ArrayList<>();
	
	tokens.add("contains");
	tokens.add("sUBToken");
	
	boolean outcome = SubTool.hasSubPrefix( tokens );
	assertThat("'sub' prefix not recognised", outcome, is(true));	
    }

    @Test 
    public void hasSubPrefixTestLowerCase() {	
	boolean outcome = SubTool.hasSubPrefix( "subtoken" );
	assertThat("'sub' prefix not recognised", outcome, is(true));	
    }
    
    @Test 
    public void hasSubPrefixTestMixedCase() {
	boolean outcome = SubTool.hasSubPrefix( "suBToken" );
	assertThat("'sub' prefix not recognised", outcome, is(true));	
    }

    @Test 
    public void hasSubPrefixTestLowerCaseSubNotPresent() {	
	boolean outcome = SubTool.hasSubPrefix( "token" );
	assertThat("'sub' prefix mistakenly recognised", outcome, is(false));	
    }
    
    
    // concatenation
    @Test
    public void doesNotConcatenateTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "some" );
	tokens.add( "token" );
	tokens.add( "list" );
	
	List<String> output = SubTool.process( tokens, SubTool.Policy.CONCATENATE );
	assertThat("Unexpected number of tokens returned by expansion", 
		output.size(), 
		is(3));
	assertThat("Unexpected token list returned by concatenation.",
		output,
		contains("some", "token", "list"));
    }
    
    @Test
    public void concatenationTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "some" );
	tokens.add( "sub" );
	tokens.add( "token" );
	tokens.add( "list" );
	
	List<String> output = SubTool.process( tokens, SubTool.Policy.CONCATENATE );
	assertThat("Unexpected number of tokens returned by concatenation", 
		output.size(), 
		is(3));
	assertThat("Unexpected token list returned by expansion.",
		output,
		contains("some", "subtoken", "list"));
    }
    
    
    
    // expansion
    
    @Test
    public void doesNotExpandTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "some" );
	tokens.add( "token" );
	tokens.add( "list" );
	
	List<String> output = SubTool.process( tokens, SubTool.Policy.EXPAND );
	assertThat("Unexpected number of tokens returned by expansion", 
		output.size(), 
		is(3));
	assertThat("Unexpected token list returned by expansion.",
		output,
		contains("some", "token", "list"));
    }
    
    
      @Test
    public void expansionTest() {
	List<String> tokens = new ArrayList<>();
	tokens.add( "some" );
	tokens.add( "subtoken" );
	tokens.add( "list" );
	
	List<String> output = SubTool.process( tokens, SubTool.Policy.EXPAND );
	assertThat("Unexpected number of tokens returned by expansion", 
		output.size(), 
		is(4));
	assertThat("Unexpected token list returned by expansion.",
		output,
		contains("some", "sub", "token", "list"));
    }
    
    
}
