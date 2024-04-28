package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.config.FileStorageConfig;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.util.FilePaths;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

@Service
public class FIleService {

    @Autowired
    private FileStorageConfig fileStorageConfig;

    private FileOnServerService fileOnServerService;

    FilePaths filePaths;

    //private final Path absolutePath = Paths.get(fileStorageConfig.getPath());
    private final Path absolutePath = Paths.get("C:\\Users\\basce\\Desktop\\pulsus-project\\frontend\\pulsus-frontend\\public\\uploads");

    public String defaultProfilePic() {
        String path = getAbsolutePath(filePaths.DEFAULT_PROFILE_PICTURE_M);
        return path;
    }

    public String profilePic(Long userId) {
        String path = getAbsolutePath("users/" + userId + "/pictures/" + userId + ".jpg");
        File file = new File(path);

        if(!file.exists()) {
            path = getAbsolutePath(filePaths.DEFAULT_PROFILE_PICTURE_M);
        }

        return path;
    }

    public void CreateUserDirs(Long userId) {
        String userPath = createPath(String.format("users/%s", userId));
        String picturesPath = createPath(String.format("users/%s/pictures", userId));
        String activitiesPath = createPath(String.format("users/%s/workouts", userId));

        CreateDir(userPath);
        CreateDir(picturesPath);
        CreateDir(activitiesPath);
    }

    public void AddUserProfilePic(Long userId, MultipartFile file) throws IOException {
        String directoryPath = createPath(String.format("users/%s/pictures", userId));
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        String filePath = directoryPath + "/" + userId + ".jpg";

        File newFile = new File(filePath);
        file.transferTo(newFile);
    }

    public void AddUserPic(Long userId) {

    }

    private String getAbsolutePath(String relative) {
        Path relativePath = Paths.get(relative);
        Path path = absolutePath.resolve(relativePath);
        String finalPath = path.toString().replace('\\', '/');
        return finalPath;
    }

    private String normalizePicExtension(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        return null;
    }

    private void addFileInfoToDB() {

    }

    private String createPath(String relativePath) {
        Path relative = Paths.get(relativePath);
        Path path = absolutePath.resolve(relative);
        String finalPath = path.toString().replace('\\', '/');

        return finalPath;
    }

    private void CreateDir(String path) {
        System.out.println(path);
        File directory = new File(path);

        if (!directory.exists()) {
            boolean success = directory.mkdirs();

            if (success) {
                System.out.println("Папка создана успешно");
            } else {
                System.out.println("Не удалось создать папку");
                throw new InternalServerException("Internal error");
            }
        } else {
            System.out.println("Папка уже существует");
        }

    }


}
