package com.plus.bysj.account.utils;

import lombok.extern.log4j.Log4j2;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.util.Properties;

/**
 * @ProjectName: account
 * @Package: com.plus.bysj.account.utils
 * @ClassName: MailUtils
 * @Author: rh
 * @Description:
 * @Date: 2021/4/23 18:03
 */
@Log4j2
public class MailUtils {
    //邮件服务器主机地址
    private static String HOST="smtp.163.com";
    //帐号
    private static String ACCOUNT = "account20210423@163.com";
    //密码
    private static String PASSWORD = "";
    //授权码
    private static String AUTH = "GNKQACDEQUEJAGKU";



    /**
     * @param toUser  发送邮件给谁
     * @param title   邮件的标题
     * @param emailMsg  邮件信息
     */
    public static void sendMail(String toUser,String title, String emailMsg)throws  MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        //设置发送的协议
        props.setProperty("mail.transport.protocol", "SMTP");

        //设置发送邮件的服务器
        props.setProperty("mail.host", HOST);
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true

        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //设置发送人的帐号和密码      帐号		   授权码
                return new PasswordAuthentication(ACCOUNT, AUTH);
            }
        };
        //会话
        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        //设置发送者
        message.setFrom(new InternetAddress(ACCOUNT));

        //设置发送方式与接收者
        message.setRecipient(RecipientType.TO, new InternetAddress(toUser));

        //设置邮件主题
        message.setSubject(title);
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        //设置邮件内容
        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
        log.info("邮件发送成功");
    }


    public static void main(String[] args) throws AddressException, MessagingException {
		//MailUtils.sendMail("6357@foxmail.com", "祝福邮件2", "哈哈哈");
        MailUtils.sendMail("635760163@qq.com", "祝福邮", "这是正文");
        //System.out.println(2.0*(0.5));
    }
}
