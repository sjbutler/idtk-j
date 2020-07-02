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

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Rudimentary test of the conservative tokeniser.
 *
 *
 */
public class SimpleNameTokeniserTest {

    @Test
    public void singleTokenTest() {
       List<String> tokens = SimpleNameTokeniser.split( "something" );
       
       assertTrue("more than one token returned", tokens.size() == 1 );
       assertTrue("unrecognised string returned", "something".equals( tokens.get( 0 ) ));
    }
    

    @Test
    public void twoTokenTest() {
       List<String> tokens = SimpleNameTokeniser.split( "someThing" );
       
       assertTrue("expected two tokens", tokens.size() == 2 );
       assertTrue(String.format("unrecognised string returned: \"%s\"", tokens.get(0)), "some".equals( tokens.get( 0 ) ));
       assertTrue(String.format("unrecognised string returned: \"%s\"", tokens.get(1)), "Thing".equals( tokens.get( 1 ) ));
    }
    
    @Test
    public void threeTokenTest() {
       List<String> tokens = SimpleNameTokeniser.split( "someThingElse" );
       
       assertTrue("expected three tokens", tokens.size() == 3 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "Thing".equals( tokens.get( 1 ) ));
       assertTrue("unrecognised string returned", "Else".equals( tokens.get( 2 ) ));
    }
    
    @Test
    public void digitTokenTest() {
       List<String> tokens = SimpleNameTokeniser.split( "someThing2Eat" );
       
       assertTrue("expected two tokens", tokens.size() == 2 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "Thing2Eat".equals( tokens.get( 1 ) ));
    }
    
    @Test
    public void underscoreTest() {
       List<String> tokens = SimpleNameTokeniser.split( "some_thing" );
       
       assertTrue("expected two tokens", tokens.size() == 2 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "thing".equals( tokens.get( 1 ) ));
    }
    
    @Test
    public void twoUnderscoreTest() {
       List<String> tokens = SimpleNameTokeniser.split( "some_thing_else" );
       
       assertTrue("expected three tokens", tokens.size() == 3 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "thing".equals( tokens.get( 1 ) ));
       assertTrue("unrecognised string returned", "else".equals( tokens.get( 2 ) ));
    }
    
   @Test
   public void contiguousUnderscoreTest() {
       List<String> tokens = SimpleNameTokeniser.split( "some__thing____else" );
       
       assertTrue("expected three tokens", tokens.size() == 3 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "thing".equals( tokens.get( 1 ) ));
       assertTrue("unrecognised string returned", "else".equals( tokens.get( 2 ) ));
    }
    
    @Test
    public void contiguousUnderscoreWithDollarTest() {
       List<String> tokens = SimpleNameTokeniser.split( "$$some_$_thing___$_else" );
       
       assertTrue("expected three tokens", tokens.size() == 3 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "thing".equals( tokens.get( 1 ) ));
       assertTrue("unrecognised string returned", "else".equals( tokens.get( 2 ) ));
    }

    @Test
    public void contiguousUnderscoreWithDollar2Test() {
       List<String> tokens = SimpleNameTokeniser.split( "$$some_$_thing___$_else___$$" );
       
       assertTrue("expected three tokens", tokens.size() == 3 );
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "thing".equals( tokens.get( 1 ) ));
       assertTrue("unrecognised string returned", "else".equals( tokens.get( 2 ) ));
    }
}
