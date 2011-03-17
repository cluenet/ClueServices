package org.cluenet.clueservices.testing;

import java.io.IOException;

import com.caucho.quercus.Quercus;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.StringBuilderValue;
import com.caucho.quercus.function.AbstractFunction;
import com.caucho.quercus.program.QuercusProgram;


public class Testing {
	static Quercus php = null;
	static Env env = null;
	
	public static void init() {
		if( php == null ) {
			php = new Quercus();
			env = new Env( php );
			php.startEnv( env );
		}
	}
	
	public static String callPHPFunction( String code, String functionName, String arg ) throws IOException {
		init();
		QuercusProgram program = php.parseCode( code );
		for( AbstractFunction fun : program.getFunctionList() )
			if( fun.getName().equals( functionName ) )
				return fun.call( env, new StringBuilderValue( arg ) ).toStringValue().toString();
		return null;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ScriptException 
	 */
	public static void main( String[] args ) throws IOException {
		System.out.println( callPHPFunction( "function foo( $bar ) { $bar .= ' is cool in Java.'; return $bar; }", "foo", "PHP" ) );
	}
	
}
