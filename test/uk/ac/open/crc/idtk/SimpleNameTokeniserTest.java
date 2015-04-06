package uk.ac.open.crc.idtk;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 *
 *
 * @author Simon Butler (simon@facetus.org.uk)
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
       assertTrue("unrecognised string returned", "some".equals( tokens.get( 0 ) ));
       assertTrue("unrecognised string returned", "Thing".equals( tokens.get( 1 ) ));
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
    
}
