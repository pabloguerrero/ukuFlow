package de.tudarmstadt.dvs.ukuflow.tools;

import java.util.Vector;

import org.apache.commons.codec.binary.Base64;

public class Base64Converter {
	public static String getBase64String(Vector<Byte> input){
		byte tmp[] = new byte[input.size()];
		for(int i = 0; i < tmp.length; i++){
			tmp[i] = input.get(i);
		}		
		return Base64.encodeBase64String(tmp);
	}
	public static String getBase64String(Integer... input){
		byte tmp[] = new byte[input.length];
		for(int i = 0; i < input.length; i++){
			tmp[i] = input[i].byteValue();
		}
		return Base64.encodeBase64String(tmp);
	}
	public static byte[] decodeFrom(String base64){
		return Base64.decodeBase64(base64);
	}
}
