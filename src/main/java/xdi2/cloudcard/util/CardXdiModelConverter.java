package xdi2.cloudcard.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import xdi2.cloudcard.model.Card;
import xdi2.cloudcard.model.CardField;
import xdi2.cloudcard.model.CardFieldPrivacy;
import xdi2.core.ContextNode;
import xdi2.core.Literal;
import xdi2.core.Relation;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.util.XDIAddressUtil;

@Component
public class CardXdiModelConverter {
	private static final Logger log = LoggerFactory.getLogger(CardXdiModelConverter.class);

	public static XDIAddress XDI_CARDS = XDIAddress.create("[$card]");
	public static XDIAddress XDI_CARD_PUBLIC = XDIAddress.create("$public");
	public static XDIAddress XDI_CARD_PRIVATE = XDIAddress.create("$private");

	public static XDIAddress XDI_CARD_DESCRIPTION = XDIAddress.create("$public<#description>&");
	public static XDIAddress XDI_CARD_TAG = XDIAddress.create("$public<#tag>&");
	public static XDIAddress XDI_CARD_CONNECT_BUTTON = XDIAddress.create("$public<#connect><#button>&");
	public static XDIAddress XDI_CARD_BACKGROUND_IMAGE = XDIAddress.create("$public<#background><#image>&");
	
	private static CardField DUMMY_CARD_FIELD = new CardField(null, null, CardFieldPrivacy.ONLY_ME);


	public Card convertXdiToCard(ContextNode cardPublicContextNode) {
		Assert.notNull(cardPublicContextNode);

		Card card = new Card();

		XDIAddress cardXdiAddress = XDIAddress.create(cardPublicContextNode.getXDIAddress().toString().replace("$public", ""));
		
		// get general card info
		card.setXdiAddress(cardXdiAddress.toString());

		Literal l = cardPublicContextNode.getGraph().getRootContextNode().getDeepLiteral(XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_DESCRIPTION));
		if (l != null) {
			card.setDescription(l.getLiteralDataString());
		}
		l = cardPublicContextNode.getGraph().getRootContextNode().getDeepLiteral(XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_TAG));
		if (l != null) {
			card.setTag(l.getLiteralDataString());
		}
		l = cardPublicContextNode.getGraph().getRootContextNode().getDeepLiteral(XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_CONNECT_BUTTON));
		if (l != null) {
			String encodedButtonMessage = l.getLiteralDataString();
			card.setMessageConnectButton(StringUtils.newStringUtf8(Base64.decodeBase64(encodedButtonMessage)));
		}
		l = cardPublicContextNode.getGraph().getRootContextNode().getDeepLiteral(XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_BACKGROUND_IMAGE));
		if (l != null) {
			card.setBackgroundImage(l.getLiteralDataString());
		}
		
		// get profile info
		card.putField("firstName", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_FIRST_NAME));
		card.putField("lastName", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_LAST_NAME));
		card.putField("nickname", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_NICKNAME));
		card.putField("gender", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_GENDER));
		card.putField("birthDate", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_BIRTH_DATE));
		card.putField("nationality", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_NATIONALITY));
		card.putField("phone", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_PHONE));
		card.putField("mobilePhone", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_MOBILE_PHONE));
		card.putField("workPhone", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_WORK_PHONE));
		card.putField("email", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_EMAIL));
		card.putField("website", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_WEBSITE));

		card.putField("address_street", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_ADDRESS_STREET));
		card.putField("address_postalCode", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_ADDRESS_POSTAL_CODE));
		card.putField("address_locality", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_ADDRESS_LOCALITY));
		card.putField("address_region", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_ADDRESS_REGION));
		card.putField("address_country", convertXdiToCardField(cardPublicContextNode, XdiModelConverter.XDI_ADDRESS_COUNTRY));

		return card;
	}

	private CardField convertXdiToCardField (ContextNode cardXdi, XDIAddress fieldXdi) {
		Assert.notNull(cardXdi);
		Assert.notNull(fieldXdi);

		XDIAddress cardXdiAddress = XDIAddress.create(cardXdi.getXDIAddress().toString().replace("$public", ""));
		
		CardField field = new CardField();

		XDIAddress fieldXdiAddress = XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_PUBLIC, fieldXdi);
		ContextNode fieldContextNode = cardXdi.getGraph().getRootContextNode().getDeepContextNode(fieldXdiAddress);
		field.setPrivacy(CardFieldPrivacy.PUBLIC);
		if (fieldContextNode == null) {
			fieldXdiAddress = XDIAddressUtil.concatXDIAddresses(cardXdiAddress, XDI_CARD_PRIVATE, fieldXdi);
			fieldContextNode = cardXdi.getGraph().getRootContextNode().getDeepContextNode(fieldXdiAddress);
			field.setPrivacy(CardFieldPrivacy.PRIVATE);
			if (fieldContextNode == null) {
				// field doesnt exist, returning dummy...
				return DUMMY_CARD_FIELD;
			}
		}

		Literal l = fieldContextNode.getLiteral();
		if (l != null) {
			field.setValue(l.getLiteralDataString());
			field.setXdiStatement(l.getStatement().toString());
		}
		else {
			Relation ref = fieldContextNode.getRelation(XDIAddress.create("$ref"));
			if (ref != null) {
				field.setXdiStatement(ref.toString());
			}
			else {
				log.debug("Address is not literal neither $ref, What is it? " + fieldContextNode.getGraph().toString("XDI DISPLAY", null));
				return DUMMY_CARD_FIELD;
			}
		}

		return field;
	}


}
