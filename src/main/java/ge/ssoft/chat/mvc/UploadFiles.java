package ge.ssoft.chat.mvc;


import ge.ssoft.chat.resources.UploadResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * Created by zviad on 10/1/16.
 */
@RestController
@RequestMapping(value = "upload-file")
public class UploadFiles {

    private static final int IMG_WIDTH = 800;
    private static final int IMG_HEIGHT = 800;
//    public static String pathToFiles = "/home/picco/toy-images";
    public static String pathToFiles = "/Users/zviad/chat-images";




    @RequestMapping(method = RequestMethod.POST)
    public   @ResponseBody UploadResult uploadFile(@RequestParam("file") MultipartFile file
    ) {
        String name = "";
        if (!file.isEmpty()) {
            try {
                System.out.println("updload=file=params====");
                System.out.println(file.getOriginalFilename());
                System.out.println(file.getName());
                System.out.println(file.getSize());
                System.out.println(file.getInputStream());
                System.out.println("=======================");
                name = UUID.randomUUID().toString();
                name = name + file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                File fileForSave = new File(pathToFiles + "/" + name);
                fileForSave.getParentFile().mkdirs();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(fileForSave));
                stream.write(bytes);
                stream.close();

                BufferedImage originalImage = ImageIO.read(new File(pathToFiles + "/" + name));
                BufferedImage resizedImage;
                if(originalImage.getHeight()>originalImage.getWidth()){
                    resizedImage = resizeImageByWidth(originalImage);
                }else{
                    resizedImage = resizeImageByHeight(originalImage);
                }

                String ext= FilenameUtils.getExtension(name);
                if(ext.equals("png")){
                ImageIO.write(resizedImage, "png", new File(pathToFiles + "/smol_" + name));
                }else{
                    ImageIO.write(resizedImage, "jpeg", new File(pathToFiles + "/smol_" + name));
                }

                return new UploadResult("0", "", name);
            } catch (Exception e) {
                e.printStackTrace();
                return new UploadResult("1", "You failed to upload", null);
            }
        } else {
            return new UploadResult("1", "You failed to upload", null);

        }

    }

    @RequestMapping(value = "get-file/{file_name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        File file = new File(pathToFiles + "/" + fileName);
        try {
            response.setContentLength((int) file.length());
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("image/jpeg");
            InputStream fis = new FileInputStream(file);
            org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
















    private static BufferedImage resizeImageByWidth(BufferedImage originalImage) {
        if(originalImage.getWidth()>IMG_WIDTH) {
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            int IMGHEIGHT = (originalImage.getHeight() * IMG_WIDTH) / originalImage.getWidth();
            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMGHEIGHT, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMGHEIGHT, null);
            g.dispose();
            return resizedImage;
        }else{
            return  originalImage;
        }

    }



    private static BufferedImage resizeImageByHeight(BufferedImage originalImage) {
        if(originalImage.getHeight()>IMG_HEIGHT){
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        int IMGWIDTH = (originalImage.getWidth() * IMG_HEIGHT) / originalImage.getHeight();
        BufferedImage resizedImage = new BufferedImage(IMGWIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMGWIDTH, IMG_HEIGHT, null);
        g.dispose();
        return resizedImage;
        }else {
            return originalImage;
        }
    }






}
