package ru.ecom4u.web.controllers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.ecom4u.web.controllers.util.dto.FileUploadForm;
import ru.ecom4u.web.domain.db.entities.Picture;
import ru.ecom4u.web.domain.db.services.PictureService;

@Controller
@RequestMapping(value = "util/fileupload")
public class FileUploadController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(method = RequestMethod.GET)
    public String getUploadForm(Model model) {
        model.addAttribute(new FileUploadForm());
        return "util/fileupload";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String uploadFile(FileUploadForm fileUploadForm, BindingResult result) {
        for (CommonsMultipartFile commonsMultipartFile : fileUploadForm.getFiles()) {
            String fn = commonsMultipartFile.getOriginalFilename();
            Picture picture = pictureService.savePicture(fn, commonsMultipartFile);

            System.err.println("-------------------------------------------");
            System.err.println("Test upload: " + fn);
            System.err.println("New name: " + picture.getUrlName());
            System.err.println("-------------------------------------------");
        }

        return "util/fileupload";
    }
}
