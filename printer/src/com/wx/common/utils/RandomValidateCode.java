package com.wx.common.utils;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 获取验证码图片。在找回密码中使用。
 * @author JZY
 *
 */
public class RandomValidateCode {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RandomValidateCode.class);

    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
    private Random random = new Random();
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    
    private int width = 80;//图片宽
    private int height = 26;//图片高
    private int lineSize = 40;//干扰线数量
    private int stringNum = 4;//随机产生字符数量
    /*
     * 获得字体
     */
    private Font getFont(){
		if (logger.isDebugEnabled()) {
			logger.debug("getFont() - start"); //$NON-NLS-1$
		}

		Font returnFont = new Font("Fixedsys", Font.CENTER_BASELINE, 18);
		if (logger.isDebugEnabled()) {
			logger.debug("getFont() - end"); //$NON-NLS-1$
		}
        return returnFont;
    }
    /*
     * 获得颜色
     */
    private Color getRandColor(int fc,int bc){
		if (logger.isDebugEnabled()) {
			logger.debug("getRandColor(int, int) - start"); //$NON-NLS-1$
		}

        if(fc > 255)
            fc = 255;
        if(bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc-fc-16);
        int g = fc + random.nextInt(bc-fc-14);
        int b = fc + random.nextInt(bc-fc-18);
		Color returnColor = new Color(r, g, b);
		if (logger.isDebugEnabled()) {
			logger.debug("getRandColor(int, int) - end"); //$NON-NLS-1$
		}
        return returnColor;
    }
    /**
     * 生成随机图片
     */
    public void getRandcode(HttpServletRequest request,
            HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("getRandcode(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}

        HttpSession session = request.getSession();
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();//产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.ROMAN_BASELINE,18));
        g.setColor(getRandColor(110, 133));
        //绘制干扰线
        for(int i=0;i<=lineSize;i++){
            drowLine(g);
        }
        //绘制随机字符
        String randomString = "";
        for(int i=1;i<=stringNum;i++){
            randomString=drowString(g,randomString,i);
        }
        session.removeAttribute(RANDOMCODEKEY);
        session.setAttribute(RANDOMCODEKEY, randomString);
        System.out.println(randomString);
        g.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());//将内存中的图片通过流动形式输出到客户端
        } catch (Exception e) {
			logger.error("getRandcode(HttpServletRequest, HttpServletResponse)", e); //$NON-NLS-1$

            e.printStackTrace();
        }

		if (logger.isDebugEnabled()) {
			logger.debug("getRandcode(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
    }
    /*
     * 绘制字符串
     */
    private String drowString(Graphics g,String randomString,int i){
		if (logger.isDebugEnabled()) {
			logger.debug("drowString(Graphics, String, int) - start"); //$NON-NLS-1$
		}

        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
        String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString +=rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13*i, 16);

		if (logger.isDebugEnabled()) {
			logger.debug("drowString(Graphics, String, int) - end"); //$NON-NLS-1$
		}
        return randomString;
    }
    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g){
		if (logger.isDebugEnabled()) {
			logger.debug("drowLine(Graphics) - start"); //$NON-NLS-1$
		}

        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x+xl, y+yl);

		if (logger.isDebugEnabled()) {
			logger.debug("drowLine(Graphics) - end"); //$NON-NLS-1$
		}
    }
    /*
     * 获取随机的字符
     */
    public String getRandomString(int num){
		if (logger.isDebugEnabled()) {
			logger.debug("getRandomString(int) - start"); //$NON-NLS-1$
		}

		String returnString = String.valueOf(randString.charAt(num));
		if (logger.isDebugEnabled()) {
			logger.debug("getRandomString(int) - end"); //$NON-NLS-1$
		}
        return returnString;
    }
}
