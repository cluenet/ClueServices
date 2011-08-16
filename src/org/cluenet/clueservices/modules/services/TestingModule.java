package org.cluenet.clueservices.modules.services;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cluenet.clueservices.core.Core;
import org.cluenet.clueservices.core.Event;
import org.cluenet.clueservices.core.Module;
import org.cluenet.clueservices.ircEvents.PrivmsgEvent;
import org.cluenet.clueservices.ircEvents.ProtocolRequestEvent;
import org.cluenet.clueservices.ircEvents.ServerSyncEvent;
import org.cluenet.clueservices.ircEvents.UserJoinEvent;
import org.cluenet.clueservices.ircEvents.UserSignonEvent;
import org.cluenet.clueservices.ircObjects.ChannelFactory;
import org.cluenet.clueservices.ircObjects.UserFactory;
import org.cluenet.clueservices.ircObjects.ChannelFactory.Channel;
import org.cluenet.clueservices.ircObjects.UserFactory.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.caucho.quercus.QuercusEngine;
import com.caucho.quercus.lib.ApcModule;
import com.caucho.quercus.lib.ArrayModule;
import com.caucho.quercus.lib.BcmathModule;
import com.caucho.quercus.lib.ClassesModule;
import com.caucho.quercus.lib.CtypeModule;
import com.caucho.quercus.lib.ErrorModule;
import com.caucho.quercus.lib.ExifModule;
import com.caucho.quercus.lib.FunctionModule;
import com.caucho.quercus.lib.HashModule;
import com.caucho.quercus.lib.HtmlModule;
import com.caucho.quercus.lib.ImageModule;
import com.caucho.quercus.lib.JavaModule;
import com.caucho.quercus.lib.MathModule;
import com.caucho.quercus.lib.MhashModule;
import com.caucho.quercus.lib.MiscModule;
import com.caucho.quercus.lib.NetworkModule;
import com.caucho.quercus.lib.OptionsModule;
import com.caucho.quercus.lib.OutputModule;
import com.caucho.quercus.lib.QuercusModule;
import com.caucho.quercus.lib.TokenModule;
import com.caucho.quercus.lib.UrlModule;
import com.caucho.quercus.lib.VariableModule;
import com.caucho.quercus.lib.bam.BamModule;
import com.caucho.quercus.lib.curl.CurlModule;
import com.caucho.quercus.lib.date.DateModule;
import com.caucho.quercus.lib.db.MysqlModule;
import com.caucho.quercus.lib.db.MysqliModule;
import com.caucho.quercus.lib.db.OracleModule;
import com.caucho.quercus.lib.db.PDOModule;
import com.caucho.quercus.lib.db.PostgresModule;
import com.caucho.quercus.lib.dom.QuercusDOMModule;
import com.caucho.quercus.lib.file.FileModule;
import com.caucho.quercus.lib.file.SocketModule;
import com.caucho.quercus.lib.file.StreamModule;
import com.caucho.quercus.lib.gettext.GettextModule;
import com.caucho.quercus.lib.i18n.MbstringModule;
import com.caucho.quercus.lib.i18n.UnicodeModule;
import com.caucho.quercus.lib.jms.JMSModule;
import com.caucho.quercus.lib.json.JsonModule;
import com.caucho.quercus.lib.mail.MailModule;
import com.caucho.quercus.lib.mcrypt.McryptModule;
import com.caucho.quercus.lib.pdf.PDFModule;
import com.caucho.quercus.lib.reflection.ReflectionModule;
import com.caucho.quercus.lib.regexp.RegexpModule;
import com.caucho.quercus.lib.simplexml.SimpleXMLModule;
import com.caucho.quercus.lib.spl.SplModule;
import com.caucho.quercus.lib.string.StringModule;
import com.caucho.quercus.lib.xml.XMLWriterModule;
import com.caucho.quercus.lib.xml.XmlModule;
import com.caucho.quercus.lib.zip.ZipModule;
import com.caucho.quercus.lib.zlib.ZlibModule;


