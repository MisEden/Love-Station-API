package org.eden.lovestation.util.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;
    @Value("${email.reset.url}")
    private String passwordResetUrl;
    @Value("${email.admin.url}")
    private String passwordAdminUrl;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String to, String uid) {

        try {
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom(adminEmail);
            passwordResetEmail.setTo(to);
            passwordResetEmail.setSubject("【伊甸愛心棧】忘記密碼");
            passwordResetEmail.setText(String.format("先生/小姐 您好：\n\n我們收到了您申請重置密碼的請求，請根據以下步驟進行密碼的重設\n\n1. 複製您的驗證碼：\n%s\n\n2. 請點擊此連結重置密碼並輸入驗證碼：\n%s\n\n\n若您未提出此項申請，請忽略這封信件。\n祝您使用愉快\n\n此信件為系統自動發出，請勿直接回覆。",
                uid, passwordResetUrl));

            System.out.println("[Mail Info] Email:"+ to +"\nCode:" + uid);

            javaMailSender.send(passwordResetEmail);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void sendAdminRegisterEmail(String to, int type, String name, String account, String authority, String reason){
        String mailContent_hello = name + " 您好：\n\n";

        String mailContent_result_pass = "系統管理員已經通過您管理者帳號(" + account + ")的註冊申請了。\n\n";
        String mailContent_result_passAndPromotion = "系統管理員已經通過您管理者帳號(" + account + ")的註冊申請了。\n並且將您的權限提升至「" + authority + "」了。\n\n";
        String mailContent_result_fail = "系統管理員拒絕了您管理者帳號(" + account + ")的註冊申請，\n因為：" + reason + "。\n\n";

        String mailContent_nextStep_pass = "您現在已經可以使用註冊時輸入的帳號與密碼進行登入，並檢視/操作後台資訊。\n\n";
        String mailContent_nextStep_passAndPromotion = "您現在已經可以使用註冊時輸入的帳號與密碼進行登入，並檢視/操作後台資訊。\n\n";
        String mailContent_nextStep_fail = "倘若您仍需要管理者權限，請再次提出申請。\n\n";

        String mailContent_bye = "\n若您未提出此項申請，請忽略這封信件。\n祝您使用愉快\n\n此信件為系統自動發出，請勿直接回覆。";

        try {
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom(adminEmail);
            passwordResetEmail.setTo(to);
            passwordResetEmail.setSubject("【伊甸愛心棧】管理者帳戶註冊");

            if(type == 1){
                passwordResetEmail.setText(mailContent_hello + mailContent_result_pass + mailContent_nextStep_pass + mailContent_bye);
            }else if(type == 2){
                passwordResetEmail.setText(mailContent_hello + mailContent_result_passAndPromotion + mailContent_nextStep_passAndPromotion + mailContent_bye);
            }else{
                passwordResetEmail.setText(mailContent_hello + mailContent_result_fail + mailContent_nextStep_fail + mailContent_bye);
            }

            System.out.println("[Mail Info] Email - Admin's Account:"+ to +"\nType:" + type);

            javaMailSender.send(passwordResetEmail);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void sendAdminRegisterByAdminEmail(String to, String name, String account, String password){
        String mailContent_hello = name + " 您好：\n\n";

        String mailContent_result = "系統管理員已經為您建立了一個管理者權限的帳號。\n\n";

        String mailContent_nextStep = "您現在可以使用下列帳號與密碼登入管理者後台，並檢視/操作後台資訊。\n";
        String mailContent_info = "帳號：" + account + "\n密碼：" + password + "\n網址：" + passwordAdminUrl + "\n(請記得於網頁右上角選項中進行密碼變更)\n\n";

        String mailContent_bye = "\n若您未提出此項申請，請忽略這封信件。\n祝您使用愉快\n\n此信件為系統自動發出，請勿直接回覆。";

        try {
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom(adminEmail);
            passwordResetEmail.setTo(to);
            passwordResetEmail.setSubject("【伊甸愛心棧】管理者帳戶註冊");
            passwordResetEmail.setText(mailContent_hello + mailContent_result + mailContent_nextStep + mailContent_info + mailContent_bye);

            System.out.println("[Mail Info] Email - Admin's Account:"+ to );

            javaMailSender.send(passwordResetEmail);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
