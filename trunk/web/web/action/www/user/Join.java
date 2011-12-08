package web.action.www.user;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import ths.core.graphics.Captcha;
import ths.web.AbstractAction;

public class Join extends AbstractAction {
	
	@Override
	protected void service() throws Exception {
		setNoCache();
		
		Captcha captcha = new Captcha();
		HttpSession session = request.getSession();
    	String words = captcha.getRandString(4);
    	session.setAttribute("captcha_join", words);
    	
    	captcha.setImageSize(100, 40);
    	captcha.setDrawDisturbLine(false);
    	BufferedImage img = captcha.getImage(words);
    	
    	OutputStream os = response.getOutputStream();
    	ImageIO.write(img, "png", os);
    	os.close();
	}
}
