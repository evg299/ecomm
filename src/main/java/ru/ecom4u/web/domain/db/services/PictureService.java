package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import ru.ecom4u.web.domain.db.entities.Picture;
import ru.ecom4u.web.services.FileStorageService;
import ru.ecom4u.web.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 04.02.14.
 */
@Service
public class PictureService extends AbstractService {

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

    public List<Picture> getAll(){
        Session session = getCurrentSession();
        return session.createCriteria(Picture.class).list();
    }

    @Transactional(rollbackFor = Throwable.class)
    public Picture savePicture(String title, CommonsMultipartFile commonsMultipartFile) {
        Picture picture = new Picture();
        picture.setTitle(title);

        String fn = commonsMultipartFile.getOriginalFilename();
        String name = fileStorageService.generateName();
        String ext = fileStorageService.extractExtention(fn);
        picture.setUrlName(name);

        try {
            InputStream is = commonsMultipartFile.getInputStream();
            String origName = name + ImageUtil.suffixO + ext;
            fileStorageService.saveFileWithName(is, origName);
            is.close();

            File origFile = fileStorageService.getFile(origName);
            BufferedImage image = ImageIO.read(origFile);

            String nameBig = name + ImageUtil.suffixB + ext;
            String nameMedium = name + ImageUtil.suffixM + ext;
            String nameSmall = name + ImageUtil.suffixS + ext;

            picture.setExtention(ext);

            if (saveScales) {
                BufferedImage bigImage = ImageUtil.createResizedCopyScaled(image, bigWidth, bigHeight, true);
                ImageIO.write(bigImage, ext, fileStorageService.createFile(nameBig));

                BufferedImage mediumImage = ImageUtil.createResizedCopyScaled(image, mediumWidth, mediumHeight, true);
                ImageIO.write(mediumImage, ext, fileStorageService.createFile(nameMedium));

                BufferedImage smallImage = ImageUtil.createResizedCopyScaled(image, smallWidth, smallHeight, true);
                ImageIO.write(smallImage, ext, fileStorageService.createFile(nameSmall));
            } else {
                BufferedImage bigImage = ImageUtil.createResizedCopy(image, bigWidth, bigHeight, true);
                ImageIO.write(bigImage, ext, fileStorageService.createFile(nameBig));

                BufferedImage mediumImage = ImageUtil.createResizedCopy(image, mediumWidth, mediumHeight, true);
                ImageIO.write(mediumImage, ext, fileStorageService.createFile(nameMedium));

                BufferedImage smallImage = ImageUtil.createResizedCopy(image, smallWidth, smallHeight, true);
                ImageIO.write(smallImage, ext, fileStorageService.createFile(nameSmall));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Session session = getCurrentSession();
        session.save(picture);
        return picture;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void deletePicture(Picture picture) {
        String name = picture.getUrlName();
        String ext = picture.getExtention();

        String nameOrig = name + ImageUtil.suffixB + ext;
        String nameBig = name + ImageUtil.suffixB + ext;
        String nameMedium = name + ImageUtil.suffixM + ext;
        String nameSmall = name + ImageUtil.suffixS + ext;

        File fileOrig = fileStorageService.getFile(nameOrig);
        File fileBig = fileStorageService.getFile(nameBig);
        File fileMedium = fileStorageService.getFile(nameMedium);
        File fileSmall = fileStorageService.getFile(nameSmall);

        if (null != fileOrig) fileOrig.delete();
        if (null != fileBig) fileBig.delete();
        if (null != fileMedium) fileMedium.delete();
        if (null != fileSmall) fileSmall.delete();

        Session session = getCurrentSession();
        session.delete(picture);
    }
}
