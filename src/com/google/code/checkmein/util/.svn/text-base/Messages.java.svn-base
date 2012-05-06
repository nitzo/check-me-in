package com.google.code.checkmein.util;

import javax.mail.Multipart;



import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


import com.google.code.checkmein.db.Customer;
import com.google.code.checkmein.db.DatabaseLogic;
import com.google.code.checkmein.db.Event;
import com.google.code.checkmein.db.QR;

/*
 * Library for messages functions
 */
public class Messages {
	
	private static String QRemailTemplate = null;
	private static String endurl;
	
	public static boolean sendQRCode(Customer customer, Event event){
		
		//if(customer.getHowToGetQR().equals("email")){
		//	return sendCouponMessageByEmail(customer, event);
		//}
		boolean phone = false;
		boolean email = false;
		
		if(sendCouponMessageByEmail(customer, event)){
			email = true;
				if(!customer.getSentEmailEventList().contains(event.getId())){
					customer.addNotifiedByEmailEvent(event.getId());
					DatabaseLogic.createCustomer(customer);
					if(customer.getCell_phone() == null){
						QR qr = new QR(event.getId(),customer.getId());
						DatabaseLogic.createQR(qr);
					}
				}
		}
		if(customer.getCell_phone() != null){
			if(!customer.getSentSMSEventList().contains(event.getId())){
				if(sendCouponMessageByPhone(customer, event)){
					phone = true;
					customer.addNotifiedBySMSEvent(event.getId());
					DatabaseLogic.createCustomer(customer);
					QR qr = DatabaseLogic.getQR(event.getId(), customer.getId());
					if(qr != null){
						qr.setUrl(endurl);
						DatabaseLogic.createQR(qr);
					}
					else {
						qr = new QR(event.getId(),customer.getId(),endurl);
						DatabaseLogic.createQR(qr);
					}
				}
			}
			else{
				phone = true;
			}
		}
		else { 
			phone = true;
		}
		
		return (phone && email);
			
	}
	
	
	/**
	 * sending email to the reception with 
	 * link to the QR code that contains the URL
	 * @param user the reception of the Email
	 * @param event the relevant event
	 */
	private static boolean sendCouponMessageByEmail(Customer customer, Event event){
		
		
		

		try {
			
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

			/*** Members ***/
	        
	        String htmlBody;        
//	        byte[] attachmentData;  
			Message msg = new MimeMessage(session);
	        Multipart mp = new MimeMultipart();
	
	        /*** HTML Body of the message ***/

	        htmlBody = GenerateCouponEmailInvetation(customer, event);
	        
	        if (htmlBody == null){
	        	return false;
	        }
	        
	        //add the html body to the message
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(htmlBody, "text/html");
	        mp.addBodyPart(htmlPart);
            msg.setContent(mp);
	        
            /*** Attachment of the message ***/
            
//	        //TODO: add attachment if necessary
//	        attachmentData = null;
//	
//	        //add the attachment to the message
//        
//	        if (attachmentData != null) {
//	        	MimeBodyPart attachment = new MimeBodyPart();
//	        	attachment.setFileName("manual.pdf");
//	        	attachment.setContent(attachmentData, "application/pdf");
//	        	mp.addBodyPart(attachment);
//	        }
        
	        /*** Edit message details & send ***/
	        String email;
            if(customer.getOther_email() != null){
            	email = customer.getOther_email();
            }
            else {
            	email = customer.getEmail();
            }
			//set sender
            msg.setFrom(new InternetAddress(System.getProperty("email"),"Check Me In"));
			//set reception
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, customer.getFirstName() + ' ' + customer.getLastName()));

            //Set subject + encode to email format (Mime format)
            //String subject = new String (MimeUtility.encodeText("קופון ל" + event.getName() ,"UTF-8","Q"));
            String subject = new String (MimeUtility.encodeText(event.getName() + " Coupon"));
            
            msg.setSubject(subject);
            
            Transport.send(msg);
            
