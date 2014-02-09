package ru.ecom4u.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ecom4u.web.services.FileStorageService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Evgeny(e299792459@gmail.com) on 02.02.14.
 */

@Controller
@RequestMapping(value = "filestorage")
public class FilestorageController {
    private static final int BUFFER_SIZE = 4096;

    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "download/{filename:.+}")
    public void downloadFile(@PathVariable(value = "filename") String filename, HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = request.getServletContext();
        File file = fileStorageService.getFile(filename);

        if (null != file) {
            String mimeType = context.getMimeType(file.getAbsolutePath());
            if (null == mimeType) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setContentLength((int) file.length());

            try {
                OutputStream outStream = response.getOutputStream();
                FileInputStream inputStream = new FileInputStream(file);

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                // write bytes read from the input stream into the output stream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
