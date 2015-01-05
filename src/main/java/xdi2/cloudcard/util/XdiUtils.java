package xdi2.cloudcard.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import xdi2.core.syntax.XDIAddress;

public class XdiUtils {

	public static String normalizeCloudName (String cloudName) {
		if (! (cloudName.startsWith("=") || cloudName.startsWith("*") || cloudName.startsWith("+")) ) {
			return "=" + cloudName;
		}
		return cloudName;
	}
	
	public static XDIAddress convertIdToXdiAddress(String id) {
		return XDIAddress.create(StringUtils.newStringUtf8(Base64.decodeBase64(id)));
	}
	
	public static String convertXdiAddressToId(String xdiAddress) {
		return Base64.encodeBase64String(xdiAddress.getBytes());
	}
	
	public static String convertXdiAddressToId(XDIAddress xdiAddress) {
		return convertXdiAddressToId(xdiAddress.toString());
	}
}
