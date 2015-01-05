package xdi2.cloudcard.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xdi2.client.XDIClient;
import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.exceptions.Xdi2DiscoveryException;
import xdi2.client.http.XDIHttpClient;
import xdi2.client.http.ssl.XDI2X509TrustManager;
import xdi2.cloudcard.model.Card;
import xdi2.cloudcard.util.CardXdiModelConverter;
import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.Relation;
import xdi2.core.exceptions.Xdi2ParseException;
import xdi2.core.features.linkcontracts.instance.PublicLinkContract;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.XDIReader;
import xdi2.core.io.XDIReaderRegistry;
import xdi2.core.syntax.CloudNumber;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.syntax.XDIStatement;
import xdi2.core.util.XDIStatementUtil;
import xdi2.core.util.iterators.ReadOnlyIterator;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.GetOperation;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;

@Service
public class CardService {

	private static final Logger log = LoggerFactory.getLogger(CardService.class);

	
	@Autowired
	private CardXdiModelConverter cardXdiModelConverter;


	public Card getCard(String env, String id) throws Xdi2ClientException {
		
		log.debug("Trying to get card in address: " + id);
		
		// handles the shortcut address
		if (id.contains("$card") == false) {
			id = id + "$card";
		}
		
		id += "$public";
		
		Card card = getCardFromPublicLC(env, id);

		return card;
	}
	

	public Card getCard(String env, String id, String privateCardXdi) throws Xdi2ParseException, IOException {

		Graph cardGraph = MemoryGraphFactory.getInstance().openGraph();
		XDIReader xdiReader = XDIReaderRegistry.getAuto();
		xdiReader.read(cardGraph, new StringReader(privateCardXdi));
		
		ContextNode cardContextNode = cardGraph.getDeepContextNode(XDIAddress.create(id));
		if (cardContextNode == null) {
			return null;
		}
		
		Card card = cardXdiModelConverter.convertXdiToCard(cardContextNode);
		
		
		// Discover CloudName
		ReadOnlyIterator<Relation> relations = cardGraph.getDeepRelations(XDIAddress.create(getCloudIdentifier(id)), XDIAddress.create("$is$ref"));
		while (relations.hasNext()) {
			Relation r = relations.next();
			card.setCloudName(r.getTargetContextNodeXDIAddress().toString());
		}
		
		return card;
	}
	
	private Card getCardFromPublicLC(String env, String cardXdiAddress) throws Xdi2DiscoveryException, Xdi2ClientException {

		XDIAddress cloudIdentifier = XDIAddress.create(getCloudIdentifier(cardXdiAddress));
		
		// Discover Cloud Endpoint
		XDI2X509TrustManager.enable();
		
		XDIDiscoveryClient xdiDiscoveryClient = "OTE".equals(env) ? XDIDiscoveryClient.NEUSTAR_OTE_DISCOVERY_CLIENT : XDIDiscoveryClient.NEUSTAR_PROD_DISCOVERY_CLIENT;
        XDIDiscoveryResult result = xdiDiscoveryClient.discoverFromRegistry(cloudIdentifier, null);

        if (result.getCloudNumber() == null) {
    		throw new RuntimeException("It was not possible to discover " + cloudIdentifier);
        }
        
        URL xdiEndpoint = result.getXdiEndpointUrl();
        CloudNumber cloudNumber = result.getCloudNumber();
        
        // Query cloud for cloud names
		XDIClient client = new XDIHttpClient(xdiEndpoint);
		MessageEnvelope me = new MessageEnvelope();
		Message m = me.createMessage(XDIAddress.create("$anon"));
		m.setToPeerRootXDIArc(cloudNumber.getPeerRootXDIArc());
		m.setLinkContract(PublicLinkContract.class);
		
		GetOperation operation = m.createGetOperation(XDIAddress.create(cardXdiAddress));
		operation.setParameter(GetOperation.XDI_ADD_PARAMETER_DEREF, Boolean.TRUE);
		
		m.createGetOperation(XDIStatementUtil.concatXDIStatement(cloudNumber.getXDIAddress(), XDIStatement.create("/$is$ref/{}")));

		MessageResult mr = client.send(me, null);
		
		ContextNode cardContextNode = mr.getGraph().getRootContextNode().getDeepContextNode(XDIAddress.create(cardXdiAddress));
		if (cardContextNode == null) {
			throw new RuntimeException("Card not found.");
		}
		
		Card card = cardXdiModelConverter.convertXdiToCard(cardContextNode);
		
		
		// Discover CloudName
		ReadOnlyIterator<Relation> relations = mr.getGraph().getDeepRelations(cloudNumber.getXDIAddress(), XDIAddress.create("$is$ref"));
		while (relations.hasNext()) {
			Relation r = relations.next();
			card.setCloudName(r.getTargetContextNodeXDIAddress().toString());
		}
		
		return card;
		
		
	}
		
	private static String getCloudIdentifier(String cardXdiAddress) {
	
		/* 
		 * Possible formats:
		 * 				* =alice[$card]!:uuid:1111
		 * 				* =alice#home$card
		 * 				* =alice$card
		*/
		
		int end = cardXdiAddress.indexOf("#");
		if (end < 0)
			end = cardXdiAddress.indexOf("[$");
		if (end < 0)
			end = cardXdiAddress.indexOf("$");
		if (end < 0)
			return null;
		
		return cardXdiAddress.substring(0, end);
	}
	

}