public class TestingModule extends Module {
	private QuercusEngine php = null;
	
	@Override
	protected void event( Event take ) {
		if( take instanceof ServerSyncEvent ) {
			if( ( (ServerSyncEvent) take ).getParameters().toString().equals( "theta.cluenet.org" ) ) {
				Core.fireEvent( new ProtocolRequestEvent( new UserSignonEvent( UserFactory.fake( "FooBar", "FooBar", "Foo.Bar", "Foo Bar", "+", "0.0.0.0", null ) ) ) );
			}
		} else if( take instanceof UserSignonEvent ) {
			User u = ( (UserSignonEvent) take ).getParameters();
			Channel c = ChannelFactory.find( "#clueirc" );
			if( u.getNick().equals( "FooBar" ) ) {
				Core.fireEvent( new ProtocolRequestEvent( new UserJoinEvent( u, c ) ) );
				Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( u, c, "\001ACTION eyes Damian.\001" ) ) );
			}
		} else if( take instanceof PrivmsgEvent ) {
			PrivmsgEvent evt = (PrivmsgEvent) take;
			if( evt.getSource() instanceof User && evt.getTarget() instanceof Channel ) {
				User u = (User) evt.getSource();
				Channel c = (Channel) evt.getTarget();
				String data = evt.getParameters();
				if( data.equals( "!testing" ) )
					Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( UserFactory.find( "FooBar" ), c, "Testing2." ) ) );
				else if( data.equals( "!force" ) )
					Core.fireEvent( new ProtocolRequestEvent( new UserJoinEvent( u, ChannelFactory.find( "#cluebotng" ) ) ) );
				else if( data.equals( "!xml" ) ) {
					try {
						DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
						Document doc = docBuilder.newDocument();
						
						Element root = doc.createElement( "XML" );
						doc.appendChild( root );
						
						root.appendChild( take.toXML( doc ) );
						
						TransformerFactory transformerFactory = TransformerFactory.newInstance();
						Transformer transformer = transformerFactory.newTransformer();
						DOMSource source = new DOMSource( doc );
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						StreamResult result =  new StreamResult( os );
						transformer.transform( source, result );
						
						String postData = URLEncoder.encode( "paste_format", "UTF-8" ) + "=" + URLEncoder.encode( "XML", "UTF-8" );
					    postData += "&" + URLEncoder.encode( "paste_subdomain", "UTF-8" ) + "=" + URLEncoder.encode( "cluenetpastes", "UTF-8" );
					    postData += "&" + URLEncoder.encode( "paste_name", "UTF-8" ) + "=" + URLEncoder.encode( "FooBar Bot", "UTF-8" );
					    postData += "&" + URLEncoder.encode( "paste_code", "UTF-8" ) + "=" + URLEncoder.encode( os.toString(), "UTF-8" );

					    // Send data
					    URL url = new URL( "http://pastebin.com/api_public.php" );
					    URLConnection conn = url.openConnection();
					    conn.setDoOutput( true );
					    OutputStreamWriter wr = new OutputStreamWriter( conn.getOutputStream() );
					    wr.write( postData );
					    wr.flush();

					    // Get the response
					    BufferedReader rd = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
					    String line;
					    while( ( line = rd.readLine() ) != null )
					    	Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( UserFactory.find( "FooBar" ), c, "XML: " + line ) ) );
					    wr.close();
					    rd.close();
					} catch( Exception e ) {
						Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( UserFactory.find( "FooBar" ), c, "Error: " + e.getMessage() ) ) );
						throw new RuntimeException( e );
					}
				} else if( data.startsWith( "!php " ) && ( u.getNick().equals( "Cobi" ) || u.getNick().equals( "Rich" ) ) ) {
					try {
						if( php == null ) {
							php = new QuercusEngine();
							// Add modules from this list: http://www.caucho.com/resin-4.0-javadoc/com/caucho/quercus/module/QuercusModule.html
							//php.getQuercus().addModule( new ApacheModule() );
							php.getQuercus().addModule( new ApcModule() );
							php.getQuercus().addModule( new ArrayModule() );
							php.getQuercus().addModule( new BamModule() );
							php.getQuercus().addModule( new BcmathModule() );
							php.getQuercus().addModule( new ClassesModule() );
							php.getQuercus().addModule( new CtypeModule() );
							php.getQuercus().addModule( new CurlModule() );
							php.getQuercus().addModule( new DateModule() );
							php.getQuercus().addModule( new ErrorModule() );
							php.getQuercus().addModule( new ExifModule() );
							php.getQuercus().addModule( new FileModule() );
							php.getQuercus().addModule( new FunctionModule() );
							php.getQuercus().addModule( new GettextModule() );
							php.getQuercus().addModule( new HashModule() );
							php.getQuercus().addModule( new HtmlModule() );
							//php.getQuercus().addModule( new HttpModule() );
							php.getQuercus().addModule( new ImageModule() );
							php.getQuercus().addModule( new JavaModule() );
							php.getQuercus().addModule( new JMSModule() );
							php.getQuercus().addModule( new JsonModule() );
							php.getQuercus().addModule( new MailModule() );
							php.getQuercus().addModule( new MathModule() );
							php.getQuercus().addModule( new MbstringModule() );
							php.getQuercus().addModule( new McryptModule() );
							php.getQuercus().addModule( new MhashModule() );
							php.getQuercus().addModule( new MiscModule() );
							php.getQuercus().addModule( new MysqliModule() );
							php.getQuercus().addModule( new MysqlModule() );
							php.getQuercus().addModule( new NetworkModule() );
							php.getQuercus().addModule( new OptionsModule() );
							php.getQuercus().addModule( new OracleModule() );
							php.getQuercus().addModule( new OutputModule() );
							php.getQuercus().addModule( new PDFModule() );
							php.getQuercus().addModule( new PDOModule() );
							php.getQuercus().addModule( new PostgresModule() );
							php.getQuercus().addModule( new QuercusDOMModule() );
							php.getQuercus().addModule( new QuercusModule() );
							php.getQuercus().addModule( new ReflectionModule() );
							php.getQuercus().addModule( new RegexpModule() );
							//php.getQuercus().addModule( new ResinModule() );
							//php.getQuercus().addModule( new SessionModule() );
							php.getQuercus().addModule( new SimpleXMLModule() );
							php.getQuercus().addModule( new SocketModule() );
							php.getQuercus().addModule( new SplModule() );
							php.getQuercus().addModule( new StreamModule() );
							php.getQuercus().addModule( new StringModule() );
							php.getQuercus().addModule( new TokenModule() );
							php.getQuercus().addModule( new UnicodeModule() );
							php.getQuercus().addModule( new UrlModule() );
							php.getQuercus().addModule( new VariableModule() );
							//php.getQuercus().addModule( new WebSocketModule() );
							php.getQuercus().addModule( new XmlModule() );
							php.getQuercus().addModule( new XMLWriterModule() );
							php.getQuercus().addModule( new ZipModule() );
							php.getQuercus().addModule( new ZlibModule() );
						}
						php.execute(
								"<?PHP " +
								"import org.cluenet.clueservices.core.Core; " +
								"import org.cluenet.clueservices.ircEvents.ProtocolRequestEvent; " +
								"import org.cluenet.clueservices.ircEvents.PrivmsgEvent; " +
								"import org.cluenet.clueservices.ircObjects.UserFactory; " +
								"import org.cluenet.clueservices.ircObjects.ChannelFactory; " +
								"import org.cluenet.clueservices.misc.IRC;" + 
								data.substring( 4 ) + 
								" ?>"
						);
					} catch( Exception e ) {
						Core.fireEvent( new ProtocolRequestEvent( new PrivmsgEvent( UserFactory.find( "FooBar" ), c, "Error: " + e.getMessage() + " " + e.toString() ) ) );
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	protected Boolean init() {
		return true;
	}
	
}
