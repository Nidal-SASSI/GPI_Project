package in.co.fennel.project.model;
import  in.co.fennel.project.exception.ApplicationException;
import in.co.fennel.project.util.EmailMessage;
import in.co.fennel.project.util.EmailUtility;
public class CheckMail {
 
  public static void main(String[] args) {
    EmailMessage msg=new EmailMessage();
    msg.setTo("gharisazwaw96@gmail.com");
    msg.setMessage("ceci est un test mail");
    try {
      EmailUtility.sendMail(msg);
    } catch (ApplicationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
