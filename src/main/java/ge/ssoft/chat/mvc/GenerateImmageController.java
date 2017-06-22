package ge.ssoft.chat.mvc;

import ge.ssoft.chat.core.model.GenerateImages;
import ge.ssoft.chat.core.repositories.GenerateImagesRepo;
import ge.ssoft.chat.exceptions.ReferencedObjectExists;
import ge.ssoft.chat.exceptions.SendConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Created by zviad on 6/22/17.
 * human check image generator
 */
@RestController
@RequestMapping("files")
public class GenerateImmageController {


    public static final int captcha_with=138;
    private int captcha_height=25;


    GenerateImagesRepo generateImagesRepo;

    @Autowired
    public void setGenerateImagesRepo(GenerateImagesRepo generateImagesRepo) {
        this.generateImagesRepo = generateImagesRepo;
    }

    @RequestMapping(value = "captcha/{imageName:.+}",method = RequestMethod.GET)
    public void generateCaptcha(@PathVariable("imageName") String imageName , HttpServletResponse response){
        try {
//        response.setContentLength((int) file.length());
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("image/jpeg");
        BufferedImage image=generateImmageFromString(imageName, generateRandomText(6));
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    public BufferedImage generateImmageFromString(String fileName, String ch){
        if(fileName==null || fileName.length()<20){
            throw  new SendConflictException("invalid file name");
        }
        GenerateImages generateImages=generateImagesRepo.findOne(fileName);
        if(generateImages!=null){
            throw new ReferencedObjectExists("File for that name already generated");
        }
        generateImages=new GenerateImages();
        generateImages.setImageName(fileName);
        generateImages.setImageText(ch);
        generateImages.setCreate_Date(new Timestamp(Calendar.getInstance().getTime().getTime()));
        generateImagesRepo.saveAndFlush(generateImages);
        BufferedImage image = new BufferedImage(captcha_with, captcha_height, BufferedImage.TYPE_INT_RGB);
        Color[] color = { Color.RED, Color.BLUE,
                new Color(0.6662f, 0.4569f, 0.3232f), Color.BLACK,
                Color.LIGHT_GRAY, Color.YELLOW, Color.LIGHT_GRAY, Color.cyan,
                Color.GREEN, Color.black, Color.DARK_GRAY, Color.MAGENTA };
        Random r = new Random();
        int f = (Math.abs(r.nextInt())%2);
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setBackground(Color.RED);
        graphics2D.fillRect(0,0,captcha_with,captcha_height);
        Color c = new Color(0.662f, 0.469f, 0.232f);
        GradientPaint gp = new GradientPaint(30, 30, color[2 << f], 15, 25,
                color[3 << f], true);
        graphics2D.setPaint(gp);
        Font font=new Font("Verdana", Font.CENTER_BASELINE , 20);
        graphics2D.setFont(font);
        graphics2D.drawString(ch,5,20);
        graphics2D.dispose();
        //write to file
        return image;
    }


    private   SecureRandom random = new SecureRandom();

    public String generateRandomText(int lengt){
        return generateRandomText("1234567890QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm",lengt);
    }

    public   String generateRandomText(String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }


    @RequestMapping( value = "filename")
    public String getUId(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }


}
