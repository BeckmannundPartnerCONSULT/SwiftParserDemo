package de.beckdev;

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.mt.mt5xx.MT537;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context
 * where the message type to parse is unknown and also it can be a system
 * message. This example uses the generic parser instead of the AbstractMT
 * class.
 * 
 * @author Based on www.prowidesoftware.com - extended by Uli Hofmann BPC 
 * @since 7.7
 */
public class ParseUnknownMessageFromFileExample {

	public static void main(String[] args) throws IOException {
		/*
		 * Read the file and create an instance of the generic parser for it Parse from
		 * File could also be used here
		 */
		String msg = "";
		if (args.length < 1) {
			System.err.println("Please provide a filename to be parsed\n");

		}
		FileReader reader = new FileReader(args[0]);
		BufferedReader inBuffer = new BufferedReader(reader);

		String line = inBuffer.readLine();

		while (line != null) {
			// System.err.println(line);
			msg = msg + line + "\n";
			line = inBuffer.readLine();
		}
		
		// The file contains a set of messges. split em up.

		String[] msg1;
		msg1 = msg.split("\n-}");
		/*
		 * 
		 * Read and parse the file content into a SWIFT message object Parse from File
		 * could also be used here
		 */
		//System.err.println( "Messages in file: "+ ((msg1.length) -1) );

		for (int i=0; i < msg1.length -1; i++) {
			int count =0;
			MT537 mt =
					// MT103.parse(Lib.readResource("c:\\uli\\mt103.txt", null));
					MT537.parse(msg1[i]);
			/*
			 * Print header information
			 */
			// System.err.println("Sender: " + mt.getSender());
			// System.err.println("Receiver: " + mt.getReceiver());
			// System.err.println( "\nSEQUENCE A" );	

			MT537.SequenceA seqA = mt.getSequenceA();
			List <MT537.SequenceB2> seqB = mt.getSequenceB2List();

			ListIterator<MT537.SequenceB2> seqBi = seqB.listIterator();
            int blen = seqB.size();
			while( seqBi.hasNext() ) {
			
			// SEME
			if( true == seqA.containsTag("20C")) {
				Field f20c = seqA.getFieldByName("20C");
				String v = f20c.getComponent(2);
				if( null != v ) {
					System.out.print(  v );
				
				}
				System.out.print( ";" );	
			}
			else
				System.out.print( ";" );	
			
			if( true == seqA.containsTag("97B")) {
				Field f97b = seqA.getFieldByName("97B");
				String f97bv = f97b.getComponent(2);
				if( null != f97bv ) {
					System.out.print( f97bv );
				}
				System.out.print( ";" );	
			}
			else {
				if( true == seqA.containsTag("97A")) {
					Field f97a = seqA.getFieldByName("97A");
					String f97av = f97a.getComponent(2);
					if( null != f97av ) {
						System.out.print( f97av );
					}
					System.out.print( ";" );	
				}
				else
					System.out.print( ";" );
			}
            // SEQUENCE B	= "STAT" Liste		
			MT537.SequenceB2  seqBb = seqBi.next(); 
			
            
				
				Field f25D = seqBb.getFieldByName("25D");
				if( f25D != null ) {
					if( null != f25D.getComponent(1)) {
						System.out.print(f25D.getComponent(1));				
					}	
					System.out.print( ";");
					if( null != f25D.getComponent(3)) {
						System.out.print( f25D.getComponent(3));				
				
					}	
					System.out.print( ";");
				}
				else
					System.out.print( ";;");

				// B1
				//System.err.println( "\nSEQUENCE B1" );	
				
				List <SwiftTagListBlock> seqREAS = seqBb.getSubBlocks("REAS");
				Iterator<SwiftTagListBlock> seqREASi = seqREAS.iterator(); 
				
				SwiftTagListBlock REASblock = seqBb.getSubBlock("REAS");
				Field f24B = REASblock.getFieldByName( "24B");
				if( f24B != null ) {
					if( null != f24B.getComponent(1)) {
						System.out.print( f24B.getComponent(1));				
					}	
					System.out.print( ";");
					if( null != f24B.getComponent(3)) {
						System.out.print( f24B.getComponent(3));				
					}	
					System.out.print( ";");
				}
				else
					System.out.print( ";;");
					
				// System.err.println( "\nSEQUENCE B2" );	
				
				SwiftTagListBlock TRANblock = seqBb.getSubBlock("TRAN");
				SwiftTagListBlock  seqTRANSDETs = TRANblock.getSubBlock("TRANSDET");
				Field f35B = seqTRANSDETs.getFieldByName( "35B");
				if( f35B != null ) {
					if( null != f35B.getComponent(2)) {
						System.out.print( f35B.getComponent(2));				
					}	
					System.out.print( ";");
				}
			
				Field f36B = seqTRANSDETs.getFieldByName( "36B");
				if( f36B != null ) {
					if( null != f36B.getComponent(3)) {
						System.out.print( f36B.getComponent(3));				
					}	
					System.out.print( ";");
				}
				else
					System.out.print( ";");
					
				Field f19A = seqTRANSDETs.getFieldByName( "19A");
				if( f19A != null ) {
					if( null != f19A.getComponent(3)) {
						String c3 = f19A.getComponent(3);
						System.out.print( c3);			
					}	
					System.out.print( ";");
					if( null != f19A.getComponent(4)) {
						String c4 = f19A.getComponent(4);
						System.out.print( c4);				
					}	
					System.out.print( ";");
				}
				else
					System.out.print( ";;");
//
				Field f22F = seqTRANSDETs.getFieldByName( "22F");
				if( f22F != null ) {
					if( null != f22F.getComponent(1)) {
						String c1 = f22F.getComponent(1);
						System.out.print( c1);			
					}	
					System.out.print( ";");
					if( null != f22F.getComponent(3)) {
						String c3 = f22F.getComponent(3);
						System.out.print( c3 );				
					}	
					System.out.print( ";");
				}
				else
					System.out.print( ";;");
//
				
				Field[] f98Al = seqTRANSDETs.getFieldsByName( "98A");
				//  :98A::EXSE//20190917
				//	:98A::SETT//20190917
				//	:98A::TRAD//20190912
				//	:98A::EXVA//20190917
				 
				String s=null;
				String exse=null;
				String sett=null;
				String trad=null;
				String exva=null;
				
				for( int ll=0; ll < f98Al.length; ll++) {
					if( f98Al[ll].getComponent(1).equals( "EXSE" )) {
						s = f98Al[ll].getComponent(2);
						exse = s.substring(6,8)+"."+s.substring(4,6)+"."+s.substring(0,4);
					}
					else
					if( f98Al[ll].getComponent(1).equals( "SETT" )) {
							s = f98Al[ll].getComponent(2);		
							sett=s.substring(6,8)+"."+s.substring(4,6)+"."+s.substring(0,4);
					}
					else
					if( f98Al[ll].getComponent(1).equals( "TRAD" )) {
							s = f98Al[ll].getComponent(2);		
							trad = s.substring(6,8)+"."+s.substring(4,6)+"."+s.substring(0,4);
					}
					else
					if( f98Al[ll].getComponent(1).equals( "EXVA" )) {
							s = f98Al[ll].getComponent(2);		
							exva = s.substring(6,8)+"."+s.substring(4,6)+"."+s.substring(0,4);
					}
				}
				if( null != exse )
					System.out.print( exse );
				System.out.print( ";");
				if( null != sett )
					System.out.print( sett );
				System.out.print( ";");
				if( null != trad )
					System.out.print( trad );
				System.out.print( ";");
				if( null != exva )
					System.out.print( exva );
				System.out.print( ";");
					
				Field[] f22Hl = seqTRANSDETs.getFieldsByName( "22H");
				String rede=null;
				for( int ll=0; ll < f22Hl.length; ll++) {
					if( f22Hl[ll].getComponent(1).equals( "REDE" )) {
						rede = f22Hl[ll].getComponent(2);
					}
				}
				if( null != rede )
					System.out.print( rede );
				System.out.print( ";");
				// "SETPRTY"
				String deag=null;
				String reag=null;
				List<SwiftTagListBlock>  seqSETPRTYs = seqTRANSDETs.getSubBlocks("SETPRTY");
	
				ListIterator<SwiftTagListBlock> seqSETPRTYi = seqSETPRTYs.listIterator();
				while( seqSETPRTYi.hasNext() ) {
					SwiftTagListBlock prtyb = seqSETPRTYi.next();
					Field f95P = prtyb.getFieldByName("95P");
					Field f95R = prtyb.getFieldByName("95R");
					if( f95P != null) {
						if( f95P.getComponent( 1 ).equals("DEAG") ) {
							deag = f95P.getComponent( 2 );
						}
						if( f95P.getComponent( 1 ).equals("REAG") ) {
							reag = f95P.getComponent( 2 );
						}
					}
					if( f95R != null) {
						if( f95R.getComponent( 1 ).equals("DEAG") ) {
							deag = f95R.getComponent( 2 );
						}
						if( f95R.getComponent( 1 ).equals("REAG") ) {
							reag = f95R.getComponent( 2 );
						}
					}
				}				
				if( deag != null) {
					System.out.print( deag);
				}
				System.out.print( ";");
				if( reag != null) {
					System.out.print( reag);
				}
				System.out.print( ";");

				
				
				System.out.println( "");
				
			}

			
			List <MT537.SequenceC> seqC = mt.getSequenceCList();
			ListIterator<MT537.SequenceC> seqCi = seqC.listIterator();			
			while( seqCi.hasNext() ) {
				System.err.print( "Section C found;");
			}
		}
		//System.out.println( "\n-----------------------");
	}
}

