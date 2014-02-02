package ru.ecom4u.web.controllers.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ru.ecom4u.web.controllers.util.dto.FileUploadForm;
import ru.ecom4u.web.services.FileStorageService;
import ru.ecom4u.web.utils.ImageUtil;

@Controller
@RequestMapping(value = "util/fileupload")
public class FileUploadController {

	@Autowired
	private FileStorageService fileStorageService;

    @Value("#{properties['picture.save_scales']}")
    private boolean saveScales;
    @Value("#{properties['picture.big.width']}")
    private int bigWidth;
    @Value("#{properties['picture.big.height']}")
    private int bigHeight;
    @Value("#{properties['picture.medium.width']}")
    private int mediumWidth;
    @Value("#{properties['picture.medium.height']}")
    private int mediumHeight;
    @Value("#{properties['picture.small.width']}")
    private int smallWidth;
    @Value("#{properties['picture.small.height']}")
    private int smallHeight;

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadForm(Model model) {
		model.addAttribute(new FileUploadForm());
		return "util/fileupload";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String uploadFile(FileUploadForm fileUploadForm, BindingResult result) {

		for (CommonsMultipartFile commonsMultipartFile : fileUploadForm.getFiles()) {
			String fn = commonsMultipartFile.getOriginalFilename();

			String name = fileStorageService.generateName();
			String extention = fileStorageService.extractExtention(fn);

			try {
				InputStream is = commonsMultipartFile.getInputStream();
				String origName = name + "-o." + extention;
				fileStorageService.saveFileWithName(is, origName);
				is.close();

				File origFile = fileStorageService.getFile(origName);
				BufferedImage image = ImageIO.read(origFile);
                if(saveScales){
                    BufferedImage bigImage = ImageUtil.createResizedCopyScaled(image, bigWidth, bigHeight, true);
                    ImageIO.write(bigImage, extention, fileStorageService.createFile(name + "-b." + extention));

                    BufferedImage mediumImage = ImageUtil.createResizedCopyScaled(image, mediumWidth, mediumHeight, true);
                    ImageIO.write(mediumImage, extention, fileStorageService.createFile(name + "-m." + extention));

                    BufferedImage smallImage = ImageUtil.createResizedCopyScaled(image, smallWidth, smallHeight, true);
                    ImageIO.write(smallImage, extention, fileStorageService.createFile(name + "-s." + extention));
                } else {
                    BufferedImage bigImage = ImageUtil.createResizedCopy(image, bigWidth, bigHeight, true);
                    ImageIO.write(bigImage, extention, fileStorageService.createFile(name + "-b." + extention));

                    BufferedImage mediumImage = ImageUtil.createResizedCopy(image, mediumWidth, mediumHeight, true);
                    ImageIO.write(mediumImage, extention, fileStorageService.createFile(name + "-m." + extention));

                    BufferedImage smallImage = ImageUtil.createResizedCopy(image, smallWidth, smallHeight, true);
                    ImageIO.write(smallImage, extention, fileStorageService.createFile(name + "-s." + extention));
                }


			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(fileStorageService.getFile(name));

			System.err.println("-------------------------------------------");
			System.err.println("Test upload: " + fn);
			System.err.println("-------------------------------------------");
		}

		return "util/fileupload";
	}
}
