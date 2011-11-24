package ths.service.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailService 
{
	private Properties props;
    protected BodyPart bodyPart;
    protected Multipart multipart = new MimeMultipart("related");   
    protected MimeMessage message;   
    protected Session session;
    
    protected InternetAddress fromAddress;
    protected InternetAddress toAddress;
    
	public MailService(String host, String username, String password)
	{
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		
		SMTPAuth auth = new SMTPAuth(username, password);
		session = Session.getDefaultInstance(props, auth);
        message = new MimeMessage(session);
        
	}
	
	public void setPriority(String level) throws MessagingException {
		message.setHeader("X-Priority", level);
	}
	
    public void setSendDate(Date date) throws MessagingException {
        message.setSentDate(date);
    }	
    public void setSubject(String subject) throws MessagingException { 
        message.setSubject(subject);
    }
    
    public void setBody(String content) throws MessagingException {
    	bodyPart = new MimeBodyPart();
        bodyPart.setContent(content, "text/html;charset=UTF-8");
        multipart.addBodyPart(bodyPart);
    }
    
    public void setAttachments(String file) throws MessagingException {   
        bodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);
        bodyPart.setDataHandler(new DataHandler(source));
        int index = file.lastIndexOf(File.separator);
        String realName = file.substring(index + 1);
        bodyPart.setFileName(realName);
        multipart.addBodyPart(bodyPart);
    }
    
    public void setFrom(String mailAddress) throws MessagingException {   
    	fromAddress = new InternetAddress(mailAddress);
        message.setFrom(fromAddress);
    }
    
    public void setTo(String[] mailAddresses, String mailType) throws Exception {
        for (int i = 0; i < mailAddresses.length; i++) {
        	toAddress = new InternetAddress(mailAddresses[i]);
            if (mailType.equalsIgnoreCase("to")) {
                message.addRecipient(Message.RecipientType.TO, toAddress);
            } else if (mailType.equalsIgnoreCase("cc")) {
                message.addRecipient(Message.RecipientType.CC, toAddress);
            } else if (mailType.equalsIgnoreCase("bcc")) {
                message.addRecipient(Message.RecipientType.BCC, toAddress);
            } else {
                throw new Exception("Unknown mailType: " + mailType + "!");
            }
        }
    }
    
    public void sendMail() throws MessagingException, SendFailedException {   
        if (toAddress == null) {
            throw new MessagingException("请你必须你填写收件人地址！");
        }
        
        message.setContent(multipart);   
        Transport.send(message);
    }
    
    public static void main(String args[]) {   
        String host = "smtp.163.com";   
        String user = "programlife555";  
        String password = "tian4461430";
        String[] toAddress = {"tianhaisheng555@163.com"};   
  
        MailService mail = new MailService(host, user, password);
        try {
        	mail.setPriority("1"); 	//1 高优先级. 有些邮件程序称之为紧急
            						//2 也是高优先级
						            //3 普通优先级
						            //4 低优先级
						            //5 最低的优先级
        	
        	mail.setSubject("邮件发送测试");
        	mail.setSendDate(new Date());
            String content = "<H1>你好,神州商城</H1><br /><center><img border=\"1\" src=\"http://www.99buy.com.cn/images/upload/Image/desc_1302855010%282%29.jpg\"></center>"; 
            mail.setBody(content);
            mail.setAttachments("C:\\code.txt");
            mail.setFrom("programlife555@163.com");
            mail.setTo(toAddress, "to");
            //mail.setMailTo(toAddress, "cc");//设置抄送给...
     
            System.out.println("正在发送邮件，请稍候.......");
            mail.sendMail();
            System.out.println("恭喜你，邮件已经成功发送!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class SMTPAuth extends Authenticator
{
	String username;
	String password;
	
	public SMTPAuth(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
