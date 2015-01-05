package xdi2.cloudcard.util;

import org.springframework.stereotype.Component;

import xdi2.core.syntax.XDIAddress;

@Component
public class XdiModelConverter {

	public static XDIAddress XDI_FIRST_NAME = XDIAddress.create("<#first><#name>&");
	public static XDIAddress XDI_LAST_NAME = XDIAddress.create("<#last><#name>&");
	public static XDIAddress XDI_NICKNAME = XDIAddress.create("<#nickname>&");
	public static XDIAddress XDI_GENDER = XDIAddress.create("<#gender>&");
	public static XDIAddress XDI_BIRTH_DATE = XDIAddress.create("<#birth><#date>&");
	public static XDIAddress XDI_NATIONALITY = XDIAddress.create("<#nationality>&");
	public static XDIAddress XDI_PHONE = XDIAddress.create("<#phone>&");
	public static XDIAddress XDI_MOBILE_PHONE = XDIAddress.create("<#mobile><#phone>&");
	public static XDIAddress XDI_WORK_PHONE = XDIAddress.create("<#work><#phone>&");
	public static XDIAddress XDI_EMAIL = XDIAddress.create("<#email>&");
	public static XDIAddress XDI_WEBSITE = XDIAddress.create("<#website>&");
	public static XDIAddress XDI_ADDRESS_STREET = XDIAddress.create("#address<#street>&");
	public static XDIAddress XDI_ADDRESS_COUNTRY = XDIAddress.create("#address<#country>&");
	public static XDIAddress XDI_ADDRESS_LOCALITY = XDIAddress.create("#address<#locality>&");
	public static XDIAddress XDI_ADDRESS_POSTAL_CODE = XDIAddress.create("#address<#postal><#code>&");
	public static XDIAddress XDI_ADDRESS_REGION = XDIAddress.create("#address<#region>&");


}