            return true;

        } catch (AddressException e) {
        	// TODO: handle exception
        	return false;
        } catch (MessagingException e) {
        	// TODO: handle exception
        	return false;
        } catch (Exception e) {
			// TODO: handle exception
        	return false;
		}
	}
	
	private static boolean sendCouponMessageByPhone(Customer customer, Event event){
		try {
			
			Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

			/*** Members ***/
	        
	        String htmlBody;        
//	        byte[] attachmentData;  
			Message msg = new MimeMessage(session);
	        Multipart mp = new MimeMultipart();
	
	        /*** HTML Body of the message ***/

	       // htmlBody = StringUtils.urlEncode(GenerateCouponPhoneInvetation(customer, event));
	        htmlBody = GenerateCouponPhoneInvetation(customer, event);
	      
	        //add the html body to the message
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        htmlPart.setContent(htmlBody, "text/html");
	        mp.addBodyPart(htmlPart);
            msg.setContent(mp);
	        
            /*** Attachment of the message ***/
            
//	        //TODO: add attachment if necessary
//	        attachmentData = null;
//	
//	        //add the attachment to the message
//        
//	        if (attachmentData != null) {
//	        	MimeBodyPart attachment = new MimeBodyPart();
//	        	attachment.setFileName("manual.pdf");
//	        	attachment.setContent(attachmentData, "application/pdf");
//	        	mp.addBodyPart(attachment);
//	        }
        
	        /*** Edit message details & send ***/
	        
			//set sender
            msg.setFrom(new InternetAddress(System.getProperty("email"),"CheckMeIn"));
			//set reception
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(customer.getCell_phone()+"@smscenter.co.il"));
           // set subject
            msg.setSubject(System.getProperty("usernameforSMS")+":"+System.getProperty("passwordforSMS"));
            
            Transport.send(msg);
            
            return true;

        } catch (AddressException e) {
        	// TODO: handle exception
        	return false;
        } catch (MessagingException e) {
        	// TODO: handle exception
        	return false;
        } catch (Exception e) {
			// TODO: handle exception
        	return false;
		}
	}
	
	/**
	 * generating and message with a coupon for the event
	 * @param user the message reception
	 * @param event the relevant event
	 * @return message with a coupon for the event
	 */
	public static String GenerateCouponEmailInvetation(Customer customer, Event event){

		
		//Read Email Template once in a session
		if (QRemailTemplate == null){
			try {
				QRemailTemplate = readFile("emailTemplates/couponTemplateHeb.html");
			} catch (IOException e) {
				return null;
			}
		}
		
		
		
		String email = QRemailTemplate;
		
		//Replace parmeters in email template
		email = replaceParameter(email, "$TITLE", event.getName());
		email = replaceParameter(email, "$NAME", customer.getFirstName() + " " + customer.getLastName());
		email = replaceParameter(email, "$EVENT_NAME", event.getName());
		email = replaceParameter(email, "$EVENT_URL", event.getLink());
		email = replaceParameter(email, "$COUPON_URL", QRCode.generateQRCodeURL(customer.getId(), event.getId()));
		
		
		
		return email;
		
	}
	
	
	
	public static String GenerateCouponPhoneInvetation(Customer customer, Event event){
		//TODO: Get buisness name from DB
		String url = QRCode.generateQRCodeURL(customer.getId(), event.getId());
		
		
		
		String htmlContent;
		
		//English version
		//htmlContent = "Link to your coupon: " + url + " from Morty & Helen";
		
		
		htmlContent = "לינק לקופון: " + generateTinyURL(url) + " מצוות " + "Check-Me-In";
		
		return htmlContent; 
		
		//return "\"" + generateTinyURL(url) + "\""; 
		
	}
	
	private static String readFile( String file ) throws IOException {
		
			FileInputStream inStream = new FileInputStream(file);
			
			InputStreamReader inReader = new InputStreamReader(inStream, "UTF-8"); 
		
		    BufferedReader reader = new BufferedReader( inReader );
		    String line  = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    return stringBuilder.toString();
	}
	
	private static String replaceParameter(String email, String parameter, String text){
		return email.replace(parameter, text);		
	}
	
	private static String generateTinyURL(String url){
		
		StringBuilder result = new StringBuilder(); 
		
		try 
		{             
			
			URL tinyRequest = new URL("http://tinyurl.com/api-create.php?url=" + url);             
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(tinyRequest.openStream()));             
			
			String line;
			
			
			while ((line = reader.readLine()) != null) {                 
				result.append(line);
			}             
			
			reader.close();			
			
		} 
		catch (MalformedURLException e) {             
			return null;
		} 
		catch (IOException e) {
			return null;
		}
		
		endurl = result.toString().substring(19);
		return result.toString();
		
	}

}
