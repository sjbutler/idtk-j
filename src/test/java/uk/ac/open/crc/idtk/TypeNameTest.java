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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Rudimentary tests of the type name class.
 *
 *
 */
public class TypeNameTest {
    
    @Test
    public void classNameOnlyTest() {
        TypeName tn = new TypeName( "SomeThing" );
        
        assertTrue( 
                "incorrect class name returned: \"" 
                        + tn.identifierName() 
                        + "\"", 
                "SomeThing".equals( tn.identifierName() ) );
        assertTrue( "unexpected value for fqn", tn.fqn().isEmpty());
        assertTrue( 
                "unexpected value for package name \"" 
                        + tn.packageName() 
                        + "\"", 
                tn.packageName().isEmpty() );
        assertTrue( "parameterised types found when none expected", tn.parameterisedTypes().isEmpty() );
        assertFalse( 
                "array count non-zero or isArrayDeclaration erroneously true", 
                tn.arrayDimensions() == 0 && tn.isArrayDeclaration() );
        
        assertTrue( 
                "type acronym not as expected: \"" 
                        + tn.typeAcronym() 
                        + "\"", 
                "st".equals( tn.typeAcronym() ) );
    }
    
    @Test
    public void arrayWithFqnTest() {
        TypeName tn = new TypeName( "org.foo.bar.SomeThing[][]" );
        assertTrue( 
                "incorrect class name returned: \"" 
                        + tn.identifierName() 
                        + "\"", 
                "SomeThing".equals( tn.identifierName() ) );
        assertTrue( "unexpected value for fqn", "org.foo.bar.SomeThing".equals( tn.fqn() ) );
        assertTrue( 
                "unexpected value for package name \"" 
                        + tn.packageName() 
                        + "\"", 
                "org.foo.bar".equals( tn.packageName() ) );
        assertTrue( "parameterised types found when none expected", tn.parameterisedTypes().isEmpty() );
        assertTrue( 
                "unexpected array dimension count: \""
                        + tn.arrayDimensions() 
                        + "\"", 
                tn.arrayDimensions() == 2 );
        assertTrue( 
                "isArrayDeclaration erroneously false", 
                tn.isArrayDeclaration() );
        
        assertTrue( 
                "type acronym not as expected: \"" 
                        + tn.typeAcronym() 
                        + "\"", 
                "st".equals( tn.typeAcronym() ) );
    }
    
    
    @Test
    public void classWithGenericsTest() {
        TypeName tn = new TypeName( "SomeThing<String,HashMap>" );
        assertTrue( 
                "incorrect class name returned: \"" 
                        + tn.identifierName() 
                        + "\"", 
                "SomeThing".equals( tn.identifierName() ) );
        assertTrue( "unexpected value for fqn", tn.fqn().isEmpty() );
        assertTrue( 
                "unexpected value for package name \"" 
                        + tn.packageName() 
                        + "\"", 
                tn.packageName().isEmpty() );
        assertTrue( 
                "unexpected number of parameterised types found: \"" 
                        + tn.parameterisedTypes().size()
                        + "\"", 
                tn.parameterisedTypes().size() == 2 );
        assertTrue( 
                "unexpected array dimension count: \""
                        + tn.arrayDimensions()
                        + "\"", 
                tn.arrayDimensions() == 0 );
        assertFalse( 
                "isArrayDeclaration erroneously true", 
                tn.isArrayDeclaration() );
        
        assertTrue( 
                "type acronym not as expected: \"" 
                        + tn.typeAcronym() 
                        + "\"", 
                "st".equals( tn.typeAcronym() ) );
    }
    
    @Test
    public void innerClassTest() {
        TypeName tn = new TypeName( "org.foo.bar.SomeThing.InnerClass" );
        
        assertTrue( 
                "type acronym not as expected: \"" 
                        + tn.typeAcronym() 
                        + "\"", 
                "ic".equals( tn.typeAcronym() ) );
        assertTrue( 
                "incorrect class name returned: \"" 
                        + tn.identifierName() 
                        + "\"", 
                "InnerClass".equals( tn.identifierName() ) );
        assertTrue( "unexpected value for fqn", "org.foo.bar.SomeThing.InnerClass".equals( tn.fqn() ) );
        assertTrue( 
                "unexpected value for package name \"" 
                        + tn.packageName() 
                        + "\"", 
                "org.foo.bar".equals(  tn.packageName() ) );
    }
    
}
